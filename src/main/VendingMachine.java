package main;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Represents the Vending Machine with the following test methods
 * 
 *  - Initialise with given coins (protects against invalid coins)
 * 	- Deposit coins (protects against invalid coins)
 *  - Produce coins to sum to a given value (preferring larger coins where necessary)
 *  - Check the contents of the vending machine 
 * 
 * Provides method to enable interaction via the Console if necessary
 * 
 * @author Peter Tomboline
 * @date 07/06/2020
 *
 */
public class VendingMachine
{

	/** Internal model if the Vending Machine's coin contents */
	private TreeMap<GBPCoin, Integer> mCoinQuantityMap;

	/** The coins deposited by the user for the transaction */
	private ArrayList<GBPCoin> mCoinsDeposited;

	/** The coins to dispense */
	private ArrayList<GBPCoin> mCoinsDispensed;

	/** The total value in the vending machine */
	private int mTotalFunds;

	/** Scanner for interactive input via the Console if preferred */
	private Scanner mScanner;

	/** 
	 * VendingMachine constructor 
	 * 
	 * Creates a Vending Machine and initialises with the quantity of each valid GBP coin specified:
	 * Each index for the input represents a GBP coin type (1p, 2p, 5p, 10p, 20p, 50p, £1, £2, £5)
	 * 
	 * @param initCoins The quantity of coins to be placed into the Vending Machine
	 * 		  
	 */
	public VendingMachine(int[] aInitCoins)
	{

		if (aInitCoins.length != 9)
		{
			System.out.println("Must specify quantity of each type of GBP coin to deposit: "
					+ "1p, 2p, 5p, 10p, 20p, 50p, £1, £2, £5 (9 total)");
			return;
		}

		else
		{

			for (int i : aInitCoins)
			{
				if (i < 0)
				{
					System.out.println("Quantity of coins cannot be negative!");
					return;
				}
			}

			mCoinQuantityMap = new TreeMap<GBPCoin, Integer>();
			mCoinsDeposited = new ArrayList<GBPCoin>();
			mCoinsDispensed = new ArrayList<GBPCoin>();

			// Load map with the coin and quantity
			for (int i = 0; i < aInitCoins.length; i++)
			{
				GBPCoin coin = new GBPCoin(calcCoinFromIndex(i));
				mCoinQuantityMap.put(coin, aInitCoins[i]);
			}

			mTotalFunds = 0;

			for (GBPCoin coin : mCoinQuantityMap.keySet())
			{
				int coinCounter = mCoinQuantityMap.get(coin);
				while (coinCounter > 0)
				{
					mTotalFunds += coin.getValue();
					coinCounter--;
				}
			}
		}
	}

	/** 
	 * Constructor helper method to determines a Coin's name from the index in the initCoins array
	 * 
	 * @param aIndex of the Coin in the array
	 * @return coinName the Name of the coin
	 */
	private String calcCoinFromIndex(int aIndex)
	{

		String coinName = "";

		switch (aIndex)
		{
		case 0:
			coinName = "1p";
			break;
		case 1:
			coinName = "2p";
			break;
		case 2:
			coinName = "5p";
			break;
		case 3:
			coinName = "10p";
			break;
		case 4:
			coinName = "20p";
			break;
		case 5:
			coinName = "50p";
			break;
		case 6:
			coinName = "£1";
			break;
		case 7:
			coinName = "£2";
			break;
		case 8:
			coinName = "£5";
			break;
		}

		return coinName;
	}

	/**
	 * Gets the internal map model of the Vending Machine funds
	 * @return mAvailableFunds the internal map model of the Vending Machine funds
	 */
	public TreeMap<GBPCoin, Integer> getCoinQuantityMap()
	{
		return mCoinQuantityMap;
	}

	/**
	 * Gets the coins deposited into the Vending Machine during deposit
	 * @return mCoinsDeposited the coins deposited into the Vending Machine
	 */
	public ArrayList<GBPCoin> getCoinsDeposited()
	{
		return mCoinsDeposited;
	}

