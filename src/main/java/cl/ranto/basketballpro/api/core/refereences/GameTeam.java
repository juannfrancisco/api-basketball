package cl.ranto.basketballpro.api.core.refereences;

import cl.ranto.basketballpro.api.core.GameState;
import cl.ranto.basketballpro.api.core.TypeTeam;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.DocumentId;

import java.util.Date;

public class GameTeam {

    @DocumentId
    private String oid;
    private DocumentReference game;
    private Date date;
    private TypeTeam type;
    private String nameTeam;
    private GameState state;


    public GameTeam() {
    }

    public GameTeam(String oid, DocumentReference game, Date date, TypeTeam type, String nameTeam, GameState state) {
        this.oid = oid;
        this.game = game;
        this.date = date;
        this.type = type;
        this.nameTeam = nameTeam;
        this.state = state;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public DocumentReference getGame() {
        return game;
    }

    public void setGame(DocumentReference game) {
        this.game = game;
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
}
