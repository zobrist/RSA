import java.util.HashMap;

public class Message {
	private static HashMap<Character, String> map1;
	private static HashMap<String, Character> map2;
	private static Character[] chars = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '.', ':', '\''};
	private static String[] str = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
	
	public static void createHashmap1() {
		map1 = new HashMap<Character, String>();
		
		for(int i = 0; i < chars.length; i++)
			map1.put(chars[i], str[i]);
	}
	
	public static void createHashmap2() {
		map2 = new HashMap<String, Character>();
		
		for(int i = 0; i < chars.length; i++)
			map2.put(str[i], chars[i]);
	}
	
	public static String toNum(String text) {
		createHashmap1();
	
		StringBuilder numString = new StringBuilder();
		text = text.toLowerCase();
		
		for(int i = 0; i < text.length(); i++)
			numString.append(((i + 1) % 3 == 0)? map1.get(text.charAt(i)) + " " : map1.get(text.charAt(i)));		
	
		return numString.toString();
	}
	
	public static String toText(String numString) {
		createHashmap2();
		
		StringBuilder textString = new StringBuilder();
		String[] strArr = numString.split("\\s+");
		
		for(String block : strArr) {
			for(String key : block.split("(?s)(?<=\\G.{2})")) {
				textString.append(map2.get(key));
			}
		}
			
		return textString.toString();
	}
	
	public static void main(String[] args) {
		String s = toNum("have a nice day rodney");
		System.out.println(s);
		String t = toText("080122 050001 001409 030500 040125 001815 041405 25");
		System.out.println(t);
	}
}
