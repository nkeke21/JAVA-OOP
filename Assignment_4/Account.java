// Account.java

import java.util.SimpleTimeZone;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private int balance;
	private int transactions;
	private ReentrantLock lock;
	private Condition condition;

	// It may work out to be handy for the account to
	// have a pointer to its Bank.
	// (a suggestion, not a requirement)
	private Bank bank;

	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		transactions = 0;
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}

	public synchronized void transfer(int amount){
		balance -= amount;
		transactions ++;
	}

	public synchronized void  deposit(int amount){
		balance += amount;
		transactions ++;
	}

	@Override
	public synchronized String toString(){
		return "Account with id: " + id + ", balance: " + balance + " transcations: " + transactions;
	}

	public synchronized int getBalance(){
		return balance;
	}
}
