import javax.swing.*;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUI implements DVDUserInterface {
	 
	 private DVDCollection dvdlist;
	 
	 public DVDGUI(DVDCollection dl)
	 {
		 dvdlist = dl;
	 }
	 
	 public void processCommands()
	 {
		 String[] commands = {"Add DVD",
				    "Modify DVD",
				 	"Remove DVD",
				 	"Get DVDs By Rating",
				 	"Show Details of DVD",
				 	"Get Total Running Time",
				 	"Exit and Save"};
		 
		 int choice;
		 
		 do {
	
			 choice = JOptionPane.showOptionDialog(null,
			         "Currently, our DVDCollection has: \n" + doDisplayDVDs() + "\nSelect an option: ", 
					 "DVD Collection", 
					 JOptionPane.YES_NO_CANCEL_OPTION, 
					 JOptionPane.QUESTION_MESSAGE, 
					 null, 
					 commands,
					 commands[commands.length - 1]);
		 
			 switch (choice) {
			 	case 0: doAddDVD(); break;
			 	case 1: doModifyDVD(); break;
			 	case 2: doRemoveDVD(); break;
			 	case 3: doGetDVDsByRating(); break;
			 	case 4: doFetchSpecificDVD(); break;
			 	case 5: doGetTotalRunningTime(); break;
			 	case 6: doSave(); break;
			 	default:  // do nothing
			 }
			 
		 } while (choice != commands.length-1);
		 System.exit(0);
	 }

	private void doAddDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating for " + title);
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
		// Request the running time
		String time = JOptionPane.showInputDialog("Enter running time for " + title);
		if (time == null) {
			return;
		}
        // Display dvd we're adding and modifying and then display the dvd updating.
        JOptionPane.showMessageDialog(null, "Adding: " + title + "/" + rating + "/" + time + "min");
        // Add or modify the DVD (assuming the rating and time are valid)
        dvdlist.addDVD(title, rating, time);
	}
	private void doModifyDVD() {
		try {
			String[] DVDs = dvdlist.getListOfDVDs();
			String[] yesOrNo = {"Yes", "No"};
			boolean ratingChange = false, timeChange = false;
			int choice = JOptionPane.showOptionDialog(null, "Please select from the following: ", "DVDCollection", JOptionPane.YES_NO_CANCEL_OPTION
					,JOptionPane.QUESTION_MESSAGE, null, DVDs, DVDs[DVDs.length - 1]);
			int ratingOption = JOptionPane.showOptionDialog(null, "Would you like to update the rating? ", "DVDCollection", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, yesOrNo, yesOrNo[yesOrNo.length - 1]);
			int minuteOption = JOptionPane.showOptionDialog(null, "Would you like to update the runtime? ", "DVDCollection", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, yesOrNo, yesOrNo[yesOrNo.length - 1]);
			if(ratingOption == 0) {
				ratingChange = true;
			}
			if(minuteOption == 0) {
				timeChange = true;
			}
			JOptionPane.showMessageDialog(null, "Modifying: " + DVDs[choice]);
			dvdlist.modifyDVD(choice, ratingChange, timeChange);
		}
		catch(ArrayIndexOutOfBoundsException a) {
			JOptionPane.showMessageDialog(null, "There is nothing to modify.");
		}
		
	}
	private void doRemoveDVD() {

		// Request the title
		try {
			String[] DVDs = dvdlist.getListOfDVDs();
			int choice = JOptionPane.showOptionDialog(null, "Please select from the following: ", "DVDCollection", JOptionPane.YES_NO_CANCEL_OPTION
					,JOptionPane.QUESTION_MESSAGE, null, DVDs, DVDs[DVDs.length - 1]);
	        // Display current collection as a means of removing stuff
	        JOptionPane.showMessageDialog(null, "Removing: " + DVDs[choice]);
	        // Remove the matching DVD if found
	        dvdlist.removeDVD(choice);
		}
		catch(ArrayIndexOutOfBoundsException a) {
			JOptionPane.showMessageDialog(null, "There are no DVDs to remove.");
		}
	}
	
	private void doGetDVDsByRating() {
		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating");
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
                String results = dvdlist.getDVDsByRating(rating);
                // Display dvd results if dvdcollection has any
                if(results != null) {
                    JOptionPane.showMessageDialog(null ,"DVDs with rating " + rating + "\n" +  results);
                }
                else {
                	JOptionPane.showMessageDialog(null, "There currently aren't any DVDs rated: " + rating);
                }
	}


	private String doDisplayDVDs() {
		return dvdlist.displayDVD();
	}
	
	private void doFetchSpecificDVD() { // Will show a big image of the rating of the film chosen
		try {
			ImageIcon GRating = new ImageIcon("GRating.png");
			ImageIcon PGRating = new ImageIcon("PGRating.png");
			ImageIcon PG13Rating = new ImageIcon("PG13Rating.png");
			ImageIcon RRating = new ImageIcon("RRating.png");
			String[] DVDs = dvdlist.getListOfDVDs();
			int choice = JOptionPane.showOptionDialog(null, "Please select from the following: ", "DVDCollection", JOptionPane.YES_NO_CANCEL_OPTION
					,JOptionPane.QUESTION_MESSAGE, null, DVDs, DVDs[DVDs.length - 1]);
			
			String fetchRating = dvdlist.fetchRatingSpecificDVD(choice);
			String fetchSpecificDVD = dvdlist.fetchSpecificDVD(choice);
			switch(fetchRating) {
			case "G":
				JOptionPane.showMessageDialog(null, "\nYou selected: " + fetchSpecificDVD, "DVDCollection Image", JOptionPane.INFORMATION_MESSAGE, GRating);
				break;
			case "PG":
				JOptionPane.showMessageDialog(null, "\nYou selected: " + fetchSpecificDVD, "DVDCollection Image", JOptionPane.INFORMATION_MESSAGE, PGRating);
				break;
			case "PG-13":
				JOptionPane.showMessageDialog(null, "\nYou selected: " + fetchSpecificDVD, "DVDCollection Image", JOptionPane.INFORMATION_MESSAGE, PG13Rating);
				break;
			case "R":
				JOptionPane.showMessageDialog(null, "\nYou selected: " + fetchSpecificDVD, "DVDCollection Image", JOptionPane.INFORMATION_MESSAGE, RRating);
				break;		
			}
		}
		catch(ArrayIndexOutOfBoundsException a) {
			JOptionPane.showMessageDialog(null, "There are currently no DVDs in your collection.");
		}

	}
    private void doGetTotalRunningTime() {
    	int total = dvdlist.getTotalRunningTime();
    	JOptionPane.showMessageDialog(null, "Total Running Time of DVDs: " + total + "min/s");
    }
   
	private void doSave() {
		dvdlist.save();
		
	}
		
}
