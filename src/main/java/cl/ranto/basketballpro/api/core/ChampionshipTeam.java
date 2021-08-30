package cl.ranto.basketballpro.api.core;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
public class ChampionshipTeam {


    private String oid;
    private Team team;
    private Championship championship;
    private Integer points = 0;
    private Integer playGames = 0;
    private Integer lostGames = 0;
    private Integer winGames = 0;


    public ChampionshipTeam(){}

    public ChampionshipTeam(String oid){
        this.setOid(oid);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }


    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPlayGames() {
        return playGames;
    }

    public void setPlayGames(Integer playGames) {
        this.playGames = playGames;
    }

    public Integer getLostGames() {
        return lostGames;
    }

    public void setLostGames(Integer lostGames) {
        this.lostGames = lostGames;
    }

    public Integer getWinGames() {
        return winGames;
    }

    public void setWinGames(Integer winGames) {
        this.winGames = winGames;
    }
}
