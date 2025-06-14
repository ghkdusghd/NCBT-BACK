package kr.kh.backend.common.dto;

import kr.kh.backend.v2.entity.SubjectTitle;
import lombok.Data;

@Data
public class PracticeComplaintsDTO {

    private int id;
    private Long userId;
    private Long subjectId;
    private SubjectTitle subjectTitle;
    private Long subjectQuestionId;
    private String title;
    private String content;
    private boolean isSolved;

}

/* Post - body
    {
    "subjectId": 1,
    "subjectQuestionId": 8,
    "title": "제목",
    "content": "이의있습니다"
}
* */