	/**
	 * Gets the coins dispensed by the Vending Machine during producecoins
	 * @return mCoinsDispensed the coins dispensed by the Vending Machine
	 */
	public ArrayList<GBPCoin> getCoinsDispensed()
	{
		return mCoinsDispensed;
	}

	/**
	 * Gets the sum of the total funds available in the Vending Machine
	 * @return mTotalFunds the total funds available in the Vending Machine
	 */
	public int getTotalFundsInPence()
	{
		return mTotalFunds;
	}

	/**
	 * Checks the internal model of the Vending Machine for funds
	 * @return String representation of the Vending Machine's available funds
	 */
	public String getInternalModelString()
	{

		StringBuffer contents = new StringBuffer();

		for (GBPCoin coin : mCoinQuantityMap.keySet())
		{
			int coinCounter = mCoinQuantityMap.get(coin);
			while (coinCounter > 0)
			{
				coinCounter--;
			}

			contents.append(coin.getName() + " | Quantity: " + mCoinQuantityMap.get(coin) + "\n");
		}

		return "Vending Machine Contents: \n-------\n" + contents + "------- \n" + "Total funds: £"
				+ Double.valueOf(mTotalFunds) / 100 + "\n";
	}

	/**
	 * Adds a coin to the Vending Machine's internal model
	 * @param aDepositedCoin The coin to be deposited
	 */
	public void depositCoin(String aCoin)
	{
		try
		{
			GBPCoin coin = new GBPCoin(aCoin);
			mCoinsDeposited.add(coin);

			int currentQuantity = mCoinQuantityMap.get(coin);
			mCoinQuantityMap.put(coin, currentQuantity + 1); // Increment quantity of coin by 1
			mTotalFunds += coin.getValue();

			System.out.println("Coin deposited: " + coin.getName());
			System.out.println("All coins deposited: " + mCoinsDeposited.toString());

		}

		catch (IllegalArgumentException e)
		{
			System.out.println("Coin rejected - " + e.getMessage());
		}
	}

	/**
	 * If possible, produces coins summing to a given value
	 * Value given must be offered in pence, i.e. £1 should be presented as 100
	 * 
	 * @param aTargetValue The value in pence that coins produced must sum to
	 * @return mCoinsDispensed The coins to dispense
	 */
	public ArrayList<GBPCoin> proceduceCoins(int aTargetValue)
	{
		System.out.println("Calculating coins for £" + Double.valueOf(aTargetValue) / 100);

		if (aTargetValue < 0) // Defend against negative input
		{
			System.out.println("Cannot produce coins for negative input £" + Double.valueOf(aTargetValue) / 100);
		}

		else
		{
			if (!hasSufficientFunds(aTargetValue)) // Check we aren't requesting more funds than available
			{
				System.out.println(
						"Insufficient funds in Vending Machine to create £" + Double.valueOf(aTargetValue) / 100);
			}

			int runningTotal = 0;
			int availableCoins = 0;

			// Prefer larger coins first (hence descending)
			for (Entry<GBPCoin, Integer> entry : mCoinQuantityMap.descendingMap().entrySet())
			{

				// entry.getKey(); = the Coin
				// entry.getValue(); = the Quantity of the coin

				availableCoins = entry.getValue();

				if (runningTotal < aTargetValue) // Try to add this type of coin
				{
					while (availableCoins > 0) // we haven't used all of this coin yet
					{
						if ((runningTotal + entry.getKey().getValue()) < aTargetValue) // less than, ok to add coin
						{
							mCoinsDispensed.add(entry.getKey());
							runningTotal += entry.getKey().getValue();
							System.out.println("Added coin: " + entry.getKey());
							System.out.println("Coins to dispense: " + mCoinsDispensed);
							System.out.println("Running Total: £" + Double.valueOf(runningTotal) / 100);
							availableCoins--;
						}

						else if ((runningTotal + entry.getKey().getValue()) == aTargetValue) // will equal what we need
						{
							System.out.println("Added coin: " + entry.getKey());

							mCoinsDispensed.add(entry.getKey());
							runningTotal += entry.getKey().getValue();

							System.out.println(
									"Running Total: £" + Double.valueOf(runningTotal) / 100 + " - Got coins needed!");
							System.out.println("Coins to dispense: " + mCoinsDispensed);

							break;
						}

						else // Adding coin will exceed target value
						{
							// skip the coin
							System.out.println("Adding " + entry.getKey() + " will exceed value!");
							availableCoins = 0;
							break;
						}
					}
				}
			}

			if (runningTotal < aTargetValue) // Check we had the coins necessary for target
			{
				mCoinsDispensed.clear();
				System.out.println("Not able to create " + aTargetValue + " with available coins!");
			}

			System.out.println("For amount: £" + Double.valueOf(aTargetValue) / 100 + ", Coins dispensed: "
					+ mCoinsDispensed + "\n");
		}

		return mCoinsDispensed;

	}

