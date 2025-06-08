package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.RankDTO;
import kr.kh.backend.v2.mapper.RankMapperV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RankServiceImpl implements RankService {

    private final RankMapperV2 rankMapper;

    public RankServiceImpl(RankMapperV2 rankMapper) {
        this.rankMapper = rankMapper;
    }

    @Override
    public List<RankDTO> getRanking(String title) {
        List<RankDTO> result = rankMapper.findTop5(title);
        return result;
    }

}
