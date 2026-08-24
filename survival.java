import java.util.Random;
import java.util.Scanner;

public class survival {

    static int health = 50;
    static int food = 30;
    static int water = 30;
    static int energy = 40;

    static int emergencyRations = 0;
    static int waterFlasks = 0;
    static int energyDrinks = 0;
    static int medKits = 0;

    static int[] dailyHealth = new int[7];
    static int[] dailyFood = new int[7];
    static int[] dailyWater = new int[7];

    static Random random = new Random();

    public static void findFood() {
        System.out.println("\nYou search the forest for food.");

        if (energy < 10) {
            System.out.println("You are too tired to search.");
            return;
        }

        energy -= 15;
        water -= 5;

        int chance = random.nextInt(100);

        if (chance < 70) {
            int amount = random.nextInt(11) + 10;
            food += amount;
            System.out.println("You found " + amount + " food.");
            System.out.println("Food +" + amount);
        } else {
            System.out.println("You could not find any food.");
        }

        System.out.println("Energy -15");
        System.out.println("Water -5");
    }

    public static void findWater() {
        System.out.println("\nYou search the area for water.");

        if (energy < 10) {
            System.out.println("You are too tired to search.");
            return;
        }

        energy -= 15;
        food -= 3;

        int chance = random.nextInt(100);

        if (chance < 75) {
            int amount = random.nextInt(11) + 10;
            water += amount;
            System.out.println("You found " + amount + " water.");
            System.out.println("Water +" + amount);
        } else {
            System.out.println("You could not find any water.");
        }

        System.out.println("Energy -15");
        System.out.println("Food -3");
    }

    public static void rest() {
        System.out.println("\nYou rest at your campsite.");

        int amount = random.nextInt(11) + 20;

        energy += amount;
        food -= 8;
        water -= 8;

        System.out.println("Energy +" + amount);
        System.out.println("Food -8");
        System.out.println("Water -8");

        if (random.nextInt(100) < 25) {
            health += 5;
            System.out.println("Health +5");
        }
    }

    public static void explore() {
        System.out.println("\nYou explore the wilderness.");

        if (energy < 15) {
            System.out.println("You are too tired to explore.");
            return;
        }

        energy -= 20;
        food -= 5;
        water -= 5;

        int event = random.nextInt(5);

        if (event == 0) {
            food += 25;
            System.out.println("You found an abandoned supply box.");
            System.out.println("Food +25");

        } else if (event == 1) {
            water += 25;
            System.out.println("You found a hidden spring.");
            System.out.println("Water +25");

        } else if (event == 2) {
            food += 15;
            water += 15;
            System.out.println("You found an abandoned campsite.");
            System.out.println("Food +15");
            System.out.println("Water +15");

        } else if (event == 3) {
            health -= 20;
            System.out.println("A wild animal attacked you.");
            System.out.println("Health -20");

        } else {
            energy -= 10;
            health -= 5;
            System.out.println("You became lost in the forest.");
            System.out.println("Health -5");
            System.out.println("Energy -10");
        }

        findPowerUp();
    }

    public static void findPowerUp() {
        int chance = random.nextInt(100);

        if (chance < 10) {
            emergencyRations++;
            System.out.println("\nYou found an Emergency Ration!");

        } else if (chance < 17) {
            waterFlasks++;
            System.out.println("\nYou found a Water Flask!");

        } else if (chance < 23) {
            energyDrinks++;
            System.out.println("\nYou found an Energy Drink!");

        } else if (chance < 28) {
            medKits++;
            System.out.println("\nYou found a Med Kit!");
        }
    }

    public static void usePowerUp(Scanner input) {

        if (emergencyRations == 0 &&
            waterFlasks == 0 &&
            energyDrinks == 0 &&
            medKits == 0) {

            System.out.println("\nYou have no power-ups.");
            return;
        }

        System.out.println("\n========== POWER-UPS ==========");
        System.out.println("1. Emergency Ration: " + emergencyRations);
        System.out.println("2. Water Flask: " + waterFlasks);
        System.out.println("3. Energy Drink: " + energyDrinks);
        System.out.println("4. Med Kit: " + medKits);
        System.out.println("5. Cancel");
        System.out.println("===============================");

        System.out.print("Choose a power-up: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid input.");
            input.next();
            return;
        }

        int choice = input.nextInt();

