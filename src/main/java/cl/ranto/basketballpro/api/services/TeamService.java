package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class TeamService {

    private final static Logger logger = LoggerFactory.getLogger(CourtService.class);
    private static final String COLLECTION_TEAMS = "teams";

    @Autowired
    private TeamRepository repository;

    @Autowired
    private PlayerRepository repositoryPlayer;


    public Flux<Team> listAll(){
        return repository.findAll();
    }

    public List<Team> findByChampionship( String oidChampionship ){
        return repository.findByOidChampionship(oidChampionship).collectList().block();
    }

    public Team findById( String oid ){
        Team team = repository.findById( oid).block();
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(oid).collectList().block();
        team.setPlayers(players);
        return team;
    }

    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    public Team save(Team team){
        team.setOid(UUID.randomUUID().toString());
        repository.save(team).block();
        return team;
    }


    /**
     *
     * @param team
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Team update(Team team) throws ExecutionException, InterruptedException {
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        DocumentReference docRef = db.collection(COLLECTION_TEAMS).document(team.getOid());

        Map<String, Object> data = new HashMap<>();
        data.put("bio", team.getBio());
        data.put("gender", team.getGender().toString());
        data.put("category", team.getCategory().toString());
        //data.put("contact", team.getContact() );
        //data.put("coach", team.getCoach());

        ApiFuture<WriteResult> writeResult = docRef.update( data );
        logger.info("Update time : " + writeResult.get().getUpdateTime());

        return team;
    }

    public Player addPlayer(Player player) {
        player.setOid(UUID.randomUUID().toString());
        repositoryPlayer.save(player).block();
        return player;
    }

    public Flux<Player> findAllPlayers( String oidTeam ) {
        return repositoryPlayer.findByOidCurrentTeam(oidTeam);
    }

    public List<Player> findPlayersByName(String name) {
        Team team = repository.findByNameURL( name ).block();
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(team.getOid()).collectList().block();
        return players;
    }

    public List<Championship> findChampionshipsByName(String name) {
        return new ArrayList<>();
    }

    public List<List<ChampionshipTeam>> findAllStandingsByName(String name) {
        return new ArrayList<>();
    }

    public List<ChampionshipTeam> findStandingsByName(String name) {
        return new ArrayList<>();
    }

    public List<Game> findMatchesByName(String name) {
        return new ArrayList<>();
    }

    public Game findLastMatch(String name) {
        return new Game();
    }

    public Team findByName(String name) {
        return repository.findByNameURL(name).block();
    }
}