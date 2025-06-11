package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.PracticeComplaintsDTO;
import kr.kh.backend.v2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final UserService userService;

    public MailServiceImpl(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @Async
    public void sendComplaintsToAdmin(PracticeComplaintsDTO practiceComplaintsDTO) {
        String title = "[NCBT] 문제 오류가 접수되었습니다.";
        String content =
                "제목 : " + practiceComplaintsDTO.getTitle()
                        + "내용 : " + practiceComplaintsDTO.getContent();

        List<User> adminList = userService.findAdmin();
        try {
            adminList.stream()
                    .map(user -> createEmail(user.getEmail(), title, content))
                    .forEach(mailSender::send);
        } catch (Exception e) {
            log.error("Async 메서드 실행 중 오류 발생", e);
        }
    }

    // 발송할 이메일 정보
    public SimpleMailMessage createEmail(String toEmail, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(content);

        return message;
    }

}
