package CoffeeMachine;

import java.lang.NumberFormatException;

enum STATE {
	WAITING, BUYING, FILLING
}
enum FILLING_STATE {
	FILLING_WATER, FILLING_MILK, FILLING_COFFEE, FILLING_CUPS
}

public class CoffeeMachine {

	//espresso constants
	private static final int E_WATER_PER_CUP = 250;
	private static final int E_COFFEE_PER_CUP = 16;
	private static final int E_COST = 4;

	// latte constants
	private static final int L_WATER_PER_CUP = 350;
	private static final int L_MILK_PER_CUP = 75;
	private static final int L_COFFEE_PER_CUP = 20;
	private static final int L_COST = 7;

	// cappuccino constants
	private static final int C_WATER_PER_CUP = 200;
	private static final int C_MILK_PER_CUP = 100;
	private static final int C_COFFEE_PER_CUP = 12;
	private static final int C_COST = 6;

	// messages
	private static final String NOT_ENOUGHT_MESSAGE = "Sorry, not enough %s!";

	// resources
	private int water;
	private int milk;
	private int coffee;
	private int cups;
	private int money;
	private boolean isWorking;
	private STATE state;
	private FILLING_STATE fState;

	private void printCoffeeMachineInfo() {
		System.out.printf("%nThe coffee machine has: %n" + 
		"%d ml of water%n" + 
		"%d ml of milk%n" + 
		"%d g of coffee beans%n" + 
		"%d disposable cups%n" + 
		"$%d of money%n%n", this.water, this.milk, this.coffee, this.cups, this.money);
	}

	private void takeMoney() {
		System.out.printf("\nI gave you $%d\n\n", this.money);
		this.money = 0;
	}

	private void returnToMainMenu() {
		state = STATE.WAITING;
		System.out.println("Write action (buy, fill, take, remaining, exit): ");
	}

	private int toInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public CoffeeMachine(int water, int milk, int coffee, int cups, int money) {
		this.water = water;
		this.milk = milk;
		this.coffee = coffee;
		this.cups = cups;
		this.money = money;
		this.isWorking = true;
		this.fState = null;
		returnToMainMenu();
	}

	public boolean getIsWorking() {
		return isWorking;
	}

	public void operate(String input) {
		switch (state) {
			case WAITING:
				switch(input) {
					case "remaining":
						printCoffeeMachineInfo();
						returnToMainMenu();
						break;
					case "take":
						takeMoney();
						returnToMainMenu();
						break;
					case "buy":
						this.state = STATE.BUYING;
						System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
						break;
					case "fill":
						this.state = STATE.FILLING;
						this.fState = FILLING_STATE.FILLING_WATER;
						System.out.println("Write how many ml of water you want to add: ");
						break;
					case "exit":
						isWorking = false;
						break;
				}
				break;
			case BUYING:
				int waterSpending = 0, coffeeSpending = 0, milkSpending = 0;
				int cost = 0;
				int inputValue = toInt(input);
				boolean incorrectInput = false;
				switch (inputValue) {
					case 1:
						waterSpending = E_WATER_PER_CUP;
						coffeeSpending = E_COFFEE_PER_CUP;
						milkSpending = 0;
						cost = E_COST;
						break;
					case 2:
						waterSpending = L_WATER_PER_CUP;
						coffeeSpending = L_COFFEE_PER_CUP;
						milkSpending = L_MILK_PER_CUP;
						cost = L_COST;
						break;	
					case 3:
						waterSpending = C_WATER_PER_CUP;
						coffeeSpending = C_COFFEE_PER_CUP;
						milkSpending = C_MILK_PER_CUP;
						cost = C_COST;
						break;
					default:
						incorrectInput = true;
						break;
				}

				if (incorrectInput) {
					break;
				}

				if (water - waterSpending < 0) {
					System.out.println(String.format(NOT_ENOUGHT_MESSAGE, "water") + "\n");
				}
				else if (coffee - coffeeSpending < 0) {
					System.out.println(String.format(NOT_ENOUGHT_MESSAGE, "coffee") + "\n");
				}
				else if (milk - milkSpending < 0) {
					System.out.println(String.format(NOT_ENOUGHT_MESSAGE, "milk") + "\n");
				} else {
					System.out.println("I have enough resources, making you a coffee!" + "\n");
					water -= waterSpending;
					coffee -= coffeeSpending;
					milk -= milkSpending;
					cups--;
					money += cost;
				}

				returnToMainMenu();
				break;
			case FILLING:
				switch(fState) {
					case FILLING_WATER:
						water += toInt(input);
						fState = FILLING_STATE.FILLING_MILK;
						System.out.println("Write how many ml of milk you want to add: ");
						break;
					case FILLING_MILK:
						milk += toInt(input);
						fState = FILLING_STATE.FILLING_COFFEE;
						System.out.println("Write how many grams of coffee beans you want to add: ");
						break;
					case FILLING_COFFEE:
						coffee += toInt(input);
						fState = FILLING_STATE.FILLING_CUPS;
						System.out.println("Write how many disposable cups of coffee you want to add: ");
						break;
					case FILLING_CUPS:
						cups += toInt(input);
						fState = null;
						returnToMainMenu();
						System.out.println();
						break;
				}
		}
	}
}