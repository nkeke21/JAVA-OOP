// For source release 20:
//#source 20
// For target release 1.8:
//		#target 1.8
// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/
import java.security.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();


	/*
     Given a byte[] array, produces a hex String,
     such as "234a6f". with 2 chars for each byte in the array.
     (provided code)
    */
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

	/*
     Given a string of hex byte values such as "24a26f", creates
     a byte[] array of those values, one byte value -128..127
     for each 2 chars.
     (provided code)
    */
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	private static CountDownLatch latch;
	private static String output;
	private static boolean stop = false;

	public static void restartStatics(){
		output = "";
		stop = false;

	}


	public static class Worker extends Thread{
		int startIndex;
		int endIndex;
		int thWorker;
		byte[] destHashBytes;
		int maxLength;

		public Worker(int i, int startIndex, int endIndex, int maxLength, byte[] input) {
			thWorker = i;
			destHashBytes = input;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.maxLength = maxLength;
		}

		@Override
		public void run() {
			for (int i = startIndex; i < endIndex; i ++){
				if(!stop) {
					wrapper(CHARS[i] + "");
				}
			}
			latch.countDown();
		}

		private void wrapper(String str){

			if(str.length() <= maxLength && !stop){
				if(Arrays.equals(getHash(str), destHashBytes)){
					output = str;
					stop = true;
					return;
				}
				for(int i = 0; i < CHARS.length; i ++){
					wrapper(str + CHARS[i]);
				}
			}
		}
	}

	public static String launch(int num, int len, byte[] hashBytes) throws InterruptedException {
		latch = new CountDownLatch(num);
		int part = CHARS.length/num;

		for (int i = 0; i < num; i ++){
			Worker curr;
			if(i == num - 1){
				curr = new Worker(i, i * part, CHARS.length, len, hashBytes);
			} else {
				curr = new Worker(i, i * part, (i + 1)* part, len, hashBytes);
			}
			curr.start();
		}
		latch.await();
		return output;
	}

	public static void main(String[] args) throws InterruptedException {
		if (args.length < 1) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		} else if (args.length == 2 || args.length > 3){
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}

		String targ = args[0];
		int num = 1;
		if(args.length == 1){
			byte[] hashBytes = getHash(targ);
			String result = hexToString(hashBytes);
			System.out.println("result: " + result);
		}
		if (args.length == 3) {
			int len = Integer.parseInt(args[1]);
			num = Integer.parseInt(args[2]);
			byte[] hashBytes = hexToArray(targ);
			String result = launch(num, len, hashBytes);
			System.out.println(result);
			System.out.println("All workers done job!!!");
		}

		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

	}

	public static byte[] getHash(String targ) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		// Convert the input string to bytes
		byte[] inputBytes = targ.getBytes();

		// Update the digest with the input bytes
		digest.update(inputBytes);

		// Calculate the hash value
		byte[] hashBytes = digest.digest();
		return hashBytes;
	}
}
