package kr.kh.backend.v2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.common.dto.ExamDTO;
import kr.kh.backend.common.response.ApiResponse;
import kr.kh.backend.v2.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/v2")
@Tag(name = "ExamControllerV2 (모의고사 API)")
public class ExamControllerV2 {

    private final ExamService examService;

    public ExamControllerV2(ExamService examService) {
        this.examService = examService;
    }

    @Operation(summary = "사용자의 모의고사 점수를 기록",
            description = "사용자가 모의고사를 마치고 점수를 제출하면 그 점수를 데이터베이스에 insert 합니다.")
    @PostMapping("/exam/record")
    public ResponseEntity<?> recordScore(@RequestBody ExamDTO examDTO,
                                         @RequestHeader("Authorization") String authorizationHeader) {
        log.info("exam controller !!!!!! {}", examDTO);
        String token = authorizationHeader.replace("Bearer ", "");
        examService.recordScore(examDTO, token);
        return ApiResponse.OK(null);
    }

}
