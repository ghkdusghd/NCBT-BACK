package kr.kh.backend.v2.repository;

import kr.kh.backend.v2.entity.Complaint;
import kr.kh.backend.v2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintsRepository extends JpaRepository<Complaint, Long> {

    Complaint findByUserAndQuestionId(User user, Long questionId);

}
