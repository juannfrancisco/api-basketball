package cl.ranto.basketballpro.api.services;
import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import cl.ranto.basketballpro.api.services.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private TeamDAO teamDAO;

    @Autowired
    private GameTeamDAO gameTeamDAO;

    @Autowired
    private GameStatsDAO gameStatsDAO;

    @Autowired
    private ScoreboardDAO scoreboardDAO;

    @Autowired
    private GameStatsPlayerDAO gameStatsPlayerDAO;

    @Autowired
    private TeamStatsDAO teamStatsDAO;


    /**
     *
     * @param oid
     * @return
     * @throws ObjectNotFoundException
     */
    public Game findGameById( String oid, String oidChampionship ) throws ObjectNotFoundException, ServicesException {
        Game game = gameDAO.findById( oid, new Championship(oidChampionship) );
        if( null != game  ){
            return game;
        }else{
            throw new ObjectNotFoundException();
        }
    }

    /**
     *
     * @param oid
     * @param oidChampionship
     * @return
     * @throws ObjectNotFoundException
     * @throws ServicesException
     */
    public GameDTO findById( String oid, String oidChampionship ) throws ObjectNotFoundException, ServicesException {
        Game game = findGameById(oid, oidChampionship);
        Championship championship = championshipRepository.findById( game.getChampionship().getId() ).block();
        Court court = courtRepository.findById( game.getCourt().getId() ).block();
        Team teamVisitor = teamRepository.findById( game.getVisitor().getId() ).block();
        Team  teamLocal = teamRepository.findById( game.getLocal().getId() ).block();
        GameDTO gameDTO = new GameDTO(game, new CourtDTO(court), championship, teamLocal ,teamVisitor );
        return gameDTO;
    }


    /**
     * Obtiene las estadisticas del partido de todos los cuartos jugados
     * @param oid
     * @return
     */
    public List<GameStat> getGameStats( String oid , String oidChampionship){
        return gameStatsDAO.getGameStats(oid, new Championship(oidChampionship));
    }

    /**
     * Obtiene las estadisticas del partido de todos los cuartos jugados
     * @param oid
     * @return
     */
    public List<GameStat> getGameStats( String oid , String oidChampionship, Integer quarter) throws ServicesException {
        return gameStatsDAO.getGameStatsByQuarter(oid, new Championship(oidChampionship), quarter);
    }


    public Map<Integer,ScoreboardItem> calculateScoreboard(List<GameStat> stats){
        Map<Integer,ScoreboardItem> mapScoreboard = new HashMap<>();
        stats.forEach( (stat) -> {
            if(!mapScoreboard.containsKey( stat.getQuarter() ) ){
                mapScoreboard.put( stat.getQuarter(), new ScoreboardItem( 0, 0, stat.getQuarter().toString() ) );
            }
            ScoreboardItem scoreboardItem = mapScoreboard.get( stat.getQuarter() );
            if (  stat.getType().equals( TypeStat.PTS )){
                if(stat.getTypeTeam().equals(TypeTeam.LOCAL)){
                    scoreboardItem.setLocalPoints((int) (scoreboardItem.getLocalPoints() + stat.getValue()));
                }
                else{
                    scoreboardItem.setVisitorPoints((int) (scoreboardItem.getVisitorPoints() + stat.getValue()));
                }
            }
        });
        return mapScoreboard;
    }

    public List<TeamStat> calculateTeamStats(List<GameStat> stats){
        List<TeamStat> teamStats = new ArrayList<>();
        TeamStat local = new TeamStat();
        TeamStat visitor = new TeamStat();
        stats.forEach( (stat) -> {
            if(stat.getTypeTeam().equals(TypeTeam.LOCAL)){
                stat.getType().addTeamStat( local, (int) stat.getValue() );
                local.setOidTeam( stat.getTeamOid() );
            }else{
                stat.getType().addTeamStat( visitor, (int) stat.getValue() );
                visitor.setOidTeam( stat.getTeamOid() );
            }
        });
        teamStats.add(local);
        teamStats.add(visitor);
        return teamStats;
    }

    public Map<String,GameStatPlayer> calculateGameStatsPlayer(List<GameStat> stats){
        Map<String,GameStatPlayer> map = new HashMap<>();
        stats.forEach( (stat) -> {
            if(!map.containsKey( stat.getOidPlayer() ) ){
                map.put( stat.getOidPlayer(), new GameStatPlayer( stat.getOidPlayer(), stat.getOidPlayer(), stat.getTeamOid() ) );
            }
            GameStatPlayer statPlayer = map.get(stat.getOidPlayer());
            stat.getType().addGameStatPlayer( statPlayer, (int) stat.getValue() );
        });
        return map;
    }


    /**
     *
     * @param oid
     * @return
     */
    public void calculateStats( String oid , String oidChampionship) throws ServicesException {
        List<GameStat> stats = this.getGameStats(oid, oidChampionship);
        List<GameStatPlayer> gameStatPlayers = new ArrayList<>();
        Map<String,GameStatPlayer> map = this.calculateGameStatsPlayer(stats);
        for (Map.Entry<String,GameStatPlayer> entry : map.entrySet()){
            this.addStatPlayer(oid, oidChampionship, entry.getValue());
            gameStatPlayers.add(entry.getValue());
        }

        Map<Integer,ScoreboardItem> mapScoreboard = this.calculateScoreboard(stats);
        for( Map.Entry<Integer,ScoreboardItem> entry : mapScoreboard.entrySet() ){
            this.addScoreboardItem( oid, oidChampionship, entry.getValue() );
        }

        List<TeamStat> teamStats = this.calculateTeamStats(stats);
        for( TeamStat stat: teamStats ){
            stat.setOidPlayerHIPoints( this.findMaxPoints(gameStatPlayers, stat.getOidTeam()) );
            stat.setOidPlayerHIRebounds( this.findMaxRebounds(gameStatPlayers, stat.getOidTeam()) );
            stat.setOidPlayerHIAssists( this.findMaxAssist(gameStatPlayers, stat.getOidTeam()) );
           this.addTeamStat(oid, oidChampionship, stat );
        }
    }

    public HiStatPlayer findMaxPoints( List<GameStatPlayer> gameStatPlayers, String oidTeam){
        GameStatPlayer statPlayer = gameStatPlayers.stream()
                .filter( gameStatPlayer ->  gameStatPlayer.getOidTeam().equals( oidTeam ) )
                .max(Comparator.comparing(GameStatPlayer::getPTS))
                .get();
        HiStatPlayer hiStatPlayer = null;
        if(statPlayer.getPTS() != 0){
            hiStatPlayer = new HiStatPlayer(statPlayer.getOidPlayer(), TypeStat.PTS, statPlayer.getPTS() );
        }
        return hiStatPlayer;

    }

    public HiStatPlayer findMaxAssist( List<GameStatPlayer> gameStatPlayers, String oidTeam){
        GameStatPlayer statPlayer = gameStatPlayers.stream()
                .filter( gameStatPlayer ->  gameStatPlayer.getOidTeam().equals( oidTeam ) )
                .max(Comparator.comparing(GameStatPlayer::getAST))
                .get();
        HiStatPlayer hiStatPlayer = null;
        if(statPlayer.getAST() != 0){
            hiStatPlayer = new HiStatPlayer(statPlayer.getOidPlayer(), TypeStat.AST, statPlayer.getAST() );
        }
        return hiStatPlayer;
    }

    public HiStatPlayer findMaxRebounds( List<GameStatPlayer> gameStatPlayers, String oidTeam){
        GameStatPlayer statPlayer = gameStatPlayers.stream()
                .filter( gameStatPlayer ->  gameStatPlayer.getOidTeam().equals( oidTeam ) )
                .map(  gameStatPlayer -> {
                    gameStatPlayer.setREB( gameStatPlayer.getOR() + gameStatPlayer.getDR() );
                    return gameStatPlayer;
                })
                .max(Comparator.comparing(GameStatPlayer::getREB))
                .get();
        HiStatPlayer hiStatPlayer = null;
        if(statPlayer.getOR() + statPlayer.getDR() != 0){
            hiStatPlayer = new HiStatPlayer(statPlayer.getOidPlayer(), TypeStat.REB, statPlayer.getOR() + statPlayer.getDR() );
        }
        return hiStatPlayer;
    }

    public void deleteById( String oid ){
        this.gameDAO.deleteById(oid);
    }


    /**
     *
     * @param championship
     * @param gameDTO
     * @return
     * @throws ServicesException
     * @throws ObjectNotFoundException
     */
    public GameDTO save(Championship championship, GameDTO gameDTO) throws ServicesException, ObjectNotFoundException {
        Team visitor = this.teamDAO.findById(gameDTO.getVisitor().getOid() );
        Team local = this.teamDAO.findById( gameDTO.getLocal().getOid() );
        gameDTO.setOid( String.format("%s-vs-%s-%s", local.getAlias(), visitor.getAlias(),gameDTO.getDate().getTime() ) );
        Game game = this.gameDAO.DTOtoObject(gameDTO);
        gameDAO.save(championship, game);
        gameTeamDAO.addGameRef(game, local, visitor);
        return gameDTO;
    }


    public GameDTO update(GameDTO game){
        return game;
    }

    /**
     * Actualiza el estado del partido a finalizado y agrega el marcador local y visitante.
     * Actualiza tambien las referencias en las colecciones de los dos equipos.
     * @param game
     * @param oidChampionship
     * @throws ServicesException
     * @throws ObjectNotFoundException
     */
    public void updateState(Game game, String oidChampionship) throws ServicesException, ObjectNotFoundException {
        Game gameFind = this.findGameById(game.getOid(), oidChampionship);
        gameDAO.updateState(game, new Championship(oidChampionship));
        gameFind.setLocalScore( game.getLocalScore() );
        gameFind.setVisitorScore( game.getVisitorScore() );
        gameTeamDAO.update(gameFind);
    }

    public GameStat addStat(String oidGame, String oidChampionship, GameStat stat) throws ServicesException {
        stat.setDate(new Date());
        return gameStatsDAO.save(oidGame, new Championship(oidChampionship), stat);
    }

    public List<GameStatPlayer> getGameStatsPlayer( String oidGame, String oidChampionship ){
        return this.gameStatsPlayerDAO.getGameStatsPlayer(oidGame,new Championship(oidChampionship));
    }

    public GameStatPlayer addStatPlayer(String oidGame, String oidChampionship, GameStatPlayer stat) throws ServicesException {
        return this.gameStatsPlayerDAO.addStatPlayer(oidGame, new Championship(oidChampionship), stat);
    }

    public List<ScoreboardItem> getScoreboard( String oidChampionship, String oidGame ){
        return this.scoreboardDAO.getScoreboard( oidGame, new Championship(oidChampionship) );
    }

    public ScoreboardItem addScoreboardItem(String oidGame, String oidChampionship, ScoreboardItem scoreboardItem) throws ServicesException {
        return this.scoreboardDAO.addScoreboardItem(oidGame, new Championship(oidChampionship), scoreboardItem);
    }


    public TeamStat addTeamStat(String oidGame, String oidChampionship, TeamStat teamStat) throws ServicesException {
        return this.teamStatsDAO.addTeamStat(oidGame, new Championship(oidChampionship), teamStat);
    }

    public List<TeamStat> getTeamStats(String oidGame, String oidChampionship ) throws ServicesException {
        return this.teamStatsDAO.getTeamStats(oidGame, new Championship(oidChampionship));
    }
}
