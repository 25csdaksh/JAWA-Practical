package test;

import exception.InsufficientFundsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import model.Account;
import service.AccountWorker;
import service.BankService;

public class MiniBankTest {

    @Test("A deposit increases the balance")
    public void testDepositIncreasesBalance() throws Exception {
        BankService service = new BankService();
        Account acc = service.openAccount(1, "Test User 1", 5000, 500);
        String accNo = acc.getAccountNumber();

        Assert.assertEquals(5000L, acc.getBalance());
        service.deposit(accNo, 1500);
        Assert.assertEquals(6500L, acc.getBalance());
    }

    @Test("A withdrawal over the balance limit throws InsufficientFundsException")
    public void testWithdrawalOverLimitThrowsException() throws Exception {
        BankService service = new BankService();
        Account acc = service.openAccount(1, "Test User 2", 2000, 500);
        String accNo = acc.getAccountNumber();

        Assert.assertThrows(InsufficientFundsException.class, () -> {
            service.withdraw(accNo, 5000);
        });
    }

    @Test("Many concurrent deposits keep the balance correct (Thread Safety)")
    public void testConcurrentDepositsKeepBalanceCorrect() throws Exception {
        BankService service = new BankService();
        Account acc = service.openAccount(1, "Concurrent Stress Test", 0, 0);

        int threadCount = 10;
        int iterationsPerThread = 100;
        long depositPerIteration = 10;
        long expectedBalance = threadCount * iterationsPerThread * depositPerIteration;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new AccountWorker(acc, iterationsPerThread, depositPerIteration, false));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        Assert.assertEquals(expectedBalance, acc.getBalance());
    }

    @Test("Saving and reloading state restores all accounts unchanged")
    public void testStatePersistence() throws Exception {
        BankService original = new BankService();
        Account a1 = original.openAccount(1, "Persist Alice", 10000, 1000);
        Account a2 = original.openAccount(2, "Persist Bob", 20000, 5000);

        Path tempState = Paths.get("temp_persistence_test.dat");
        try {
            original.saveState(tempState);

            BankService reloaded = new BankService();
            int count = reloaded.loadState(tempState);

            Assert.assertEquals(2L, (long) count);
            Assert.assertEquals(2L, (long) reloaded.getAccountCount());

            Account loadedA1 = reloaded.findAccount(a1.getAccountNumber());
            Assert.assertNotNull(loadedA1);
            Assert.assertEquals(a1.getOwnerName(), loadedA1.getOwnerName());
            Assert.assertEquals(a1.getBalance(), loadedA1.getBalance());

            Account loadedA2 = reloaded.findAccount(a2.getAccountNumber());
            Assert.assertNotNull(loadedA2);
            Assert.assertEquals(a2.getOwnerName(), loadedA2.getOwnerName());
            Assert.assertEquals(a2.getBalance(), loadedA2.getBalance());
        } finally {
            Files.deleteIfExists(tempState);
        }
    }

    @Test("Searching accounts by owner name returns matching results")
    public void testSearchByOwnerName() throws Exception {
        BankService service = new BankService();
        service.openAccount(1, "Daksh Soni", 5000, 500);
        service.openAccount(2, "Alice Sharma", 8000, 2000);
        service.openAccount(3, "Bob Soni", 15000, 0);

        ArrayList<Account> soniAccounts = service.searchByOwnerName("Soni");
        Assert.assertEquals(2L, (long) soniAccounts.size());

        ArrayList<Account> aliceAccounts = service.searchByOwnerName("Alice");
        Assert.assertEquals(1L, (long) aliceAccounts.size());

        ArrayList<Account> none = service.searchByOwnerName("NonExistent");
        Assert.assertEquals(0L, (long) none.size());
    }
}
