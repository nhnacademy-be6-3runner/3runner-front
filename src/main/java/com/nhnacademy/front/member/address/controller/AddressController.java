package com.nhnacademy.front.member.address.controller;

import com.nhnacademy.front.member.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;
import com.nhnacademy.front.purchase.purchase.dto.member.request.CreateAddressRequest;
import com.nhnacademy.front.util.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AddressController {
	private final AddressControllerClient addressControllerClient;

	@PostMapping("/member/address")
	@ResponseBody
	public Boolean createAddress(@RequestBody CreateAddressRequest createAddressRequest) {
		ApiResponse<Void> apiResponse = addressControllerClient.createAddress(createAddressRequest);//들어오긴들어옴
		return apiResponse.getHeader().isSuccessful();
	}

	@GetMapping("/member/address")
	public List<AddressResponse> readAllAddress(Model model) {
		List<AddressResponse> addresses = addressControllerClient.readAllAddresses().getBody().getData();
		model.addAttribute("addresses", addresses);
		return addresses;
	}

	@PutMapping("/member/address/{addressId}")
	public String updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest,
		@PathVariable(name = "addressId") Long addressId) {
		addressControllerClient.updateAddress(updateAddressRequest, addressId);
		return null;
	}

	@DeleteMapping("/member/address/{addressId}")
	public Boolean deleteAddress(@PathVariable("addressId") String addressId) {

		ApiResponse<Void> result = addressControllerClient.deleteAddress(Long.valueOf(addressId));

		return result.getHeader().isSuccessful();
	}
}

