package kr.kh.backend.v2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kh.backend.common.dto.BookmarkDTO;
import kr.kh.backend.common.dto.PracticeComplaintsDTO;
import kr.kh.backend.common.response.ApiResponse;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.v2.service.BookmarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v2")
@Tag(name = "BookmarkControllerV2 (연습문제 API)")
public class BookmarkControllerV2 {

    @Autowired
    private  BookmarkService bookmarkService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "사용자가 북마크한 문제 저장",
            description = "사용자가 북마크한 문제를 추후 확인할 수 있도록 저장합니다.")
    @PostMapping("/bookmarks")
    public ResponseEntity<?> saveBookmark(@RequestBody BookmarkDTO bookmarkDTO,
                                              @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String message = bookmarkService.saveBookmark(bookmarkDTO, token);
        return ApiResponse.UPDATE_BOOKMARK(message);
    }

    @Operation(summary = "연습문제 페이지에서 북마크한 문제 조회",
            description = "연습문제 페이지에서 불러온 랜덤 문제들 중 사용자가 북마크한 문제가 있는지 조회합니다.")
    @GetMapping("/bookmarks")
    public ResponseEntity<?> getBookmark(@RequestParam("questionId") Long questionId,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        List<Long> bookmarkId = bookmarkService.getBookmark(questionId, token);
        if (bookmarkId == null || bookmarkId.isEmpty()) {
            return ApiResponse.NO_CONTENT();
        }
        return ApiResponse.OK(questionId);
    }

    // 북마크 문제 가져오기
    @Operation(summary = "북마크 페이지에서 북마크한 문제 조회",
            description = "사용자가 북마크한 문제를 과목별로 조회")
    @GetMapping("/bookmarks/{subjectName}")
    public ResponseEntity<?> getBookmarks(@PathVariable String subjectName,
                                                          @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        String username = jwtTokenProvider.getUsernameFromToken(token);

        List<BookmarkDTO> bookmarkList = bookmarkService.getSubjectBookmarks(username, subjectName);

        if (bookmarkList == null || bookmarkList.isEmpty()) {
            log.info("북마크한 문제가 없습니다");
            return ApiResponse.NO_CONTENT();
        }

        log.info("북마크 정보를 가져왔습니다. {}", bookmarkList);
        return ApiResponse.OK(bookmarkList);
    }

}

