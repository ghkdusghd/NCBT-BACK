package kr.kh.backend.v1.service;

import kr.kh.backend.common.dto.RankDTO;
import kr.kh.backend.v1.mapper.RankMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankService {

    private final RankMapper rankMapper;
    private Map<String, List> results = new ConcurrentHashMap<>();

    /**
     * 랭킹 조회 메서드
     */
    private void findRanker(RankDTO rankDTO) {
        // 1회차 랭킹 조회
        rankDTO.setTable("first_score");
        List<RankDTO> firstDTO = rankMapper.findTop5V2(rankDTO);

        // 다회차 랭킹 조회
        rankDTO.setTable("last_score");
        List<RankDTO> lastDTO = rankMapper.findTop5V2(rankDTO);

        // 리턴값 세팅
        String first = rankDTO.getTitle() + "first"; // EX) NCAfirst, NCP200first
        String last = rankDTO.getTitle() + "last"; // EX) NCAlast, NCP200last
        results.put(first, firstDTO);
        results.put(last, lastDTO);
    }

    /**
     * V1 : 여러 개의 쿼리 수행
     */
    public List<RankDTO> getRankingV1(RankDTO rankDTO) {
        // 1. title 로 subject_id 검색
        rankDTO.setSubjectId(rankMapper.findIdByTitle(rankDTO.getTitle()));

        // 2. 해당하는 subject_id 중에서 score 가 높은 순서로 정렬. (5행까지만 추출)
        return rankMapper.findTop5V1(rankDTO);
    }

    /**
     * V2 : join 쿼리로 한번에 검색
     */
    public List<RankDTO> getRankingV2(String title) {
        log.info("랭킹 서비스 !!!!!");
        List<RankDTO> result = rankMapper.findTop5(title);
        return result;
    }

}
