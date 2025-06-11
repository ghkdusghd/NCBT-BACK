package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.ExamDTO;
import kr.kh.backend.common.exception.custom.RecordFailureException;
import kr.kh.backend.common.exception.custom.UserNotFoundException;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.v2.entity.Record;
import kr.kh.backend.v2.entity.User;
import kr.kh.backend.v2.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RecordRepository recordRepository;
    private final UserService userService;

    public ExamServiceImpl(JwtTokenProvider jwtTokenProvider, RecordRepository recordRepository, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    public void recordScore(ExamDTO examDTO, String token) {
        log.info("exam Service !!!");
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.findByNickname(username);

        Record record = Record.builder()
                .user(user)
                .subject(examDTO.getSubjectTitle())
                .score(examDTO.getScore())
                .createdAt(LocalDateTime.now())
                .build();

        recordRepository.save(record);

        if(record == null) {
            throw new RecordFailureException("점수 등록에 실패했습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        }
    }

}
