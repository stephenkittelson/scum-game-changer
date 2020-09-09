import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AutomatedGameManager {

    public void setup() throws IOException {
        AtomicInteger playerNumber = new AtomicInteger(0);
        int numPlayers = 10;
        List<Player> players = IntStream.range(0, numPlayers).mapToObj(name -> new Player(Role.Citizen, String.valueOf(playerNumber.getAndIncrement()))).collect(Collectors.toList());
        players.get(0).setRole(Role.King);
        players.get(players.size() - 1).setRole(Role.Scum);

        boolean hasVicePresident = numPlayers >= 4;
        if (hasVicePresident) {
            players.get(1).setRole(Role.VicePresident);
            players.get(players.size() - 2).setRole(Role.MedScum);
        }
        boolean hasHouseSpeaker = numPlayers >= 6;
        if (hasHouseSpeaker) {
            players.get(2).setRole(Role.HouseSpeaker);
            players.get(players.size() - 3).setRole(Role.HighScum);
        }

        players.stream().forEach(player -> System.out.println(player.getName() + ": " + player.getRole()));

        CardDeck deck = new CardDeck();
        while(deck.hasCards()) {
            players.stream().forEach(player -> {
                if (deck.hasCards()) {
                    player.addToHand(deck.drawCard());
                }
            });
        }

        int numCardsToTransfer = 1;
        if (hasVicePresident) {
            numCardsToTransfer++;
        }
        if (hasHouseSpeaker) {
            numCardsToTransfer++;
        }
        for (int cardsTransferred = 0; cardsTransferred < numCardsToTransfer; cardsTransferred++ ) {
            Card cardToTransfer = players.get(0).takeLowestCard(); // TODO president may not want to transfer lowest cards - chose lowest priority instead
            players.get(players.size() - 1).addToHand(cardToTransfer);

            cardToTransfer = players.get(players.size() - 1).takeHighestCard();
            players.get(0).addToHand(cardToTransfer);
        }
        // TODO continue here - more transfers
    }
}
