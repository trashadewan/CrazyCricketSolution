package pojo;

import dao.GameDAO;
import web.response.NameValuePair;

import java.util.*;

/**
 * Created by trasha on 26/6/16.
 */
public class GameLeaderBoardService {

    public List<NameValuePair> getCountryLeaderBoard() {
        Map<String, Integer> countryAndCount = new HashMap<>();
        List<Game> games = new GameDAO().getAllGames();
        Integer count = 0;
        if (games != null) {
            for (Game game : games) {
                if (countryAndCount.containsKey(game.getWinnerCountry())) {
                    count = countryAndCount.get(game.getWinnerCountry());
                    count++;
                } else {
                    count = 1;
                }
                countryAndCount.put(game.getWinnerCountry(), count);
            }
        }
        //sort by score
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(countryAndCount.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {

                return (me1.getValue()).compareTo(me2.getValue());
            }
        });

        // Convert sorted map back to a Map
        List<NameValuePair> nameValuePairs = new LinkedList<>();
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
            NameValuePair nameValuePair = new NameValuePair(entry.getKey(), entry.getValue());
            nameValuePairs.add(nameValuePair);
        }
        return nameValuePairs;
    }

}
