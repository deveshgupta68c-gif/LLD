package DesignPatterns.StratergyPattern.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class PaymentDetails {
    private String userId;
    private Double amount;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String paymentMode;
}
