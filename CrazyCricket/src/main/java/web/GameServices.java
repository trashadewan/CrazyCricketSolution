package web;

/**
 * Created by trasha on 26/6/16.
 */

import dao.GameDAO;
import pojo.Game;
import pojo.GameLeaderBoardService;
import web.response.NameValuePair;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")
public class GameServices {

    GameDAO gameDao = new GameDAO();
    private GameLeaderBoardService gameLeaderBoardService = new GameLeaderBoardService();

    @GET
    @Path("/games")
    @Produces(MediaType.APPLICATION_XML)
    public List<Game> getGames(){
        return new GameDAO().getAllGames();
    }

    @GET
    @Path("/leaderboard")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NameValuePair> getCountryLeaderBoard(){
        return gameLeaderBoardService.getCountryLeaderBoard();
    }

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(){
        return "pong";
    }


}