package automaStatiFiniti;

public class asfParser {
	public static AutomaStatiFiniti parse(String content) {
		AutomaStatiFiniti asf = new SempliceASF("prova");
		return asf;
	}
	
	public static String encode(AutomaStatiFiniti asf) {
		return asf.toString();
	}
}
