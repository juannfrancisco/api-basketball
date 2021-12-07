package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.dto.GameTeamDTO;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import cl.ranto.basketballpro.api.services.dao.GameDAO;
import cl.ranto.basketballpro.api.services.dao.GameTeamDAO;
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
    private GameTeamDAO gameTeamDAO;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private GameService gameService;

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


    public List<Player> findPlayersByName(String name) throws ObjectNotFoundException {
        Team team = this.findByName(name);
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(team.getOid()).collectList().block();
        return players;
    }

    public List<Championship> findChampionshipsByName(String name) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    public List<List<ChampionshipTeam>> findAllStandingsByName(String name) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    public List<ChampionshipTeam> findStandingsByName(String name) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }


    /**
     *
     * @param name
     * @return
     */
    public List<GameTeamDTO> findGamesByName(String name) throws ObjectNotFoundException, ServicesException {
        Team team = this.findByName(name);
        return this.gameTeamDAO.findGames(team);
    }

    /**
     *
     * @param oid
     * @return
     */
    public List<GameTeamDTO> findGamesById(String oid, String state) throws ObjectNotFoundException, ServicesException {
        if(null != state && !state.isEmpty()){
            return this.gameTeamDAO.findGamesByState( new Team(oid), GameState.valueOf(state) );
        }else{
            return this.gameTeamDAO.findGames( new Team(oid) );
        }
    }

    public GameDTO findLastGame(String oidTeam, String oidChampionship) throws ServicesException, ObjectNotFoundException {
        LOGGER.info("Ultimo partido de {}" , oidTeam);
        List<GameTeamDTO> games = this.gameTeamDAO.findGamesByState( new Team(oidTeam), GameState.FINALIZED);
        if( ! games.isEmpty() ){
            games.sort(Comparator.comparing(GameTeamDTO::getDate).reversed());
            GameTeamDTO dto = games.get(0);
            return this.gameService.findById( dto.getGame(), oidChampionship );
        }
        else{
            return null;
        }
    }

    public GameDTO findNextGame(String oidTeam, String oidChampionship) throws ServicesException, ObjectNotFoundException {
        LOGGER.info("Proximo partido de {}" , oidTeam);
        List<GameTeamDTO> games = this.gameTeamDAO.findGamesByState( new Team(oidTeam), GameState.PENDING);
        if( ! games.isEmpty() ){
            games.sort(Comparator.comparing(GameTeamDTO::getDate));
            GameTeamDTO dto = games.get(0);
            return this.gameService.findById( dto.getGame(), oidChampionship );
        }
        else{
            return null;
        }
    }

}