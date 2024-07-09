package com.nhnacademy.bookstore.book.bookLike.service.impl;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.bookLike.exception.BookLikeNotExistsException;
import com.nhnacademy.bookstore.book.bookLike.repository.BookLikeRepository;
import com.nhnacademy.bookstore.book.bookLike.service.BookLikeService;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookLike.BookLike;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookLikeServiceImpl implements BookLikeService {

    private final BookLikeRepository bookLikeRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Override
    public BookLike findById(Long bookLikeId) {
        Optional<BookLike> bookLike = bookLikeRepository.findById(bookLikeId);
        if (bookLike.isEmpty()) {
            throw new BookLikeNotExistsException("존재하지 않는 도서-좋아요입니다.");
        }
        return bookLike.get();
    }

    @Override
    public void createBookLike(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotExistsException::new);
        Book book = bookRepository.findById(bookId).orElseThrow();

        boolean alreadyLiked = bookLikeRepository.existsByMemberAndBook(member, book);
        if (alreadyLiked) {
            // 누른 적이 있으면 어떻게 ? delete?
        }

        BookLike bookLike = new BookLike();
        bookLike.setMember(member);
        bookLike.setBook(book);

        bookLikeRepository.save(bookLike);
    }

    @Override
    public void deleteBookLike(Long bookLikeId, Long memberId) {
        if (!bookLikeRepository.existsById(bookLikeId)) {
            throw new BookLikeNotExistsException("존재하지 않는 도서-좋아요입니다.");
        }
        bookLikeRepository.deleteById(bookLikeId);
    }

    @Override
    public Page<BookListResponse> findBookLikeByMemberId(Long memberId, Pageable pageable) {
        return bookLikeRepository.findBookLikeByMemberId(memberId, pageable);
    }

    @Override
    public Long countLikeByBookId(Long bookId) {
        return bookLikeRepository.countLikeByBookId(bookId);
    }

    @Override
    public Page<BookListResponse> findBooksOrderByLikes(Pageable pageable) {
        return bookLikeRepository.findBooksOrderByLikes(pageable);
    }
}
