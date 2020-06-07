package main;

/**
 * The class to execute the interactive solution
 * 
 * @author Peter Tomboline
 * @date 07/06/2020
 *
 */
public class InteractiveSolution
{

	public static void main(String[] args)
	{
		
		// 1p, 2p, 5p, 10p, 20p, 50p, £1, £2, £5
		int[] initCoins = new int[]{ 1, 2, 5, 1, 2, 5, 1, 2, 5 };
		
		VendingMachine vm = new VendingMachine(initCoins);
		
		vm.init();

	}

}
