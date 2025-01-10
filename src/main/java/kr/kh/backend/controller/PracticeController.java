package kr.kh.backend.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.kh.backend.dto.BookmarkDTO;
import kr.kh.backend.dto.PracticeComplaintsDTO;
import kr.kh.backend.security.jwt.JwtTokenProvider;
import kr.kh.backend.service.practice.PracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class PracticeController {

    @Autowired
    private  PracticeService practiceService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 문제 북마크
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
    @PostMapping("/practice-complaints")
    public ResponseEntity<String> addComplaint(@RequestBody PracticeComplaintsDTO practiceComplaintsDTO,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "" );
        return practiceService.addQuestionComplaints(practiceComplaintsDTO, token);

    }

    // 북마크한 문제 가져오기
//    @GetMapping("/bookmarks/{subjectName}")
//    public ResponseEntity<List<BookmarkDTO>> getBookmarks(@PathVariable String subjectName,
//                                                          @RequestHeader("Authorization") String authorizationHeader) {
//
//        String token = authorizationHeader.replace("Bearer ", "");
//        String username = jwtTokenProvider.getUsernameFromToken(token);
//
//        log.info("username: {}", username);
//        log.info("subjectName: {}", subjectName);
//
//
//        List<BookmarkDTO> bookmarkList = practiceService.getSubjectBookmarks(username, subjectName);
//
//        if (bookmarkList == null || bookmarkList.isEmpty()) {
//            log.info("북마크 정보가 없습니다");
//            return ResponseEntity.status(204).build();
//        }
//
//        log.info("북마크 정보를 가져왔습니다. {}", bookmarkList);
//        return ResponseEntity.status(200).body(bookmarkList);
//    }

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

