package com.nhnacademy.bookstore.purchase.bookCart.service.impl;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.*;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.NotExistsBookCartException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.NotExistsBookException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.NotExistsCartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nhnacademy.bookstore.purchase.cart.exception.CartDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.entity.cart.Cart;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
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
        List<ReadAllBookCartMemberResponse> responses = new ArrayList<>();

        Optional<Cart> cartOptional = cartRepository
                .findByMemberId(readAllCartMemberRequest.userId());

        Cart cart = null;

        if (cartOptional.isEmpty()) {
            cart = new Cart(memberRepository
                    .findById(readAllCartMemberRequest.userId())
                    .orElseThrow(MemberNotExistsException::new));
        } else {
            cart = cartOptional.get();
        }

        cartRepository.save(cart);


        List<BookCart> allBookCarts = bookCartRepository.findAllByCart(cart);

        for (BookCart bookCart : allBookCarts) {
            String url = "/img/no-image.png";

            if (bookCart.getBook().getBookImageList() != null && !bookCart.getBook().getBookImageList().isEmpty()) {
                url = bookCart.getBook().getBookImageList().getFirst().getTotalImage().getUrl();
            }

            responses.add(ReadAllBookCartMemberResponse.builder()
                    .quantity(bookCart.getQuantity())
                    .bookCartId(bookCart.getId())
                    .bookId(bookCart.getBook().getId())
                    .price(bookCart.getBook().getPrice())
                    .url(url)
                    .title(bookCart.getBook().getTitle())
                    .leftQuantity(bookCart.getBook().getQuantity())
                    .build());
        }
        return  responses;
    }

    @Override
    public Long createBookCartMember(CreateBookCartRequest createBookCartRequest) {
        Optional<Cart> optionalCart = cartRepository
                .findByMemberId(createBookCartRequest.userId());

        Cart cart;

        if (optionalCart.isEmpty()) {
            Member member = memberRepository
                    .findById(createBookCartRequest.userId())
                    .orElseThrow(MemberNotExistsException::new);

            cart = new Cart(member);

            cartRepository.save(cart);
        } else {

            cart = optionalCart.get();
        }

        Optional<BookCart> bookCartOptional = bookCartRepository
                .findBookCartByBookIdAndCartId(createBookCartRequest.bookId(), cart.getId());

        BookCart bookCart;

        if (bookCartOptional.isPresent()) {
            bookCart = bookCartOptional.get();
            bookCart.setQuantity(bookCart.getQuantity() + createBookCartRequest.quantity());

        } else {
            bookCart = new BookCart(
                    createBookCartRequest.quantity(),
                    bookRepository.findById(createBookCartRequest.bookId()).orElseThrow(NotExistsBookException::new),
                    cart
            );
        }

        bookCartRepository.save(bookCart);

        return bookCart.getId();
    }

    @Override
    public Long updateBookCartMember(UpdateBookCartRequest updateBookCartRequest, Long memberId) {
        BookCart bookCart = bookCartRepository
                .findBookCartByBookIdAndMemberId(
                        updateBookCartRequest.bookId(),
                        memberId)
                .orElseThrow(NotExistsBookCartException::new);

        Cart cart = cartRepository
                .findByMemberId(memberId)
                .orElseThrow(NotExistsCartException::new);

        bookCart.setQuantity(bookCart.getQuantity() + updateBookCartRequest.quantity());

        bookCart.setBook(
            bookRepository
                    .findById(updateBookCartRequest.bookId())
                    .orElseThrow(NotExistsBookException::new)
        );

        bookCart.setCart(cart);

        if (bookCart.getQuantity() <= 0) {
            bookCartRepository.deleteByCart(cart);
        } else {
            bookCartRepository.save(bookCart);
        }

        return bookCart.getId();
    }

    @Override
    public Long deleteBookCartMember(DeleteBookCartRequest deleteBookCartMemberRequest, Long memberId) {
        bookCartRepository.delete(
            bookCartRepository
                    .findById(deleteBookCartMemberRequest.bookCartId())
                    .orElseThrow(NotExistsBookCartException::new)
        );

        return memberId;
    }

    @Override
    public Long deleteAllBookCart(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CartDoesNotExistException("카트 없음"));

        bookCartRepository.deleteByCart(cart);

        return memberId;
    }
}