	/** 
	 * A helper method to determine if the Vending Machine has the 
	 * funds sufficient to make the requested value
	 * 
	 * O(n) operation
	 * 
	 * @param aValue The value to sum to
	 * @return If the Vending Machine has sufficient funds
	 */
	public boolean hasSufficientFunds(int aValue)
	{
		mTotalFunds = 0;

		for (GBPCoin coin : mCoinQuantityMap.keySet())
		{

			int coinCounter = mCoinQuantityMap.get(coin);
			while (coinCounter > 0)
			{
				mTotalFunds += coin.getValue();
				coinCounter--;
			}
		}

		System.out.println("Total funds available in Vending Machine: £" + Double.valueOf(mTotalFunds) / 100);

		if (mTotalFunds < aValue)
		{
			return false;
		}

		return true;
	}

	/**
	 * Removes the coins specified from the Vending Machine
	 * @param coins The coins to remove from the Vending Machine
	 */
	public void dispenseCoins(ArrayList<GBPCoin> coins)
	{

		for (GBPCoin coin : coins)
		{
			int currentQuantity = mCoinQuantityMap.get(coin);
			mCoinQuantityMap.put(coin, currentQuantity - 1); // Decrement quantity of coin by 1
			mTotalFunds -= coin.getValue();

			System.out.println("Coin dispensed: " + coin.getName());
		}
		System.out.println("All coins dispensed: " + mCoinsDispensed.toString());
	}

	/** 
	 * Starts the interactive Vending Machine process
	 */
	public void init()
	{
		mScanner = new Scanner(System.in);

		System.out.println("Starting up Vending Machine...\n");
		System.out.println(getInternalModelString());
		System.out.println(
				"Please enter one of the following actions: deposit <coin>, producecoins <value>, check, exit" + "\n");

		while (mScanner.hasNext())
		{
			String input = mScanner.nextLine().toLowerCase();

			if (input.equals("exit"))
			{
				System.out.println("Terminating Vending Machine...");
				System.out.println("Bye! \n");
				mScanner.close();
				break;
			}

			else if (input.startsWith("deposit"))
			{
				String[] parts = input.split("deposit ");
				depositCoin(parts[1]);
			}

			else if (input.startsWith("producecoins"))
			{
				String[] parts = input.split("producecoins ");

				try
				{
					proceduceCoins(Integer.valueOf(parts[1]));
					dispenseCoins(mCoinsDispensed);

					mCoinsDeposited = new ArrayList<GBPCoin>();
					mCoinsDispensed = new ArrayList<GBPCoin>();
				}

				catch (NumberFormatException nfe)
				{
					System.out.println(
							"Cannot process input: " + parts[1] + ", please provide value in pence, e.g. £1 = 100");
				}
			}

			else if (input.startsWith("check"))
			{
				System.out.println("Counting Available Vending Machine coins" + "\n");
				System.out.println(getInternalModelString());
			}

			else if (input.isEmpty())
			{
				// do nothing
			}

			else
			{
				// prompt for a transaction above
				System.out.println(
						"Please enter one of the following actions: deposit <coin>, producecoins <value>, check, exit"
								+ "\n");
			}

		}
	}

}
