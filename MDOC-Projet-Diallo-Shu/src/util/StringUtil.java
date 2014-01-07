package util;

public class StringUtil {
	public static String getValue(String s){
		if((s == null) || s.isEmpty()){
			return "";
		} else {
			return s;
		}
	}
}
