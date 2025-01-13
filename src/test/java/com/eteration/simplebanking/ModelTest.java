package com.eteration.simplebanking;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.BankAccount;
import com.eteration.simplebanking.model.transaction.DepositTransaction;
import com.eteration.simplebanking.model.transaction.WithdrawalTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

	@Test
	public void testCreateAccountAndSetBalanceZero() {
		BankAccount account = new BankAccount("Kerem Karaca", "17892");
		assertEquals("Kerem Karaca", account.getOwner());
		assertEquals("17892", account.getAccountNumber());
		assertEquals(0.0, account.getBalance());	}

	@Test
	public void testDepositIntoBankAccount() {
		BankAccount account = new BankAccount("Demet Demircan", "9834");
		DepositTransaction depositTransaction = new DepositTransaction(100);
		depositTransaction.apply(account);
		assertEquals(100, account.getBalance());
	}

	@Test
	public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
		BankAccount account = new BankAccount("Demet Demircan", "9834");
		DepositTransaction depositTransaction = new DepositTransaction(100);
		depositTransaction.apply(account);
		assertEquals(100, account.getBalance());

		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(50);
		withdrawalTransaction.apply(account);
		assertEquals(50, account.getBalance());
	}

	@Test
	public void testWithdrawException() {
		Assertions.assertThrows(InsufficientBalanceException.class, () -> {
			BankAccount account = new BankAccount("Demet Demircan", "9834");
			DepositTransaction depositTransaction = new DepositTransaction(100);
			depositTransaction.apply(account);
			assertEquals(100, account.getBalance());

			WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(500);
			withdrawalTransaction.apply(account);
		});
	}

	@Test
	public void testTransactions() throws InsufficientBalanceException {
		// Create account
		BankAccount account = new BankAccount("Canan Kaya", "1234");
		assertEquals(0, account.getTransactions().size());

		// Deposit Transaction
		DepositTransaction depositTrx = new DepositTransaction(100);
		assertNotNull(depositTrx.getDate());
		depositTrx.apply(account);
		assertEquals(100, account.getBalance());
		assertEquals(1, account.getTransactions().size());

		// Withdrawal Transaction
		WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
		assertNotNull(withdrawalTrx.getDate());
		withdrawalTrx.apply(account);
		assertEquals(40, account.getBalance());
		assertEquals(2, account.getTransactions().size());
	}

	@Test
	public void testNegativeDeposit() {
		BankAccount account = new BankAccount("Test User", "0001");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			account.deposit(-100);
		});
	}

	@Test
	public void testNegativeWithdrawal() {
		BankAccount account = new BankAccount("Test User", "0001");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			account.withdraw(-50);
		});
	}

	@Test
	public void testTransactionListIntegrity() throws InsufficientBalanceException {
		BankAccount account = new BankAccount("Test User", "0001");
		assertEquals(0, account.getTransactions().size());

		DepositTransaction depositTransaction = new DepositTransaction(100);
		depositTransaction.apply(account);
		assertEquals(1, account.getTransactions().size());
		assertEquals(depositTransaction, account.getTransactions().get(0));

		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(50);
		withdrawalTransaction.apply(account);
		assertEquals(2, account.getTransactions().size());
		assertEquals(withdrawalTransaction, account.getTransactions().get(1));
	}
}