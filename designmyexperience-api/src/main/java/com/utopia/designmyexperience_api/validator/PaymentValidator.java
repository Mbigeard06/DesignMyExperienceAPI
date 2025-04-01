package com.utopia.designmyexperience_api.validator;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class PaymentValidator implements IPaymentValidator {

    private static final String SEPOLIA_RPC_URL = "https://sepolia.infura.io/v3/https://mainnet.infura.io/v3/f5f02cc24d2f451c9543db0d817567a4";
    private static final String RECEIVER_ADDRESS = "0x7Be9992213C89CDc53f086897A20c40d1c0C8C97";

    @Override
    public boolean validatePayment(String transactionHash, double expectedUsdPrice) {
        try {
            Web3j web3 = Web3j.build(new HttpService(SEPOLIA_RPC_URL));

            EthTransaction ethTransaction = web3.ethGetTransactionByHash(transactionHash).send();
            if (ethTransaction.getTransaction().isEmpty()) {
                throw new IllegalStateException("Transaction not found");
            }

            Transaction tx = ethTransaction.getTransaction().get();

            if (tx.getBlockNumber() == null || tx.getBlockNumber().equals(BigInteger.ZERO)) {
                throw new IllegalStateException("Transaction is not mined yet");
            }

            if (!tx.getTo().equalsIgnoreCase(RECEIVER_ADDRESS)) {
                throw new IllegalStateException("Transaction was not sent to the expected wallet address");
            }

            // Fetch ETH/USD price from a service
            double ethToUsdRate = getEthPrice(); // Replace with your actual method
            BigDecimal valueInEth = Convert.fromWei(tx.getValue().toString(), Convert.Unit.ETHER);
            double sentUsd = valueInEth.doubleValue() * ethToUsdRate;

            if (sentUsd < expectedUsdPrice) {
                throw new IllegalStateException("Sent amount is less than expected price");
            }

            return true;

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch transaction from Ethereum network", e);
        }
    }

    /**
     *
     * @return Fake eth price
     */
    private double getEthPrice() {
        return 2000; // Example static value
    }
}