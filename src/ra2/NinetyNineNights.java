package ra2;

import java.util.Random;
import java.util.Scanner;

/**
 * 99 Nights in the Forest Single-class, single-main Java game using static
 * methods (no extra objects).
 *
 * Controls (during each day): choose one of the available actions by typing the
 * number.
 *
 * Goal: survive until night 99. If health reaches 0 you lose.
 *
 * Author: JPortugal
 */
public class NinetyNineNights {

    static final int TARGET_NIGHTS = 99;
    public static final int FORAGE = 1;
    public static final int BUILD_FIRE = 2;
    public static final int SLEEP = 3;
    public static final int EXPL0RE = 4; 
    public static final int CRAFT_SIGNAL = 5;

    // Player state
    static int days = 1;
    static int health = 100;        // if <= 0 -> dead
    static int energy = 100;        // used for actions, regenerates when resting
    static int fireStrength = 50;   // keeps cold away; 0 = very cold
    static int morale = 50;         // affects chance of good outcomes
    static int food = 3;            // simple food units
    static Random rnd = new Random();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printIntro();
        pauseShort();

        while (true) {
            if (health <= 0) {
                gameOver();
                break;
            }
            if (days > TARGET_NIGHTS) {
                victory();
                break;
            }

            displayDayHeader();
            displayStatus();

            // Night actions: player chooses one action to perform
            int choice = chooseAction();

            switch (choice) {
                case FORAGE:
                    forage();
                    break;
                case BUILD_FIRE:
                    buildFire();
                    break;
                case SLEEP:
                    sleep();
                    break;
                case EXPLORE:
                    explore();
                    break;
                case CRAFT_SIGNAL:
                    craftSignal();
                    break;
                default:
                    System.out.println("You hesitate and do nothing this night.");
                    doNothing();
                    break;
            }

            // Night event after action
            pauseShort();
            displayNightHeader();
            nightEvent();

            // End-of-days natural effects
            endOfNightTick();

            pauseShort();
            //days++; 
        }

