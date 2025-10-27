package DesignPatterns.StratergyPattern;

import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;

public class PaymentService implements IPaymentService{
    private PaymentStrategy paymentStrategy;

    @Override
    public PaymentResponse pay(PaymentDetails paymentDetails) {
        if(paymentStrategy == null){
            System.out.println("Error first select Payment Stratergy");
            return null;
        }
        paymentStrategy.validatePaymentDetails(paymentDetails);
        return paymentStrategy.pay(paymentDetails);

    }

    @Override
    public void setStratergy(PaymentStrategy paymentStrategy) {
        System.out.println("setting payment type");
        this.paymentStrategy = paymentStrategy;
    }
}
