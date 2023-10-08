package machine;

class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private State state;

    enum State {
        CHOOSING_ACTION,
        CHOOSING_COFFEE,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_COFFEE_BEANS,
        FILLING_CUPS
    }

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        this.state = State.CHOOSING_ACTION;
    }

    public String processInput(String input) {
        switch (state) {
            case CHOOSING_ACTION:
                return chooseAction(input);
            case CHOOSING_COFFEE:
                return chooseCoffee(input);
            case FILLING_WATER:
                return fillWater(Integer.parseInt(input));
            case FILLING_MILK:
                return fillMilk(Integer.parseInt(input));
            case FILLING_COFFEE_BEANS:
                return fillCoffeeBeans(Integer.parseInt(input));
            case FILLING_CUPS:
                return fillCups(Integer.parseInt(input));
            default:
                return "Unknown state";
        }
    }

    private String chooseAction(String action) {
        switch (action) {
            case "buy":
                state = State.CHOOSING_COFFEE;
                return "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case "fill":
                state = State.FILLING_WATER;
                return "Write how many ml of water you want to add:";
            case "take":
                int cash = money;
                money = 0;
                state = State.CHOOSING_ACTION;
                return "I gave you $" + cash;
            case "remaining":
                state = State.CHOOSING_ACTION;
                return remaining();
            case "exit":
                System.exit(0);
            default:
                return "Unknown action";
        }
    }

    private String chooseCoffee(String coffeeType) {
        switch (coffeeType) {
            case "1":
                return buyCoffee(250, 0, 16, 4);
            case "2":
                return buyCoffee(350, 75, 20, 7);
            case "3":
                return buyCoffee(200, 100, 12, 6);
            case "back":
                state = State.CHOOSING_ACTION;
                return "Write action (buy, fill, take, remaining, exit):";
            default:
                return "Unknown coffee type";
        }
    }

    private String buyCoffee(int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int cost) {
        if (water < waterNeeded) {
            state = State.CHOOSING_ACTION;
            return "Sorry, not enough water!";
        } else if (milk < milkNeeded) {
            state = State.CHOOSING_ACTION;
            return "Sorry, not enough milk!";
        } else if (coffeeBeans < coffeeBeansNeeded) {
            state = State.CHOOSING_ACTION;
            return "Sorry, not enough coffee beans!";
        } else if (cups < 1) {
            state = State.CHOOSING_ACTION;
            return "Sorry, not enough disposable cups!";
        } else {
            water -= waterNeeded;
            milk -= milkNeeded;
            coffeeBeans -= coffeeBeansNeeded;
            money += cost;
            cups--;
            state = State.CHOOSING_ACTION;
            return "I have enough resources, making you a coffee!";
        }
    }

    private String fillWater(int amount) {
        water += amount;
        state = State.FILLING_MILK;
        return "Write how many ml of milk you want to add:";
    }

    private String fillMilk(int amount) {
        milk += amount;
        state = State.FILLING_COFFEE_BEANS;
        return "Write how many grams of coffee beans you want to add:";
    }

    private String fillCoffeeBeans(int amount) {
        coffeeBeans += amount;
        state = State.FILLING_CUPS;
        return "Write how many disposable cups you want to add:";
    }

    private String fillCups(int amount) {
        cups += amount;
        state = State.CHOOSING_ACTION;
        return "Write action (buy, fill, take, remaining, exit):";
    }

    private String remaining() {
        return String.format(
                "The coffee machine has:\n" +
                        "%d ml of water\n" +
                        "%d ml of milk\n" +
                        "%d g of coffee beans\n" +
                        "%d disposable cups\n" +
                        "$%d of money\n", water, milk, coffeeBeans, cups, money);
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Write action (buy, fill, take, remaining, exit): ");
            String action = scanner.nextLine();
            String response = coffeeMachine.processInput(action);
            System.out.println(response);
        }
    }
}
