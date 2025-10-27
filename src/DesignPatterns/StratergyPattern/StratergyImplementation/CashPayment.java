package DesignPatterns.StratergyPattern.StratergyImplementation;

import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;
import DesignPatterns.StratergyPattern.PaymentStratergy;

import java.util.UUID;

public class CashPayment implements PaymentStratergy {
    private PaymentDetails paymentDetails;
    @Override
    public PaymentResponse pay(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        PaymentResponse paymentResponse =  PaymentResponse.builder().txnId("Cash transaction").amount(paymentDetails.getAmount()).userId(paymentDetails.getUserId()).build();
        System.out.println("Completed payment Cash received");
        return  paymentResponse;
    }

    @Override
    public void getPaymentDetails() {
        System.out.println("Last Txn with mode : " + paymentDetails.getPaymentMode());
    }

    @Override
    public void validatePaymentDetails(PaymentDetails paymentDetails) {
        if(paymentDetails.getUserId() == null){
            throw  new RuntimeException("Invalid UserId");
        }
        if(paymentDetails.getAmount() == null || paymentDetails.getAmount() <= 0){
            throw new RuntimeException("Invalid Amount");
        }
    }
}
