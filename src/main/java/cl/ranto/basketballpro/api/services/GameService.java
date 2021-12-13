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

    @Autowired
    private StatsServices statsServices;


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
        Team teamVisitor = teamDAO.findById( game.getVisitor().getId() );
        Team  teamLocal = teamDAO.findById( game.getLocal().getId() );
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


    /**
     *  Calcula las estadisticas del partido.
     *  Estadisticas sumaizadas por jugador de cada equipo
     *  Estadisticas sumaizadas por Equipo
     *  Marcador por cada cuarto.
     * @param oid
     * @return
     */
    public void calculateStats( String oid , String oidChampionship) throws ServicesException {
        List<GameStat> stats = this.getGameStats(oid, oidChampionship);
        List<GameStatPlayer> gameStatPlayers = new ArrayList<>();
        Map<String,GameStatPlayer> map = statsServices.calculateGameStatsPlayer(stats);
        for (Map.Entry<String,GameStatPlayer> entry : map.entrySet()){
            this.addStatPlayer(oid, oidChampionship, entry.getValue());
            gameStatPlayers.add(entry.getValue());
        }

        Map<Integer,ScoreboardItem> mapScoreboard = statsServices.calculateScoreboard(stats);
        for( Map.Entry<Integer,ScoreboardItem> entry : mapScoreboard.entrySet() ){
            this.addScoreboardItem( oid, oidChampionship, entry.getValue() );
        }

        List<TeamStat> teamStats = statsServices.calculateTeamStats(stats);
        for( TeamStat stat: teamStats ){
            stat.setOidPlayerHIPoints( statsServices.findMaxPoints(gameStatPlayers, stat.getOidTeam()) );
            stat.setOidPlayerHIRebounds( statsServices.findMaxRebounds(gameStatPlayers, stat.getOidTeam()) );
            stat.setOidPlayerHIAssists( statsServices.findMaxAssist(gameStatPlayers, stat.getOidTeam()) );
           this.addTeamStat(oid, oidChampionship, stat );
        }
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
