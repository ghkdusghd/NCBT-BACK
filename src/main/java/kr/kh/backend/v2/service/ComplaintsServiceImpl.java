package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.PracticeComplaintsDTO;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.v2.entity.Complaint;
import kr.kh.backend.v2.entity.User;
import kr.kh.backend.v2.repository.ComplaintsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComplaintsServiceImpl implements ComplaintsService {

    private final ComplaintsRepository complaintsRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public ComplaintsServiceImpl(ComplaintsRepository complaintsRepository, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.complaintsRepository = complaintsRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<String> saveComplaints(PracticeComplaintsDTO practiceComplaintsDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.findByNickname(username);
        try {
            PracticeComplaintsDTO existingComplaint = practiceMapper.findComplaintByUserIdAndSubjectQuestionId(userId, practiceComplaintsDTO.getSubjectQuestionId());

            if (existingComplaint != null) {
                return ResponseEntity.badRequest().body("이미 해당 문제에 대한 오류 신고가 접수되었습니다.");
            }

            // 문제 오류가 접수되면 관리자 계정으로 이메일 알람 발송
            int result = practiceMapper.addComplaint(practiceComplaintsDTO);
            if(result == 1) {
                emailVerificationService.sendComplaintsToAdmin(practiceComplaintsDTO);
            }
            return ResponseEntity.ok("문제 오류가 성공적으로 접수되었습니다.");

        }catch (Exception e) {
            return ResponseEntity.status(500).body("문제 오류 신고 중 문제가 발생했습니다: " + e.getMessage());
        }
    }

}
