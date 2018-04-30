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
	
	public static void decryptNoPrivateKey(String text, int n, int e) {
		ArrayList<Integer> primes = PrimalityTest.getPrimesBetween(0, n);
		ArrayList<Integer[]> factorPrimes = new ArrayList<Integer[]>();
		BigInteger bigN = BigInteger.valueOf(n);
		
		System.out.println("length: " + primes.size());
		for(int i = 0; i < primes.size(); i++) {
			for(int j = i + 1; j < primes.size(); j++) {
				//System.out.println("Inside double loop");
				BigInteger product = BigInteger.valueOf(primes.get(i)).multiply(BigInteger.valueOf(primes.get(j)));
				if(bigN.compareTo(product) == 0) {
					Integer[] factor = new Integer[2];
					factor[0] = primes.get(i);
					factor[1] = primes.get(j);
					//add something here to compute phi, e, and d
					factorPrimes.add(factor);
				}
			}
		}
		
		ArrayList<Integer> phis = new ArrayList<Integer>();
		for(Integer[] i : factorPrimes) {
			BigInteger phi = BigInteger.valueOf(i[0]).multiply(BigInteger.valueOf(i[1]));
			System.out.println(i[0] + " * " + i[1] + " = " + n);
		}
		//call decrypt(text, n, d) ones d is found
	}
	
	public static int modularInverse(int e, int phi) {
		int m0 = phi;
        int y = 0, x = 1;
 
        if (phi == 1)
            return 0;
 
        while (e > 1)
        {
            // q is quotient
            int q = e / phi;
 
            int t = phi;
 
            // m is remainder now, process
            // same as Euclid's algo
            phi = e % phi;
            e = t;
            t = y;
 
            // Update x and y
            y = x - q * y;
            x = t;
        }
 
        // Make x positive
        if (x < 0)
            x += m0;
 
        return x;
	}
	
	public static void main(String[] args) {
		int x = modularInverse(693647, 802193);
		System.out.print("mod: " + x);
		//encrypt("have a nice day", 999797, 123457);
		//decryptNoPrivateKey("080122 050001 001409 030500 040125", 999797, 123457);
	}
}
