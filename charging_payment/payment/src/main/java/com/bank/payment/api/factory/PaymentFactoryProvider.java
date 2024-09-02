package com.bank.payment.api.factory;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:04 PM
 */
public class PaymentFactoryProvider {

    public static PaymentFactory getPaymentFactory(String paymentType) {
        return switch (paymentType.toLowerCase()) {
            case "direct" -> new DirectPaymentFactory();
            case "gateway" -> new GatewayPaymentFactory();
            default -> throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        };
    }

}
