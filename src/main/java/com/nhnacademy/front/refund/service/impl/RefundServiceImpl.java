package com.nhnacademy.front.refund.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseManagerControllerClient;
import com.nhnacademy.front.refund.dto.request.CreateRefundRequest;
import com.nhnacademy.front.refund.dto.response.ReadRefundResponse;
import com.nhnacademy.front.refund.feign.RefundControllerClient;
import com.nhnacademy.front.refund.feign.RefundRecordGuestControllerClient;
import com.nhnacademy.front.refund.feign.RefundRecordMemberControllerClient;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

	private final PurchaseBookControllerClient purchaseBookControllerClient;
	private final RefundControllerClient refundControllerClient;
	private final PurchaseManagerControllerClient purchaseManagerControllerClient;
	private final RefundRecordGuestControllerClient refundRecordGuestControllerClient;
	private final RefundRecordMemberControllerClient refundRecordMemberControllerClient;

	@Override
	public List<ReadPurchaseBookResponse> readMemberPurchaseBooks(Long orderNumber) {
		refundRecordMemberControllerClient.updateRefundRecordAllMember(orderNumber);
		return purchaseBookControllerClient.readPurchaseBook(orderNumber).getBody().getData();
	}

	@Override
	public List<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber) {
		refundRecordGuestControllerClient.updateRefundRecordAllGuest(orderNumber);
		return purchaseBookControllerClient.readGuestPurchaseBook(orderNumber).getBody().getData();
	}

	@Override
	public ReadRefundResponse readRefund(Long refundId) {
		return refundControllerClient.readRefundRecprd(refundId).getBody().getData();
	}

	@Override
	public Map<String, Object> refundToss(String orderNumber, Integer price, String cancelReason) {

		Map<String, Object> result = new HashMap<>();

		String paymentKey = refundControllerClient.readTossOrderId(orderNumber).getBody().getData().paymentKey();

		String secretKey = "test_sk_nRQoOaPz8LEXJvm6Reo53y47BMw6";

		try {
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] encodedBytes = encoder.encode(secretKey.getBytes(StandardCharsets.UTF_8));
			String authorizations = "Basic " + new String(encodedBytes);

			URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel");

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestProperty("Authorization", authorizations);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			JSONObject obj = new JSONObject();
			obj.put("cancelReason", cancelReason);
			if (price != null) {
				obj.put("cancelAmount", price);
			}

			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

			int code = connection.getResponseCode();
			boolean isSuccess = (code == 200);

			InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

			Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject)parser.parse(reader);
			responseStream.close();

			result.put("isSuccess", isSuccess);
			result.put("jsonObject", jsonObject);
			if (isSuccess) {
				refundControllerClient.createRefundCancelPayment(orderNumber);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public Long createRefundRequest(Long orderId, Integer price, String refundReason) {
		return refundControllerClient.createRefund(orderId,
			CreateRefundRequest.builder().refundContent(refundReason).price(price).build()).getBody().getData();
	}

	@Override
	public Boolean updateRefundSuccess(Long refundId) {
		return refundControllerClient.updateSuccessRefund(refundId).getBody().getData();
	}

	@Override
	public Boolean updateRefundReject(Long refundId) {
		return refundControllerClient.updateRefundRejected(refundId).getBody().getData();

	}

	@Override
	public List<ReadRefundResponse> readRefundAll() {
		return refundControllerClient.readAllRefund().getBody().getData();
	}

}
