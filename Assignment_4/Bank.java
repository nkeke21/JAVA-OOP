// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Bank {
	public static final int ACCOUNTS = 20;// number of accounts
	public static final int MAX_CAPACITY = 50;
	public static final int MONEY = 1000;
	int counter = 0;
	private ArrayBlockingQueue<Transaction> blockingQueue;
	private Account[] Accounts;
	private Thread[] workers;
	private final Transaction nullTrans = new Transaction(-1,0,0);


	/*
     Reads transaction data (from/to/amt) from a file for processing.
     (provided code)
     */
	public Bank(){
		Accounts = new Account[ACCOUNTS];
		for(int i = 0; i < ACCOUNTS; i++){
			Accounts[i] = new Account(this, i, MONEY);
		}
	}

	public void readFile(String file) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);

			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;

				tokenizer.nextToken();
				int to = (int)tokenizer.nval;

				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;

				// Use the from/to/amount
				Transaction transaction = new Transaction(from, to, amount);
				blockingQueue.put(transaction);
			}
			for(int i = 0; i < workers.length; i ++){
				blockingQueue.put(nullTrans);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	public class Worker extends Thread{
		CountDownLatch countDownLatch;

		public Worker(String name, CountDownLatch countDownLatch) {
			super(name);
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			while (true){
				try {
					Transaction curr = blockingQueue.take();
					if(curr == nullTrans){
						break;
					}
					Thread.sleep(100);
					Accounts[curr.from].transfer(curr.amount);
					Accounts[curr.to].deposit(curr.amount);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			countDownLatch.countDown();
		}
	}

	public String getResultStr(){
		return resultStr;
	}

	private static String resultStr;

	/*
         Processes one file of transaction data
         -fork off workers
         -read file into the buffer
         -wait for the workers to finish
        */
	public void processFile(String file, int numWorkers) {
		blockingQueue = new ArrayBlockingQueue<>(MAX_CAPACITY);
		CountDownLatch countDownLatch = new CountDownLatch(numWorkers);
		workers = new Thread[numWorkers];
		resultStr = "";
		for (int i = 0; i < numWorkers; i++) {
			workers[i] = new Worker(Integer.toString(i), countDownLatch);
			workers[i].start();
		}

		readFile(file);

		for (int i = 0; i < numWorkers; i++) {
			workers[i] = new Worker(Integer.toString(i), countDownLatch);
			try {
				workers[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		try {
			countDownLatch.await();
			for(int i = 0; i < ACCOUNTS; i ++){
				resultStr += Accounts[i].toString() + "\n";
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/*
     Looks at commandline args and calls Bank processing.
    */
	public static void main(String[] args) {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		String file = args[0];
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}
		Bank bank = new Bank();
		bank.processFile(file, numWorkers);
		System.out.println(resultStr);
	}
}

