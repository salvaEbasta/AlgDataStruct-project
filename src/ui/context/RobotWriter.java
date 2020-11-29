package ui.context;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class RobotWriter implements Runnable{

	private ArrayList<String> lines;


	public RobotWriter(ArrayList<String> lines) {
		this.lines = lines;
	}

	@Override
	public void run() {
		Robot robot = null;
		try {
			robot = new Robot();
			robot.setAutoDelay(10);
			robot.setAutoWaitForIdle(true);
		} catch (AWTException e) {
			return;
		}
		robot.delay(200);
		for(String line: lines)
			insert(robot, line);
	}


	private void insert(Robot robot, String line) {

		char[] chars = line.toCharArray();
		for (char c : chars) {
			boolean underscore = false;
			 if(new Character(c).equals('_')) {
		          c = '-';
		          underscore = true;
			 }
			int code = KeyEvent.getExtendedKeyCodeForChar(c);
			if(Character.isUpperCase(c) || underscore)
				robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(code);
			robot.keyRelease(code);
			if(Character.isUpperCase(c) || underscore)
				robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(150);
	}


}

