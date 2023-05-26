import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
    Account ac1;
    Account ac2;
    Account ac3;
    Account ac4;
    Account ac5;
    Account ac6;
    Account ac7;
    Account ac8;

    Bank bank = new Bank();



    @Before
    public void setUp() throws Exception {
        bank = new Bank();
        ac1 = new Account(1, 21595);
        ac2 = new Account(2, 54563);
        ac3 = new Account(3, 76123);
        ac4 = new Account(4, 13465);
        ac5 = new Account(5, 121595);
        ac6 = new Account(6, 38173);
        ac7 = new Account(7, 163822);
        ac8 = new Account(8, 72565);

        bank.addAccountToBank(ac1);
        bank.addAccountToBank(ac2);
        bank.addAccountToBank(ac3);
        bank.addAccountToBank(ac4);
        bank.addAccountToBank(ac5);
        bank.addAccountToBank(ac6);
        bank.addAccountToBank(ac7);
        bank.addAccountToBank(ac8);

    }

    @Test
    public void testAllSumMoneyAccounts() {
        System.out.print("\n┌---------testAllSumMoneyAccounts()---------┐");
        long expected = ac1.getMoney() + ac2.getMoney() + ac3.getMoney() + ac4.getMoney()
                + ac5.getMoney() + ac6.getMoney() + ac7.getMoney() + ac8.getMoney();
        long actual = bank.getSumAllAccounts();
        Assert.assertEquals(expected, actual);
        System.out.print("└---------------------end-------------------┘");
        System.out.print("\n");
    }

    @Test
    public void testTransferBeforeVerificationLimit() {
        System.out.print("\n┌---testTransferBeforeVerificationLimit()---┐");
        bank.transfer(String.valueOf(ac1.getAccountNum()), String.valueOf(ac2.getAccountNum()), 10_000);
        long expectedClientOne = 11595;
        long actualClientOne = bank.getBalance(String.valueOf(ac1.getAccountNum()));
        Assert.assertEquals(expectedClientOne, actualClientOne);

        long expectedClientTwo = 64563;
        long actualClientTwo = bank.getBalance(String.valueOf(ac2.getAccountNum()));
        Assert.assertEquals(expectedClientTwo, actualClientTwo);
        System.out.print("\n└-------------------end---------------------┘");
        System.out.print("\n");
    }

    @Test
    public void testTransferAfterVerificationLimit() {
        System.out.print("\n┌---testTransferAfterVerificationLimit()----┐");
        bank.transfer(String.valueOf(ac2.getAccountNum()), String.valueOf(ac3.getAccountNum()), 50_001);
        long expectedClientOne;
        long expectedClientTwo;
        if (ac2.getStatus()) {
            expectedClientOne = 0;
            expectedClientTwo = 0;
        } else {
            expectedClientOne = 4562;
            expectedClientTwo = 126124;
        }
        long actualClientOne = bank.getBalance(String.valueOf(ac2.getAccountNum()));
        Assert.assertEquals(expectedClientOne, actualClientOne);


        long actualClientTwo = bank.getBalance(String.valueOf(ac3.getAccountNum()));
        Assert.assertEquals(expectedClientTwo, actualClientTwo);
        System.out.print("\n└------------------end----------------------┘");
        System.out.print("\n");
    }

    @Test
    public void testSumMoneyAfterTransfer(){
        System.out.print("\n┌---------testSumMoneyAfterTransfer---------┐");
        bank.transfer(String.valueOf(ac4.getAccountNum()), String.valueOf(ac5.getAccountNum()), 10_000);
        long expectedSumMoneyAfterTransfer = 561901;
        long actualSumMoneyAfterTransfer = bank.getSumAllAccounts();
        Assert.assertEquals(expectedSumMoneyAfterTransfer, actualSumMoneyAfterTransfer);
        System.out.print("└------------------end----------------------┘");
        System.out.print("\n");
    }
}


