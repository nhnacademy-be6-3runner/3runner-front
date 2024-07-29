package com.nhnacademy.front.purchase.purchase.feign;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@FeignClient(name = "PaymentControllerClient", url = "${feign.client.url}")
public interface PaymentControllerClient {
	@RequestMapping(value = "/bookstore/payments/guests/confirm")
	ResponseEntity<JSONObject> confirmGuestPayment(
		@RequestParam(required = false) Long cartId,
		@RequestParam(required = false) String address,
		@RequestParam(required = false) String password,
		@RequestParam(required = false) String isPacking,
		@RequestParam(required = false) String shipping,
		@RequestBody String jsonBody) throws Exception;

	@RequestMapping(value = "/bookstore/payments/members/confirm")
	ResponseEntity<JSONObject> confirmMemberPayment(
		@RequestParam(required = false) String discountedPrice,
		@RequestParam(required = false) String discountedPoint,
		@RequestParam(required = false) String isPacking,
		@RequestParam(required = false) String shipping,
		@RequestParam(required = false) String road,
		@RequestParam(required = false) Long couponFormId,
		@RequestBody String jsonBody) throws Exception;

}
