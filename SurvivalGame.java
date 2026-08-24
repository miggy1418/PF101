import java.util.Scanner;

public class SurvivalGame {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int health = 50;
        int food = 50;
        int water = 50;
        int energy = 50;

        System.out.println("You got lost in the deep in the forest...");
        System.out.println("With your phone, you sent a distress call for help...");

        input.nextLine();

        System.out.println("Before night falls, you managed to build a shelter...");

        input.nextLine();

        System.out.println("Rescue is on the way...");
        System.out.println("But it will be a while...");

        input.nextLine();

        System.out.println("For now, you need to wait and survive until rescue arrives...");

        input.nextLine();

        System.out.println("Estimated time of arrival: 7 days...");

        input.nextLine();

        int dayCount = 0;

        while (health > 0) {

            dayCount++;
            
            if (dayCount == 8) {
            System.out.println("\n==============================");
            System.out.println("           YOU WIN!");
            System.out.println("==============================");
            System.out.println("Rescue has arrived!");
            System.out.println("You survived for 7 days.");
            break;
        }
            
            System.out.println("\n==============================");
            System.out.println("           DAY " + dayCount);
            System.out.println("==============================");
            System.out.println("Health:  " + health);
            System.out.println("Food:    " + food);
            System.out.println("Water:   " + water);
            System.out.println("Energy:  " + energy);
            System.out.println("==============================");

            System.out.println("\nChoose an option:");
            System.out.println("1. Rest");
            System.out.println("2. Explore");
            System.out.print("Enter choice: ");

            int choice = input.nextInt();

            boolean validAction = false;

            if (choice == 1) {

                energy += 20;

                if (energy > 100) {
                    energy = 100;
                }

                System.out.println("\nYou rested.");
                System.out.println("Your energy increased by 20.");
                System.out.println("Current Energy: " + energy);

                validAction = true;

            } else if (choice == 2) {

                if (energy < 20) {

                    System.out.println("\n==============================");
                    System.out.println("\nYou don't have enough energy to explore.");
                    System.out.println("You need at least 20 energy.");
                    System.out.println("Current Energy: " + energy);

                } else {

                    int find = (int)(Math.random() * 2);

                    System.out.println("\n==============================");
                    System.out.println("\nYou explored the forest...");

                    if (find == 0) {

                        int foundFood = (int)(Math.random() * 4 + 1) * 5;

                        food += foundFood;

                        if (food > 100) {
                            food = 100;
                        }

                        System.out.println("You found +" + foundFood + " food!");

                    } else {

                        int foundWater = (int)(Math.random() * 4 + 1) * 5;

                        water += foundWater;

                        if (water > 100) {
                            water = 100;
                        }

                        System.out.println("You found +" + foundWater + " water!");
                    }

                    energy -= 20;

                    System.out.println("Exploring used -20 energy.");

                    validAction = true;
                }

            } else {

                System.out.println("\nInvalid choice.");
            }

            if (validAction) {

                // Wait for the player to finish the action
                input.nextLine();

                System.out.print("\nPress Enter to end the day...");
                input.nextLine();

                // The day ends here
                food -= 10;
                water -= 10;

                if (food < 0) {
                    food = 0;
                }

                if (water < 0) {
                    water = 0;
                }

                // Health penalty if food or water reaches 0
                if (food == 0 && water == 0) {

                    health -= 20;

                    System.out.println("\nYou have no food or water.");
                    System.out.println("Health -20");

                } else if (food == 0 || water == 0) {

                    health -= 10;

                    if (food == 0) {
                        System.out.println("\nYou have no food.");
                    }

                    if (water == 0) {
                        System.out.println("\nYou have no water.");
                    }

                    System.out.println("Health -10");
                }

                System.out.println("\n==============================");
                System.out.println("\nThe day has ended.");
                System.out.println("Food -10");
                System.out.println("Water -10");

                System.out.print("\nPress Enter to continue to the next day...");
                input.nextLine();
            }
        }

        System.out.println("\n==============================");
        System.out.println("          GAME OVER");
        System.out.println("==============================");
        System.out.println("Days Survived: " + dayCount);
        System.out.println("Final Health: " + health);
        System.out.println("Final Food: " + food);
        System.out.println("Final Water: " + water);
        System.out.println("Final Energy: " + energy);

        input.close();
    }
}