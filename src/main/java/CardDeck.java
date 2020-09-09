import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    private Stack<Card> cards;

    public CardDeck() {
        cards = new Stack<>();
        for (int cardValue = 1; cardValue <= 14; cardValue++ ) {
            for (int cardColor = 0; cardColor < 4; cardColor++ ) {
                cards.add(new Card(cardValue));
            }
        }
        // Rook card
        cards.add(new Card(0));
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.pop();
    }

    public boolean hasCards() {
        return !cards.empty();
    }
}
