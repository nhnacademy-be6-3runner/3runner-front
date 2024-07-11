package com.nhnacademy.bookstore.purchase.refundRecord.repository;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.bookstore.purchase.refundRecord.dto.response.ReadRefundRecordResponse;

public interface RefundRecordRedisRepository {
	Long create(String hashName, Long id, ReadRefundRecordResponse readRefundRecordResponse);

	Long update(String hashName, Long id, int quantity);

	Long delete(String hashName, Long id);

	void deleteAll(String hashName);

	List<ReadRefundRecordResponse> readAll(String hashName);

	boolean isHit(String hashName);

	boolean detailIsHit(String hashName, Long id);



}
