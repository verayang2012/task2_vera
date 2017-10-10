package automationFramework;

public class User {
	public String firstName;
	public String lastName;
	public String date;
	public String email;
	
	public User(){
		
	}
	
	public User(String firstname, String lastname, String udate, String uemail){
		firstName=firstname;
		lastName=lastname;
		date=udate;
		email=uemail;
		
	}
	public void randomUser()
	{
		this.firstName=Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
		this.lastName="LastName";
		this.date="2017-11-11";
		this.email="testing@test.com";
	}
}
