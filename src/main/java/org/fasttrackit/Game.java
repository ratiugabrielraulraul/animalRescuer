package org.fasttrackit;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Animal animal;
    private Adopter adopter;
    private Food[] foods = new Food[3];
    private RecreationActivity[] recreationActivities = new RecreationActivity[2];
    private int foodNumber;
    private int recreationActivityNumber;
    private int i = 0;

    private void initAnimal() {
        this.animal = new Animal();
        this.animal.setName("Motanu ");
        this.animal.setAge(20);
        this.animal.setHealthStatus(3);
        this.animal.setHunger(10);
        this.animal.setSpiritMood("sad");
        this.animal.setFavoriteActivity("Being near a human");
        this.animal.setFavoriteFood("milk");
    }

    private void initRescuer() {
        this.adopter = new Adopter("Thomas");

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Adopter with the name of " + this.adopter.getName() + " has been created");
            System.out.println("Please enter the amount of money you want to have: ");
            this.adopter.setMoney(scanner.nextInt());
        } catch (IllegalStateException | NoSuchElementException e) {
            initRescuer();
        }

        this.adopter.setName("Thomas");
    }

    private void requireFeeding() {
        System.out.println("The types of food that we have are");
        for (int i = 0; i< this.foods.length; i++) {
            System.out.println(i + " - " + this.foods[i].getName());
        }
        askForInput();
        Scanner scanner = new Scanner(System.in);
        try {
            this.foodNumber = scanner.nextInt();
            System.out.println("You have chosen" + this.foods[this.foodNumber].getName());
            this.adopter.feed(this.animal, this.foods[this.foodNumber]);
        } catch (IllegalStateException | NoSuchElementException e) {
            this.foodNumber = -1;
            System.out.println("You have not chosen any food! Your animal will starve");
            requireActivity();
        }
    }

    private void askForInput() {
        System.out.println("Please choose one: ");
        System.out.println("To cancel please enter any letter: ");
    }

    private void requireActivity() {
        System.out.println("The types of recreation activities that we have are");
        for (int i = 0; i< this.recreationActivities.length; i++) {
            System.out.println(i + " - " + this.recreationActivities[i].getName());
        }
        Scanner scanner = new Scanner(System.in);
        askForInput();
        try {
            this.recreationActivityNumber = scanner.nextInt();
            System.out.println("You have chosen" + this.recreationActivities[this.recreationActivityNumber].getName());
            this.adopter.play(this.recreationActivities[this.recreationActivityNumber], this.animal);
        } catch (IllegalStateException | NoSuchElementException e) {
            this.recreationActivityNumber = -1;
            System.out.println("You have not chosen any activity! Your animal won't be happy");
        }
    }

    private void initRecreationActivities() {
        RecreationActivity running = new RecreationActivity("running", "Hill");
        RecreationActivity ballCatching = new RecreationActivity("ball catching", "garen");

        this.recreationActivities[0] = running;
        this.recreationActivities[1] = ballCatching;
    }

    private void initFoods() {
        Food pedigree = new Food();
        pedigree.setExpirationDate(LocalDate.of((2019), (11), (3)));
        pedigree.setPrice(5.73);
        pedigree.setStock(true);
        pedigree.setQuantity(35);
        pedigree.setName("pedigree");

        Food seeds = new Food();
        seeds.setExpirationDate(LocalDate.of(2019, 11, 3));
        seeds.setPrice(3.50);
        seeds.setStock(true);
        seeds.setQuantity(54);
        seeds.setName("seeds");

        Food milk = new Food();
        milk.setExpirationDate(LocalDate.of(2019, 10, 14));
        milk.setPrice(5.50);
        milk.setStock(true);
        milk.setQuantity(100);
        milk.setName("Milk");

        this.foods[0] = pedigree;
        this.foods[1] = seeds;
        this.foods[2] = milk;
    }

    public void start() {
        initMessages();
        initAnimal();
        initRescuer();
        initFoods();
        initRecreationActivities();
        requireFeeding();
        requireActivity();
        feedOrActivity();

    }

    public void feedOrActivity()
    {
        checkIfGameIsEnded();
        Random random = new Random();

        for (; this.i <= 15; this.i++) {
            System.out.println("Round " + i + " starts");
            System.out.println("Your animal has: \n" +
                    "hunger:" + this.animal.getHunger() + "\n" +
                    "mood:" + this.animal.getMood() + "\n" );

            //if i is even
               if ((i & 1) == 0) {
                   requireFeeding();
                   //if number of the round is 2 do this
                   if (i == 2) {
                       this.animal.setHunger(this.animal.getHunger() - random.nextInt(3));
                   }
                   // if user has chosen a food then we decrease food level
                   if (this.foodNumber >= 0) {
                       this.animal.setHunger(this.animal.getHunger() - random.nextInt(3));
                   } else {
                       this.animal.setHunger(this.animal.getHunger() + random.nextInt(4));
                   }
               } else {
                   requireActivity();
                   if (i == 2) {
                       this.animal.setMood(this.animal.getMood() - random.nextInt(3));
                   }
                   //if user has chosen an activity we increase mood && we increase hunger
                   if (this.recreationActivityNumber >= 0) {
                       this.animal.setMood(this.animal.getMood() + random.nextInt(3));
                       this.animal.setHunger(this.animal.getHunger() + random.nextInt(2));
                   } else {
                       this.animal.setMood(this.animal.getMood() - random.nextInt(2));
                   }
               }
        }
    }

    public void checkIfGameIsEnded() {
        if (this.animal.getHunger() >= 10 | this.animal.getMood() == 0) {
            System.out.println("This game has ended your animal reached mood or hunger 0");
        }
    }

    public void initMessages() {
        System.out.println("Game has started");
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Adopter getAdopter() {
        return adopter;
    }

    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }

    public Food[] getFoods() {
        return foods;
    }

    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    public RecreationActivity[] getRecreationActivities() {
        return recreationActivities;
    }

    public void setRecreationActivities(RecreationActivity[] recreationActivities) {
        this.recreationActivities = recreationActivities;
    }

}