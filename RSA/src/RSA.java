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
		
		System.out.println("Decrypted Message: " + decryptedMsg.toString());
		System.out.println("Message: " + Message.toText(decryptedMsg.toString()));
	}
	
	public static void decryptNoPrivateKey(String text, int n, int e) {
		ArrayList<Integer> primes = PrimalityTest.getPrimesBetween(0, (int)Math.ceil(Math.sqrt(n)));
		BigInteger bigN = BigInteger.valueOf(n);
		
		int p = 0, q = 0, phi, d = 0;
		
		for(int i = 0; i < primes.size(); i++) {
			p = primes.get(i);
			q = n / p;
			if(BigInteger.valueOf(p*q).compareTo(bigN) == 0)
				if(PrimalityTest.isPrime(q))
					break;
		}
		
		phi = (p - 1) * (q - 1);
		BigInteger bigPhi = BigInteger.valueOf(phi);
		BigInteger bigE = BigInteger.valueOf(e);
		int gcd = (bigE.gcd(bigPhi)).intValue();
		
		if(gcd == 1) {
			System.out.println("n:" + n + " p:" + p + " q:" + q + " phi:" + phi);
			//compute d
		}
		
		//decrypt(text, n, d);
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
		decryptNoPrivateKey("080122 050001 001409 030500 040125", 999797, 123457);
		decryptNoPrivateKey("080122 050001 001409 030500 040125", 26219, 123457);
		decryptNoPrivateKey("080122 050001 001409 030500 040125", 840097, 123457);
		decryptNoPrivateKey("080122 050001 001409 030500 040125", 4166269, 123457);
		decryptNoPrivateKey("080122 050001 001409 030500 040125", 1830529, 123457);
		//int x = modularInverse(693647, 802193);
		//System.out.println("x : " + x);
		//decrypt("082976 371981 814231 505650 853440 353277 596004 250518 494162 922046 540928 633792 779152 973836 494176 019498 125267 683832 244888 922046 522776 395123 915899 132032 620457 568301 878543 623328 746341 710542", 999797, 253825);
	}
}
