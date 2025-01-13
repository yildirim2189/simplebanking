package com.eteration.simplebanking.services;

import com.eteration.simplebanking.constants.MessageConstants;
import com.eteration.simplebanking.dto.BankAccountDTO;
import com.eteration.simplebanking.dto.TransactionDTO;
import com.eteration.simplebanking.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.BankAccount;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.model.transaction.Transaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.payload.request.CommonTransactionRequest;
import com.eteration.simplebanking.payload.request.PhoneBillPaymentTransactionRequest;
import com.eteration.simplebanking.payload.response.CommonTransactionResponse;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing bank accounts and transactions.
 */
@Service
@Transactional
public class BankAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Creates a new bank account.
     *
     * @param owner the owner of the bank account
     * @param accountNumber the account number
     * @param initialBalance the initial balance
     * @return the created BankAccountDTO
     */
    public BankAccountDTO createBankAccount(String owner, String accountNumber, double initialBalance) {
        logger.info("Creating bank account for owner: {}, account number: {}", owner, accountNumber);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(owner);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBalance(initialBalance);

        bankAccountRepository.save(bankAccount);

        logger.info("Bank account created successfully for account number: {}", accountNumber);
        return convertToDTO(bankAccount);
    }

    /**
     * Finds a bank account by account number.
     *
     * @param accountNumber the account number
     * @return the found BankAccountDTO
     * @throws BankAccountNotFoundException if the bank account is not found
     */
    public BankAccountDTO findBankAccountByAccountNumber(String accountNumber) {
        logger.info("Finding bank account with account number: {}", accountNumber);

        BankAccount bankAccount = bankAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));

        logger.info("Bank account found for account number: {}", accountNumber);
        return convertToDTO(bankAccount);
    }

    /**
     * Processes a deposit transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @return the transaction response
     */
    public CommonTransactionResponse deposit(String accountNumber, CommonTransactionRequest request) {
        logger.info("Processing deposit for account number: {}, amount: {}", accountNumber, request.getAmount());

        if (request.getAmount() <= 0) {
            logger.error("Deposit amount must be greater than zero for account number: {}", accountNumber);
            throw new IllegalArgumentException(MessageConstants.AMOUNT_GREATER_THAN_ZERO);
        }

        logger.info("Deposit processed successfully for account number: {}", accountNumber);
        return processTransaction(accountNumber, request, new DepositTransaction(request.getAmount()));
    }

    /**
     * Processes a withdrawal transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @return the transaction response
     * @throws InsufficientBalanceException if the balance is insufficient
     */
    public CommonTransactionResponse withdraw(String accountNumber, CommonTransactionRequest request) throws InsufficientBalanceException {
        logger.info("Processing withdrawal for account number: {}, amount: {}", accountNumber, request.getAmount());

        return processTransaction(accountNumber, request, new WithdrawalTransaction(request.getAmount()));
    }

    /**
     * Processes a phone bill payment transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @return the transaction response
     * @throws InsufficientBalanceException if the balance is insufficient
     */
    public CommonTransactionResponse phoneBillPayment(String accountNumber, PhoneBillPaymentTransactionRequest request) throws InsufficientBalanceException {
        logger.info("Processing phone bill payment for account number: {}, amount: {}, phone number: {}", accountNumber, request.getAmount(), request.getPhoneNumber());
        return processTransaction(accountNumber, request, new PhoneBillPaymentTransaction(request.getAmount(), request.getPhoneNumber(), request.getPayee()));
    }


    /**
     * Processes a transaction.
     *
     * @param accountNumber the account number
     * @param request the transaction request
     * @param transaction the transaction to process
     * @return the transaction response
     */
    private CommonTransactionResponse processTransaction(String accountNumber, CommonTransactionRequest request, Transaction transaction) {
        BankAccount bankAccount = bankAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException(MessageConstants.COULD_NOT_FIND_ACCOUNT + accountNumber));

        String approvalCode = UUID.randomUUID().toString();
        transaction.setDate(new Date());
        transaction.setApprovalCode(approvalCode);
        transaction.setAccount(bankAccount);

        transaction.apply(bankAccount);

        bankAccountRepository.save(bankAccount);
        logger.info("Transaction processed successfully for account number: {}, approval code: {}", accountNumber, approvalCode);

        return new CommonTransactionResponse(MessageConstants.OK, approvalCode);
    }

    /**
     * Converts a BankAccount entity to a BankAccountDTO.
     *
     * @param bankAccount the bank account entity
     * @return the converted BankAccountDTO
     */
    private BankAccountDTO convertToDTO(BankAccount bankAccount) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setOwner(bankAccount.getOwner());
        dto.setAccountNumber(bankAccount.getAccountNumber());
        dto.setBalance(bankAccount.getBalance());
        dto.setCreateDate(bankAccount.getCreateDate());

        List<TransactionDTO> transactionDTOs = bankAccount.getTransactions().stream()
                .map(this::convertTransactionToDTO)
                .collect(Collectors.toList());
        dto.setTransactions(transactionDTOs);

        return dto;
    }

    /**
     * Converts a Transaction entity to a TransactionDTO.
     *
     * @param transaction the transaction entity
     * @return the converted TransactionDTO
     */
    private TransactionDTO convertTransactionToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setDate(transaction.getDate());
        dto.setAmount(transaction.getAmount());
        dto.setApprovalCode(transaction.getApprovalCode());
        dto.setTransactionType(transaction.getClass().getSimpleName());
        return dto;
    }
}