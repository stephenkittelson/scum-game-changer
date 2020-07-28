public class Card {
    private int quantity;
    private int value;

    public Card(int quantity, int value) {
        this.quantity = quantity;
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getValue() {
        return value;
    }

    public void reduceQuantity(int delta) {
        quantity = Math.max(0, quantity - delta);
    }
}
