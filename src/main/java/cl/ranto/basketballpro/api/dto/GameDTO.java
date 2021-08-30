package cl.ranto.basketballpro.api.dto;

import cl.ranto.basketballpro.api.core.*;

import java.util.Date;

public class GameDTO {

    private String oid;
    private Date date;
    private Team visitor;
    private Team local;
    private int scoreVisitor;
    private int scoreLocal;
    private CourtDTO court;
    private Referee referee;
    private Championship championship;
    private GameState state;


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

    public Team getVisitor() {
        return visitor;
    }

    public void setVisitor(Team visitor) {
        this.visitor = visitor;
    }

    public Team getLocal() {
        return local;
    }

    public void setLocal(Team local) {
        this.local = local;
    }

    public int getScoreVisitor() {
        return scoreVisitor;
    }

    public void setScoreVisitor(int scoreVisitor) {
        this.scoreVisitor = scoreVisitor;
    }

    public int getScoreLocal() {
        return scoreLocal;
    }

    public void setScoreLocal(int scoreLocal) {
        this.scoreLocal = scoreLocal;
    }

    public CourtDTO getCourt() {
        return court;
    }

    public void setCourt(CourtDTO court) {
        this.court = court;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
