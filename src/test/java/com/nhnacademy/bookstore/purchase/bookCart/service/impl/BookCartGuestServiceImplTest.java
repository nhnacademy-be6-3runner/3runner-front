//package com.nhnacademy.bookstore.purchase.bookCart.service.impl;
//
//import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
//import com.nhnacademy.bookstore.book.book.repository.BookRepository;
//import com.nhnacademy.bookstore.entity.book.Book;
//import com.nhnacademy.bookstore.entity.bookCart.BookCart;
//import com.nhnacademy.bookstore.entity.cart.Cart;
//import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
//import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartDoesNotExistException;
//import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRedisRepository;
//import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRepository;
//import com.nhnacademy.bookstore.purchase.cart.repository.CartRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.ZonedDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class BookCartGuestServiceImplTest {
//    @Mock
//    private BookCartRedisRepository bookCartRedisRepository;
//
//    @Mock
//    private BookCartRepository bookCartRepository;
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private CartRepository cartRepository;
//
//    @InjectMocks
//    private BookCartGuestServiceImpl bookCartGuestService;
//
//    private Book book;
//    private Cart cart;
//    private BookCart bookCart;
//
//    @BeforeEach
//    void setUp() {
//        book = new Book(
//            "Test Title",
//            "Test Description",
//            ZonedDateTime.now(),
//            1000,
//            10,
//            900,
//            0,
//            true,
//            "Test Author",
//            "123456789",
//            "Test Publisher",
//            null,
//            null,
//            null
//        );
//        book.setId(1L);
//
//        cart = new Cart();
//        cart.setId(1L);
//
//        bookCart = new BookCart();
//        bookCart.setBook(book);
//        bookCart.setCart(cart);
//        bookCart.setQuantity(1);
//        bookCart.setCreatedAt(ZonedDateTime.now());
//    }
//
//    @Test
//    void testCreateBookCart() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
//        when(bookCartRepository.save(any(BookCart.class))).thenReturn(bookCart);
//
//        Long bookCartId = bookCartGuestService.createBookCart(book.getId(), cart.getId(), 2);
//
//        assertThat(bookCartId).isEqualTo(bookCart.getId());
//        verify(bookCartRedisRepository, times(1)).create(anyString(), anyLong(), any(ReadBookCartGuestResponse.class));
//    }
//
//    @Test
//    void testCreateBookCart_BookDoesNotExist() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> bookCartGuestService.createBookCart(book.getId(), cart.getId(), 2))
//                .isInstanceOf(BookDoesNotExistException.class);
//    }
//
//    @Test
//    void testUpdateBookCart() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
//        when(bookCartRepository.findByBookAndCart(any(Book.class), any(Cart.class))).thenReturn(Optional.of(bookCart));
//
//        Long bookCartId = bookCartGuestService.updateBookCart(book.getId(), cart.getId(), 3);
//
//        assertThat(bookCartId).isEqualTo(bookCart.getId());
//        verify(bookCartRedisRepository, times(1)).update(anyString(), anyLong(), anyInt());
//    }
//
//    @Test
//    void testUpdateBookCart_BookCartDoesNotExist() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
//        when(bookCartRepository.findByBookAndCart(any(Book.class), any(Cart.class))).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> bookCartGuestService.updateBookCart(book.getId(), cart.getId(), 3))
//                .isInstanceOf(BookCartDoesNotExistException.class);
//    }
//
//    @Test
//    void testReadAllBookCart() {
//        when(bookCartRedisRepository.readAllHashName(anyString())).thenReturn(List.of());
//        when(bookCartRedisRepository.isMiss(anyString())).thenReturn(true);
//
//        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
//        when(bookCartRepository.findAllByCart(any(Cart.class))).thenReturn(List.of(bookCart));
//
//        List<ReadBookCartGuestResponse> responses = bookCartGuestService.readAllBookCart(cart.getId());
//
//        assertThat(responses).isNotEmpty();
//        verify(bookCartRedisRepository, times(1)).loadData(anyList(), anyString());
//    }
//
//    @Test
//    void testHasDataToLoad() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
//        when(bookCartRepository.findAllByCart(any(Cart.class))).thenReturn(List.of(bookCart));
//
//        when(bookCartRedisRepository.isMiss(anyString())).thenReturn(true);
//
//        bookCartGuestService.createBookCart(book.getId(), cart.getId(), 2);
//
//        verify(bookCartRedisRepository, times(1)).loadData(anyList(), anyString());
//        verify(bookCartRedisRepository, times(1)).loadData(anyList(), anyString());
//    }
//}