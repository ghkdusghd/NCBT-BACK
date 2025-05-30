package kr.kh.backend.v1.domain;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Payments {
    private Long id;
    private String orderId;
    private String paymentKey;
    private Integer amount;
    private String payType;
    private String status;
    private Timestamp paidAt;
}
