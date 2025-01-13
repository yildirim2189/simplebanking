package com.eteration.simplebanking;

import com.eteration.simplebanking.constants.MessageConstants;
import com.eteration.simplebanking.dto.BankAccountDTO;
import com.eteration.simplebanking.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.payload.request.CommonTransactionRequest;
import com.eteration.simplebanking.payload.request.PhoneBillPaymentTransactionRequest;
import com.eteration.simplebanking.payload.response.CommonTransactionResponse;
import com.eteration.simplebanking.services.BankAccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    public void givenId_GetAccount_thenReturnJson() throws Exception {
        BankAccountDTO account = new BankAccountDTO("Kerem Karaca", "17892", 1000.0);
        Mockito.when(bankAccountService.findBankAccountByAccountNumber(anyString())).thenReturn(account);

        mockMvc.perform(get("/account/v1/17892"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("Kerem Karaca"))
                .andExpect(jsonPath("$.accountNumber").value("17892"))
                .andExpect(jsonPath("$.balance").value(1000.0));
    }

    @Test
    public void givenId_Credit_thenReturnJson() throws Exception {
        CommonTransactionResponse response = new CommonTransactionResponse("OK", "APPROVAL123");
        Mockito.when(bankAccountService.deposit(anyString(), any(CommonTransactionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/account/v1/credit/17892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").value("APPROVAL123"));
    }

    @Test
    public void givenId_Debit_thenReturnJson() throws Exception {
        CommonTransactionResponse response = new CommonTransactionResponse("OK", "APPROVAL124");
        Mockito.when(bankAccountService.withdraw(anyString(), any(CommonTransactionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/account/v1/debit/17892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 50.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").value("APPROVAL124"));
    }

    @Test
    public void givenId_Payment_thenReturnJson() throws Exception {
        CommonTransactionResponse response = new CommonTransactionResponse("OK", "APPROVAL125");
        Mockito.when(bankAccountService.phoneBillPayment(anyString(), any(PhoneBillPaymentTransactionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/account/v1/payment/17892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 150.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").value("APPROVAL125"));
    }

    @Test
    public void givenInvalidId_GetAccount_thenReturnNotFound() throws Exception {
        Mockito.when(bankAccountService.findBankAccountByAccountNumber(anyString()))
                .thenThrow(new BankAccountNotFoundException(MessageConstants.ACCOUNT_NOT_FOUND));

        mockMvc.perform(get("/account/v1/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessageConstants.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void givenId_DebitInsufficientBalance_thenReturnBadRequest() throws Exception {
        Mockito.when(bankAccountService.withdraw(anyString(), any(CommonTransactionRequest.class)))
                .thenThrow(new InsufficientBalanceException("Insufficient balance"));

        mockMvc.perform(post("/account/v1/debit/17892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 5000.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Insufficient balance"));
    }

    @Test
    public void givenId_CreditNegativeAmount_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/account/v1/credit/17892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": -1000.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MessageConstants.AMOUNT_GREATER_THAN_ZERO));
    }
}