// CLASS: Whodunit
//
// Author: Egbedi Kome
//
// REMARKS: This class sets up the cards and the player inorder to start the game
//
//-----------------------------------------

import java.util.ArrayList;
import java.util.Scanner;

public class Whodunit {

    public static void main(String[] args) {

        //creates cards
        ArrayList <Card> suspects = new ArrayList<>();
        ArrayList <Card> location = new ArrayList<>();
        ArrayList <Card> weapons = new ArrayList<>();
        ArrayList <IPlayer> gamePlayers = new ArrayList<>();

        suspects.add(new Card("suspect" , "Scarlett"));
        suspects.add(new Card("suspect" , "Plum"));
        suspects.add(new Card("suspect" , "Peacock"));
        suspects.add(new Card("suspect" , "Mustard"));
        suspects.add(new Card("suspect" , "Orchid"));
        suspects.add(new Card("suspect" , "Green"));
        suspects.add(new Card("suspect" , "White"));
        suspects.add(new Card("suspect" , "Rose"));

        location.add(new Card("location" , "lounge"));
        location.add(new Card("location" , "ballroom"));
        location.add(new Card("location" , "conservatory"));
        location.add(new Card("location" , "hall"));
        location.add(new Card("location" , "kitchen"));
        location.add(new Card("location" , "library"));
        location.add(new Card("location" , "study"));
        location.add(new Card("location" , "billiard room"));

        weapons.add(new Card("weapon" , "dagger"));
        weapons.add(new Card("weapon" , "rope"));
        weapons.add(new Card("weapon" , "lead pipe"));
        weapons.add(new Card("weapon" , "revolver"));
        weapons.add(new Card("weapon" , "candlestick"));
        weapons.add(new Card("weapon" , "wrench"));
        weapons.add(new Card("weapon" , "knife"));
        weapons.add(new Card("weapon" , "AK47"));


        System.out.println("Welcome to \"whodunit?\" ");
        System.out.println("How many computer opponents would you like?");

        //ask user how many players they will like to have
        Scanner userInput = new Scanner(System.in);
        int numOfPlayer = userInput.nextInt();

        //set up players
        System.out.println("Setting up players....");
        createPlayer(numOfPlayer , gamePlayers);

        Model runGame = new Model();
        runGame.setUpGame(suspects , location , weapons , gamePlayers);
        runGame.playGame();

        System.out.println("Game Over");
    }

    //this method creates the players
    public static void createPlayer(int numOfPlayer , ArrayList <IPlayer> players){
        players.add(new HumanPlayer()); // there's always one human in the game

        //create computer players
        for(int i=0; i<numOfPlayer; i++)
            players.add(new ComputerPlayer());
    }
}
