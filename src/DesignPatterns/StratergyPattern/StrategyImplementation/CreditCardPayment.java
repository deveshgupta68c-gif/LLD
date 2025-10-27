package DesignPatterns.StratergyPattern.StrategyImplementation;


import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;
import DesignPatterns.StratergyPattern.PaymentStrategy;

import java.util.Objects;
import java.util.UUID;

public class CreditCardPayment implements PaymentStrategy {
    private PaymentDetails paymentDetails;
    @Override
    public PaymentResponse pay(PaymentDetails paymentDetails) {
        PaymentResponse paymentResponse =  PaymentResponse.builder().txnId(UUID.randomUUID().toString()).amount(paymentDetails.getAmount()).userId(paymentDetails.getUserId()).build();
        System.out.println("Completed Txn with txn ID : " + paymentResponse.getTxnId());
        this.paymentDetails = paymentDetails;
        return  paymentResponse;
    }

    @Override
    public void getPaymentDetails() {
        System.out.println("Last Txn with mode : " + paymentDetails.getPaymentMode());
    }

    @Override
    public void validatePaymentDetails(PaymentDetails paymentDetails) {
        System.out.println("Validating Payment Details");
        if(Objects.isNull(paymentDetails)) {
            throw new RuntimeException("Please Ensure all details are present before making the request");
        }
        if(paymentDetails.getCardNumber() == null || paymentDetails.getCardNumber().isEmpty()){
            throw new RuntimeException("Invalid Card Number");
        }
        if(!paymentDetails.getPaymentMode().equalsIgnoreCase("credit_card")){
            throw new RuntimeException("This payment method only supports credit card");
        }
        if(paymentDetails.getExpiryDate() == null || paymentDetails.getExpiryDate().isEmpty()){
            throw new RuntimeException("Invalid Expiry Date");
        }
        if(paymentDetails.getCvv() == null || paymentDetails.getCvv().isEmpty()){
            throw new RuntimeException("Invalid CVV");
        }
        if(paymentDetails.getAmount() == null || paymentDetails.getAmount() <= 0){
            throw new RuntimeException("Invalid Amount");
        }
    }
}
