package com.nhnacademy.front.refund.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseBookControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseGuestControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseManagerControllerClient;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.front.refund.feign.RefundControllerClient;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

	private final PurchaseBookControllerClient purchaseBookControllerClient;
	private final RefundControllerClient refundControllerClient;
	private final PurchaseManagerControllerClient purchaseManagerControllerClient;

	@Override
	public Page<ReadPurchaseBookResponse> readGuestPurchaseBooks(String orderNumber,int page,int size,String sort) {
		return purchaseBookControllerClient.readGuestPurchaseBook(orderNumber,page,size,sort).getBody().getData();
	}


	@Override
	public Map<String, Object> refundToss(String orderNumber, String cancelReason) {

		Map<String, Object> result = new HashMap<>();

		String paymentKey = refundControllerClient.readTossOrderId(orderNumber).getBody().getData().paymentKey();

		String secretKey = "test_sk_nRQoOaPz8LEXJvm6Reo53y47BMw6";

		try {
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] encodedBytes = encoder.encode(secretKey.getBytes(StandardCharsets.UTF_8));
			String authorizations = "Basic " + new String(encodedBytes);

			URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", authorizations);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			JSONObject obj = new JSONObject();
			obj.put("cancelReason", cancelReason);


			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

			int code = connection.getResponseCode();
			boolean isSuccess = (code == 200);

			InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

			Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			responseStream.close();

			result.put("isSuccess", isSuccess);
			result.put("jsonObject", jsonObject);
			if(isSuccess) {
				refundControllerClient.createRefundCancelPayment(orderNumber);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return result;

	}




}
