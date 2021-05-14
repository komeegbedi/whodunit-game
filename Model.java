// CLASS: Model
//
// Author: Egbedi Kome
//
// REMARKS: This  class controls the game and allows the user play the game
//
//-----------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model {

    //the secret cards
    private Card suspectAnswer;
    private Card locationAnswer;
    private Card weaponAnswer;

    private ArrayList <Card> gameCards; // all the cards in the card disturbed
    private ArrayList <IPlayer> gamePlayers; // all the players in the game
    private int numGamePlayers; // number of players in the game

    //constructor in the game
    public Model(){
        gameCards = new ArrayList<>();
        gamePlayers = new ArrayList<>();
        suspectAnswer = locationAnswer = weaponAnswer = null;
    }

    //this methods sets up the game in order to all users player

    /*
        This method starts by shuffling the three different categories of cards
        then pick the secret cards
        then add all the cards together then shuffle it
        create the  players ( one human and n computer players)
        after creating the players , lets the users know the cards in the game then deal out the cards to the players
     */
    public void setUpGame(ArrayList <Card> suspect , ArrayList <Card> location , ArrayList <Card> weapons , ArrayList <IPlayer> players){

        gamePlayers = players;
        numGamePlayers = players.size();

        ArrayList<Card> gameSuspects = new ArrayList<>(suspect);
        ArrayList<Card> gameLocations = new ArrayList<>(location);
        ArrayList<Card> gameWeapons = new ArrayList<>(weapons);

        //shuffle the cards first
        Collections.shuffle(gameSuspects);
        Collections.shuffle(gameLocations);
        Collections.shuffle(gameWeapons);

        //get the random answer cards
        suspectAnswer = getRandom(gameSuspects);
        locationAnswer = getRandom(gameLocations);
        weaponAnswer = getRandom(gameWeapons);

        //add the rest of cards to the game Cards
        gameCards.addAll(gameSuspects);
        gameCards.addAll(gameLocations);
        gameCards.addAll(gameWeapons);
        Collections.shuffle(gameCards);

        //set up players
        for (int index=0; index < numGamePlayers; index++)
            gamePlayers.get(index).setUp(numGamePlayers , index , suspect , location , weapons);

        //prints out cards
        System.out.println( "\nHere are the names of all the suspects: ");
        printCards(suspect);

        System.out.println( "\nHere are all the locations:");
        printCards(location);

        System.out.println("\nHere are all the weapons:");
        printCards(weapons);
        System.out.println("\n");

        dealCards();
    }//setUpCard


    //this method plays the game
    public void playGame(){

        ArrayList<IPlayer> activePlayers = new ArrayList<>(gamePlayers); // keeps track of active players

        boolean gameOver = false; // flag to keep track if the game is over
        IPlayer winner = null;
        Guess winnerGuess = null;
        int currentPlayer = 0; // let's start playing with player 0

        while (!gameOver){

            //ask the current player to make a guess
            IPlayer currPlayer = activePlayers.get(currentPlayer);
            Guess playerGuess = currPlayer.getGuess();

            if(playerGuess.isAccusation()){ // if it was an accusation made

                System.out.println("Player " + currPlayer.getIndex() +": Accusation: "+playerGuess);

                // if the accusation is true, game is over
                gameOver = playerGuess.getSuspect().isEqual(suspectAnswer) &&
                           playerGuess.getLocation().isEqual(locationAnswer) && playerGuess.getWeapon().isEqual(weaponAnswer);

                //if the accusation is false , we need to remove the player from the active players
                //if it was only one player in the game, remove the player and the game is over
                if(!gameOver){
                    System.out.println("Player " +currPlayer.getIndex()+ " made a bad accusation and was removed from the game.");
                    activePlayers.remove(currentPlayer);
                    gameOver = activePlayers.size() == 0;
                }
                else {
                    winnerGuess = playerGuess;
                    System.out.println("Player " + currPlayer.getIndex() + " won the game.");
                }

                winner = currPlayer;
            }
            else{

                System.out.println("Player " + currPlayer.getIndex() +": Suggestion: "+playerGuess);

                //if the guess was not an accusation, we need to round and ask all player's if they are able to refute the guess
               int askNext = (gamePlayers.indexOf(currPlayer) + 1 ) % numGamePlayers;

               Card cardShown = null;
               IPlayer playerThatAnswered = null;

               // loop through the list of all players and ask if they are able to refute the suggestion
                // loop till someone is able to answer or till all the players are not able to answer
               for(int index=0; index<numGamePlayers-1 && cardShown == null; index++){

                   System.out.println("Asking player " + gamePlayers.get(askNext).getIndex());
                   //ask all players the next player
                   cardShown = gamePlayers.get(askNext).canAnswer(playerGuess , currPlayer);

                   //save the player who answered
                   if(cardShown != null)
                       playerThatAnswered = gamePlayers.get(askNext);

                   //move to the next player
                   askNext = (askNext + 1) % numGamePlayers;
               }

               //pass the information to the player
               currPlayer.receiveInfo(playerThatAnswered , cardShown);

               //print report
                if(playerThatAnswered == null && cardShown == null)
                    System.out.println("No one could answer.");

                else
                    System.out.println("Player " + playerThatAnswered.getIndex() +" answered.");
            }//if-else


            if(!gameOver) // go to next player if game is not over
                currentPlayer = (currentPlayer + 1) % activePlayers.size();

        }//while()

        //inform all players about the outcome
        if(activePlayers.size() == 0) // all players were removed from the game
            System.out.println("Player "+ winner.getIndex() + " won by being the last player in the game");
        else
            System.out.println("Player "+ winner.getIndex() + " won with Guess: " + winnerGuess);


    }//playGame()

    // this methods prints out a list
    private void printCards(ArrayList<Card> cardList){

        for(int index=0; index<cardList.size(); index++){

            System.out.print(cardList.get(index).getValue());

            if(index != cardList.size()-1)
                System.out.print(", ");
        }//for

    }//printCards

    //this method deals cards out cards to all players
    private void dealCards(){
        int currentPlayer = 0;

        for (Card gameCard : gameCards) {

            gamePlayers.get(currentPlayer).setCard(gameCard);
           currentPlayer =  (currentPlayer+1) % numGamePlayers; // shuffle around the table
        }//for

    }//dealCards()

    //this method helps gets a random card from a list
    private Card getRandom(ArrayList <Card> List){
        Random rand = new Random();
        int randomIndex = rand.nextInt(List.size());
        return List.remove(randomIndex);
    }//getRandom

}
