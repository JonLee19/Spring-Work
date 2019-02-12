
public class Wizard extends Traveler
{
	private String color;
	
	//Call Traveler's constructor (look it up p587) to set the name field and then 
	//   set the color field for this class   
	public Wizard(String name, String color)
	{
		super(name);
		this.color = color;
	}
	
		//Wizards travel 3 times as far as other travelers in their party because they scout ahead.
	//  Override the travel method, calling the method from the superclass and passing it
	//  triple the miles passed in as a parameter.
	//  Ex.  Calling travel(50) would increase a Wizard's distance traveled by 150.
	public void travel(int miles)
	{
		super.travel(3*miles);
	}
	
	//Wizards have colors associated with them.  Saruman the White, Gandalf the Grey, etc
		//  Override the Traveler's getName method to append the Wizard's color 
	public String getName()
	{
		return super.getName()+" the "+color;
	}
}
