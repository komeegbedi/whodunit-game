// CLASS: Guess
//
// Author: Kome Egbedi
//
// REMARKS: This class presents a guess made by a player
//
//-----------------------------------------
public class Guess {

    private Card weapon;
    private Card suspect;
    private Card location;
    private boolean isAccusation;

    public Guess(Card suspectCard  , Card weaponCard , Card locationCard , boolean suggestion){
        suspect = suspectCard;
        weapon = weaponCard;
        location = locationCard;
        isAccusation = suggestion;
    }

    public Card getLocation() {
        return location;
    }

    public Card getSuspect() {
        return suspect;
    }

    public Card getWeapon() {
        return weapon;
    }

    public boolean isAccusation() {
        return isAccusation;
    }

    public String toString(){
        return suspect.getValue() + " in the " + location.getValue() + " with the "+ weapon.getValue();
    }
}
