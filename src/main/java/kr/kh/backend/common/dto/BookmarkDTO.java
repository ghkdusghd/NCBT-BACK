package kr.kh.backend.common.dto;

import kr.kh.backend.v2.entity.SubjectTitle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class BookmarkDTO {

    private Long id;
    private SubjectTitle subject;
    private Long questionId;
    private Long userId;

}

/* Post - body
    {
    "subjectId": 1,
    "questionId": 8
    }
*/

/* GET - parameter
    /bookmarks?questionId=19
*/