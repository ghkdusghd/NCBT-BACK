package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.PracticeComplaintsDTO;
import kr.kh.backend.common.exception.custom.EmailFailureException;
import kr.kh.backend.common.exception.custom.ExistComplaintException;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.v2.entity.Complaint;
import kr.kh.backend.v2.entity.ComplaintStatus;
import kr.kh.backend.v2.entity.User;
import kr.kh.backend.v2.repository.ComplaintsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ComplaintsServiceImpl implements ComplaintsService {

    private final ComplaintsRepository complaintsRepository;
    private final MailService mailService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public ComplaintsServiceImpl(ComplaintsRepository complaintsRepository, MailService mailService, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.complaintsRepository = complaintsRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void saveComplaints(PracticeComplaintsDTO practiceComplaintsDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.findByNickname(username);
        Long questionId = practiceComplaintsDTO.getSubjectQuestionId();
        try {
            Complaint existingComplaint = complaintsRepository.findByUserAndQuestionId(user, questionId);

            if (existingComplaint != null) {
                throw new ExistComplaintException("이미 해당 문제에 대한 오류 신고가 접수되었습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
            }

            // 문제 오류가 접수되면 관리자 계정으로 이메일 알람 발송
            Complaint complaint = Complaint.builder()
                    .user(user)
                    .subject(practiceComplaintsDTO.getSubjectTitle())
                    .questionId(practiceComplaintsDTO.getSubjectQuestionId())
                    .title(practiceComplaintsDTO.getTitle())
                    .comment(practiceComplaintsDTO.getContent())
                    .status(ComplaintStatus.WAITING)
                    .createdAt(LocalDateTime.now())
                    .build();
            complaintsRepository.save(complaint);
            mailService.sendComplaintsToAdmin(practiceComplaintsDTO); // 비동기 실행

        }catch (EmailFailureException ex) {
            throw new EmailFailureException("문제 오류 신고 중 문제가 발생했습니다.", LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
