import lombok.*;

@Setter
@Getter
public class Account {
    private int accountNum;
    private long money;
    private boolean isBlocked;

    public Account(int accountNum, long money) {
        this.accountNum = accountNum;
        this.money = money;
        this.isBlocked = false;
    }

    public synchronized boolean getStatus() {
        return isBlocked;
    }

    public void blockAccount() {
        isBlocked = true;
        setMoney(0);
    }
}
