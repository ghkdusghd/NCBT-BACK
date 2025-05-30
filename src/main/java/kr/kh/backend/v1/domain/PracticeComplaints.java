package kr.kh.backend.v1.domain;

import lombok.Data;

@Data
public class PracticeComplaints {

    private Long userId;
    private Long subjectId;
    private Long subjectQuestionId;
    private String title;
    private String content;

}
