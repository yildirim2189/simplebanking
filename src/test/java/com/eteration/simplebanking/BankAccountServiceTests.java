package com.eteration.simplebanking;

import com.eteration.simplebanking.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.BankAccount;
import com.eteration.simplebanking.payload.request.CommonTransactionRequest;
import com.eteration.simplebanking.payload.response.CommonTransactionResponse;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.services.BankAccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class BankAccountServiceTests {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("Kerem Karaca", "17892", 1000.0);
        Mockito.when(bankAccountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        CommonTransactionRequest request = new CommonTransactionRequest(500.0);
        CommonTransactionResponse response = bankAccountService.deposit("17892", request);

        assertEquals("OK", response.getStatus());
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        BankAccount account = new BankAccount("Kerem Karaca", "17892", 1000.0);
        Mockito.when(bankAccountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        CommonTransactionRequest request = new CommonTransactionRequest(1500.0);
        assertThrows(InsufficientBalanceException.class, () -> {
            bankAccountService.withdraw("17892", request);
        });
    }
}