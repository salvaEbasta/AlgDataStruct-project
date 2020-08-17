package ui;


public class Exit implements CommandInterface, NoParameters{

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, InOutStream io) {
		if(!check(args, io, "Troppi parametri"))
			return false;
		System.exit(0);
		return true;
	}

}
