package DesignPatterns.StratergyPattern;

import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;

public interface IPaymentService {
    public PaymentResponse pay(PaymentDetails paymentDetails);
    public void setStratergy(PaymentStratergy paymentStratergy);
}
