package CoffeeMachine;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
		while (coffeeMachine.getIsWorking()) {
			String input = sc.next();
			coffeeMachine.operate(input);
		}
		sc.close();
    }
}
