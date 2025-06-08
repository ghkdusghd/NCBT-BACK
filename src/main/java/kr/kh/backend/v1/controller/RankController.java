package kr.kh.backend.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.common.dto.RankDTO;
import kr.kh.backend.common.dto.SubjectNameDTO;
import kr.kh.backend.v1.exception.CustomException;
import kr.kh.backend.v1.service.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "RankController (랭킹 API)")
public class RankController {

    private final RankService rankService;

    @Operation(summary = "사용자의 모의고사 점수를 조회 (V1)",
            description = "과목별 전체 모의고사 점수들 중에서 상위 5인의 점수를 조회합니다. (ORDER BY score DESC LIMIT 5)")
    @PostMapping("/ranking/v1")
    public ResponseEntity<List<RankDTO>> getRankingV1(@RequestBody RankDTO rankDTO) {
        if(rankDTO == null || rankDTO.getTitle() == null || rankDTO.getTable() == null) {
            throw new CustomException(
                    "title 및 table 정보는 필수입니다.",
                    "INVALID_RANK_INFO",
                    HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.ok(rankService.getRankingV1(rankDTO));
    }

    @Operation(summary = "사용자의 모의고사 점수를 조회 (V2 : V1 버전을 리팩토링)",
            description = "V1 버전은 사용자의 한 번의 요청에 API 를 여러 번 호출하도록 설계되어 있어 비효율적이며, 네트워크 통신 비용을 증가시킨다는 문제가 있습니다. " +
                    "그래서 V2 버전에서는 사용자의 한 번의 요청에 API 를 한 번만 호출하여 모든 데이터를 조회하도록 개선했습니다.")
    @PostMapping("/ranking/v2")
    public ResponseEntity<?> getRankingV2(@RequestBody SubjectNameDTO subjectNameDTO) {
        log.info("랭킹 컨트롤러 !!!!!! {}", subjectNameDTO.getTitle());
        return ResponseEntity.ok(rankService.getRankingV2(subjectNameDTO.getTitle()));
    }

}
