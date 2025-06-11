package kr.kh.backend.common.dto;

import kr.kh.backend.v2.entity.SubjectTitle;
import lombok.Data;

@Data
public class ExamDTO {
    private int ScoreId;
    private int score;
    private int userId;
    private int subjectId;
    private SubjectTitle subjectTitle;
}
