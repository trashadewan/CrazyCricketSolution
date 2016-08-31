package pojo;


/**
 * Created by trasha on 26/6/16.
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    private String winnerUserId;
    private String winnerCountry;
    private Long date;

    public Game() {
    }

    public Game(CrazyCricketProtos.Game crazyCricketGame){
        this.winnerUserId = crazyCricketGame.getWinner().getUserId();
        this.winnerCountry = crazyCricketGame.getWinner().getCountry();
        this.date = crazyCricketGame.getGameDate();
    }
    public Game(String winnerUserId, String winnerCountry, Long date) {
        this.winnerUserId = winnerUserId;
        this.winnerCountry = winnerCountry;
        this.date = date;
    }

    public String getWinnerUserId() {
        return winnerUserId;
    }

    @XmlElement
    public void setWinnerUserId(String winnerUserId) {
        this.winnerUserId = winnerUserId;
    }

    public String getWinnerCountry() {
        return winnerCountry;
    }

    @XmlElement
    public void setWinnerCountry(String winnerCountry) {
        this.winnerCountry = winnerCountry;
    }

    public Long getDate() {
        return date;
    }

    @XmlElement
    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new StringBuffer(" winnerUserId : ")
                .append(this.winnerUserId)
                .append(" WinnerCountry : ")
                .append(this.winnerCountry)
                .append(" Date : ")
                .append(this.date)
                .toString();
    }
}
