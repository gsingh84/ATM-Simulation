/*
 * Gursimran singh
 * 06/15/17
 * CheckingAccount.java
 * This class represents ChekingAccount, that allows 
 * you to create new user and store new user's data 
 * in the text file.
 */

package atm_simulation;
import java.io.*;
import java.util.Scanner;


/**
 * This class represents the CheckingAccount object, you can 
 * deposit or withdraw money by using your card number and pin.
 * 
 * @author Gursimran Singh
 * @version 1.0
 */
public class CheckingAccount extends Accounts
{
	//fileds
	private String cardNumber;
	private String pin;
	private String firstName;
	private String lastName;
	private double amount;
	
	/**
	 * This creates new Checking Account object and stores card number,
	 * pin, first name and last name. 
	 * 
	 * @param cardNumber sixteen digit card number
	 * @param pin four digit card number
	 * @param firstName user first name
	 * @param lastName user last name
	 */
	//constructor
	public CheckingAccount(String cardNumber, String pin, String firstName, String lastName)
	{
		super(cardNumber, pin);
		this.cardNumber = cardNumber;
		this.pin = pin;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * This creates new ChekingAccount object and stores
	 * card number and pin only.
	 * 
	 * @param cardNumber sixteen digit card number
	 * @param pin four digit pin
	 */
	//constructor
	public CheckingAccount(String cardNumber, String pin)
	{
		super(cardNumber, pin);
		this.cardNumber = cardNumber;
		this.pin = pin;
	}
	
	/**
	 * This is a setter for setting deposit or withdrawal amount
	 */
	//this sets deposit or withdraw amount
	public void amount(double amount)
	{
		this.amount = amount;
	}
	
	/**
	 * This method counts how many bills deposited by user.
	 * 
	 * @param bill_20_Dollar twenty dollar bills
	 * @param bill_50_Dollar fifty dollar bills
	 * @param bill_100_Dollar hundred dollar bills
	 * @return returns total deposited amount
	 */
	//this counts how many bills deposited by user and returns total amount
	public static double countBills(String bill_20_Dollar, String bill_50_Dollar, String bill_100_Dollar)
	{
		final double TWENTY = 20.0;
		final double FIFTY = 50.0;
		final double HUNDRED = 100.0;
		
		//variables for counting bills
		double countTwenties = 0;
		double countFifties = 0;
		double countHundreds = 0;
		
		if(Integer.parseInt(bill_20_Dollar) > 0)
		{
			countTwenties = TWENTY * Integer.parseInt(bill_20_Dollar);
		}
		if(Integer.parseInt(bill_50_Dollar) > 0)
		{
			countFifties = FIFTY * Integer.parseInt(bill_50_Dollar);
		}
		if(Integer.parseInt(bill_100_Dollar) > 0)
		{
			countHundreds = HUNDRED * Integer.parseInt(bill_100_Dollar);
		}
		
		//returns total
		if(countTwenties > 0 || countFifties > 0 || countHundreds > 0)
		{
			double total = (countTwenties + countFifties + countHundreds);
			return total;
		}
		else
		{
			System.out.println("Invalid input!");
			return 0.0;
		}
	}
	
	/**
	 * This confirms deposit amount from user.
	 * 
	 * @param console for taking user input
	 * @param amount deposit amount
	 * @return returns true if user enter yes other wise false
	 */
	//confirm deposit amount from user
	public static boolean confirmDeposit(Scanner console, double amount)
	{
		System.out.println("Deposit Amt: $" + amount);
		System.out.println("confirm (yes/no): ");
		String input = console.nextLine();
		if(input.equalsIgnoreCase("yes"))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This checks first and last name is blank or not.
	 * 
	 * @return returns false if user left first or last 
	 * name field blank other wise true 
	 */
	//check name field is empty or not
	public boolean checkName()
	{
		if(firstName.isEmpty() || lastName.isEmpty())
		{
			System.out.println("Don't leave first name or last name field blank!");
			System.out.println();
			return false;
		}
		return true;
	}
	
	/**
	 * This checks user input is in numeric form or not.
	 * 
	 * @return returns false if enter non-numeric data other wise true
	 */
	//check input is in numeric form or not
	public boolean isNumeric(String input1, String input2, String input3)
	{
		String[] input = {input1, input2, input3};
		
		for(int i = 0; i < input.length; i++)
		{
			String character = input[i];
			for(int j = 0; j < character.length(); j++)
			{
				char current = character.charAt(j);
				if(!Character.isDigit(current))
				{
					System.out.println("Please enter a numeric value!");
					return false; 
				}
			}	
		}
		
		return true;
	}
	
	/**
	 * This checks user entered input is numeric data or not
	 * 
	 * @return returns false if entered input is not numeric other wise true
	 */
	//this also checks user input is in form or not.
	public boolean isNumeric(String input)
	{
		for(int i = 0; i < input.length(); i++)
		{
			char current = input.charAt(i);
			if(!Character.isDigit(current))
			{
				System.out.println("Please enter a numeric value!");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This stores account information in the text file.
	 */
	//Saves account details into text file
	public void saveAccDetails()
	{
		try (Scanner reader = new Scanner(cardNumber +","+ pin +","+ firstName +","+ lastName +","+ amount +"\n");
				PrintWriter writer = new PrintWriter(new FileOutputStream("files/acc_details.txt", true)))
		{
			while(reader.hasNextLine())
			{
				String line = reader.nextLine();
				writer.println(line);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
		
	}
}
