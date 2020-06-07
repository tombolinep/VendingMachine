package junit;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import main.GBPCoin;

/**
 * Tests the robustness of the GBP Coin class
 * 
 * @author Peter Tomboline
 * @date 07/06/2020
 */
class TestGBPCoin
{

	/** The coin that test cases will be run on */
	private GBPCoin testCoin;

	/**
	 * Verifies that a GBP coin with an invalid name cannot be created
	 */
	@Test
	void testInvalidName()
	{

		try
		{
			testCoin = new GBPCoin("15p");
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("has not (yet) been minted"));
		}

	}

	/**
	 * Verifies that a GBP coin with an invalid name cannot be created
	 */
	@Test
	void testEmptyName()
	{

		try
		{
			testCoin = new GBPCoin("");
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("has not (yet) been minted"));
		}

	}

	/**
	 * Verifies that a GBP coin with an invalid value cannot created
	 */
	@Test
	void testInvalidValue()
	{
		try
		{
			testCoin = new GBPCoin(15);
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Invaid value for GBP coin"));
		}
	}

	/**
	 * Verifies that a GBP coin with an invalid negative value cannot created
	 */
	@Test
	void testInvalidValueNagative()
	{
		try
		{
			testCoin = new GBPCoin(-5);
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Invaid value for GBP coin"));
		}
	}

	/**
	 * Verifies that a GBP coin with no value cannot created
	 */
	@Test
	void testInvalidValueZero()
	{
		try
		{
			testCoin = new GBPCoin(0);
		}

		catch (IllegalArgumentException e)
		{
			Assert.assertTrue(e.getMessage().contains("Invaid value for GBP coin"));
		}
	}

	/**
	 * Verifies that a GBP coin with valid name can be created
	 */
	@Test
	void testValidName()
	{
		testCoin = new GBPCoin("10p");
		Assert.assertTrue(testCoin.getName().equals("10p"));
	}

	/**
	 * Verifies that a GBP coin with valid name has correct value
	 */
	@Test
	void testValidNameValue()
	{
		testCoin = new GBPCoin("£1");
		Assert.assertTrue(testCoin.getValue() == 100);
	}

	/**
	 * Verifies that a GBP coin with valid name does not have incorrect value
	 */
	@Test
	void testValidNameValue2()
	{
		testCoin = new GBPCoin("£1");
		Assert.assertFalse(testCoin.getValue() == 1);
	}

	/**
	 * Verifies that a GBP coin with valid value can be created
	 */
	@Test
	void testValidValue()
	{
		testCoin = new GBPCoin(10);
		Assert.assertTrue(testCoin.getValue() == 10);
	}

	/**
	 * Verifies that a GBP coin with valid value has correct name
	 */
	@Test
	void testValidValueName()
	{
		testCoin = new GBPCoin(100);
		Assert.assertTrue(testCoin.getName().equals("£1"));
	}

	/**
	 * Verifies that a GBP coin with valid value does not have incorrect name
	 */
	@Test
	void testValidValueName2()
	{
		testCoin = new GBPCoin(500);
		Assert.assertFalse(testCoin.getName().equals("500p"));
	}

}
