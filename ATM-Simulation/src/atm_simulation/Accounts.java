/*
 * Gursimran singh
 * 06/15/17
 * Accounts.java
 * This class represents Accounts, that reads the 
 * user account details and save new transaction entries 
 * in text file.
 */

package atm_simulation;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This abstract class represents Accounts object, that reads
 * user account details from text file and saves new transactions 
 * in text file. Also prints deposit or withdrawal receipts. 
 * 
 * @author Gursimran Singh
 * @version 1.0
 */
public abstract class Accounts
{
	//fields
	private String cardNumber;
	private String pin;
	private double depAmount;
	private double withdrawalAmt;
	
	/**
	 * This creates Account object and stores account holder card number and pin.
	 * 
	 * @param cardNumber sixteen digit card number
	 * @param pin four digit pin code
	 */
	//constructor
	public Accounts(String cardNumber, String pin)
	{
		this.cardNumber = cardNumber;
		this.pin = pin;
	}
	
	/**
	 * This sets deposit amount.
	 * 
	 * @param depAmount total deposit amount
	 */
	//sets deposit amount
	public void depositAmount(double depAmount)
	{
		this.depAmount = depAmount;
	}
	
	/**
	 * This sets withdraw amount.
	 * 
	 * @param withdrawalAmt total withdraw amount
	 */
	//sets withdraw amount
	public void withdrawalAmt(double withdrawalAmt)
	{
		this.withdrawalAmt = withdrawalAmt;
	}
	
	/**
	 * These all abstract methods implement in child class (ChekingAccount) 
	 */
	//this sets amount in checking account class
	public abstract void amount(double amount);
	//this save new entries in checking account class 
	public abstract void saveAccDetails();
	//this checks valid user name in checking account class
	public abstract boolean checkName();
	//these methods check input is in numeric form or not
	public abstract boolean isNumeric(String input1, String input2, String input3);
	public abstract boolean isNumeric(String input);
	
	/**
	 * This reads text from file with given user account information
	 * and return it all as a big string.
	 * 
	 * @return returns all text from file
	 */
	/*Reads the text from the file with given account details and return
	 * it all as a big string.
	 */
	public static ArrayList<String> readAccDetails() 
	{
		ArrayList<String> lines = new ArrayList<String>();
		Scanner input = null;
		try
		{
			input = new Scanner(new File("files/acc_details.txt"));
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
		
		while(input.hasNextLine())
		{
			String line = input.nextLine().trim();
			
			if(line.length() > 0)
			{
				lines.add(line);
			}
		}
		return lines;
	}
	
	/**
	 * This saves temporary new account entries into text file.
	 */
	//saves transaction temporary for printing receipt.
	public void saveTransactions()
	{
		try (Scanner reader = new Scanner(cardNumber +","+ pin +","+ depAmount +","+ withdrawalAmt +"\n");
				PrintWriter writer = new PrintWriter(new FileOutputStream("files/receipt.txt", true)))
		{
			while(reader.hasNextLine())
			{
				String line = reader.nextLine();
				writer.println(line);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Something went wrong!");
		}
		
	}
	
	/**
	 * This prints receipt of total deposit and withdrawal amount with
	 * last four digits of account number.
	 */
	//prints receipt of deposit and withdrawal transactions 
	public void printReceipt()
	{
		Scanner input = null;
		double totalDep = 0.0;
		double totalWithdrwl = 0.0;
		String cardNumber = null;
		try
		{
			input = new Scanner(new File("files/receipt.txt"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Filed not found!");
		}
		while(input.hasNextLine())
		{
			String line = input.nextLine();
			String[] parts = line.split(",");
			cardNumber = parts[0];
			
			for(int i = 0; i < parts.length; i++)
			{
				totalDep += Double.parseDouble(parts[2]);
				totalWithdrwl += Double.parseDouble(parts[3]);
				break;
			}
		}
		
		System.out.println("\t RECEIPT");
		System.out.println("--------------------------");
		//prints total deposit receipt and account number
		if(totalDep > 0.0)
		{
			System.out.println("Cash Dep to Acc_#"+ cardNumber.substring(cardNumber.length()-4,
								cardNumber.length()));
			System.out.println(" Total Deposit Amt: $" + totalDep + "\n");
		}
		//prints total withdrawal receipt and account number
		if(totalWithdrwl > 0.0)
		{
			System.out.println("Cash WithDrwl from Acc_#" + cardNumber.substring(cardNumber.length()-4,
								cardNumber.length()));
			System.out.println(" Total WithDrawal Amt: $" + totalWithdrwl + "\n");
		}
	}
	
	/**
	 * This removes all content from text file.
	 */
	//this removes all content from text file that saved in saveTransactions() method.
	public static void removeContent()
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(new FileOutputStream("files/receipt.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
		finally 
		{
			if(writer != null)
			{
				writer.close();
			}
		}
	}
}