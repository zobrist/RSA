import java.util.ArrayList;

public class PrimalityTest {
	public static boolean isPrime(int num) {
		if(num == 2) {
			return true;
		} else if(num < 2 || num % 2 == 0) {
			return false;
		} else {
			double tmp = num;
			double limit = Math.ceil(Math.sqrt(tmp));
			for(int i = 3; i <= limit; i += 2) {
				if(num % i == 0)
					return false;
			}
		}
		return true;
	}
	
	public static ArrayList<Integer> getFirstNPrimes(int limit) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(limit >= 1) {
			list.add(2);
			
			for(int i = 3; list.size() < limit; i += 2) {
				if(isPrime(i))
					list.add(i);
			}
		}
		
		return list;
	}
	
	public static ArrayList<Integer> getPrimesBetween(int min, int max) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(min <= 2) {
			list.add(2);
		}
		
		if(min % 2 == 0) {
			min += 1;
		}
		
		for(int i = min; i <= max; i += 2) {
			if(isPrime(i))
				list.add(i);
		}
		
		return list;
	}
}
