package kr.kh.backend.v2.repository;

import kr.kh.backend.v2.entity.Bookmark;
import kr.kh.backend.v2.entity.SubjectTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT b.id FROM Bookmark b WHERE b.user.nickname = :nickname AND b.questionId = :questionId")
    List<Long> findBookmarkIdsByUsername(@Param("nickname") String nickname, @Param("questionId") Long id);

    List<Bookmark> findByUserNicknameAndSubject(String nickname, SubjectTitle subject);

    Bookmark findByUserNicknameAndQuestionId(String nickname, Long questionId);

}
