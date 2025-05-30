package kr.kh.backend.v1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.v1.domain.Sponsor;
import kr.kh.backend.v1.dto.PaymentVerifyRequestDTO;
import kr.kh.backend.v1.dto.SponsorDTO;
import kr.kh.backend.v1.service.SponsorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sponsor")
@Tag(name = "SponsorController (결제 API) - 추가 구현 예정")
public class SponsorController {
    private static final Logger log = LoggerFactory.getLogger(SponsorController.class);
    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    // 고객키 생성
    @PostMapping("/create-customer-key")
    public ResponseEntity<Map<String, String>> createCustomerKey() {
        String customerKey = sponsorService.generateCustomerKey();

        Map<String, String> response = new HashMap<>();
        response.put("customerKey", customerKey);

        return ResponseEntity.ok(response);
    }

    // 결제 생성
    @PostMapping("/create")
    public void createOrder(@RequestBody SponsorDTO sponsorDto) {
        sponsorService.createOrder(
                sponsorDto.getOrderId(),
                sponsorDto.getOrderName(),
                sponsorDto.getCustomerName(),
                sponsorDto.getCustomerEmail(),
                sponsorDto.getAmount()
        );
    }

    // 결제 검증
    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerifyRequestDTO request) {
        try {
            sponsorService.verifyPayment(
                    request.getPaymentType(),
                    request.getOrderId(),
                    request.getPaymentKey(),
                    request.getAmount()
            );
            return ResponseEntity.ok("결제 검증 및 업데이트 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public Sponsor getOrderById(@PathVariable Long id) {
        return sponsorService.getOrderById(id);
    }

    @PutMapping("/update-status")
    public void updateOrderStatus(@RequestParam String orderId, @RequestParam String status) {
        sponsorService.updateOrderStatus(orderId, status);
    }
}