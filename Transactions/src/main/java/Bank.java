
import java.util.Map;
import java.util.Random;

public class Bank {
    private Map<String, Account> accounts;
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) {
        if(!fromAccountNum.equals(toAccountNum)){
            accounts.entrySet().stream().forEach(entryFrom -> {
                if(entryFrom.getValue().getAccNumber().equals(fromAccountNum)){
                    Account accFrom = entryFrom.getValue();
                    accounts.entrySet().stream().forEach(entryTo ->{
                        if(entryTo.getValue().getAccNumber().equals(toAccountNum)){
                            Account accTo = entryTo.getValue();
                            if(accFrom.getMoney() >= amount){
                                 if(accFrom.getMoney() < amount){
                                     try {
                                         wait();
                                     } catch (InterruptedException e) {
                                         e.printStackTrace();
                                     }
                                 }
                                    System.out.printf("остаток до перевода на счёте %s: %d руб |\t" +
                                            "остаток до перевода на счёте %s: %d руб\n",accFrom.getAccNumber(),accFrom.getMoney()
                                            ,accTo.getAccNumber(),accTo.getMoney());
                                    accFrom.setMoney(accFrom.getMoney()-amount);
                                    accTo.setMoney(accTo.getMoney()+amount);
                                    getBalanceAccounts(accFrom, accTo,amount);
                            }
                        }
                    });
                }
            });
        } else System.out.println("невозможно перевести деньги на тот же счёт с которого" +
                " совершается перевод");
    }

    private void getBalanceAccounts(Account accountFrom, Account accountTo, long amount){
        System.out.printf("перевод со счёта %s остаток %d руб.|  сумма перевода: %d руб | перевод получен на счёт %s остаток %d\n"
                ,accountFrom.getAccNumber(), accountFrom.getMoney(), amount, accountTo.getAccNumber(), accountTo.getMoney());
        notify();
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        for(Map.Entry<String, Account> entry: accounts.entrySet()){
            if(entry.getKey().equals(accountNum)){
                return entry.getValue().getMoney();
            }
        }
        return 0;
    }

    public long getSumAllAccounts() {
       return accounts.entrySet().stream()
               .mapToLong(entry -> entry.getValue().getMoney())
               .sum();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
