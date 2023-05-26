
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.Thread.sleep;

public class Bank {
    @Getter
    private ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private long fraudLimit = 50_000;
    @Getter
    private int numWrngTrnsct = 0; //NumberWarningTransactions

    public synchronized boolean isFraud(Account from, Account to, long amount) {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (random.nextBoolean()) {
            from.blockAccount();
            to.blockAccount();
        }
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAcc = accounts.get(fromAccountNum);
        Account toAcc = accounts.get(toAccountNum);
        Account topSyncAcc = fromAcc;
        Account lowSyncAcc = toAcc;
        if (Integer.parseInt(fromAccountNum) > Integer.parseInt(toAccountNum)) {
            topSyncAcc = toAcc;
            lowSyncAcc = fromAcc;
        }

        synchronized (topSyncAcc) {
            synchronized (lowSyncAcc) {
                String before = stringOutputBefore(fromAcc, toAcc, amount);
                if (!fromAcc.getStatus() && !toAcc.getStatus()
                        && fromAcc.getMoney() >= amount &&
                        !Objects.equals(fromAcc.getAccountNum(), toAcc.getAccountNum())) {
                    fromAcc.setMoney(fromAcc.getMoney() - amount);
                    toAcc.setMoney(toAcc.getMoney() + amount);
                }
                if (!before.equals(stringOutputAfter(fromAcc, toAcc))) {
                    numWrngTrnsct += 1;
                }
            }
            if (amount > fraudLimit) {
                isFraud(fromAcc, toAcc, amount);
            }
        }

    }

    private static String stringOutputAfter(Account from, Account to) {
        return Thread.currentThread().getName()
                + " " + from.getAccountNum() + " " + from.getMoney()
                + " " + to.getAccountNum() + " " + to.getMoney();
    }
    private static String stringOutputBefore(Account from, Account to, long amount) {
        return Thread.currentThread().getName()
                + " " + from.getAccountNum() + " " + (from.getMoney() - amount)
                + " " + to.getAccountNum() + " " + (to.getMoney() + amount);
    }


    public long getBalance(String accountNum) {
        long sum = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (String.valueOf(entry.getValue().getAccountNum()).equals(accountNum)) {
                sum = entry.getValue().getMoney();
            }
        }
        return sum;
    }
    public void addAccountToBank(Account account) {
        accounts.put(String.valueOf(account.getAccountNum()), account);
    }
    public long getSumAllAccounts() {
        long sum = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            sum += entry.getValue().getMoney();
        }
        return sum;
    }

}

