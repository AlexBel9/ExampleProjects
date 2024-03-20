public class Threads implements Runnable{

    private Bank bank;
    private String fromAccountNumber;
    private String toAccountNumber;
    private long amount;

    public Threads(Bank bank, String fromAccountNumber, String toAccountNumber, long amount) {
        this.bank = bank;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    @Override
    public void run() {
        bank.transfer(fromAccountNumber,toAccountNumber,amount);
    }
}
