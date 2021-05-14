// CLASS: Card
//
// Author: Kome Egbedi
//
// REMARKS: This class presents one card in the game
//
//-----------------------------------------
public class Card {

    private String type;
    private String value;

    public Card(String cardType , String cardValue){
        type = cardType.toLowerCase();
        value = cardValue.toLowerCase();
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    //compares two cards
    public boolean isEqual(Card other){
        return other.type.equalsIgnoreCase(type) && other.value.equalsIgnoreCase(value);
    }
}
