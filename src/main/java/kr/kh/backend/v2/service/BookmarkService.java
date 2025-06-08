package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.BookmarkDTO;

import java.util.List;

public interface BookmarkService {

    String saveBookmark(BookmarkDTO bookmarkDTO, String token);

    List<Long> getBookmark(Long questionId, String token);

    List<BookmarkDTO> getSubjectBookmarks(String username, String subjectName);
}
