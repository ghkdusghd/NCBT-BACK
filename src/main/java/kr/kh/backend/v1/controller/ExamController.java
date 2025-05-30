package kr.kh.backend.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.v1.dto.ExamDTO;
import kr.kh.backend.v1.service.ExamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "ExamController (모의고사 API)")
public class ExamController {

    private final ExamService examService;

    @Operation(summary = "사용자의 모의고사 점수를 기록",
            description = "사용자가 모의고사를 마치고 점수를 제출하면 그 점수를 데이터베이스에 insert 합니다.")
    @PostMapping("/exam/record")
    public ResponseEntity<?> recordScore(@RequestBody ExamDTO examDTO,
                                         @RequestHeader("Authorization") String authorizationHeader) {
        log.info("exam controller !!!!!! {}", examDTO);
        String token = authorizationHeader.replace("Bearer ", "");
        return examService.recordScore(examDTO, token);
    }

}
