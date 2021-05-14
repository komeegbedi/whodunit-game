// CLASS: HumanPlayer
//
// Author: Egbedi Kome
//
// REMARKS: This class presents the Human Player
//
//-----------------------------------------
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer implements IPlayer{

    private int playerIndex;
    private int numGamePlayers;
    private ArrayList <Card> playerCards;
    private ArrayList <Card> suspect;
    private ArrayList <Card> location;
    private ArrayList <Card> weapon;

    public HumanPlayer(){
        playerCards = new ArrayList<>();
    }

    //receives five parameters: the number of players in the game, the index of the current player (what player number am I?),
    // a list of all the suspects, a list of all the locations and a list of all the weapons.
    public void setUp(int numPlayers, int index, ArrayList<Card> ppl, ArrayList<Card> places, ArrayList<Card> weapons) {
        numGamePlayers = numPlayers;
        playerIndex = index;
        suspect = ppl;
        location = places;
        weapon = weapons;
    }//setUp()

    // indicates that the player has been dealt a particular card.
    public void setCard(Card c){
        System.out.println("You received the card " + c.getValue());
        playerCards.add(c);
    }//setCard()

    // returns the player's index
    public int getIndex() {
        return playerIndex;
    }//getIndex()

    // this method asks the user for their guess and return it
    public Guess getGuess(){
        //variables needed
        Card personCard , placeCard , weaponCard;
        boolean isAccusation = false;

        System.out.println("It is your turn.");
        
        //ask user for the person they want to suggest
        System.out.println("Which person do you want to suggest?");
        personCard = selectCard(suspect);;

        //ask user for the weapon they want to suggest
        System.out.println("Which weapon do you want to suggest?");
        weaponCard = selectCard(weapon);;
        
        //ask user for the place they want to suggest
        System.out.println("Which location do you want to suggest?");
        placeCard = selectCard(location);;
        
        //ask user if it is an accusation or not
        System.out.println("Is this an accusation (Y/[N])?");

        boolean correctInput = false;
        //check that the input is valid
        while(!correctInput){

            Scanner ans = new Scanner(System.in);
            String userAnswer = ans.next();

            correctInput = userAnswer.equalsIgnoreCase("y") || userAnswer.equalsIgnoreCase("n");

              if(!correctInput)
                System.out.println("Not valid. Try Again");

              else
                  isAccusation = userAnswer.equalsIgnoreCase("y"); // if it's y then it's an accusation , else it's a not an accusation
        }//while

        return new Guess(personCard ,weaponCard , placeCard, isAccusation );
    }//getGuess()

    //this method prints out suggestions the user can pick, then asks users the to pick
    private Card selectCard(ArrayList<Card> chooseCard){


        //list out all the cards in order for the player to pick
        for(int i=0; i<chooseCard.size(); i++){
            System.out.println(i + ": "+ chooseCard.get(i).getValue());
        }//for()

        //let the player pick a card
        boolean inRange = false;
        int num = 0;

        // keep asking the user to pick a valid input till it's the appropriate range
        while(!inRange){

            Scanner playerGuess = new Scanner(System.in);
            num = playerGuess.nextInt();
            inRange = num >= 0 && num < chooseCard.size();

            //print error message if not valid
            if(!inRange)
                System.out.println("Not valid. Try Again");

        }//while

        return  chooseCard.get(num);
    }//pickSuspect()


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

        // check how cards the players actually have
        if(cardAvailable.size() == 1){
            // show that card
            answer = cardAvailable.get(0);
            System.out.println("Player "+ ip.getIndex() +" asked you about " + g+ ", you only have one card, "+ answer.getValue()+ ", showed it to them." );
        }
        else if(cardAvailable.size() > 1){ // the player has multiple of the guess cards

            // ask the user what card they want to show
            System.out.println("Player "+ ip.getIndex() +" asked you about " + g+ ", Which do you show?" );
            answer = selectCard(cardAvailable);
        }
        else { // the player does not have any of the guess card
            //else there's no card to show
            System.out.println("Player "+ ip.getIndex() +" asked you about " + g+ ", but you couldn't answer." );
        }

        return answer;
    }

    // This method tells the player know if someone was able refute their guess
    public void receiveInfo(IPlayer ip, Card c){

        if(ip != null && c != null)
            System.out.println("Player " + ip.getIndex() + " refuted your suggestion by showing you " + c.getValue());
        
        else
            System.out.println("No one could refute your suggestion.");
        
    }//receiveInfo
}
