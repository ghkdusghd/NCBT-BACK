package kr.kh.backend.v2.service;

import kr.kh.backend.common.dto.RankDTO;
import java.util.List;

public interface RankService {

    List<RankDTO> getRanking(String title);

}
