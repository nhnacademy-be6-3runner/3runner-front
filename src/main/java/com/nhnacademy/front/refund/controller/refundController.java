package com.nhnacademy.front.refund.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refund")
public class refundController {

	private final RefundService refundService;

	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable(name = "orderNumber") String purchaseId
		,@RequestParam(defaultValue = "0") int page
		,@RequestParam(defaultValue = "10") int pageSize
		,@RequestParam(required = false) String sort
		, Model model) {

		Page<ReadPurchaseBookResponse> books = refundService.readGuestPurchaseBooks(purchaseId,page,pageSize,sort);
		model.addAttribute("books", books);

		return "refund";
	}

	@PostMapping("/cancelPayment/{orderNumber}")
	public String cancelPayment(@PathVariable(name = "orderNumber") String orderNumber,@RequestParam String cancelReason, Model model) {

		String paymentKey = refundService.readPaymentKey(orderNumber);

		String secretKey = "test_sk_zXLkKEypNArWmo50nX3lmeaxYG5R:";

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

			JSONObject refundReceiveAccount = new JSONObject();

			obj.put("refundReceiveAccount", refundReceiveAccount);

			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

			int code = connection.getResponseCode();
			boolean isSuccess = (code == 200);

			InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

			Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			responseStream.close();

			model.addAttribute("isSuccess", isSuccess);
			model.addAttribute("jsonObject", jsonObject);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "result";
	}

}
