package DesignPatterns.StratergyPattern;

import DesignPatterns.StratergyPattern.DTO.PaymentDetails;
import DesignPatterns.StratergyPattern.DTO.PaymentResponse;
import DesignPatterns.StratergyPattern.StratergyImplementation.CashPayment;
import DesignPatterns.StratergyPattern.StratergyImplementation.CreditCardPayment;

public class PaymentStrategyTest {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("STRATEGY PATTERN TEST SUITE");
        System.out.println("========================================\n");

        int totalTests = 0;
        int passedTests = 0;

        // Test 1: Credit Card Payment - Valid Case
        totalTests++;
        if (testCreditCardPaymentValid()) {
            passedTests++;
            System.out.println("✓ Test 1 PASSED: Credit Card Payment - Valid Case\n");
        } else {
            System.out.println("✗ Test 1 FAILED: Credit Card Payment - Valid Case\n");
        }

        // Test 2: Credit Card Payment - Invalid Card Number
        totalTests++;
        if (testCreditCardInvalidCardNumber()) {
            passedTests++;
            System.out.println("✓ Test 2 PASSED: Credit Card Payment - Invalid Card Number\n");
        } else {
            System.out.println("✗ Test 2 FAILED: Credit Card Payment - Invalid Card Number\n");
        }

        // Test 3: Credit Card Payment - Invalid Payment Mode
        totalTests++;
        if (testCreditCardInvalidPaymentMode()) {
            passedTests++;
            System.out.println("✓ Test 3 PASSED: Credit Card Payment - Invalid Payment Mode\n");
        } else {
            System.out.println("✗ Test 3 FAILED: Credit Card Payment - Invalid Payment Mode\n");
        }

        // Test 4: Credit Card Payment - Invalid Expiry Date
        totalTests++;
        if (testCreditCardInvalidExpiryDate()) {
            passedTests++;
            System.out.println("✓ Test 4 PASSED: Credit Card Payment - Invalid Expiry Date\n");
        } else {
            System.out.println("✗ Test 4 FAILED: Credit Card Payment - Invalid Expiry Date\n");
        }

        // Test 5: Credit Card Payment - Invalid CVV
        totalTests++;
        if (testCreditCardInvalidCVV()) {
            passedTests++;
            System.out.println("✓ Test 5 PASSED: Credit Card Payment - Invalid CVV\n");
        } else {
            System.out.println("✗ Test 5 FAILED: Credit Card Payment - Invalid CVV\n");
        }

        // Test 6: Credit Card Payment - Invalid Amount
        totalTests++;
        if (testCreditCardInvalidAmount()) {
            passedTests++;
            System.out.println("✓ Test 6 PASSED: Credit Card Payment - Invalid Amount\n");
        } else {
            System.out.println("✗ Test 6 FAILED: Credit Card Payment - Invalid Amount\n");
        }

        // Test 7: Credit Card Payment - Null Payment Details
        totalTests++;
        if (testCreditCardNullPaymentDetails()) {
            passedTests++;
            System.out.println("✓ Test 7 PASSED: Credit Card Payment - Null Payment Details\n");
        } else {
            System.out.println("✗ Test 7 FAILED: Credit Card Payment - Null Payment Details\n");
        }

        // Test 8: Cash Payment - Valid Case
        totalTests++;
        if (testCashPaymentValid()) {
            passedTests++;
            System.out.println("✓ Test 8 PASSED: Cash Payment - Valid Case\n");
        } else {
            System.out.println("✗ Test 8 FAILED: Cash Payment - Valid Case\n");
        }

        // Test 9: Payment Without Setting Strategy
        totalTests++;
        if (testPaymentWithoutStrategy()) {
            passedTests++;
            System.out.println("✓ Test 9 PASSED: Payment Without Setting Strategy\n");
        } else {
            System.out.println("✗ Test 9 FAILED: Payment Without Setting Strategy\n");
        }

