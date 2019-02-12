import java.util.ArrayList;

public class ThereAndBackAgain 
{

	public static void main(String[] args) 
	{
		
		// Create a traveling party called party1 by creating an array of Travelers 
		// and filling it with frodo, sam, and gimli
		Hobbit frodo = new Hobbit("Frodo");
		Hobbit sam = new Hobbit("Sam");
		Dwarf gimli = new Dwarf("Gimli");
		Wizard gandalf = new Wizard("Gandalf","Grey");
		Traveler[] party1 = {frodo, sam, gimli, gandalf};

		// Then, use a loop to make all travelers go a distance of 50 miles 
		for (Traveler traveler:party1) {
			traveler.travel(50);
		// Then, for each Traveler in the travelingParty, print their name and how far they've
		//    traveled in miles.  (In the next piece, you'll do this in methods, but 
		//    for a first pass, just do it in main and print to the console.)
			System.out.println(traveler.getName()+" has traveled "+traveler.getDistanceTraveled()+" miles.");
		}
		// Expected output:  Frodo has traveled 50 miles.
		//                   Sam has traveled 50 miles.
		//                   Gimli has traveled 50 miles.
		System.out.println();
		System.out.println("\n\n\nPART 2: \n");

		String[] dwarfNames = {"Fili", "Kili", "Dori", "Ori", "Nori", "Balin", "Dwalin", 
		"Oin", "Gloin", "Bifur", "Bofur", "Bombur", "Thorin"};

		// Make a new ArrayList to hold a 2nd party of Travelers called party2:


		// Call the createParty method and pass it party2 and the dwarfNames array.


		// Finally, call the allTravel method passing it party2 and 100 (representing
		// the 100 miles that party2 has traveled together.
	}

	
}
