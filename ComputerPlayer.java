// CLASS: Computer Player
//
// Author: Egbedi Kome
//
// REMARKS: This class presents the Computer Player
//
//-----------------------------------------

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer implements IPlayer{

    private int playerIndex;
    private int numGamePlayers;
    private ArrayList <Card> playerCards;

    //this variables are used in order to make smart guesses
    private ArrayList <Card> suspectGuess;
    private ArrayList <Card> locationGuess;
    private ArrayList <Card> weaponGuess;

    // the next guess that the AI is to make
    private Card currSuspectGuess;
    private Card currLocationGuess;
    private Card currWeaponGuess;
    private boolean isAccusation;


    public ComputerPlayer(){
        playerCards = new ArrayList<>();
        suspectGuess = new ArrayList<>();
        locationGuess = new ArrayList<>();
        weaponGuess = new ArrayList<>();
        currSuspectGuess = currLocationGuess = currWeaponGuess = null;
        isAccusation = false;
    }

    public void setUp(int numPlayers, int index, ArrayList<Card> ppl, ArrayList<Card> places, ArrayList<Card> weapons) {
        numGamePlayers = numPlayers;
        playerIndex = index;

        //at the start the player should be able to suggest all the cards available
        suspectGuess.addAll(ppl);
        locationGuess.addAll(places);
        weaponGuess.addAll(weapons);
    }

    //This method is used to give the player cards
    // while the player is handed some cards, we filter out the suggestions
    // because it not logically for the computer to be able to suggest the cards he has
    public void setCard(Card c) {
        playerCards.add(c);
        filterSuggestions(c);
    }

    // this method is used to filter out cards that should not be suggested in order for the computer to make smart choices
    // accepts in a card that should be removed
    private void filterSuggestions(Card newCard){
        boolean hasCard = false;

        // based on the type of card, we will filter from that list

        if(newCard.getType().equalsIgnoreCase("suspect")){
            int size = suspectGuess.size();
            //loop through the list till the card is found1
            for (int index=0; index<size && !hasCard; index++){

                hasCard = suspectGuess.get(index).isEqual(newCard);
                // if the card is found, remove it from the list
                if(hasCard)
                    suspectGuess.remove(index);
            }//for
        }
        else if(newCard.getType().equalsIgnoreCase("location")){

            int size = locationGuess.size();

            for (int index=0; index<size && !hasCard; index++){

                hasCard = locationGuess.get(index).isEqual(newCard);
                if(hasCard)
                    locationGuess.remove(index);
            }
        }
        else {
            int size = weaponGuess.size();

            for (int index=0; index<size && !hasCard; index++){

                hasCard = weaponGuess.get(index).isEqual(newCard);
                if(hasCard)
                    weaponGuess.remove(index);
            }//for
        }//if-else if -else

    }//filterSuggestion()

    // this method returns the player's index
    public int getIndex(){
        return playerIndex;
    }//getIndex

    // this method returns the player's guess
    public Guess getGuess(){

        //if they have not be initialized, we can go ahead and initialized
        if(currSuspectGuess == null && currLocationGuess == null && currWeaponGuess == null){

            currSuspectGuess = getRandom(suspectGuess);
            currLocationGuess = getRandom(locationGuess);
            currWeaponGuess = getRandom(weaponGuess);
        }

        //if there are only one of each cards, then that is the only possible answer and we can make an accusation
        if(suspectGuess.size() == 1 && locationGuess.size() == 1 && weaponGuess.size() == 1)
            isAccusation = true;

        return new Guess(currSuspectGuess , currWeaponGuess , currLocationGuess , isAccusation);
    }//getGuess()

    // This method represents that player i (which is different from the current player) has made guess g.
    // The current player is responsible for “answering” that guess, if possible.
    // The method should either return a card (which the current player must have in their hand) or null,
    // to represent that the current player cannot answer that guess.
    public Card canAnswer(Guess g, IPlayer ip) {

        ArrayList <Card> cardAvailable = new ArrayList<>();
        Card answer = null;

        //count how many cards from the guesses the user player has
        for (Card playerCard : playerCards) {

            //if the player has any of the cards
            if (playerCard.isEqual(g.getSuspect()) || playerCard.isEqual(g.getLocation())
                    || playerCard.isEqual(g.getWeapon()))
                cardAvailable.add(playerCard);
        }

        // if there is a card to show, we can just pick a card from the cards available to show
        if(cardAvailable.size() > 0)
            answer = getRandom(cardAvailable);

        return answer;
    }//canAnswer()

    //This method provides the player with a feedback on their previous guess
    // if someone was able refute the guess or not
    public void receiveInfo(IPlayer ip, Card c) {

        if(ip != null && c != null){
            filterSuggestions(c); // remove that card since it not a valid suggestion

            //get new suggestions
            currSuspectGuess = getRandom(suspectGuess);
            currLocationGuess = getRandom(locationGuess);
            currWeaponGuess = getRandom(weaponGuess);
        }
        else{
            // that must be the right guess, since no one had a card to show
            // the next guess should be an accusation, with the previous guess
            isAccusation = true;
        }//if-else

    }//receiveInfo

    //this method helps gets a random card from a list
    private Card getRandom(ArrayList <Card> List){
        Random rand = new Random();
        int randomIndex = rand.nextInt(List.size());

        return List.get(randomIndex);
    }//getRandom
}
