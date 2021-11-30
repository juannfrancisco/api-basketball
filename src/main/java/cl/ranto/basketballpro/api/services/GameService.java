package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.core.refereences.GameTeam;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import cl.ranto.basketballpro.api.services.dao.GameDAO;
import cl.ranto.basketballpro.api.services.dao.GameTeamDAO;
import cl.ranto.basketballpro.api.services.dao.TeamDAO;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameRepository repository;

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


    /**
     *
     * @return
     */
    public List<GameDTO> listAll(){
        List<Game> games = repository.findAll().collectList().block();
        List<GameDTO> gamesDTO = new ArrayList<>();
        if(null != games) {
            games.forEach( game -> {
                gamesDTO.add( this.toDTO( game ) );
            });
        }
        return gamesDTO;
    }

    /**
     *
     * @param oidChampionship
     * @return
     */
    public List<GameDTO> findByChampionship(String oidChampionship) throws ServicesException {
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
     * @param oidChampionship
     * @return
     */
    public List<GameDTO> findByTeam(String oidChampionship, String oidTeam) throws ServicesException {
        try
        {

            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference championshipRef = db.collection(Constants.COLLECTION_CHAMPIONSHIPS).document(oidChampionship);
            DocumentReference teamRef = db.collection(Constants.COLLECTION_TEAMS).document(oidTeam);
            List<GameDTO> gamesDTO = new ArrayList<>();
            Query query = db.collection(Constants.COLLECTION_GAMES).
                    whereEqualTo( "championship", championshipRef ).
                    whereEqualTo( "local",teamRef);
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
    public Game findGameById( String oid ) throws ObjectNotFoundException {
        Game game =repository.findById( oid).block();
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
    public GameDTO findById( String oid ) throws ObjectNotFoundException {
        Game game = findGameById(oid);
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
    public List<GameStat> getGameStats( String oid ){
        List<GameStat> stats = new ArrayList<>();
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        CollectionReference gameStats = db.collection(Constants.COLLECTION_GAMES).document(oid).collection( Constants.COLLECTION_STATS);
        gameStats.listDocuments().forEach( documentReference -> {
            try{
                GameStat gameStat = documentReference.get().get().toObject(GameStat.class);
                stats.add(gameStat);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return stats;
    }


    /**
     *
     * @param oid
     * @return
     */
    public void calculateStats( String oid ) throws ServicesException {
        List<GameStat> stats = this.getGameStats(oid);
        Map<String,GameStatPlayer> map = new HashMap<>();
        stats.forEach( (stat) -> {
            if(!map.containsKey( stat.getOidPlayer() ) ){
                map.put( stat.getOidPlayer(), new GameStatPlayer( "", stat.getOidPlayer(), stat.getTeamOid(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ) );
            }
            GameStatPlayer statPlayer = map.get(stat.getOidPlayer());
            if( stat.getType().equals( TypeStat.PTS ) ){
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
            this.addStatPlayer(oid, entry.getValue());
            LOGGER.info( "Stats player {} PTS:{} PF:{} OR:{} DR:{} STL:{} BLK:{}", entry.getValue().getOidPlayer(),
                    entry.getValue().getPTS(),
                    entry.getValue().getPF(),
                    entry.getValue().getOR(),
                    entry.getValue().getDR(),
                    entry.getValue().getSTL(),
                    entry.getValue().getBLK());
        }

    }


    /**
     *
     * @param oid
     * @return
     */
    public List<GameStatPlayer> getGameStatsPlayer( String oid ){
        List<GameStatPlayer> stats = new ArrayList<>();
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        CollectionReference gameStats = db.collection(Constants.COLLECTION_GAMES).document(oid).collection( "statsPlayer" );
        gameStats.listDocuments().forEach( documentReference -> {
            try{
                GameStatPlayer gameStat = documentReference.get().get().toObject(GameStatPlayer.class);
                stats.add(gameStat);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return stats;
    }




    /**
     *
     * @param oid
     * @return
     */
    public List<ScoreboardItem> getScoreboard( String oid ){
        List<ScoreboardItem> scoreboardItems = new ArrayList<>();
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        CollectionReference scoreboard = db.collection(Constants.COLLECTION_GAMES).document(oid).collection( "scoreboard");
        scoreboard.listDocuments().forEach( documentReference -> {
            try{
                ScoreboardItem scoreboardItem = documentReference.get().get().toObject(ScoreboardItem.class);
                scoreboardItems.add(scoreboardItem);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return scoreboardItems;
    }

    /**
     *
     * @param game
     * @return
     */
    public GameDTO toDTO(Game game){
        GameDTO gameDTO = new GameDTO(game);
        gameDTO.setChampionship( championshipRepository.findById( game.getChampionship().getId() ).block() );
        gameDTO.setCourt( new CourtDTO( courtRepository.findById( game.getCourt().getId() ).block() ));
        gameDTO.setVisitor( teamRepository.findById( game.getVisitor().getId() ).block() );
        gameDTO.setLocal( teamRepository.findById( game.getLocal().getId() ).block() );
        return gameDTO;
    }

    /**
     *
     * @param game
     * @return
     */
    public Game dtoToObject(GameDTO game,
                            Team local,
                            Team visitor,
                            DocumentReference visitorRef,
                            DocumentReference localRef,
                            DocumentReference courtRef,
                            DocumentReference championshipRef){
        Game gameDocument = new Game();
        gameDocument.setOid( local.getAlias() + "-vs-" + visitor.getAlias()+"-"+game.getDate().getTime() );
        gameDocument.setChampionship( championshipRef );
        gameDocument.setCourt( courtRef );
        gameDocument.setVisitor( visitorRef );
        gameDocument.setLocal( localRef );
        gameDocument.setDate( game.getDate() );
        gameDocument.setState( GameState.PENDING );
        gameDocument.setStats( new ArrayList<>() );
        return gameDocument;

    }

    /**
     *
     * @param oid
     */
    public void deleteById( String oid ){
        LOGGER.info("championship OID {}" , oid );
        repository.deleteById(oid).block();
    }


    /**
     *
     * @param gameDTO
     * @return
     * @throws ServicesException
     * @throws ObjectNotFoundException
     */
    public GameDTO save(GameDTO gameDTO) throws ServicesException, ObjectNotFoundException {
        Team visitor = this.teamDAO.findById(gameDTO.getVisitor().getOid() );
        Team local = this.teamDAO.findById( gameDTO.getLocal().getOid() );
        gameDTO.setOid( String.format("%s-vs-%s-%s", local.getAlias(), visitor.getAlias(),gameDTO.getDate().getTime() ) );
        Game game = this.gameDAO.DTOtoObject(gameDTO);
        gameDAO.save(game);
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


    /**
     *
     * @param game
     */
    public void updateState(Game game) throws ServicesException {
        try{
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference gameRef = db.collection(Constants.COLLECTION_GAMES).document(game.getOid());
            Map<String, Object> data = new HashMap<>();
            data.put("state", GameState.FINALIZED.toString());
            data.put("localScore", game.getLocalScore());
            data.put("visitorScore", game.getVisitorScore());
            ApiFuture<WriteResult> writeResult = gameRef.update( data);
            LOGGER.info("Update time : {}" , writeResult.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }

    /**
     *
     * @param gameOid
     * @param stat
     */
    public GameStat addStat(String gameOid, GameStat stat) throws ServicesException {
        try {
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference gameRef = db.collection(Constants.COLLECTION_GAMES).document(gameOid);
            stat.setOid(UUID.randomUUID().toString());
            ApiFuture<DocumentReference>  ref = gameRef.collection( Constants.COLLECTION_STATS ).add(stat);
            ref.get().getId();
            return stat;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }


    public GameStatPlayer addStatPlayer(String gameOid, GameStatPlayer stat) throws ServicesException {
        try {
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference gameRef = db.collection(Constants.COLLECTION_GAMES).document(gameOid);
            stat.setOid(UUID.randomUUID().toString());
            ApiFuture<DocumentReference>  ref = gameRef.collection( "statsPlayer" ).add(stat);
            ref.get().getId();
            return stat;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }



    public ScoreboardItem addScoreboardItem(String gameOid, ScoreboardItem scoreboardItem) throws ServicesException {
        try {
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference gameRef = db.collection(Constants.COLLECTION_GAMES).document(gameOid);
            scoreboardItem.setOid(UUID.randomUUID().toString());
            ApiFuture<DocumentReference>  ref = gameRef.collection( "scoreboard" ).add(scoreboardItem);
            String id = ref.get().getId();
            scoreboardItem.setOid(id);
            return scoreboardItem;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }
}
