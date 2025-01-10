package kr.kh.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.dto.BookmarkDTO;
import kr.kh.backend.dto.PracticeComplaintsDTO;
import kr.kh.backend.service.practice.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "PracticeController (연습문제 API)")
public class PracticeController {

    @Autowired
    private  PracticeService practiceService;

    // 문제 북마크
    @Operation(summary = "사용자가 북마크한 문제 저장",
            description = "사용자가 북마크한 문제를 추후 확인할 수 있도록 저장합니다.")
    @PostMapping("/bookmarks")
    public ResponseEntity<String> addBookmark(@RequestBody BookmarkDTO bookmarkDTO,
                                              @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return practiceService.addBookmark(bookmarkDTO, token);

    }

    @GetMapping("/bookmarks")
    public ResponseEntity<Long> getBookmark(@RequestParam("questionId") Long questionId,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return practiceService.getBookmark(questionId, token);
    }

    // 문제 오류 신고
    @Operation(summary = "사용자가 문제 오류를 신고하면 관리자에게 알림",
            description = "사용자가 문제 오류를 발견하면 관리자에게 신고할 수 있습니다. 신고가 접수되면 ROLE_ADMIN 으로 등록된 이메일로 메일을 발송합니다.")
    @PostMapping("/practice-complaints")
    public ResponseEntity<String> addComplaint(@RequestBody PracticeComplaintsDTO practiceComplaintsDTO,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "" );
        return practiceService.addQuestionComplaints(practiceComplaintsDTO, token);

    }
}

