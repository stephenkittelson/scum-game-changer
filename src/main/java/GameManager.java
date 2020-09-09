import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GameManager {

    public void setup() throws IOException {
        System.out.println("Enter players from king to scum separated by commas (use 'me' for yourself): ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String rawPlayers = inputReader.readLine();
        List<String> splitPlayers = Arrays.asList(rawPlayers.split(","));
        List<Player> players = splitPlayers.stream().map(name -> new Player(Role.Citizen, StringUtils.trim(name))).collect(Collectors.toList());
        /*
        1 2 3 4 5 6 7 8 9 10 11 12 13
        P V H                HS MS S

        1 2 3 4 5 6
        P V H H M S

        1 2 3 4 5
        P V   M S

        1 2 3 4
        P V M S

        1 2 3
        P   S

        1 2
        P S
         */
        players.get(0).setRole(Role.King);
        players.get(players.size() - 1).setRole(Role.Scum);
        if (splitPlayers.size() >= 4) {
            players.get(1).setRole(Role.VicePresident);
            players.get(players.size() - 2).setRole(Role.MedScum);
        }
        if (splitPlayers.size() >= 6) {
            players.get(2).setRole(Role.HouseSpeaker);
            players.get(players.size() - 3).setRole(Role.HighScum);
        }

        players.stream().forEach(player -> System.out.println(player.getName() + ": " + player.getRole()));

        System.out.println("Enter your cards (\"face value\" \"quantity\"\\n) (use 0 for rook cards):");
        Map<Card, Integer> myHand = new HashMap<>();
        BiConsumer<Integer, Integer> cardConsumer = (value, quantity) -> myHand.put(new Card(value), quantity);

        readCards(inputReader, cardConsumer);

        Player me = players.stream().filter(player -> StringUtils.equals("me", player.getName())).findFirst().get();
        if (!me.getRole().equals(Role.Citizen)) {
            System.out.print("Enter cards you received: ");
            readCards(inputReader, (newCardValue, addedQuantity) -> myHand.merge(new Card(newCardValue), addedQuantity, Integer::sum));
            System.out.print("Enter cards you gave: ");
            readCards(inputReader, (lostCardValue, removedQuantity) -> myHand.merge(new Card(lostCardValue), -removedQuantity, Integer::sum));
        }
        // TODO continue here
    }

    private void readCards(BufferedReader inputReader, BiConsumer<Integer, Integer> cardConsumer) throws IOException {
        String rawCard;
        while (StringUtils.isNotBlank(rawCard = inputReader.readLine())) {
            String[] cardPieces = rawCard.split(" ");
            Integer value = Integer.valueOf(cardPieces[0]);
            Integer quantity = Integer.valueOf(cardPieces[1]);
            cardConsumer.accept(value, quantity);
        }
    }
}
