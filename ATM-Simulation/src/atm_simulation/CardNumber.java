/*
 * Gursimran singh
 * 06/15/17
 * CardNumber.java
 * This class represents CardNumber, that generates
 * unique sixteen digits card number and four digit
 * pin. 
 */

package atm_simulation;

import java.util.Random;

/**
 * This class represents CardNumber object, that
 * allows you to generate sixteen digit unique 
 * card number and four digit pin.
 * 
 * @author Gursimran Singh
 * @version 1.0
 */
public class CardNumber
{	
	//fields
	private long number;
	private Random random = new Random();
	private int pinCode;
	
	/**
	 * This creates new card number object.
	 */
	//constructor
	public CardNumber()
	{
		genNumber();
		genPin();
	}
	
	/**
	 * This generates sixteen digit unique card number
	 */
	//this generates 16 digit card number
	public void genNumber()
	{
		long first14Digits = (long) (Math.random() * 100000000000000L);
		number = 5200000000000000L + first14Digits;
		
		if(ValidateAccount.isDuplicateCard(number + ""))
		{
			genNumber();
		}
	}
	
	/**
	 * This generates four digit pin code.
	 */
	//this generates and returns four digit pin number
	public void genPin()
	{
		final int PIN_LENGTH = 4;
		pinCode = random.nextInt(10000);
		int length = (int)Math.log10(number) + 1;
		
		if(length < PIN_LENGTH)
		{
			genPin();
		}
	}
	
	/**
	 * This is a getter for getting four digit pin
	 * 
	 * @return return pin as a string
	 */
	//get four digit pin
	public String getPin()
	{
		return pinCode + "";
	}
	
	/**
	 * This is a getter for getting card number.
	 * 
	 * @return returns card number
	 */
	//this returns card number as String 
	public String getCardNumber()
	{
		return number + "";
	}
}
