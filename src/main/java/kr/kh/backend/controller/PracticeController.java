package kr.kh.backend.controller;

import kr.kh.backend.dto.BookmarkDTO;
import kr.kh.backend.dto.PracticeComplaintsDTO;
import kr.kh.backend.service.practice.PracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PracticeController {

    @Autowired
    private  PracticeService practiceService;

    // 문제 북마크
    @PostMapping("/bookmarks")
    public ResponseEntity<String> addBookmark(@RequestBody BookmarkDTO bookmarkDTO,
                                              @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return practiceService.addBookmark(bookmarkDTO, token);

    }

    // 북마크 페이지 요청
    @GetMapping("/bookmarks")
    public ResponseEntity<Long> getBookmark(@RequestParam("questionId") Long questionId,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        return practiceService.getBookmark(questionId, token);
    }

    // 문제 오류 신고
    @PostMapping("/practice-complaints")
    public ResponseEntity<String> addComplaint(@RequestBody PracticeComplaintsDTO practiceComplaintsDTO,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "" );
        return practiceService.addQuestionComplaints(practiceComplaintsDTO, token);

    }

    // 북마크한 문제 가져오기
    @GetMapping("/bookmarks/{username}")
    public ResponseEntity<List<BookmarkDTO>> getBookmarks(@PathVariable String username) {
        log.info("북마크 가져오기 컨트롤러 !!! user : {}", username);

        List<BookmarkDTO> bookmarkList = practiceService.getAllBookmarks(username);

        if(bookmarkList.isEmpty() || bookmarkList.size() == 0 || bookmarkList == null) {
            log.info("북마크 정보가 없습니다");
            return ResponseEntity.status(400).build();
        }

        log.info("북마크 정보를 가져왔습니다. {}", bookmarkList);
        return ResponseEntity.status(200).body(bookmarkList);

    }
}

