/*
 * Gursimran singh
 * 06/15/17
 * RunProgram.java
 * This class starts the program where you can login 
 * into your account and can deposit or withdrawal 
 * money from your account.
 */

package atm_simulation;
import java.util.*;


/**
 * This is the entry point to my application which
 * lets a user start a ATM simulation program!
 * 
 * @author Gursimran Singh
 * @version 1.0
 */
public class RunProgram
{
	//fields
	private static Scanner console = new Scanner(System.in);
	private static String option_1 = "1";
	private static String option_2 = "2";
	private static String option_3 = "3";
	
	/**
	 * This is the entry point to my application.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args)
	{
		System.out.println("\t WELCOME TO DESI BANK!");
		System.out.println("\t --------------------- \n");
		//calling method that takes user input 
		userInput();
	}
	
	//this takes user input and pass that input to different methods
	private static void userInput()
	{
		//keep looping as much as user wants
		while(true)
		{
			System.out.println("1.New User \t 2.Login \t 3.Exit");
			//prompt for options
			String selectOption = console.nextLine();
			
			//this prompt first and last name to new user
			if(selectOption.equals(option_1))
			{
				System.out.println("Enter your first name: ");
				String firstName = console.nextLine();
				System.out.println("Enter your last name: ");
				String lastName = console.nextLine();
				
				//passing user name to newUser() method 
				newUser(firstName, lastName);
				
			}
			//this prompt card number and pin
			else if(selectOption.equals(option_2))
			{
				System.out.println("Enter your card number: ");
				String swipeCard = console.nextLine();
				System.out.println("Enter your pin: ");
				String pin = console.nextLine();
				
				//passing card number and pin to login() method
				login(swipeCard, pin);

			}
			//this allows user to exit the program
			else if(selectOption.equals(option_3))
			{
				break; //quit
			}
			else
			{
				System.out.println("Invalid input! \n");
			}
		}
	}
	
	//this creates new user and assign new card number and pin to new user
	private static void newUser(String firstName, String lastName)
	{
		//instantiate CardNumber class 
		CardNumber card = new CardNumber();
		
		//instantiate CheckingAccount and passes user and card details 
		Accounts chkAcc = new CheckingAccount(card.getCardNumber(), card.getPin(), firstName, lastName);
		
		if(chkAcc.checkName())
		{
			//calling method from CheckingAccount class that saves account details
			chkAcc.saveAccDetails();
			
			//prints card details for user 
			System.out.println("Use the following Account details to login.");
			System.out.println("Card number: " +card.getCardNumber());
			System.out.println("Pin: " + card.getPin());
			System.out.println();
		}
	}
	
	//this checks card details and ask user for deposit or withdrawal 
	private static void login(String cardNum, String pin)
	{
		//calling method from Accounts class that remove the contents in text file 
		Accounts.removeContent();
		
		//keep looping as much time as user wants
		String finish = "no";
		String ask = "";
		while(finish.equalsIgnoreCase("no"))
		{
			//this creates new object and pass all accounts details to CheckAndValidate class
			ValidateAccount validateAcc = new ValidateAccount(Accounts.readAccDetails());
			
			//this checks card number and pin is valid or not
			if(validateAcc.checkAcc(cardNum, pin))
			{
				//creating another checking account object that takes only card number and pin
				Accounts account = new CheckingAccount(cardNum, pin);
				
				//ask for deposit, withdrawal and check balance options
				System.out.println("1.Deposit \t 2.Withdrawal \t 3.Check balance");
				String option = console.nextLine();
				
				if(option.equals(option_1))
				{
					//calling deposit() method if user selects 1.
					deposit(account);
				}
				else if(option.equals(option_2))
				{
					//calling withdrawal() method if user selects 2.
					withdrawal(account, validateAcc, cardNum);
				}
				else if(option.equals(option_3))
				{
					//this prints how much balance available in user account
					System.out.println("Your Available balance is: ");
					System.out.println("$" + validateAcc.checkBalance(cardNum));
				}
				else
				{
					System.out.println("Invalid input");
				}
				//ask user if he/she wants to perform more transaction.
				System.out.println();
				System.out.println("Are you finish (yes/no): ");
				finish = console.nextLine();
				
				//ask if user need a receipt 
				if(!finish.equalsIgnoreCase("no"))
				{
					System.out.println("Do you need receipt");
					System.out.println("1. YES \t 2. NO");
					ask = console.nextLine();
					
					//this prints receipt if user select 1.
					if(ask.equals(option_1))
					{
						//passing latest account details to CheckAndValidate class
						validateAcc = new ValidateAccount(Accounts.readAccDetails());
						account.printReceipt();
						System.out.println(" Available Balance: $" + validateAcc.checkBalance(cardNum)+"\n");
					}
				}	
			}
			else //breaks loop
			{
				System.out.println("Invalid card or pin number!");
				finish = "yes"; //quit
			}
		}
	}
	
	//this prompt user how much he wants to deposit and save that deposit entry into text file
	private static void deposit(Accounts account)
	{
		//prompt user how many bills he/she wants to insert
		System.out.println("You can desposit up to 40 bills at time.");
		System.out.println("Enter how many $20 bills you want to deposit: ");
		String twentyDollarBill = console.nextLine();
		System.out.println("Enter how many $50 bills you want to deposit: ");
		String fiftyDollarBill = console.nextLine();
		System.out.println("Enter how many $100 bills you want to deposit: ");
		String hundredDollarBill = console.nextLine();
		
		//set amount to zero if left above field blank
		if(twentyDollarBill.isEmpty())
			twentyDollarBill = "0";
		if(fiftyDollarBill.isEmpty())
			fiftyDollarBill = "0";
		if(hundredDollarBill.isEmpty())
			hundredDollarBill = "0";
		
		//check input is numeric or not
		if(account.isNumeric(twentyDollarBill, fiftyDollarBill, hundredDollarBill))
		{
			//count total bills
			int totalBills = Integer.parseInt(twentyDollarBill) + Integer.parseInt(fiftyDollarBill)
								+ Integer.parseInt(fiftyDollarBill);
			//count total cash
			double totalAmt = CheckingAccount.countBills(twentyDollarBill, fiftyDollarBill, hundredDollarBill);
			
			//this check bills limit
			if(ValidateAccount.checkBillsLimit(totalBills))
			{
				//this checks deposit limit
				if(ValidateAccount.checkDepositLimit(totalAmt))
				{
					//this confirms deposit amount from user
					if(CheckingAccount.confirmDeposit(console, totalAmt))
					{	
						//passing amount to method from checkingAccount class
						account.amount(totalAmt);
						//passing total deposit to method form Accounts class
						account.depositAmount(totalAmt);
						//save deposit into user account
						account.saveAccDetails();
						System.out.println("$" + totalAmt + " has been deposited into your account.");
						//save transaction temporary for printing details in receipt
						account.saveTransactions();
					}
				}
			}
		}
	}
	
	//this prompt user how much amount he wants to withdraw and save that entry into text file
	private static void withdrawal(Accounts account, ValidateAccount validateAcc, String card)
	{
		System.out.println("Enter amount $: ");
		String amount = console.nextLine();
		
		//set amount to zero if user left above field blank
		if(amount.isEmpty())
			amount = "0";
		
		//check amount is in numeric form or not
		if(account.isNumeric(amount))
		{
			//convert withdrawal amount into negative numbers 
			String convAmt = "-" + amount;
			
			//check withdraw limit
			if(validateAcc.checkWithdrawLimit(amount, card))
			{
				double amt = Double.parseDouble(convAmt);
				//passing amount to method from CheckingAccount class
				account.amount(amt);
				//passing amount to method from Accounts class 
				account.withdrawalAmt(Double.parseDouble(amount));
				//save latest entry into text file
				account.saveAccDetails();
				System.out.println("Collect your money: \n $" + Double.parseDouble(amount));
				//save transaction temporary for printing details in receipt
				account.saveTransactions();
			}
		}
	}
}