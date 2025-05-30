package kr.kh.backend.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.v1.dto.PracticeComplaintsDTO;
import kr.kh.backend.v1.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "AdminController (관리자 API)")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "사용자가 신고한 문제 오류들을 조회",
            description = "요청 시점에 처리되지 않은 모든 Complaints 을 조회합니다. 관리자만 사용할 수 있습니다.")
    @GetMapping("/admin/complaints")
    public ResponseEntity<?> getAllComplaints() {
        try {
            List<PracticeComplaintsDTO> complaintsList = adminService.getAllComplaints();
            return ResponseEntity.ok().body(complaintsList);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "문제 오류가 해결되면 사용자에게 메일 발송",
            description = "사용자의 Complaints 가 해결되면 사용자에게 메일로 알람을 보냅니다. 관리자만 사용할 수 있습니다.")
    @PostMapping("/admin/solvedComplaints")
    public ResponseEntity<?> solvedComplaints(@RequestBody PracticeComplaintsDTO practiceComplaintsDTO) {
        log.info("문제 오류 해결 ! 신고한 사용자 = {}", practiceComplaintsDTO.getUserId());
        int result = adminService.solvedComplaints(practiceComplaintsDTO);

        if(result == 1) {
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }

}
