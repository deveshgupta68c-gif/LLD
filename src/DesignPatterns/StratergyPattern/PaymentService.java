package DesignPatterns.StratergyPattern;

import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;

public class PaymentService implements IPaymentService{
    private PaymentStratergy paymentStratergy;

    @Override
    public PaymentResponse pay(PaymentDetails paymentDetails) {
        if(paymentStratergy == null){
            System.out.println("Error first select Payment Stratergy");
            return null;
        }
        paymentStratergy.validatePaymentDetails(paymentDetails);
        return paymentStratergy.pay(paymentDetails);

    }

    @Override
    public void setStratergy(PaymentStratergy paymentStratergy) {
        System.out.println("setting payment type");
        this.paymentStratergy = paymentStratergy;
    }
}
