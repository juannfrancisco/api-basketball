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


    public GameDTO(){

    }

    public GameDTO( Game game ){
        this.setScoreLocal( game.getScoreLocal() );
        this.setScoreVisitor( game.getScoreVisitor() );
        this.setState( game.getState() );

        this.setOid( game.getOid() );
        this.setDate( game.getDate() );
    }


    /**
     *
     * @param game
     * @param court
     * @param championship
     * @param teamLocal
     * @param teamVisitor
     */
    public GameDTO( Game game, CourtDTO court, Championship championship, Team teamLocal, Team teamVisitor ){
        this( game );
        this.setVisitor(teamVisitor);
        this.setLocal(teamLocal);
        this.setChampionship(championship);
        this.setCourt(court);
    }

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
