package com.nhnacademy.bookstore.purchase.bookCart.service.impl;

import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.entity.cart.Cart;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRedisRepository;
import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRepository;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartGuestService;
import com.nhnacademy.bookstore.purchase.cart.exception.CartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.cart.repository.CartRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

/**
 * 도서장바구니 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class BookCartGuestServiceImpl implements BookCartGuestService {
    private final BookCartRedisRepository bookCartRedisRepository;
    private final BookCartRepository bookCartRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    /**
     * 도서장바구니 추가.
     *
     * @param bookId 도서아이디
     * @param cartId 장바구니아이디
     * @param quantity 수량
     * @return bookCartId
     */
    @Override
    public Long createBookCart(Long bookId, Long cartId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookDoesNotExistException("도서가 존재하지 않습니다"));

        if (Objects.isNull(cartId)) {
            Cart newCart = new Cart();
            cartRepository.save(newCart);
            cartId = newCart.getId();
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartDoesNotExistException("카트가 존재하지 않습니다"));

        hasDataToLoad(cart.getId());

        BookCart bookCart = new BookCart(quantity, ZonedDateTime.now(), book, cart);

        bookCartRepository.save(bookCart);

        bookCartRedisRepository.create(
                cartId.toString(),
                bookCart.getId(),
                ReadBookCartGuestResponse.builder()
                        .bookCartId(bookCart.getId())
                        .bookId(bookCart.getBook().getId())
                        .cartId(bookCart.getCart().getId())
                        .quantity(bookCart.getQuantity())
                        .build()
        );

        return bookCart.getId();
    }

    /**
     * 도서장바구니 업데이트.
     *
     * @param bookId 도서아이디
     * @param cartId 장바구니아이디
     * @param quantity 수량
     * @return bookCartId
     */
    @Override
    public Long updateBookCart(Long bookId, Long cartId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookDoesNotExistException(bookId + "가 존재하지 않습니다"));

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartDoesNotExistException(cartId + "가 존재하지 않습니다"));

        BookCart bookCart = bookCartRepository.findByBookAndCart(book, cart)
                .orElseThrow(() -> new BookCartDoesNotExistException("북카트 존재하지 않습니다."));

        hasDataToLoad(cartId);

        int amount = bookCart.getQuantity() + quantity;
        if (amount > 0) {
            bookCart.setQuantity(amount);
            bookCartRepository.save(bookCart);
            bookCartRedisRepository.update(cartId.toString(), bookCart.getId(), amount);
        } else {
            bookCartRepository.delete(bookCart);
            bookCartRedisRepository.delete(cartId.toString(), bookCart.getId());
        }

        return bookCart.getId();
    }

    /**
     * 도서장바구니 목록 읽기.
     *
     * @param cartId 카트아이디
     * @return 도서장바구니 목록
     */
    @Override
    public List<ReadBookCartGuestResponse> readAllBookCart(Long cartId) {
        List<ReadBookCartGuestResponse> bookCartGuestResponseList = bookCartRedisRepository.readAllHashName(cartId.toString());
        if (!bookCartGuestResponseList.isEmpty()) {
            return  bookCartGuestResponseList;
        }

        return  hasDataToLoad(cartId);

    }

    /**
     * 레디스 데이터 업데이트 메서드.
     *
     * @param cartId 장바구니아이디
     * @return 도서장바구니 목록
     */
    private List<ReadBookCartGuestResponse> hasDataToLoad(Long cartId) {
        List<ReadBookCartGuestResponse> readBookCartGuestResponses = readAllFromDb(cartId);
        if (bookCartRedisRepository.isMiss(cartId.toString()) && !readBookCartGuestResponses.isEmpty()) {
            bookCartRedisRepository.loadData(readBookCartGuestResponses, cartId.toString());
        }
        return readBookCartGuestResponses;
    }

    /**
     * DB에서 데이터 목록 불러오기.
     *
     * @param cartId 카트 아이디
     * @return DB 도서 장바구니 목록
     */
    private List<ReadBookCartGuestResponse> readAllFromDb(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);

        return cart.map(value -> bookCartRepository.findAllByCart(value)
                .stream()
                .map(bookCart -> ReadBookCartGuestResponse.builder()
                        .bookCartId(bookCart.getId())
                        .bookId(bookCart.getBook().getId())
                        .cartId(bookCart.getCart().getId())
                        .quantity(bookCart.getQuantity())
                        .build())
                .toList()).orElseGet(List::of);
    }
}
