package main;

import java.util.Arrays;

/**
 * Represents GBP coin in terms of GBP Coins and Notes. 
 * Validates input to verify that only valid coin can be created.
 * 
 * Valid coins: 1p, 2p, 5p, 10p, 20p, 50p, £1, £2, £5
 * 
 * @author Peter Tomboline 
 * @date 07/06/2020
 *
 */
public class GBPCoin implements Comparable<GBPCoin>
{

	/** The name of the coin */
	private String mName;

	/** The value of the coin in pence */
	private int mValue;

	private final String[] mValidCoinNames = { "1p", "2p", "5p", "10p", "20p", "50p", "£1", "£2", "£5" };

	private final int[] mValidCoinValues = { 1, 2, 5, 10, 20, 50, 100, 200, 500 };

	/** Coin Constructor from name */
	public GBPCoin(String aName)
	{
		if (isValidCoinName(aName))
		{
			this.mName = aName;
			this.mValue = matchValue(aName);
		}

		else
		{
			throw new IllegalArgumentException("The " + aName + " coin has not (yet) been minted!");
		}
	}

	/** Coin Constructor from value in pence */
	public GBPCoin(int aValue)
	{
		if (isValidCoinValue(aValue))
		{
			this.mName = matchName(aValue);
			this.mValue = aValue;
		}

		else
		{
			throw new IllegalArgumentException("Invaid value for GBP coin: " + aValue);
		}
	}

	/**
	 * Gets the coin's name
	 * 
	 * @return the Name of the coin
	 */
	public String getName()
	{
		return this.mName;
	}

	/**
	 * Gets the value of the coin in pence
	 * 
	 * @return the value of the coin in pence
	 */
	public int getValue()
	{
		return this.mValue;
	}
	
	/**
	 * Represents the coin in String format
	 */
	@Override
	public String toString()
	{
		return this.mName; 
	}

	/**
	 * Checks if the given name of the coin is valid
	 * 
	 * @param aName The name of the coin
	 * @return Whether The name of the coin is valid
	 */
	private boolean isValidCoinName(String aName)
	{
		if (Arrays.asList(mValidCoinNames).contains(aName))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given name of the coin is valid
	 * 
	 * @param aValue The value of the coin
	 * @return Whether the value of the coin is valid
	 */
	private boolean isValidCoinValue(int aValue)
	{
		for (int value : mValidCoinValues)
		{
			if (aValue == value)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Matches the curreny's name to the it's value
	 * 
	 * @param aName The name of the coin
	 * @return value The value of the coin in pence
	 */
	private int matchValue(String aName)
	{
		int value = 0;

		switch (aName)
		{
		case "1p":
			value = 1;
			break;
		case "2p":
			value = 2;
			break;
		case "5p":
			value = 5;
			break;
		case "10p":
			value = 10;
			break;
		case "20p":
			value = 20;
			break;
		case "50p":
			value = 50;
			break;
		case "£1":
			value = 100;
			break;
		case "£2":
			value = 200;
			break;
		case "£5":
			value = 500;
			break;
		default:
			value = 0;
			break;
		}

		return value;

	}

	/**
	 * Matches the coin's name to the the value in pence
	 * 
	 * @param aValue The value of the coin in pence
	 * @return name The name of the coin
	 */
	private String matchName(int aValue)
	{
		String name = "";

		switch (aValue)
		{
		case 1:
			name = "1p";
			break;
		case 2:
			name = "2p";
			break;
		case 5:
			name = "5p";
			break;
		case 10:
			name = "10p";
			break;
		case 20:
			name = "20p";
			break;
		case 50:
			name = "50p";
			break;
		case 100:
			name = "£1";
			break;
		case 200:
			name = "£2";
			break;
		case 500:
			name = "£5";
			break;
		default:
			name = "";
			break;
		}

		return name;
	}
	
	 
	/** 
	 * Override comapreTo method for TreeSet
	 * @param coin The coin being compared 
	 * @return if the Coins are equal
	 */
	
	@Override
	public int compareTo(GBPCoin otherCoin)
	{
		if (this.getValue() > otherCoin.getValue())
		{
			return 1;
		}

		else if (this.getValue() < otherCoin.getValue())
		{
			return -1;
		}

		return 0;
	}
}