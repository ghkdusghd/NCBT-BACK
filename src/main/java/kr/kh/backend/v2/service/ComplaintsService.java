package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.PracticeComplaintsDTO;

public interface ComplaintsService {
    void saveComplaints(PracticeComplaintsDTO practiceComplaintsDTO, String token);
}
