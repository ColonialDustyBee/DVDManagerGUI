import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
// Create modification
public class DVDCollection {

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public String toString() {
		// Return a string containing all the DVDs in the
		// order they are stored in the array along with
		// the values for numdvds and the length of the array.
		// See homework instructions for proper format.
		String dvdDebugDetails = "numdvds = " + numdvds + "\ndvdArray.length = " + dvdArray.length + "\n"; // Concatenate later
		String stringDVDDetails = "";
		for(int i = 0; i < numdvds; i++) {
			stringDVDDetails = stringDVDDetails + "dvdArray[" + i + "] = " + dvdArray[i].toString() + "\n"; 
		}
		String fullDetailsOfDVDs = dvdDebugDetails.concat(stringDVDDetails);
		
		return fullDetailsOfDVDs;
	}

	public void addDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.
		boolean ratingValid = false;
		try {
			int runTime = Integer.parseInt(runningTime);
			if(numdvds == dvdArray.length){
				increaseArraySize(); // doubles array
			}
			ratingValid = checkRating(rating);
			if(ratingValid){ // Checks if rating is valid.
				dvdArray[numdvds] = new DVD(title, rating, runTime); // Adds DVD
				numdvds++;
				sortingDVDs(); 
			}
			else {
				JOptionPane.showMessageDialog(null, rating + " is an invalid rating.");
			}
		
		}
		catch(NumberFormatException validNumber) {
			JOptionPane.showMessageDialog(null, "Number entered is invalid: ");
		}	
	}
	public void modifyDVD(int numberDVD, boolean ratingChange, boolean timeChange) {
		// Separated add and modify into two separate options. Felt like it's easier to modify a DVD by simply selecting it rather than asking for modification
		boolean ratingValid = false;
		String rating, time = "";
		if(ratingChange) {
			rating = JOptionPane.showInputDialog("Please enter new rating: ");
			rating = rating.toUpperCase();
			ratingValid = checkRating(rating);
			if(ratingValid){
				dvdArray[numberDVD].setRating(rating);
			}
			else {
				JOptionPane.showMessageDialog(null, "Rating entered is invalid!");
			}
		}
		try {
			if(timeChange) {
				time = JOptionPane.showInputDialog("Please enter new time: ");
				int runTime = Integer.parseInt(time);
				dvdArray[numberDVD].setRunningTime(runTime);
			}
		}
		catch(NumberFormatException validNumber) {
			JOptionPane.showMessageDialog(null, "Number entered is invalid: ");
		}
	
	}
		
	public void removeDVD(int choice) { // Updated this so it removes it at the click of a button
		int length = dvdArray.length;
		DVD[] updatedDvdArray = new DVD[length];
		int indexComparator = 0;
		for (int indexIterator = 0; indexIterator < numdvds; indexIterator++) { // Shifts array index one to the left to remove element.
			if(indexIterator != choice){
				updatedDvdArray[indexComparator] = dvdArray[indexIterator];
				indexComparator++;
			}
		}
		dvdArray = updatedDvdArray.clone(); // Copies elements from updated array into dvdArray.
		numdvds--; // Reduce number of numdvds.
	}
	
	public String getDVDsByRating(String rating) { 

		String dvdRatings = getRating(rating);
		if(dvdRatings == null){
			return null;
		}
		return dvdRatings; // Returns String representation of strings found based on rating entered.
	}

	public int getTotalRunningTime() {
		int totalRunTime = 0;
		if(numdvds == 0){
			return 0;
		}
		for(int i = 0; i < numdvds; i++){
			totalRunTime += dvdArray[i].getRunningTime(); 
		}
		return totalRunTime;
	}

	
	public void loadData(String filename) {
		try{
			File loadingFile = new File(filename);
			Scanner readingFile = new Scanner(loadingFile);
			while(readingFile.hasNextLine()){
				String contentsOfFile = readingFile.nextLine();
				String[] differentContents = contentsOfFile.split(","); // split by comma.
				addDVD(differentContents[0], differentContents[1], differentContents[2]); 
			}
			sourceName = filename; // Save filename
			readingFile.close();
		}
		catch (FileNotFoundException exception){ // Skips file load and initializes new array to work with alongside new file to save too
			sourceName = filename; 
			JOptionPane.showMessageDialog(null, "Filename is not found!, creating new file to save to...Done!");
		} 
	}
	
	public void save() {
		try{
			FileWriter writingFile = new FileWriter(sourceName);
			for(int i = 0; i < numdvds; i++){
				writingFile.write(dvdArray[i].getTitle() + "," + dvdArray[i].getRating() + "," + dvdArray[i].getRunningTime() + "\n");
			}
			writingFile.close();
			JOptionPane.showMessageDialog(null, "File saved successfully!");
		}
		catch(IOException exception){
			JOptionPane.showMessageDialog(null, "Filename is not valid!");
		}
	}	
	public String displayDVD() {
		if(numdvds == 0) {
			return "There are no DVDs currently in your collection.";
		}
		String[] listOfDVDs = new String[numdvds];
		String DVDs = "";
		for(int i = 0; i < numdvds; i++) {
			listOfDVDs[i] = dvdArray[i].toString();
		}
		DVDs = String.join("\n", listOfDVDs);
		return DVDs;
	}
	public String[] getListOfDVDs() {
		String[] DVDs = new String[numdvds];
		for(int i = 0; i < numdvds; i++) {
			DVDs[i] = dvdArray[i].getTitle();
		}
		return DVDs;
	}
	
	public String fetchRatingSpecificDVD(int selected) {
		return dvdArray[selected].getRating();
	}
	public String fetchSpecificDVD(int selected) {
		return "\nName of Film: " + dvdArray[selected].getTitle() + "\nRating Of Film: " + dvdArray[selected].getRating() + "\nTotal Runtime: " + dvdArray[selected].getRunningTime();
	}
	
	// Additional private helper methods go here:
	private boolean checkRating(String rating){
		String[] ratingList= {"G", "PG", "PG-13", "R"}; // Different ratings available
		for(int i = 0; i < ratingList.length; i++){
			if(Objects.equals(rating, ratingList[i])){
				return true;
			}
		}
		return false;
	}
	private String getRating(String rating){
		String titlesByRating = "";
		boolean found = false;
		for(int i = 0; i < numdvds; i++){
			if(Objects.equals(rating, dvdArray[i].getRating())){
				titlesByRating = titlesByRating + dvdArray[i].toString() + "\n";
				found = true;
			}
		}
		if(found){
			return titlesByRating;
		}

		return null; // return 0
	}	
	private void sortingDVDs(){ // Implemented insertion sort algorithm that is used to compare strings. Using Arrays.sort() would require me to drastically change the class.
		DVD tempValue;
		int i, j = 0;
		for(i = 1; i < numdvds; i++){
			tempValue = dvdArray[i]; 
			j = i - 1;
			while(j >= 0 && tempValue.getTitle().compareTo(dvdArray[j].getTitle()) < 0){
				dvdArray[j + 1] = dvdArray[j];
				j--;
			}
			dvdArray[j + 1] = tempValue;
		}
	} 
	private void increaseArraySize(){
		dvdArray = Arrays.copyOf(dvdArray, dvdArray.length * 2);  // will double size.
	}

}

	
