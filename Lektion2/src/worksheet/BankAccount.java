package worksheet;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class BankAccount {
    @GuardedBy("this")
   private int balance = 0;

   public synchronized void deposit(int amount) {
       balance += amount;
   }

   public synchronized void withdraw(int amount) {
       balance -= amount;
   }

   public synchronized int getBalance() {
       return balance;
   }
}
