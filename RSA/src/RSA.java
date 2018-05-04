import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RSA {
	public static void generateKey(int min, int max) {				//will generate p and q between min and max
		ArrayList<Integer> primes = PrimalityTest.getPrimesBetween(min, max);
		Random rand = new Random();
		
		int p = primes.get(rand.nextInt(primes.size()-1));
		int q = primes.get(rand.nextInt(primes.size()-1));
		int n = p*q;
		int phi = (p-1)*(q-1);
		
		ArrayList<Integer> admissibleE = PrimalityTest.getPrimesBetween(2, phi-1);
		int e = admissibleE.get(rand.nextInt(admissibleE.size() - 1));
		while(getGCD(e, phi) != 1) {
			e = admissibleE.get(rand.nextInt(admissibleE.size() - 1));
		}
		
		int d = solveLinearCongruence(e, 1, phi).get(0);
		System.out.println("Public key\nn: " + n + "\ne: " + e);
		System.out.println("Private key\nn: " + n + "\nd: " + d);
	}
	
	public static void encrypt(String text, int n, int e) {			//encrypt the given text using public key (n, e)
		String numString = Message.toNum(text);
		System.out.println("Original text: " + text);
		System.out.println("Translated text: " + numString);
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
		
		System.out.println("Ciphertext: " + encryptedMsg.toString());
	}
	
	public static void decrypt(String text, int n, int d) {			//decrypt the given text using private key (n, d)
		String[] blocks = text.split("\\s+");
		System.out.println("Ciphertext: " + text);
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
		
		System.out.println("Deciphered text: " + decryptedMsg.toString());
		System.out.println("Plaintext: " + Message.toText(decryptedMsg.toString()));
	}
	
	public static void decryptNoPrivateKey(String text, int n, int e) {				//decrypt the given text using the public key (n, e)
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
			d = solveLinearCongruence(e, 1, phi).get(0);
			System.out.println("n:" + n + "\np:" + p + "\nq:" + q + "\nphi:" + phi + "\nd: " + d);
			decrypt(text, n, d);
		}
	}
	
	public static ArrayList<Integer> solveLinearCongruence(int a, int b, int mod) {			//solves all x in the linear congruence
		ArrayList<Integer> result = new ArrayList<>();
		
		if (b % getGCD(a, mod) != 0)
			return result;
		
		int com = getGCD(mod, getGCD(a, b));
		int sol = (b / com) * getXByExtendedEuclideanAlgorithm((a / com), (mod / com));
		
		if (sol < 0) {
			while (sol < 0) {
				sol += (mod / com);
			}
		} else if (sol > mod) {
			while (sol > mod) {
				sol -= (mod / com);
			}
		}
		
		while (sol < mod) {
			result.add(sol);
			sol += (mod /com);
		}
		
		return result;
	}
	
	public static int getGCD(int a, int b) {
		if (b == 0)
			return a;
		
		return getGCD(b, a % b);
	}
	
	public static int getXByExtendedEuclideanAlgorithm(int e, int phi) {
		int x = 0, y = 1;
		int u = 1, v = 0;
		
		while(e != 0) {
			int p = phi / e;
			int q = phi % e;
			
			int m = x - u * p;
			int n = y - v * p;
			
			phi = e;
			e = q;
			x = u;
			y = v;
			u = m;
			v = n;
		}
		
		return x;
	}
	
	public static void main(String[] args) {
		//generateKey(1000, 1100);	//will generate p and q between 1000 and 1100
		decryptNoPrivateKey("082976 371981 814231 505650 853440 353277 596004 250518 494162 922046 540928 633792 779152 973836 494176 019498 125267 683832 244888 922046 522776 395123 915899 132032 620457 568301 878543 623328 746341 710542", 999797, 123457);
	}
}
