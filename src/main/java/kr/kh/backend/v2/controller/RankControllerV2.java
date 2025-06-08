package kr.kh.backend.v2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.common.dto.SubjectNameDTO;
import kr.kh.backend.common.response.ApiResponse;
import kr.kh.backend.v2.service.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2")
@Tag(name = "RankControllerV2 (랭킹 API)")
public class RankControllerV2 {

    private final RankService rankService;

    @Operation(summary = "사용자의 모의고사 점수를 조회")
    @PostMapping("/ranking")
    public ResponseEntity<?> getRankingV2(@RequestBody SubjectNameDTO subjectNameDTO) {
        log.info("랭킹 컨트롤러 !!!!!! {}", subjectNameDTO.getTitle());
        return ApiResponse.OK(rankService.getRanking(subjectNameDTO.getTitle()));
    }

}
