import java.util.*;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Map<String, Account> accounts = new HashMap<>();
        List<Thread> threadList = new ArrayList<>();

        for(int i = 1; i <=1000; i++){
         Account account = new Account();
         account.setAccNumber("accNumber: "+i);
         account.setMoney((long) Math.abs(Math.random()*100000));
         accounts.put("id "+i, account);
        }
        bank.setAccounts(accounts);
        System.out.println(bank.getSumAllAccounts());

        Random generator = new Random();
//        for(int i = 1; i<=100; i++){
        while (true){
            Object[] values = accounts.values().toArray();
            Account fromAcc = (Account) values[generator.nextInt(values.length)];
            Account toAcc = (Account) values[generator.nextInt(values.length)];
            int amount = generator.nextInt(60000);
            Thread thread = new Thread(new Threads(bank,fromAcc.getAccNumber(),toAcc.getAccNumber(),amount));
            thread.start();

        }
    }
}
