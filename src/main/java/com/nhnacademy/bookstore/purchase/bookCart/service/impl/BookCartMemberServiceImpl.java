package com.nhnacademy.bookstore.purchase.bookCart.service.impl;

import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.DeleteBookCartRequest;
import com.nhnacademy.bookstore.purchase.bookCart.exception.NotExistsBookCartException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.NotExistsBookException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.NotExistsCartException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.entity.cart.Cart;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.ReadAllBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartBook;
import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRepository;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartMemberService;
import com.nhnacademy.bookstore.purchase.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BookCartMemberServiceImpl implements BookCartMemberService {
    private final BookCartRepository bookCartRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ReadAllBookCartMemberResponse> readAllCartMember(
            ReadAllBookCartMemberRequest readAllCartMemberRequest) {
        Cart cart = cartRepository.findByMemberId(readAllCartMemberRequest.userId()).orElse(null);

        if (Objects.isNull(cart)) {
            cart = new Cart(memberRepository.findById(readAllCartMemberRequest.userId()).orElseThrow(MemberNotExistsException::new));
        }

        List<BookCart> allBookCarts = bookCartRepository.findAllByCartId(Objects.requireNonNull(cart).getId());

        return allBookCarts.stream().map(bookCart -> ReadAllBookCartMemberResponse.builder()
            .quantity(bookCart.getQuantity())
            .book(ReadBookCartBook.builder()
                .title(bookCart.getBook().getTitle())
                .author(bookCart.getBook().getAuthor())
                .price(bookCart.getBook().getPrice())
                .description(bookCart.getBook().getDescription())
                .publisher(bookCart.getBook().getPublisher())
                .packing(bookCart.getBook().isPacking())
                .publishedDate(bookCart.getBook().getPublishedDate())
                .sellingPrice(bookCart.getBook().getSellingPrice())
                .quantity(bookCart.getBook().getQuantity())
                .build())
            .build()).collect(Collectors.toList());
    }

    @Override
    public Long createBookCartMember(CreateBookCartRequest createBookCartRequest) {
        Cart cart = cartRepository.findByMemberId(createBookCartRequest.userId()).orElse(null);

        if (Objects.isNull(cart)) {
            cart = new Cart(memberRepository.findById(createBookCartRequest.userId()).orElseThrow(
                MemberNotExistsException::new));
            cartRepository.save(cart);
        }
        BookCart bookCart = bookCartRepository.findBookCartByBookIdAndCartId(createBookCartRequest.bookId(), cart.getId()).orElse(null);

        if (!Objects.isNull(bookCart)) {
            bookCart.setQuantity(bookCart.getQuantity() + createBookCartRequest.quantity());
        } else {
            bookCart = new BookCart(
                createBookCartRequest.quantity(), bookRepository.findById(createBookCartRequest.bookId()).orElseThrow(NotExistsBookException::new), cart);
        }
        bookCartRepository.save(bookCart);



        return bookCart.getId();
    }

    @Override
    public Long updateBookCartMember(UpdateBookCartRequest updateBookCartRequest) {
        BookCart bookCart = bookCartRepository.findBookCartByBookIdAndCartId(updateBookCartRequest.bookId(), updateBookCartRequest.cartId())
            .orElseThrow(NotExistsBookCartException::new);
        Cart cart = cartRepository.findById(updateBookCartRequest.cartId()).orElseThrow(NotExistsCartException::new);
        bookCart.setQuantity(updateBookCartRequest.quantity());
        bookCart.setBook(
            bookRepository.findById(updateBookCartRequest.bookId()).orElseThrow(NotExistsBookException::new));
        bookCart.setCart(cart);

        if (bookCart.getQuantity() <= 0) {
            bookCartRepository.deleteByCart(cart);
        } else {
            bookCartRepository.save(bookCart);
        }

        return bookCart.getId();

    }

    @Override
    public Long deleteBookCartMember(DeleteBookCartRequest deleteBookCartMemberRequest) {
        bookCartRepository.delete(
            bookCartRepository.findById(deleteBookCartMemberRequest.bookCartId()).orElseThrow(NotExistsBookCartException::new));
        return deleteBookCartMemberRequest.cartId();
    }
}
