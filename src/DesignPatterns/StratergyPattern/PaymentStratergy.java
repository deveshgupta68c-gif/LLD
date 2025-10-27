package DesignPatterns.StratergyPattern;

import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;

public interface PaymentStratergy {
    public PaymentResponse pay(PaymentDetails paymentDetails);
    public void getPaymentDetails();
    public void validatePaymentDetails(PaymentDetails paymentDetails);
}
