package kr.kh.backend.v2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.common.dto.PracticeComplaintsDTO;
import kr.kh.backend.v2.service.ComplaintsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/v2")
@Tag(name = "ComplaintsControllerV2 (문제 오류 신고 API)")
public class ComplaintsControllerV2 {

    private final ComplaintsService complaintsService;

    public ComplaintsControllerV2(ComplaintsService complaintsService) {
        this.complaintsService = complaintsService;
    }

    @Operation(summary = "연습문제 오류 신고 및 이의제기",
            description = "사용자가 문제 오류를 발견하면 관리자에게 신고할 수 있습니다. 신고가 접수되면 ROLE_ADMIN 으로 등록된 이메일로 메일을 발송합니다.")
    @PostMapping("/practice-complaints")
    public ResponseEntity<String> addComplaint(@RequestBody PracticeComplaintsDTO practiceComplaintsDTO,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "" );
        return complaintsService.saveComplaints(practiceComplaintsDTO, token);
    }

}
