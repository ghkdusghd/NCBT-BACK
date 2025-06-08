package kr.kh.backend.v2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private SubjectTitle subject;

    @Column(name = "question_id")
    private Long questionId;

    private String title;
    private String comment;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}