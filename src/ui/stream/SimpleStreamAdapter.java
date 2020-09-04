package ui.stream;

import java.io.IOException;
import java.util.Scanner;


/**
 * Classe che implementa l'interfaccia InOut utilizzando i più semplici input e output stream
 * @author Matteo Salvalai [715827], Jacopo Mora [715149]
 *
 */
public class SimpleStreamAdapter implements InOutStream{
	
	public static final String YES = "y";
	public static final String NO = "n";
	
	/**
	 * Lo scanner in ingresso
	 */
	private Scanner in;
	
	/**
	 * Costruttore
	 */
	public SimpleStreamAdapter() {
		in = new Scanner(System.in);
	}
	
	/* (non-Javadoc)
	 * @see command.InOutStream#read()
	 */
	@Override
	public String read(String str) {
		write(str);
		return in.nextLine();
	}

	/* (non-Javadoc)
	 * @see command.InOutStream#write(java.lang.String)
	 */
	@Override
	public void write(String str) {
		System.out.print(str);	
	}

	/* (non-Javadoc)
	 * @see command.InOutStream#writeln(java.lang.String)
	 */
	@Override
	public void writeln(String str) {
		System.out.println(str);	
	}

	/* (non-Javadoc)
	 * @see command.InOutStream#close()
	 */
	@Override
	public void close() throws IOException{
		in.close();
	}

	@Override
	public String yesOrNo(String str) {
		String ans = null;
		do {
			ans = read(str.concat(String.format(" (%s|%s) ", YES, NO)));
		} while(!ans.equalsIgnoreCase(YES) && !ans.equalsIgnoreCase(NO));
		return ans;
	}
	
}