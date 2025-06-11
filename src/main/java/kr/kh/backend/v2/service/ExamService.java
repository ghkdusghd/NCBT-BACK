package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.ExamDTO;

public interface ExamService {
    void recordScore(ExamDTO examDTO, String token);
}