        if (choice == 1) {

            if (emergencyRations > 0) {
                emergencyRations--;
                food += 25;
                System.out.println("You used an Emergency Ration.");
                System.out.println("Food +25");
            } else {
                System.out.println("You don't have an Emergency Ration.");
            }

        } else if (choice == 2) {

            if (waterFlasks > 0) {
                waterFlasks--;
                water += 25;
                System.out.println("You used a Water Flask.");
                System.out.println("Water +25");
            } else {
                System.out.println("You don't have a Water Flask.");
            }

        } else if (choice == 3) {

            if (energyDrinks > 0) {
                energyDrinks--;
                energy += 30;
                System.out.println("You used an Energy Drink.");
                System.out.println("Energy +30");
            } else {
                System.out.println("You don't have an Energy Drink.");
            }

        } else if (choice == 4) {

            if (medKits > 0) {
                medKits--;
                health += 20;
                System.out.println("You used a Med Kit.");
                System.out.println("Health +20");
            } else {
                System.out.println("You don't have a Med Kit.");
            }

        } else if (choice == 5) {

            System.out.println("You saved your power-ups.");

        } else {

            System.out.println("Invalid choice.");
        }
    }

    public static void displayStatus() {

        System.out.println("\n==============================");
        System.out.println("       SURVIVOR STATUS");
        System.out.println("==============================");
        System.out.println("Health : " + health);
        System.out.println("Food   : " + food);
        System.out.println("Water  : " + water);
        System.out.println("Energy : " + energy);
        System.out.println("------------------------------");
        System.out.println("Emergency Rations: " + emergencyRations);
        System.out.println("Water Flasks     : " + waterFlasks);
        System.out.println("Energy Drinks    : " + energyDrinks);
        System.out.println("Med Kits         : " + medKits);
        System.out.println("==============================");
    }

    public static void limitValues() {

        if (health > 100) {
            health = 100;
        }

        if (food > 100) {
            food = 100;
        }

        if (water > 100) {
            water = 100;
        }

        if (energy > 100) {
            energy = 100;
        }

        if (health < 0) {
            health = 0;
        }

        if (food < 0) {
            food = 0;
        }

        if (water < 0) {
            water = 0;
        }

        if (energy < 0) {
            energy = 0;
        }
    }

    public static void dailyNeeds() {

        food -= 8;
        water -= 8;
        energy -= 5;

        System.out.println("\nA new day begins.");
        System.out.println("Food -8");
        System.out.println("Water -8");
        System.out.println("Energy -5");

        if (food <= 0) {
            food = 0;
            health -= 15;
            System.out.println("You are starving.");
            System.out.println("Health -15");
        }

        if (water <= 0) {
            water = 0;
            health -= 20;
            System.out.println("You are dehydrated.");
            System.out.println("Health -20");
        }

        if (energy <= 0) {
            energy = 0;
            health -= 10;
            System.out.println("You are exhausted.");
            System.out.println("Health -10");
        }
    }

    public static boolean isAlive() {
        return health > 0;
    }

    public static int getChoice(Scanner input) {

        while (true) {

            System.out.print("\nChoose an action: ");

            if (input.hasNextInt()) {

                int choice = input.nextInt();

                if (choice >= 1 && choice <= 5) {
                    return choice;
                }

                System.out.println("Please enter a number from 1 to 5.");

            } else {

                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("          THE LAST SURVIVOR");
        System.out.println("======================================");
        System.out.println("Your plane has crashed in the wilderness.");
        System.out.println("A rescue team will arrive after 7 days.");
        System.out.println("Survive until they arrive.");

        for (int day = 1; day <= 7; day++) {

            System.out.println("\n======================================");
            System.out.println("              DAY " + day);
            System.out.println("======================================");

            dailyNeeds();
            limitValues();

            if (!isAlive()) {
                System.out.println("\nYou died during the night.");
                System.out.println("GAME OVER.");
                break;
            }

            displayStatus();

            System.out.println("\nWhat do you want to do?");
            System.out.println("--------------------------------------");
            System.out.println("1. Find Food");
            System.out.println("2. Find Water");
            System.out.println("3. Rest");
            System.out.println("4. Explore");
            System.out.println("5. Use Power-Up");
            System.out.println("--------------------------------------");

            int choice = getChoice(input);

            if (choice == 1) {
                findFood();

            } else if (choice == 2) {
                findWater();

            } else if (choice == 3) {
                rest();

            } else if (choice == 4) {
                explore();

            } else if (choice == 5) {
                usePowerUp(input);
            }

            limitValues();

            dailyHealth[day - 1] = health;
            dailyFood[day - 1] = food;
            dailyWater[day - 1] = water;

            displayStatus();

            if (!isAlive()) {
                System.out.println("\nYou died in the wilderness.");
                System.out.println("GAME OVER.");
                break;
            }

            if (day < 7) {
                System.out.println("\nYou survived Day " + day + ".");
                System.out.println("Prepare for tomorrow.");
            }
        }

        if (health > 0) {

            System.out.println("\n======================================");
            System.out.println("           YOU SURVIVED!");
            System.out.println("======================================");
            System.out.println("The rescue team has arrived.");
            System.out.println("You survived all 7 days!");
        }

        System.out.println("\n======================================");
        System.out.println("            SURVIVAL LOG");
        System.out.println("======================================");

        for (int i = 0; i < 7; i++) {

            if (dailyHealth[i] != 0) {

                System.out.println(
                    "Day " + (i + 1)
                    + " | Health: " + dailyHealth[i]
                    + " | Food: " + dailyFood[i]
                    + " | Water: " + dailyWater[i]
                );
            }
        }

        input.close();
    }
}
