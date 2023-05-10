import javax.swing.*;
/**
 * 	Program to display and modify a simple DVD collection
 */

public class DVDManager {

	public static void main(String[] args) {
		DVDUserInterface dlInterface;
		DVDCollection dl = new DVDCollection();
		String filename = JOptionPane.showInputDialog("Enter name of data file to load:");			
		dl.loadData(filename);
		dlInterface = new DVDGUI(dl);
		dlInterface.processCommands();
	}

}
