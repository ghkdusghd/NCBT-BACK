package kr.kh.backend.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RankDTO {

    private String title;
    private String table;
    private int subjectId;

    private int score;
    private int userId;
    private String nickname;

}
