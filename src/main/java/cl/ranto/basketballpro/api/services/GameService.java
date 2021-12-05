package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.core.refereences.GameTeam;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import cl.ranto.basketballpro.api.services.dao.*;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

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


    /**
     *
     * @param oidChampionship
     * @return
     */
    public List<GameDTO> findByChampionship(String oidChampionship , String x) throws ServicesException {
        try
        {
            List<GameDTO> gamesDTO = new ArrayList<>();
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            Query query = db.collection(Constants.COLLECTION_GAMES).whereEqualTo( "championship", oidChampionship );
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                document.toObject(Game.class);
            }
            return gamesDTO;
        }
        catch (InterruptedException | ExecutionException ex){
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al obtener la informacion", ex );
        }
    }

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
     * @return
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
     *
     * @param oid
     * @return
     */
    public List<GameStat> getGameStats( String oid , String oidChampionship){

        return gameStatsDAO.getGameStats(oid, new Championship(oidChampionship));
    }


    /**
     *
     * @param oid
     * @return
     */
    public void calculateStats( String oid , String oidChampionship) throws ServicesException {

        List<GameStat> stats = this.getGameStats(oid, oidChampionship);
        Map<String,GameStatPlayer> map = new HashMap<>();
        Map<Integer,ScoreboardItem> mapScoreboard = new HashMap<>();

        stats.forEach( (stat) -> {
            if(!map.containsKey( stat.getOidPlayer() ) ){
                map.put( stat.getOidPlayer(), new GameStatPlayer( "", stat.getOidPlayer(), stat.getTeamOid(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ) );
            }
            if(!mapScoreboard.containsKey( stat.getQuarter() ) ){
                mapScoreboard.put( stat.getQuarter(), new ScoreboardItem( 0, 0, stat.getQuarter().toString() ) );
            }

            GameStatPlayer statPlayer = map.get(stat.getOidPlayer());
            ScoreboardItem scoreboardItem = mapScoreboard.get( stat.getQuarter() );
            if( stat.getType().equals( TypeStat.PTS ) ){

                if(stat.getTypeTeam().equals(TypeTeam.LOCAL)){
                    scoreboardItem.setLocalPoints((int) (scoreboardItem.getLocalPoints() + stat.getValue()));
                }
                else{
                    scoreboardItem.setVisitorPoints((int) (scoreboardItem.getVisitorPoints() + stat.getValue()));
                }

                statPlayer.setPTS((int) (statPlayer.getPTS() + stat.getValue()));
                if( stat.getValue() == 1 ){
                    statPlayer.setPTS1((int) (statPlayer.getPTS1() + stat.getValue()));
                }else if(stat.getValue() == 2) {
                    statPlayer.setPTS2((int) (statPlayer.getPTS2() + stat.getValue()));
                }else if( stat.getValue() == 3 ){
                    statPlayer.setPTS3((int) (statPlayer.getPTS3() + stat.getValue()));
                }
            }
            if( stat.getType().equals( TypeStat.MPT ) ){
                statPlayer.setMPT((int) (statPlayer.getMPT() + stat.getValue()));
                if( stat.getValue() == 1 ){
                    statPlayer.setMPT1((int) (statPlayer.getMPT1() + stat.getValue()));
                }else if(stat.getValue() == 2) {
                    statPlayer.setMPT2((int) (statPlayer.getMPT2() + stat.getValue()));
                }else if( stat.getValue() == 3 ){
                    statPlayer.setMPT3((int) (statPlayer.getMPT3() + stat.getValue()));
                }
            }
            if( stat.getType().equals( TypeStat.PF ) ){
                statPlayer.setPF((int) (statPlayer.getPF() + stat.getValue()));
            }
            if( stat.getType().equals( TypeStat.AST ) ){
                statPlayer.setAST((int) (statPlayer.getAST() + stat.getValue()));
            }
            if( stat.getType().equals( TypeStat.MAS ) ){
                statPlayer.setMAS((int) (statPlayer.getMAS() + stat.getValue()));
            }
            if( stat.getType().equals( TypeStat.OR ) ){
                statPlayer.setOR((int) (statPlayer.getOR() + stat.getValue()));
            }
            if( stat.getType().equals( TypeStat.DR ) ){
                statPlayer.setDR((int) (statPlayer.getDR() + stat.getValue()));
            }
            if( stat.getType().equals( TypeStat.STL ) ){
                statPlayer.setSTL((int) (statPlayer.getSTL() + stat.getValue()));
            }
            if( stat.getType().equals( TypeStat.BLK ) ){
                statPlayer.setBLK((int) (statPlayer.getBLK() + stat.getValue()));
            }
        });

        for (Map.Entry<String,GameStatPlayer> entry : map.entrySet()){
            this.addStatPlayer(oid, oidChampionship, entry.getValue());
            LOGGER.info( "Stats player {} PTS:{} PF:{} OR:{} DR:{} STL:{} BLK:{}", entry.getValue().getOidPlayer(),
                    entry.getValue().getPTS(),
                    entry.getValue().getPF(),
                    entry.getValue().getOR(),
                    entry.getValue().getDR(),
                    entry.getValue().getSTL(),
                    entry.getValue().getBLK());
        }

        for( Map.Entry<Integer,ScoreboardItem> entry : mapScoreboard.entrySet() ){
            this.addScoreboardItem( oid, oidChampionship, entry.getValue() );
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

    /**
     *
     * @param game
     * @param team
     * @return
     * @throws ServicesException
     */
    public GameTeam findGameRef(Game game , Team team) throws ServicesException {

        try {
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference teamRef = db.collection( Constants.COLLECTION_TEAMS ).document( team.getOid() );
            DocumentReference gameRef = db.collection( Constants.COLLECTION_GAMES ).document( game.getOid() );

            CollectionReference gamesRef = teamRef.collection( Constants.COLLECTION_GAMES );
            Query query = gamesRef.whereArrayContains("game", gameRef);

            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                return document.toObject(GameTeam.class);
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }

    public GameDTO update(GameDTO game){
        return game;
    }

    public void updateState(Game game, String oidChampionship) throws ServicesException {
        gameDAO.updateState(game, new Championship(oidChampionship));
    }

    public GameStat addStat(String oidGame, String oidChampionship, GameStat stat) throws ServicesException {
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
}
