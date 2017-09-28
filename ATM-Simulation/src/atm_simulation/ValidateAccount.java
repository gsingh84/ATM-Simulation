/*
 * Gursimran singh
 * 06/15/17
 * ValidateAccount.java
 * This class represents ValidateAccount, that
 * validates user account with card number 
 * and pin that entered by user.
 */

package atm_simulation;

import java.util.*;


/**
 * This class represents ValidateAccount object, that
 * validates your account by using your card number 
 * and pin.
 * 
 * @author Gursimran Singh
 * @version 1.0
 */
public class ValidateAccount
{
	//fields
	private static TreeMap<String, String> map = new TreeMap<>();
	private String[] parts;
	private String accountNum;
	private String pin;
	private ArrayList<String> accDetails;
	
	/**
	 * This created new ValidateAccount object and assign 
	 * keys and values in map.
	 * 
	 * @param lines all text from file
	 */
	//constructor
	public ValidateAccount(ArrayList<String> lines)
	{
		//checks list is empty or null 
		if(lines.size() == 0 || lines == null)
		{
			System.out.println("Data Not found!");
		}
		
		//assigning lines to new list
		accDetails = lines;
		 
		for(int i = 0; i < lines.size(); i++)
		{
			//split data with with commas
			parts = lines.get(i).split(",");
			//all account numbers
			accountNum = parts[0];
			//pin code
			pin = parts[1];
			//assigning account number as keys and pin as values
			map.put(accountNum, pin);
		}
	}
	
	/**
	 * This checks entered card number and pin is valid or not.
	 * 
	 * @param cardNum sixteen digit card number
	 * @param pin four digit pin
	 * @return returns true if card is valid other wise false
	 */
	//Checks card number and pin is correct or not
	public boolean checkAcc(String cardNum, String pin)
	{
		if(cardNum.equals(null) || cardNum.length() == 0 || pin.equals(null) || pin.length() == 0)
		{
			System.out.println("Account number or pin cannot be empty or null");
		}
		
		if(map.containsKey(cardNum) && map.containsValue(pin))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * This method checks new generated card number is in map 
	 * or not.
	 *  
	 * @param cardNum sixteen digit card number
	 * @return returns true if card found in map other wise false
	 */
	/* This method returns true if card number is already in the map other 
	 * wise returns false, this method used when new card number generates 
	 * by CardNumber class for preventing duplicacy.
	 */
	public static boolean isDuplicateCard(String cardNum)
	{
		if(map.containsKey(cardNum))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This method gives total available balance 
	 * in user account.
	 * 
	 * @param card sixteen digits card number
	 * @return returns total balance
	 */
	//This counts how much total balance is in user account.
	public double checkBalance(String card)
	{
		double currentBalance = 0.0;
		
		for(int i = 0; i < accDetails.size(); i++)
		{
			String[] parts = accDetails.get(i).split(",");
			String cardNum = parts[0];
			String balance = parts[4];
			
			if(cardNum.equals(card))
			{
				currentBalance += Double.parseDouble(balance);
			}
		}
		
		return currentBalance;
	}
	
	/**
	 * This is a method for checking withdrawal limit.
	 * 
	 * @param amount withdraw amount
	 * @param cardNum sixteen digit card number
	 * @return returns false if amount is greater than withdrawal limit
	 * or amount is less than current balance or amount is less than 
	 * minimum withdraw limit
	 */
	//This checks withdraw limit
	public boolean checkWithdrawLimit(String amount, String cardNum)
	{
		final double WITHDRAW_LIMIT = 1000;
		final double MIN_WITHDRAW_LIM = 1.0;
		
		if(checkBalance(cardNum) < Double.parseDouble(amount))
		{
			System.out.println("Your available balanace is lower than " + Double.parseDouble(amount) + " !");
			return false;
		}
		else if(Double.parseDouble(amount) > WITHDRAW_LIMIT)
		{
			System.out.println("You cannot withdraw more than $1000.0 !");
			return false;
		}
		else if(Double.parseDouble(amount) < MIN_WITHDRAW_LIM)
		{
			System.out.println("Withdraw amount must be greater than $0 !");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * This is a method for checking a deposit limit.
	 * 
	 * @param amount deposit amount
	 * @return returns false if amount is greater than deposit
	 * limit or amount is less minimum deposit limit other 
	 * wise returns false.
	 */
	//checks deposit limit
	public static boolean checkDepositLimit(double amount)
	{
		final double DEPOSIT_LIMIT = 4000.0;
		final double MIN_DEP_LIM = 20.0;
		if(amount > DEPOSIT_LIMIT)
		{
			System.out.println("You cannot Deposit more than $4000.0 with ATM!");
			return false;
		}
		else if(amount < MIN_DEP_LIM)
		{
			System.out.println("Deposit amount should be more than $20!");
			return false;
		}
		return true;
	}
	
	/**
	 * This method checks how many total number of bills
	 * user can deposit at a time.
	 * 
	 * @param bills number of bills
	 * @return returns false if bills are more than standard
	 * limit other wise true
	 */
	//checks how many total number of bills user can deposit
	public static boolean checkBillsLimit(int bills)
	{
		final int BILLS_LIMIT = 40;  
		if(bills > BILLS_LIMIT)
		{
			System.out.println("You cannot Deposit more than 40 bills!");
			return false;
		}
		else
		{
			return true;
		}
	}
}
