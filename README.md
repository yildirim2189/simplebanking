# Simplebanking

In this assignment you will build a banking service that can handle any number of transactions for bank accounts. The service is part of a larger collection of services that model the inner workings of a bank. The services for the "bank account" provide a simple model of how bank accounts might work in an overly simplified world.

For this assignment, the bank account is exclusively interested in maintaining the name of the account owner, the number of the account and the account’s balance. The endpoints will be limited to methods that provide a means of crediting and debiting the account.
The following code demonstrates how BankAccounts might be used

To deposit money:

    POST
    /account/v1/credit/669-7788
    request data:
    {
        "amount": 1000.0
    }
    response  (200):
    {
        "status": "Completed",
        "transactionId": "123123-acsas-safas-23"
    }

To withdraw money:

    POST
    /account/v1/debit/669-7788
    request data:
    {
        "amount": 50.0
    }
    response  (200):
    {
        "status": "Completed",
        "transactionId": "7567-acsas-safas-23"
    }

To get the current account data:

    GET
    /account/v1/669-7788
    {
        "owner": "Ali Doğru",
        "number": "669-7788"
        "balance": 950.0
    }

Your data model for the bank account object must have fields owner where the field type is java.lang.String, fields to hold the account number (String) and balance (double). the credit() service as specified above adds the supplied amount to the receiving BankAccounts balance.
and the the debit() service subtracts the supplied amount from the receiving BankAccounts balance.  

The object model for our banking system must include transaction objects. A transaction object keeps track of the kind of transaction (deposit, withdrawal, payments etc.) as well as the date and amount of the transaction. Each transaction type will require its own parameters. The following diagram shows how BankAccounts and Transactions are related. An instance of DepositTransaction represents a deposit; a WithdrawalTransaction represents a withdrawal (the triangle on the diagram indicates inheritance). Inheritance for the PhoneBillPaymentTransaction, CheckTransaction etc. is not shown - you must decide where to put this class.  All transactions must have  have the fields date and amount at a minimum. The date field should contain the time of the transaction and should nbe automatically calculated.
 

These transaction objects will be used both to make financial requests of a BankAccount and to keep a record of those requests. The following Unit code test segment indicates how transactions will be used on the service side:

    BankAccount account = new BankAccount("Jim", 12345);
    account.post(new DepositTransaction(1000));
    account.post(new WithdrawalTransaction(200));
    account.post(new PhoneBillPaymentTransaction("Vodafone", "5423345566", 96.50));
    assertEquals(account.getBalance(), 703.50, 0.0001)


BONUS
Th bank account post method must do omething special for each Transaction type. e.g. post(DepositTransaction) and post(WithdrawalTransaction. This solution will work but creating families of overloaded methods is discouraged as it causes problems with maintenance. Consider, if we added more Transaction subclasses we would need to keep changing the BankAccount class, overloading even more post methods. It is considered bad form in OO  to write case statements based on the type of objects. It also has the same maintenance problems as the first solution. Adding more Transaction subclasses would require changes. Find a solution to delegate the operation using polymorphism so that the Bankount is never changed by introducing new transaaction types.




