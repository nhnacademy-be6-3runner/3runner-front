package com.nhnacademy.bookstore.purchase.purchase.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.purchase.enums.MemberType;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.member.member.service.MemberService;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;

class PurchaseMemberServiceImplTest {

	@Mock
	private PurchaseRepository purchaseRepository;

	@Mock
	private MemberService memberService;

	@InjectMocks
	private PurchaseMemberServiceImpl purchaseMemberService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePurchase_Success() {
		// Mock data
		Long memberId = 1L;
		CreatePurchaseRequest createPurchaseRequest = CreatePurchaseRequest.builder().deliveryPrice(100).totalPrice(2000).road("Test Road").build();

		Member member = new Member();
		member.setId(memberId);

		Purchase purchase = new Purchase(
			UUID.randomUUID(),
			PurchaseStatus.PROCESSING,
			createPurchaseRequest.deliveryPrice(),
			createPurchaseRequest.totalPrice(),
			ZonedDateTime.now(),
			createPurchaseRequest.road(),
			null,
			MemberType.MEMBER,
			member,
			null,
			null,
			null
		);

		when(memberService.findById(memberId)).thenReturn(member);
		when(purchaseRepository.existsPurchaseByOrderNumber(any(UUID.class))).thenReturn(false);
		when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

		// Call service method
		Long createdPurchaseId = purchaseMemberService.createPurchase(createPurchaseRequest, memberId);

		// Verify
		assertEquals(purchase.getId(), createdPurchaseId);
		verify(purchaseRepository, times(1)).save(any(Purchase.class));
	}

	@Test
	void testUpdatePurchase_Success() {
		// Mock data
		Long memberId = 1L;
		Long purchaseId = 1L;
		UpdatePurchaseMemberRequest updatePurchaseRequest = UpdatePurchaseMemberRequest.builder().purchaseStatus(PurchaseStatus.SHIPPED).build();

		Member member = new Member();
		member.setId(memberId);

		Purchase existingPurchase = new Purchase(
			UUID.randomUUID(),
			PurchaseStatus.PROCESSING,
			10,
			10,
			null,
			null,
			null,
			null,
			member,
			null,
			null,
			null


		);
		existingPurchase.setId(purchaseId);


		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(existingPurchase));
		when(memberService.findById(memberId)).thenReturn(member);

		// Call service method
		Long updatedPurchaseId = purchaseMemberService.updatePurchase(updatePurchaseRequest, memberId, purchaseId);

		// Verify
		assertEquals(existingPurchase.getId(), updatedPurchaseId);
		assertEquals(updatePurchaseRequest.purchaseStatus(), existingPurchase.getStatus());
		verify(purchaseRepository, times(1)).save(existingPurchase);
	}

	@Test
	void testReadPurchase_Success() {
		// Mock data
		Long memberId = 1L;
		Long purchaseId = 1L;

		Member member = new Member();
		member.setId(memberId);

		Purchase purchase = new Purchase(
			UUID.randomUUID(),
			PurchaseStatus.PROCESSING,
			10,
			10,
			null,
			null,
			null,
			null,
			member,
			null,
			null,
			null


		);

		purchase.setId(purchaseId);

		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

		// Call service method
		ReadPurchaseResponse response = purchaseMemberService.readPurchase(memberId, purchaseId);

		// Verify
		assertEquals(purchase.getId(), response.id());
		verify(purchaseRepository, times(1)).findById(purchaseId);
	}

	@Test
	void testDeletePurchase_Success() {
		// Mock data
		Long memberId = 1L;
		Long purchaseId = 1L;

		Member member = new Member();
		member.setId(memberId);

		Purchase purchase = new Purchase(
			UUID.randomUUID(),
			PurchaseStatus.PROCESSING,
			10,
			10,
			null,
			null,
			null,
			null,
			member,
			null,
			null,
			null


		);

		purchase.setId(purchaseId);


		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

		// Call service method
		purchaseMemberService.deletePurchase(memberId, purchaseId);

		// Verify
		verify(purchaseRepository, times(1)).delete(purchase);
	}
}
