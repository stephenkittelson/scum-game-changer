import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameManager {

    public void setup() throws IOException {
        System.out.println("Enter players from king to scum separated by commas: ");
        new InputStreamReader(System.in);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String rawPlayers = inputReader.readLine();
        List<String> splitPlayers = Arrays.asList(rawPlayers.split(","));
        List<Player> players = splitPlayers.stream().map(name -> new Player(Role.Citizen, name)).collect(Collectors.toList());
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

        System.out.println("Enter your cards (\"quantity\" \"face value\"\\n):");
        String rawCard;
        Map<Integer, Card> myHand = new HashMap<>();
        while (StringUtils.isNotBlank(rawCard = inputReader.readLine())) {
            String[] cardPieces = rawCard.split(" ");
            Integer value = Integer.valueOf(cardPieces[1]);
            myHand.put(value, new Card(Integer.valueOf(cardPieces[0]), value));
        }


        System.out.println("Enter cards you (gave/received if PVH or HMS) TODO ....");
        // TODO continue here
    }
}
