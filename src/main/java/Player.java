import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private Role role;
    private String name;
    private List<Card> hand;

    public Player(Role role, String name) {
        this.role = role;
        this.name = name;
        this.hand = new LinkedList<>();
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public Card takeHighestCard() {
        Card highestCard = hand.stream().sorted(Comparator.comparing(card -> card.getValue())).findFirst().get();
        hand.remove(highestCard);
        return highestCard;
    }

    public Card takeLowestCard() {
        List<Card> sortedHand = hand.stream().sorted(Comparator.comparing(Card::getValue)).collect(Collectors.toList());
        Card lowestCard = sortedHand.get(sortedHand.size() - 1);
        hand.remove(lowestCard);
        return lowestCard;
    }
}
