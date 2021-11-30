package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.core.refereences.GameTeam;
import cl.ranto.basketballpro.api.dto.GameTeamDTO;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import cl.ranto.basketballpro.api.utils.Constants;
import cl.ranto.basketballpro.api.utils.StringsUtils;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class TeamService {

    private  static final Logger LOGGER = LoggerFactory.getLogger(CourtService.class);

    @Autowired
    private TeamRepository repository;

    @Autowired
    private PlayerRepository repositoryPlayer;

    @Autowired
    private Firestore firestore;


    public Flux<Team> listAll(){
        return repository.findAll();
    }

    public List<Team> findByChampionship( String oidChampionship ){
        return repository.findByOidChampionship(oidChampionship).collectList().block();
    }

    /**
     *
     * @param oid
     * @return
     * @throws ObjectNotFoundException
     */
    public Team findByIdPlayers( String oid ) throws ObjectNotFoundException {
        Team team = this.findById(oid);
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(oid).collectList().block();
        team.setPlayers(players);
        return team;
    }


    /**
     *
     * @param oid
     * @return
     * @throws ObjectNotFoundException
     */
    public Team findById( String oid ) throws ObjectNotFoundException {
        Team team = repository.findById( oid).block();
        if(null != team){
            return team;
        }else{
            throw new ObjectNotFoundException();
        }
    }


    /**
     *
     * @param name
     * @return
     * @throws ObjectNotFoundException
     */
    public Team findByName( String name ) throws ObjectNotFoundException {
        Team team = repository.findByName( name).block();
        if(null != team){
            return team;
        }else{
            throw new ObjectNotFoundException();
        }
    }


    /**
     *
     * @param name
     * @return
     * @throws ObjectNotFoundException
     */
    public Team findByNameURL(String name) throws ObjectNotFoundException {
        Team team = repository.findByNameURL(name).block();
        if(null != team){
            return team;
        }
        else{
            throw new ObjectNotFoundException();
        }
    }

    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    /**
     *
     * @param team
     * @return
     * @throws ServicesException
     */
    public Team save(Team team) throws ServicesException {
        try {
            this.findById( StringsUtils.nameID( team.getName()) );
            throw new ServicesException("Ya existe un equipo con el mismo nombre", null);
        } catch (ObjectNotFoundException e) {
            team.setOid(StringsUtils.nameID( team.getName() ));
            team.setNameURL(StringsUtils.nameID(team.getNameURL()));
            team.setAlias(  team.getAlias().toLowerCase() );
            repository.save(team).block();
            return team;
        }
    }


    /**
     *
     * @param team
     * @return
     */
    public Team update(Team team) throws ServicesException {
        try{
            DocumentReference docRef = this.firestore.collection(Constants.COLLECTION_TEAMS).document(team.getOid());
            Map<String, Object> data = new HashMap<>();
            data.put("bio", team.getBio());
            data.put("gender", team.getGender().toString());
            data.put("category", team.getCategory().toString());
            ApiFuture<WriteResult> writeResult = docRef.update( data );
            LOGGER.info("Update time : {}" ,writeResult.get().getUpdateTime());
            return team;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }

    /**
     *
     * @param player
     * @return
     */
    public Player addPlayer(Player player) {
        player.setOid(UUID.randomUUID().toString());
        repositoryPlayer.save(player).block();
        return player;
    }

    /**
     *
     * @param oidTeam
     * @return
     */
    public Flux<Player> findAllPlayers( String oidTeam ) {
        return repositoryPlayer.findByOidCurrentTeam(oidTeam);
    }

    /**
     *
     * @param oidTeam
     * @return
     */
    public List<Player> findAllPlayersOrders( String oidTeam ) throws ServicesException {

        try {
            List<Player> players = new ArrayList<>();
            ApiFuture<QuerySnapshot> query = this.firestore.collection(Constants.COLLECTION_TEAMS)
                    .whereEqualTo("oidCurrentTeam", oidTeam)
                    .orderBy("number").get();
            for (DocumentSnapshot document : query.get().getDocuments()) {
                Player player = document.toObject(Player.class);
                players.add(player);
            }
            return players;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }

    public List<Player> findPlayersByName(String name) throws ObjectNotFoundException {
        Team team = this.findByName(name);
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(team.getOid()).collectList().block();
        return players;
    }

    public List<Championship> findChampionshipsByName(String name) throws ObjectNotFoundException {
        Team team = this.findByName(name);
        return new ArrayList<>();
    }

    public List<List<ChampionshipTeam>> findAllStandingsByName(String name) throws ObjectNotFoundException {
        Team team = this.findByName(name);
        return new ArrayList<>();
    }

    public List<ChampionshipTeam> findStandingsByName(String name) throws ObjectNotFoundException {
        Team team = this.findByName(name);
        return new ArrayList<>();
    }



    public List<GameTeamDTO> findGames(Team team, GameState state){
        List<GameTeamDTO> gamesRef = new ArrayList<>();
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        DocumentReference teamRef = db.collection( Constants.COLLECTION_TEAMS ).document( team.getOid() );
        ApiFuture<QuerySnapshot> queryGames = teamRef.collection( Constants.COLLECTION_GAMES ).whereEqualTo( "state", state ).get();

        try{
            for (DocumentSnapshot document : queryGames.get().getDocuments()) {
                    GameTeam gameRef = document.toObject(GameTeam.class);
                    GameTeamDTO dto = new GameTeamDTO();
                    dto.setOid( gameRef.getOid() );
                    dto.setDate( gameRef.getDate());
                    dto.setType( gameRef.getType() );
                    dto.setState( gameRef.getState() );
                    dto.setNameTeam( gameRef.getNameTeam() );
                    gamesRef.add(dto);
            }
        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
        return gamesRef;
    }


    /**
     *
     * @param team
     * @return
     */
    public List<GameTeamDTO> findGames(Team team ){
        List<GameTeamDTO> gamesRef = new ArrayList<>();
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        DocumentReference teamRef = db.collection( Constants.COLLECTION_TEAMS ).document( team.getOid() );
        CollectionReference games = teamRef.collection( Constants.COLLECTION_GAMES );
        games.listDocuments().forEach( documentReference -> {
            try{
                GameTeam gameRef = documentReference.get().get().toObject(GameTeam.class);
                GameTeamDTO dto = new GameTeamDTO();
                dto.setOid( gameRef.getOid() );
                dto.setDate( gameRef.getDate());
                dto.setType( gameRef.getType() );
                dto.setState( gameRef.getState() );
                dto.setNameTeam( gameRef.getNameTeam() );
                gamesRef.add(dto);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return gamesRef;
    }


    /**
     *
     * @param name
     * @return
     */
    public List<GameTeamDTO> findGamesByName(String name) throws ObjectNotFoundException {
        Team team = this.findByName(name);
        return findGames(team);
    }


    /**
     *
     * @param oid
     * @return
     */
    public List<GameTeamDTO> findGamesById(String oid, String state) throws ObjectNotFoundException {
        if(null != state && !state.isEmpty()){
            return findGames( new Team(oid), GameState.valueOf(state) );
        }else{
            return findGames( new Team(oid) );
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Game findLastGame(String name) {
        LOGGER.info("Ultimo partido de {}" , name);
        return new Game();
    }

}