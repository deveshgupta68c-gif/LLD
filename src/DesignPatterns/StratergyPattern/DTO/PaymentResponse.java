package DesignPatterns.StratergyPattern.DTO;

import lombok.Builder;

@Builder
public class PaymentResponse {
    private String userId;
    private String txnId;
    private Double amount;


    public PaymentResponse(String userId, String txnId, Double amount) {
        this.userId = userId;
        this.txnId = txnId;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getTxnId() {
        return txnId;
    }

    public Double getAmount() {
        return amount;
    }
}