        sc.close();
    }

    // ---------- UI / Intro ----------
    static void printIntro() {
        System.out.println("=== 99 NIGHTS IN THE FOREST ===");
        System.out.println("You are alone in a vast forest. Your goal: survive 99 nights.");
        System.out.println("Manage health, energy, fire, food and morale.");
        System.out.println("Good luck.");
        System.out.println();
    }

    static void displayDayHeader() {
        System.out.println("\n------------------------");
        System.out.println("\n--- Day " + days + " ---");
        System.out.println("\n------------------------");
    }

    static void displayNightHeader() {
        System.out.println("\n--- Night is approaching  ---");
    }

    static void displayStatus() {
        System.out.printf("Health: %d | Energy: %d | Fire: %d | Morale: %d | Food: %d\n",
                clamp(health), clamp(energy), clamp(fireStrength), clamp(morale), Math.max(food, 0));
    }

    static void pauseShort() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    // ---------- Player actions (methods) ----------
    static int chooseAction() {
        System.out.println("\nChoose an action this night:");
        System.out.println("1) Forage for food (risky, can find food)");
        System.out.println("2) Maintain fire (keeps you warm, costs energy)");
        System.out.println("3) Sleep (restore energy/health)");
        System.out.println("4) Explore (chance to find shelter or danger)");
        System.out.println("5) Craft a signal (low chance to be spotted, costs resources)");
        System.out.print("> ");
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void forage() {
        System.out.println("You go out looking for food...");
        energy -= 15;
        int r = rnd.nextInt(100);

        if (r < 40 + morale / 4) { // success probability depends on morale
            int found = 1 + rnd.nextInt(3); // 1-3 food
            food += found;
            morale += 2;
            System.out.println("Success! You found " + found + " food unit(s).");
        } else if (r < 40 + morale / 4 + 20) {
            System.out.println("You found nothing but made it back safely.");
            morale -= 1;
        } else {
            System.out.println("You ran into danger while foraging!");
            int damage = 5 + rnd.nextInt(16);
            health -= damage;
            morale -= 5;
            System.out.println("You take " + damage + " damage escaping.");
        }
    }

    static void buildFire() {
        System.out.println("You work on the fire, chopping some nearby trees for wood...");
        energy -= 12; // slightly more energy because chopping wood is harder
        int r = rnd.nextInt(100);

        if (r < 70) {
            int gain = 5 + rnd.nextInt(11); // increase fire 5-15
            fireStrength += gain;
            morale += 1;
            System.out.println("The new wood burns brightly. The fire grows stronger by " + gain + ".");
        } else if (r < 90) {
            System.out.println("You gather some damp branches. They smoke but barely catch fire.");
            fireStrength += 2;
            morale -= 1;
        } else {
            System.out.println("While chopping, you cut your hand slightly!");
            int dmg = 3 + rnd.nextInt(5);
            health -= dmg;
            morale -= 3;
            System.out.println("You lose " + dmg + " health but the fire still flickers weakly.");
            fireStrength += 1;
        }
    }

    static void sleep() {
        System.out.println("You try to sleep and recover...");
        // If fire too low, sleep is dangerous
        if (fireStrength < 15) {
            System.out.println("It's very cold. Your sleep is restless.");
            energy += 10;
            health -= 8;
            morale -= 2;
        } else {
            energy += 30;
            health += 5;
            System.out.println("A good night of sleep restores you.");
        }
        // consume food while sleeping
        if (food > 0) {
            food -= 1;
            System.out.println("You eat 1 food before sleeping.");
        } else {
            System.out.println("No food tonight. Hunger gnaws at you.");
            health -= 5;
        }

        // clamp
        energy = Math.min(energy, 100);
        health = Math.min(health, 100);
    }

    static void explore() {
        System.out.println("You explore the surroundings...");
        energy -= 20;
        int r = rnd.nextInt(100);

        if (r < 25) {
            System.out.println("You discovered a small, sheltered cave! Fire effectiveness improved.");
            fireStrength += 20;
            morale += 10;
        } else if (r < 55) {
            System.out.println("You found a useful resource cache: +2 food.");
            food += 2;
        } else if (r < 80) {
            System.out.println("You found footprints but no safety. Nothing decisive happened.");
            morale += 1;
        } else {
            System.out.println("A dangerous animal attacked during exploration!");
            int dmg = 10 + rnd.nextInt(21);
            health -= dmg;
            morale -= 7;
            System.out.println("You escape wounded, losing " + dmg + " health.");
        }
    }

    static void craftSignal() {
        System.out.println("You attempt to craft a signal to attract help...");
        energy -= 25;
        int success = rnd.nextInt(100) + morale / 2;
        if (success > 90) {
            System.out.println("A distant rescue party sees your signal! You are evacuated.");
            days = TARGET_NIGHTS + 1; // force victory
        } else {
            System.out.println("No one noticed. The effort cost you energy.");
            morale -= 3;
            fireStrength -= 5;
        }
    }

    static void doNothing() {
        energy -= 5;
        morale -= 1;
    }

    // ---------- Events ----------
    static void nightEvent() {
        int r = rnd.nextInt(100);
        // Simple event system, becomes more dangerous with later nights
        int dangerOffset = Math.min(30, days / 4);

        if (r < 8 + dangerOffset) {
            System.out.println("Night storm! Fire weakens severely.");
            int loss = 10 + rnd.nextInt(16);
            fireStrength -= loss;
            health -= 5;
        } else if (r < 15 + dangerOffset) {
            System.out.println("You hear strange sounds — but nothing approaches. Morale drops slightly.");
            morale -= 2;
        } else if (r < 20 + dangerOffset / 2) {
            System.out.println("You find a small berry patch while walking in the dark.");
            int found = 1 + rnd.nextInt(2);
            food += found;
            System.out.println("You pick " + found + " food.");
        } else if (r < 30) {
            System.out.println("A hungry deer charges into your camp!");
            int dmg = 25 + rnd.nextInt(10);
            health -= dmg;
            morale -= 3;
            System.out.println("The deer hits you before running away! You lose " + dmg + " health.");
        } else if (r < 40) {
            System.out.println("You hear chanting in the distance... Cultists are approaching!");
            int dmg = rnd.nextInt(10);
            int moraleLoss = 6 + rnd.nextInt(5);
            fireStrength -= 10;
            health -= dmg;
            morale -= moraleLoss;
            System.out.println("A group of cultists attack your camp! You fight them off but take " + dmg + " damage and lose morale.");
        } else if (r > 95) {
            System.out.println("A helpful traveler passes by and leaves some supplies.");
            food += 2;
            morale += 5;
        } else {
            // nothing special
            if (rnd.nextInt(100) < 10) {
                System.out.println("The forest is eerily quiet tonight...");
            }
        }
    }

    // ---------- End-of-days natural effects ----------
    static void endOfNightTick() {
        // Cold affects health if fire low
        if (fireStrength < 20) {
            int coldDamage = 5;
            System.out.println("It's freezing. You suffer " + coldDamage + " cold damage.");
            health -= coldDamage;
            morale -= 2;
        }

        // Energy cannot be negative
        energy = clamp(energy);

        // Passive food consumption if not eaten earlier
        if (food <= 0) {
            health -= 3; // starving
            morale -= 2;
            System.out.println("No food consumed this night — hunger reduces your health.");
        }

        // Small natural fire decay
        fireStrength -= 8; 
        // Minor morale drift
        morale = clamp(morale);

        // Clamp health
        health = clamp(health);

        // Print summary
        System.out.println("End of night " + days + " summary:");
    }

    // ---------- Utility ----------
    static int clamp(int v) {
        if (v > 100) {
            return 100;
        }
        if (v < -999) {
            return v; // keep allowing large negatives for logic, but mostly health clamped elsewhere
        }
        return v;
    }

    static void gameOver() {
        System.out.println("\n*** You didn't make it. ***");
        System.out.println("You survived until night " + days + ".");
        System.out.println("Final status:");
        displayStatus();
        System.out.println("Try a different strategy next time.");
    }

    static void victory() {
        System.out.println("\n*** Congratulations! ***");
        System.out.println("You survived " + TARGET_NIGHTS + " nights in the forest.");
        displayStatus();
        System.out.println("You have endured. The forest will remember you.");
    }
}
