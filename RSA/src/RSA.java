import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RSA {
	public static void generateKey() {
		ArrayList<Integer> primes = PrimalityTest.getPrimesBetween(1000, 1100);
		Random rand = new Random();
		
		int p = primes.get(rand.nextInt(primes.size()-1));
		int q = primes.get(rand.nextInt(primes.size()-1));
		int n = p*q;
		int phi = (p-1)*(q-1);
		//choose public exponent e
		//int e = ;
		//compute private exponent d
		//int d = ;
		//public key (n, e) private key (n, d)
	}
	
	public static void encrypt(String text, int n, int e) {
		String numString = Message.toNum(text);
		System.out.println("Original Message: " + numString);
		String[] blocks = numString.split("\\s+");
		ArrayList<BigInteger> ciphers = new ArrayList<BigInteger>();
		
		for(String block : blocks) {
			BigInteger m = new BigInteger(block);
			BigInteger modulos = BigInteger.valueOf(n);
			BigInteger c = (m.pow(e)).mod(modulos);
			ciphers.add(c);
		}
		
		StringBuilder encryptedMsg = new StringBuilder();
		for(BigInteger cipher : ciphers) {
			String str = cipher.toString();
			while(str.length() < 6) {
				str = "0" + str;
			}
			encryptedMsg.append(str + " ");
		}
		
		System.out.println("Encrypted Message: " + encryptedMsg.toString());
	}
	
	public static void decrypt(String text, int n, int d) {
		String[] blocks = text.split("\\s+");
		System.out.println("Original Message: " + text);
		ArrayList<BigInteger> deciphers = new ArrayList<BigInteger>();
		
		for(String block : blocks) {
			BigInteger c = new BigInteger(block);
			BigInteger modulos = BigInteger.valueOf(n);
			BigInteger m = (c.pow(d)).mod(modulos);
			deciphers.add(m);
		}
		
		StringBuilder decryptedMsg = new StringBuilder();
		for(BigInteger decipher : deciphers) {
			String str = decipher.toString();
			while(str.length() < 6) {
				str = "0" + str;
			}
			decryptedMsg.append(str + " ");
		}
		
		System.out.println("Decrypted Message: " + Message.toText(decryptedMsg.toString()));
	}
	
	public static void main(String[] args) {
		encrypt("have a nice day", 3233, 17);
		decrypt("080122 050001 001409 030500 040125", 3233, 413);
	}
}
