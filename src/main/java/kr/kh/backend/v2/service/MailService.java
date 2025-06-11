package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.PracticeComplaintsDTO;
import kr.kh.backend.v2.entity.User;

public interface MailService {
    void sendComplaintsToAdmin(PracticeComplaintsDTO practiceComplaintsDTO);
}
