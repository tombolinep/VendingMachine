package junit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import main.GBPCoin;
import main.VendingMachine;

/**
 * Tests the Vending Machine class and APIs
 * 
 * @author Peter Tomboline
 * @date 07/06/2020
 */
class TestVendingMachine
{

	/** The Vending Machine to test*/
	VendingMachine vm;

	/** Used to generate a random number */
	Random randomGen = new Random();

	/** The range of the number randomly generated (0 .. n-1) */
	int randRange = 25;

	/** Random numbers generated from the defined range */
	int random1 = randomGen.nextInt(randRange);
	int random2 = randomGen.nextInt(randRange);
	int random3 = randomGen.nextInt(randRange);
	int random4 = randomGen.nextInt(randRange);
	int random5 = randomGen.nextInt(randRange);
	int random6 = randomGen.nextInt(randRange);
	int random7 = randomGen.nextInt(randRange);
	int random8 = randomGen.nextInt(randRange);
	int random9 = randomGen.nextInt(randRange);

	/** Initialise the Vending Machine with 10 of all coins */
	int[] initCoins10 = new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10 };

	/** Initialise the Vending Machine with odd quantities of all coins */
	int[] initCoinsOdd = new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17 };

	/** Initialise the Vending Machine with no coins */
	int[] initCoinsZero = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	/** Initialise the Vending Machine with negative quantity of coins*/
	int[] initCoinsNegative = new int[] { -1, -2, -3, -4, -5, -6, -7, -8, 0 };

	/** Initialise the Vending Machine with an empty number coins*/
	int[] initCoinsEmpty = new int[] {};

	/** Initialise the Vending Machine with an only half number of coins*/
	int[] initCoinsHalf = new int[] { 1, 2, 3, 4, 5 };

	/** Initialise the Vending Machine with randomly generated quantities of all coins */
	int[] initCoinsRandom = new int[] { random1, random2, random3, random4, random5, random6, random7, random8,
			random9 };

	/**
	 * Verifies that a Vending Machine cannot be created with empty
	 * quantities of coins
	 */
	@Test
	void testEmptyInput()
	{
		try
		{
			vm = new VendingMachine(initCoinsEmpty);
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Must specify quantity of each type of GBP coin"));
		}
	}

	/**
	 * Verifies that a Vending Machine cannot be created with empty
	 * quantities of coins
	 */
	@Test
	void testNegativeInput()
	{
		try
		{
			vm = new VendingMachine(initCoinsNegative);
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("cannot be negative"));
		}
	}

	/**
	 * Verifies that a Vending Machine can only be created when all quantities
	 * for all valid coins are specified
	 */
	@Test
	void testHalfInput()
	{
		try
		{
			vm = new VendingMachine(initCoinsHalf);
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Must specify quantity of each type of GBP coin"));
		}
	}

	/**
	 * Verifies that valid input initialises a Vending Machine
	 */
	@Test
	void testValidInput()
	{
		vm = new VendingMachine(initCoins10);
		Assert.assertFalse(vm == null);
	}

	/**
	 * Verifies that a Vending Machine can be created with zero coins
	 */
	@Test
	void testValidInputZero()
	{
		vm = new VendingMachine(initCoinsZero);
		Assert.assertFalse(vm == null);
	}

	/**
	 * Verifies that valid input initialises a Vending Machine with correct quantity
	 */
	@Test
	void testValidInputQuantity()
	{
		vm = new VendingMachine(initCoins10); // 10 of each coin

		Collection<Integer> values = vm.getCoinQuantityMap().values();

		for (Integer val : values) // check quantities of all coins is 10
		{
			if (val != 10)
			{
				Assert.fail();
			}
		}
	}

	/**
	 * Verifies that valid input initialises a Vending Machine with correct (zero) quantity
	 */
	@Test
	void testValidInputQuantityZero()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin

		Assert.assertTrue(vm.getTotalFundsInPence() == 0);
	}

	/**
	 * Verifies ability to deposit a valid GBP coin
	 */
	@Test
	void testDepositValid()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin
		vm.depositCoin("1p");

		TreeMap<GBPCoin, Integer> vmModel = vm.getCoinQuantityMap();
		GBPCoin onePence = new GBPCoin("1p");

		Assert.assertTrue(vmModel.get(onePence) == 1);
	}

	/**
	 * Verifies ability to deposit a valid GBP coin
	 */
	@Test
	void testDepositValidRandom()
	{
		vm = new VendingMachine(initCoinsRandom); // 0 of each coin
		vm.depositCoin("1p");

		TreeMap<GBPCoin, Integer> vmModel = vm.getCoinQuantityMap();
		GBPCoin onePence = new GBPCoin("1p");

		Assert.assertTrue(vmModel.get(onePence) == (random1 + 1));
	}

	/**
	 * Verifies that it is not possible to deposit an invalid GBP coin
	 */
	@Test
	void testDepositInvalid()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin

		try
		{
			vm.depositCoin("167p");
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Coin rejected"));
		}
	}

	/**
	 * Verifies that it is not possible to deposit an empty
	 */
	@Test
	void testDepositInvalidEmpty()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin

		try
		{
			vm.depositCoin("");
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Coin rejected"));
		}
	}

	/**
	 * Verifies ability to deposit a valid GBP coin and model is updated
	 */
	@Test
	void testDepositValidUpdatesFunds()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin
		vm.depositCoin("1p");

		Assert.assertTrue(vm.getTotalFundsInPence() == 1);
	}

	/**
	 * Verifies ability to deposit consecutive valid GBP coin
	 */
	@Test
	void testDepositValidConsecutive()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin
		vm.depositCoin("1p");
		vm.depositCoin("1p");
		vm.depositCoin("2p");
		vm.depositCoin("5p");
		vm.depositCoin("10p");
		vm.depositCoin("£2");
		vm.depositCoin("£1");
		vm.depositCoin("£1");
		vm.depositCoin("£1");
		vm.depositCoin("£2");

		TreeMap<GBPCoin, Integer> vmModel = vm.getCoinQuantityMap();
		GBPCoin onePence = new GBPCoin("1p");
		GBPCoin onePound = new GBPCoin("£1");
		GBPCoin tenPence = new GBPCoin("10p");

		Assert.assertTrue(vmModel.get(onePence) == 2);
		Assert.assertTrue(vmModel.get(onePound) == 3);
		Assert.assertTrue(vmModel.get(tenPence) == 1);
		Assert.assertTrue(vm.getTotalFundsInPence() == 719);

	}

	/**
	 * Verifies ability to produce coins for valid input
	 */
	@Test
	void testProduceCoinsValid()
	{
		vm = new VendingMachine(initCoins10); // 10 of each coin

		ArrayList<GBPCoin> coinsToDispense = vm.proceduceCoins(1000); // £10

		int coinsTotal = 0;
		for (GBPCoin coin : coinsToDispense)
		{
			coinsTotal += coin.getValue();
		}

		Assert.assertTrue(coinsTotal == 1000);
	}

	/**
	 * Verifies ability to produce coins to identify an input sum that is too large
	 */
	@Test
	void testProduceCoinsInputTooLarge()
	{
		vm = new VendingMachine(initCoins10); // 10 of each coin

		try
		{
			vm.proceduceCoins(1234567); // £12,345.67
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Insufficient funds in Vending Machine"));
		}

	}

	/**
	 * Verifies ability to produce coins to catch insufficient coins 
	 */
	@Test
	void testProduceCoinsInsufficient()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin
		vm.depositCoin("10p");
		vm.depositCoin("£2");

		try
		{
			vm.proceduceCoins(203); // £2.30
		} catch (RuntimeException e)
		{
			Assert.assertTrue(e.getMessage().contains("Not able to create"));
		}
	}

	/**
	 * Verifies ability to produce coins to catch insufficient coins 
	 */
	@Test
	void testProduceCoinsInsufficient2()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin
		vm.depositCoin("10p");
		vm.depositCoin("£2");

		try
		{
			vm.proceduceCoins(203); // £2.30
		} catch (RuntimeException e)
		{
			Assert.assertTrue(e.getMessage().contains("Not able to create"));
		}
	}

	/**
	 * Verifies ability to produce coins to catch negative input
	 */
	@Test
	void testProduceCoinsNegative()
	{
		vm = new VendingMachine(initCoins10); // 10 of each coin

		try
		{
			vm.proceduceCoins(-5); // -£0.05
		} catch (RuntimeException e)
		{
			Assert.assertTrue(e.getMessage().contains("Cannot produce coins for negative input"));
		}
	}

	/**
	 * Verifies ability to produce coins for valid input
	 */
	@Test
	void testProduceCoinsValid2()
	{
		vm = new VendingMachine(initCoinsOdd);

		ArrayList<GBPCoin> coinsToDispense = vm.proceduceCoins(12345); // £123.45

		int coinsTotal = 0;
		for (GBPCoin coin : coinsToDispense)
		{
			coinsTotal += coin.getValue();
		}

		Assert.assertTrue(coinsTotal == 12345);
	}

	/**
	 * Verifies ability to produce coins for valid input and updates the 
	 * Vending Machine's model
	 */
	@Test
	void testProduceCoinsValidAndUpdates()
	{
		vm = new VendingMachine(initCoins10); // 10 of each coin
		int startingFunds = vm.getTotalFundsInPence();
		ArrayList<GBPCoin> coinsToDispense = vm.proceduceCoins(748); // £7.48

		int coinsTotal = 0;
		for (GBPCoin coin : coinsToDispense)
		{
			coinsTotal += coin.getValue();
		}

		Assert.assertTrue(coinsTotal == 748); // Checks coins total sum

		vm.dispenseCoins(coinsToDispense);

		Assert.assertTrue(vm.getTotalFundsInPence() == (startingFunds - coinsTotal));
	}

	/**
	 * Verifies ability to produce coins for valid input and updates the 
	 * Vending Machine's model
	 */
	@Test
	void testProduceCoinsValidAndUpdates2()
	{
		vm = new VendingMachine(initCoinsZero); // 0 of each coin
		vm.depositCoin("1p");
		vm.depositCoin("5p");

		TreeMap<GBPCoin, Integer> vmModel = vm.getCoinQuantityMap(); // Check model
		GBPCoin onePence = new GBPCoin("1p");
		GBPCoin fivePence = new GBPCoin("5p");

		Assert.assertTrue(vmModel.get(onePence) == 1);
		Assert.assertTrue(vmModel.get(fivePence) == 1);

		Assert.assertTrue(vm.getTotalFundsInPence() == 6); // Check funds have updated

		ArrayList<GBPCoin> coinsToDispense = vm.proceduceCoins(6); // £0.06

		int coinsTotal = 0;
		for (GBPCoin coin : coinsToDispense)
		{
			coinsTotal += coin.getValue();
		}

		Assert.assertTrue(coinsTotal == 6); // Checks coins being dispensed total sum

		vm.dispenseCoins(coinsToDispense);

		Assert.assertTrue(vmModel.get(onePence) == 0); // Check coins removed from model
		Assert.assertTrue(vmModel.get(fivePence) == 0);

		Assert.assertTrue(vm.getTotalFundsInPence() == 0); // Check total funds update

	}

}