        // Test 10: Switch Strategy at Runtime
        totalTests++;
        if (testSwitchStrategyAtRuntime()) {
            passedTests++;
            System.out.println("✓ Test 10 PASSED: Switch Strategy at Runtime\n");
        } else {
            System.out.println("✗ Test 10 FAILED: Switch Strategy at Runtime\n");
        }

        // Print Summary
        System.out.println("========================================");
        System.out.println("TEST SUMMARY");
        System.out.println("========================================");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success Rate: " + String.format("%.2f", (passedTests * 100.0 / totalTests)) + "%");
        System.out.println("========================================");
    }

    // Test 1: Valid Credit Card Payment
    private static boolean testCreditCardPaymentValid() {
        System.out.println("Test 1: Credit Card Payment - Valid Case");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user123");
            paymentDetails.setAmount(1000.0);
            paymentDetails.setCardNumber("1234-5678-9012-3456");
            paymentDetails.setExpiryDate("12/25");
            paymentDetails.setCvv("123");
            paymentDetails.setPaymentMode("credit_card");

            PaymentResponse response = paymentService.pay(paymentDetails);

            if (response != null && response.getAmount().equals(1000.0) &&
                    response.getUserId().equals("user123") && response.getTxnId() != null) {
                return true;
            }
            System.out.println("ERROR: Response validation failed");
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    // Test 2: Invalid Card Number
    private static boolean testCreditCardInvalidCardNumber() {
        System.out.println("Test 2: Credit Card Payment - Invalid Card Number");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user123");
            paymentDetails.setAmount(1000.0);
            paymentDetails.setCardNumber(""); // Invalid
            paymentDetails.setExpiryDate("12/25");
            paymentDetails.setCvv("123");
            paymentDetails.setPaymentMode("credit_card");

            paymentService.pay(paymentDetails);
            System.out.println("ERROR: Should have thrown exception for invalid card number");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
            return e.getMessage().contains("Invalid Card Number");
        }
    }

    // Test 3: Invalid Payment Mode
    private static boolean testCreditCardInvalidPaymentMode() {
        System.out.println("Test 3: Credit Card Payment - Invalid Payment Mode");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user123");
            paymentDetails.setAmount(1000.0);
            paymentDetails.setCardNumber("1234-5678-9012-3456");
            paymentDetails.setExpiryDate("12/25");
            paymentDetails.setCvv("123");
            paymentDetails.setPaymentMode("cash"); // Wrong mode

            paymentService.pay(paymentDetails);
            System.out.println("ERROR: Should have thrown exception for invalid payment mode");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
            return e.getMessage().contains("only supports credit card");
        }
    }

    // Test 4: Invalid Expiry Date
    private static boolean testCreditCardInvalidExpiryDate() {
        System.out.println("Test 4: Credit Card Payment - Invalid Expiry Date");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user123");
            paymentDetails.setAmount(1000.0);
            paymentDetails.setCardNumber("1234-5678-9012-3456");
            paymentDetails.setExpiryDate(""); // Invalid
            paymentDetails.setCvv("123");
            paymentDetails.setPaymentMode("credit_card");

            paymentService.pay(paymentDetails);
            System.out.println("ERROR: Should have thrown exception for invalid expiry date");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
            return e.getMessage().contains("Invalid Expiry Date");
        }
    }

    // Test 5: Invalid CVV
    private static boolean testCreditCardInvalidCVV() {
        System.out.println("Test 5: Credit Card Payment - Invalid CVV");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user123");
            paymentDetails.setAmount(1000.0);
            paymentDetails.setCardNumber("1234-5678-9012-3456");
            paymentDetails.setExpiryDate("12/25");
            paymentDetails.setCvv(null); // Invalid
            paymentDetails.setPaymentMode("credit_card");

            paymentService.pay(paymentDetails);
            System.out.println("ERROR: Should have thrown exception for invalid CVV");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
            return e.getMessage().contains("Invalid CVV");
        }
    }

    // Test 6: Invalid Amount
    private static boolean testCreditCardInvalidAmount() {
        System.out.println("Test 6: Credit Card Payment - Invalid Amount");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user123");
            paymentDetails.setAmount(-100.0); // Invalid
            paymentDetails.setCardNumber("1234-5678-9012-3456");
            paymentDetails.setExpiryDate("12/25");
            paymentDetails.setCvv("123");
            paymentDetails.setPaymentMode("credit_card");

            paymentService.pay(paymentDetails);
            System.out.println("ERROR: Should have thrown exception for invalid amount");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
            return e.getMessage().contains("Invalid Amount");
        }
    }

    // Test 7: Null Payment Details
    private static boolean testCreditCardNullPaymentDetails() {
        System.out.println("Test 7: Credit Card Payment - Null Payment Details");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CreditCardPayment());

            paymentService.pay(null);
            System.out.println("ERROR: Should have thrown exception for null payment details");
            return false;
        } catch (RuntimeException e) {
            System.out.println("Expected exception caught: " + e.getMessage());
            return e.getMessage().contains("all details are present");
        }
    }

    // Test 8: Valid Cash Payment
    private static boolean testCashPaymentValid() {
        System.out.println("Test 8: Cash Payment - Valid Case");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            paymentService.setStratergy(new CashPayment());

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user456");
            paymentDetails.setAmount(500.0);
            paymentDetails.setPaymentMode("cash");

            PaymentResponse response = paymentService.pay(paymentDetails);

            if (response != null && response.getAmount().equals(500.0) &&
                    response.getUserId().equals("user456") && 
                    response.getTxnId().equals("Cash transaction")) {
                return true;
            }
            System.out.println("ERROR: Response validation failed");
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    // Test 9: Payment Without Setting Strategy
    private static boolean testPaymentWithoutStrategy() {
        System.out.println("Test 9: Payment Without Setting Strategy");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();
            // Not setting any strategy

            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setUserId("user789");
            paymentDetails.setAmount(750.0);

            PaymentResponse response = paymentService.pay(paymentDetails);

            if (response == null) {
                System.out.println("Expected behavior: Returned null when strategy not set");
                return true;
            }
            System.out.println("ERROR: Should have returned null without strategy");
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Unexpected exception: " + e.getMessage());
            return false;
        }
    }

    // Test 10: Switch Strategy at Runtime
    private static boolean testSwitchStrategyAtRuntime() {
        System.out.println("Test 10: Switch Strategy at Runtime");
        System.out.println("------------------------------------------");
        try {
            PaymentService paymentService = new PaymentService();

            // First payment with Credit Card
            paymentService.setStratergy(new CreditCardPayment());
            PaymentDetails creditCardDetails = new PaymentDetails();
            creditCardDetails.setUserId("user999");
            creditCardDetails.setAmount(2000.0);
            creditCardDetails.setCardNumber("1234-5678-9012-3456");
            creditCardDetails.setExpiryDate("12/25");
            creditCardDetails.setCvv("123");
            creditCardDetails.setPaymentMode("credit_card");

            PaymentResponse response1 = paymentService.pay(creditCardDetails);
            System.out.println("First payment completed with Credit Card");

            // Switch to Cash Payment
            paymentService.setStratergy(new CashPayment());
            PaymentDetails cashDetails = new PaymentDetails();
            cashDetails.setUserId("user999");
            cashDetails.setAmount(500.0);
            cashDetails.setPaymentMode("cash");

            PaymentResponse response2 = paymentService.pay(cashDetails);
            System.out.println("Second payment completed with Cash");

            if (response1 != null && response2 != null &&
                    !response1.getTxnId().equals("Cash transaction") &&
                    response2.getTxnId().equals("Cash transaction")) {
                return true;
            }
            System.out.println("ERROR: Strategy switching validation failed");
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
