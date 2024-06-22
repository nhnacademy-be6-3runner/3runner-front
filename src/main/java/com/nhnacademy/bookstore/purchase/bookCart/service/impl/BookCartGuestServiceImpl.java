package com.nhnacademy.bookstore.purchase.bookCart.service.impl;

import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.entity.cart.Cart;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRepository;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartGuestService;
import com.nhnacademy.bookstore.purchase.cart.exception.CartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.cart.repository.CartRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class BookCartGuestServiceImpl implements BookCartGuestService {
    private final BookCartRepository bookCartRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    @Override
    public Long createBookCart(Long bookId, Long cartId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookDoesNotExistException(bookId + "가 존재하지 않습니다"));

        Cart cart;

        if (Objects.isNull(cartId)) {
            cart = new Cart();
            cartRepository.save(cart);
        } else {
            cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new CartDoesNotExistException(cartId + "가 존재하지 않습니다"));
        }

        BookCart bookCart = new BookCart(quantity, ZonedDateTime.now(), book, cart);
        bookCartRepository.save(bookCart);

        return bookCart.getId();
    }

    @Override
    public void removeBookCart(Long bookId, Long cartId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookDoesNotExistException(bookId + "가 존재하지 않습니다"));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartDoesNotExistException(cartId + "가 존재하지 않습니다"));

        BookCart bookCart =bookCartRepository.findByBookAndCart(book, cart)
                .orElseThrow(() -> new BookCartDoesNotExistException("북카트 존재하지 않습니다."));

        bookCartRepository.delete(bookCart);
    }

    @Override
    public List<ReadBookCartGuestResponse> readAllBookCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartDoesNotExistException(cartId + "가 존재하지 않습니다"));

        return bookCartRepository.findAllByCart(cart)
                .stream()
                .map(bookCart -> ReadBookCartGuestResponse.builder()
                        .bookId(bookCart.getBook().getId())
                        .cartId(bookCart.getCart().getId())
                        .quantity(bookCart.getQuantity())
                        .createdAt(bookCart.getCreatedAt()).build())
                .toList();
    }
}
