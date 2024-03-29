package cl.ranto.basketballpro.api.core;


import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

@Document(collectionName = "gamestat")
public class MatchStat {

    @DocumentId
    private String oid;
    private Game match;
    private Player player;
    private Integer quarter;
    private TypeStat type;
    private double value;
    private TypeTeam typeTeam;
    private String teamOid;
    private String championshipOid;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Game getMatch() {
        return match;
    }

    public void setMatch(Game match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public TypeStat getType() {
        return type;
    }

    public void setType(TypeStat type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public TypeTeam getTypeTeam() {
        return typeTeam;
    }

    public void setTypeTeam(TypeTeam typeTeam) {
        this.typeTeam = typeTeam;
    }

    public String getTeamOid() {
        return teamOid;
    }

    public void setTeamOid(String teamOid) {
        this.teamOid = teamOid;
    }

    public String getChampionshipOid() {
        return championshipOid;
    }

    public void setChampionshipOid(String championshipOid) {
        this.championshipOid = championshipOid;
    }
}
