package cl.ranto.basketballpro.api.dto;

import cl.ranto.basketballpro.api.core.GameState;
import cl.ranto.basketballpro.api.core.TypeTeam;
import com.google.cloud.firestore.DocumentReference;

import java.util.Date;

public class GameTeamDTO {

    private String oid;
    private Date date;
    private TypeTeam type;
    private String nameTeam;
    private GameState state;
    private String game;
    private Integer localScore;
    private Integer visitorScore;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TypeTeam getType() {
        return type;
    }

    public void setType(TypeTeam type) {
        this.type = type;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getLocalScore() {
        return localScore;
    }

    public void setLocalScore(Integer localScore) {
        this.localScore = localScore;
    }

    public Integer getVisitorScore() {
        return visitorScore;
    }

    public void setVisitorScore(Integer visitorScore) {
        this.visitorScore = visitorScore;
    }
}
