package cl.ranto.basketballpro.api.core;

import com.google.cloud.firestore.annotation.DocumentId;

public class GameStat implements IResponse{

    @DocumentId
    private String oid;
    private String oidPlayer;
    private Integer quarter;
    private String quarterTimeText;
    private TypeStat type;
    private double value;
    private TypeTeam typeTeam;
    private String teamOid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOidPlayer() {
        return oidPlayer;
    }

    public void setOidPlayer(String oidPlayer) {
        this.oidPlayer = oidPlayer;
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

    public String getQuarterTimeText() {
        return quarterTimeText;
    }

    public void setQuarterTimeText(String quarterTimeText) {
        this.quarterTimeText = quarterTimeText;
    }
}
