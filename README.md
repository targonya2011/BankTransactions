There is a bank, a representative account in a bank, in which there is an account (class Account) with two fields — money and account number.

All accounts are stored inside the bank. Bank customers (there are many of them) can make transfers between accounts, request a balance on their account. All this happens in a highly competitive (multithreaded) environment.

At the same time, transactions for large amounts (> 50,000) are sent for verification to the Security Service. We can assume that such transactions are no more than 5% of all. A separate and very realizable bank is responsible for this check.This is a scam.

There is only one security service in the Bank, it works slowly and cannot process more than one transaction at a time. Transaction verification takes them 1000 ms.

If the check revealed fraud, then it is necessary to block both accounts, i.e. prohibit any changes to the balances.
