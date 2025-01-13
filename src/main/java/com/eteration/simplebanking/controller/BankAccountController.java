package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.BankAccountDTO;
import com.eteration.simplebanking.payload.request.CommonTransactionRequest;
import com.eteration.simplebanking.payload.request.PhoneBillPaymentTransactionRequest;
import com.eteration.simplebanking.payload.response.CommonTransactionResponse;
import com.eteration.simplebanking.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing bank account-related endpoints.
 */
@RestController
@RequestMapping("/account/${api.version}")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Retrieves a bank account by account number.
     *
     * @param accountNumber the account number
     * @return the bank account details
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getAccount(@PathVariable String accountNumber) {
        BankAccountDTO response = bankAccountService.findBankAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * Processes a withdrawal transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @return the transaction response
     */
    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<CommonTransactionResponse> withdraw(@PathVariable String accountNumber,
                                                              @RequestBody CommonTransactionRequest request) {

        CommonTransactionResponse response = bankAccountService.withdraw(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Processes a deposit transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @return the transaction response
     */
    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<CommonTransactionResponse> deposit(@PathVariable String accountNumber,
                                                             @RequestBody CommonTransactionRequest request) {

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        CommonTransactionResponse response = bankAccountService.deposit(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Processes a phone bill payment transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @return the transaction response
     */
    @PostMapping("/payment/{accountNumber}")
    public ResponseEntity<CommonTransactionResponse> payment(@PathVariable String accountNumber,
                                                             @RequestBody PhoneBillPaymentTransactionRequest request) {

        CommonTransactionResponse response = bankAccountService.phoneBillPayment(accountNumber, request);
        return ResponseEntity.ok(response);
    }
}