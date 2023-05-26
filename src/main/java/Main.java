import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 1; i < 100; i++) {
            Account account = new Account(i, getRandomLong(22_000, 100_000));
            bank.addAccountToBank(account);
        }
        for (int i = 0; i < 8; i++) {
            Thread threadOne = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    bank.transfer(getRandomInt(1, 99), getRandomInt(1, 99), getRandomLong(1_000, 51_000));
                }
            });
            threads.add(threadOne);
        }


        long time2Sync = System.currentTimeMillis();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("\nComputing time with double synchronized var - " + (System.currentTimeMillis() - time2Sync) + " ms");
        System.out.printf(bank.getNumWrngTrnsct() + " wrong transactions\n");


    }
    private static Long getRandomLong(long f, long t) {
        return f + (long) (Math.random() * t);
    }

    private static String getRandomInt(int f, int t) {
        return String.valueOf((int) (f + (Math.random() * t)));
    }
}
