
package dao;

/**
 * Created by trasha on 26/6/16.
 */

import pojo.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    public List<Game> getAllGames() {
        List<Game> userList = null;
        try {
            File file = new File("Game.dat");
            if (!file.exists()) {
                Game user = new Game("Mahesh", "Teacher", 1l);
                userList = new ArrayList<Game>();
                userList.add(user);
                saveGameList(userList);
            }
            else{
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<Game>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void saveGameList(List<Game> gameList) {
        for (Game game:gameList)
            System.out.println(game.toString());
        try {
            File file = new File("Game.dat");
            FileOutputStream fos;

            fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        GameDAO gameDAO = new GameDAO();
        List<Game> games =  gameDAO.getAllGames();
        for (Game game: games)
            System.out.println(game.toString());
    }
}
