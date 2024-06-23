package com.nhnacademy.bookstore.purchase.bookCart.repository.impl;

import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.bookstore.purchase.bookCart.repository.BookCartRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 도서장바구니 레디스 저장소 구현체.
 *
 * @author 김병우
 */
@Repository
@RequiredArgsConstructor
public class BookCartRedisRepositoryImpl implements BookCartRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 도서장바구니 레디스 저장.
     *
     * @param hashName 해쉬
     * @param id 해쉬키
     * @param readBookCartGuestResponse 해쉬밸류
     * @return 해쉬키
     */
    @Override
    public Long create(String hashName, Long id, ReadBookCartGuestResponse readBookCartGuestResponse) {
        redisTemplate.opsForHash().put(hashName, id, readBookCartGuestResponse);
        return id;
    }

    /**
     * 도서장바구니 레디스 수정.
     *
     * @param hashName 해쉬
     * @param id 해쉬키
     * @param quantity 수정데이터
     * @return 해쉬키
     */
    @Override
    public Long update(String hashName, Long id, int quantity) {
        ReadBookCartGuestResponse response = (ReadBookCartGuestResponse) redisTemplate.opsForHash().get(hashName,id);

        assert response != null;
        response = ReadBookCartGuestResponse.builder().bookCartId(response.bookCartId())
                .cartId(response.cartId())
                .bookId(response.bookId())
                .quantity(response.quantity()+quantity)
                .createdAt(response.createdAt()).build();

        redisTemplate.opsForHash().put(hashName, id, response);
        return id;
    }

    /**
     * 도서장바구니 레디스 삭제.
     *
     * @param hashName 해쉬
     * @param id 해쉬키
     * @return 해쉬키
     */
    @Override
    public Long delete(String hashName, Long id) {
        redisTemplate.opsForHash().delete(hashName, id);
        return id;
    }

    /**
     * 도서장바구니 레디스 목록 읽기.
     *
     * @param hashName 해쉬
     * @return 도서장바구니 응답 dto
     */
    @Override
    public List<ReadBookCartGuestResponse> readAllHashName(String hashName) {
        List<Object> bookCartList = redisTemplate.opsForHash().values(hashName);

        return  bookCartList
                .stream()
                .map(ReadBookCartResponse -> (ReadBookCartGuestResponse)ReadBookCartResponse)
                .toList();
    }

    /**
     * 도서장바구니 레디스 데이터가 있는지 확인 메소드.
     *
     * @param hashName 해쉬
     * @return boolean
     */
    @Override
    public boolean isHit(String hashName) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(hashName));
    }

    /**
     * 도서장바구니 레디스 데이터가 없는지 확인 메소드.
     *
     * @param hashName 해쉬
     * @return boolean
     */
    @Override
    public boolean isMiss(String hashName) {
        return Boolean.FALSE.equals(redisTemplate.hasKey(hashName));
    }

    /**
     * 도서장바구니 데이터를 레디스에 저장.
     *
     * @param bookCartGuestResponses 도서장바구니 dto
     * @param hashName 해쉬
     */
    @Override
    public void loadData(List<ReadBookCartGuestResponse> bookCartGuestResponses, String hashName) {
        for(ReadBookCartGuestResponse o : bookCartGuestResponses){
            redisTemplate.opsForHash().put(hashName, o.bookCartId(), o);
        }
    }
}
