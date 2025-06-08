package kr.kh.backend.v2.mapper;

import kr.kh.backend.common.dto.RankDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RankMapperV2 {
    @Select("SELECT u.nickname, t.score " +
            "FROM users u " +
            "INNER JOIN records t ON u.id = t.user_id " +
            "WHERE t.subject = #{title} " +
            "ORDER BY t.score DESC, t.created_at DESC LIMIT 5")
    List<RankDTO> findTop5(String title);
}
