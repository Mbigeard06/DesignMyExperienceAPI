package com.utopia.designmyexperience_api.validator;

public interface IPaymentValidator {
    /**
     *
     * @return status of the payement
     */
    public boolean validatePayment(String transactionHash, double price);
}
