package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.BookmarkDTO;
import kr.kh.backend.common.exception.custom.UserNotFoundException;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.v2.entity.Bookmark;
import kr.kh.backend.v2.entity.SubjectTitle;
import kr.kh.backend.v2.entity.User;
import kr.kh.backend.v2.repository.BookmarkRepository;
import kr.kh.backend.v2.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {

    private final UserService userService;
    private final BookmarkRepository bookmarkRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public BookmarkServiceImpl(UserService userService, BookmarkRepository bookmarkRepository, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.bookmarkRepository = bookmarkRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String saveBookmark(BookmarkDTO bookmarkDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        if (username == null) {
            throw new UserNotFoundException("사용자 정보가 없습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        }

        Bookmark existingBookmark = bookmarkRepository.findByUserNicknameAndQuestionId(username, bookmarkDTO.getQuestionId());
        if (existingBookmark != null) {
            bookmarkRepository.delete(existingBookmark);
            return "북마크가 성공적으로 삭제되었습니다.";
        } else {
            User user = userService.findByNickname(username);
            Bookmark bookmark = Bookmark.builder()
                    .user(user)
                    .subject(bookmarkDTO.getSubject())
                    .questionId(bookmarkDTO.getQuestionId())
                    .createdAt(LocalDateTime.now())
                    .build();
            bookmarkRepository.save(bookmark);
            return "북마크가 성공적으로 추가되었습니다.";
        }
    }

    @Override
    public List<Long> getBookmark(Long questionId, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        if (username == null) {
            throw new UserNotFoundException("사용자 정보가 없습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        }

        return bookmarkRepository.findBookmarkIdsByUsername(username, questionId);
    }

    @Override
    public List<BookmarkDTO> getSubjectBookmarks(String username, String subjectName) {
        SubjectTitle subject = SubjectTitle.valueOf(subjectName);
        List<Bookmark> bookmarks = bookmarkRepository.findByUserNicknameAndSubject(username, subject);
        return convertToDtoList(bookmarks);
    }

    private List<BookmarkDTO> convertToDtoList(List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BookmarkDTO convertToDto(Bookmark bookmark) {
        return BookmarkDTO.builder()
                .id(bookmark.getId())
                .subject(bookmark.getSubject())
                .questionId(bookmark.getQuestionId())
                .userId(bookmark.getUser().getId())
                .build();
    }

}




