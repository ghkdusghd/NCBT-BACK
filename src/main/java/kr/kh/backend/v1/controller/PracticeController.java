package kr.kh.backend.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.v1.dto.BookmarkDTO;
import kr.kh.backend.v1.dto.PracticeComplaintsDTO;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.v1.service.practice.PracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "PracticeController (연습문제 API)")
public class PracticeController {

    @Autowired
    private  PracticeService practiceService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 문제 북마크
    @Operation(summary = "사용자가 북마크한 문제 저장",
            description = "사용자가 북마크한 문제를 추후 확인할 수 있도록 저장합니다.")
    @PostMapping("/bookmarks")
    public ResponseEntity<String> addBookmark(@RequestBody BookmarkDTO bookmarkDTO,
                                              @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return practiceService.addBookmark(bookmarkDTO, token);

    }

    // 북마크한 문제 조회
    @Operation(summary = "연습문제 페이지 북마크",
            description = "연습문제 페이지에서 불러온 랜덤 문제들 중 사용자가 북마크한 문제가 있는지 조회합니다.")
    @GetMapping("/bookmarks")
    public ResponseEntity<Long> getBookmark(@RequestParam("questionId") Long questionId,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return practiceService.getBookmark(questionId, token);
    }

    // 문제 오류 신고
    @Operation(summary = "연습문제 오류 신고 및 이의제기",
            description = "사용자가 문제 오류를 발견하면 관리자에게 신고할 수 있습니다. 신고가 접수되면 ROLE_ADMIN 으로 등록된 이메일로 메일을 발송합니다.")
    @PostMapping("/practice-complaints")
    public ResponseEntity<String> addComplaint(@RequestBody PracticeComplaintsDTO practiceComplaintsDTO,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "" );
        return practiceService.addQuestionComplaints(practiceComplaintsDTO, token);

    }

    // 북마크 문제 가져오기
    @Operation(summary = "북마크 페이지",
            description = "사용자가 북마크한 문제를 과목별로 조회")
    @GetMapping("/bookmarks/{subjectName}")
    public ResponseEntity<List<BookmarkDTO>> getBookmarks(@PathVariable String subjectName,
                                                          @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String username = jwtTokenProvider.getUsernameFromToken(token);

        log.info("username: {}", username);
        log.info("subjectName: {}", subjectName);

        List<BookmarkDTO> bookmarkList = practiceService.getSubjectBookmarks(username, subjectName);

        if (bookmarkList == null || bookmarkList.isEmpty()) {
            log.info("북마크 정보가 없습니다");
            return ResponseEntity.status(204).body(Collections.emptyList());
        }

        log.info("북마크 정보를 가져왔습니다. {}", bookmarkList);
        return ResponseEntity.status(200).body(bookmarkList);
    }

}

