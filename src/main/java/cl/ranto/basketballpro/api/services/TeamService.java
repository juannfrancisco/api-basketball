package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private final static Logger logger = LoggerFactory.getLogger(CourtService.class);

    @Autowired
    private TeamRepository repository;

    @Autowired
    private PlayerRepository repositoryPlayer;


    public Flux<Team> listAll(){
        return repository.findAll();
    }

    public Team findById( String oid ){
        Team team = repository.findById( oid).block();
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(oid).collectList().block();
        team.setPlayers(players);
        return team;
    }

    public void deleteById( String oid ){
        logger.info("Court OID : " + oid );
        repository.deleteById(oid).block();
    }

    public Team save(Team team){
        team.setOid(UUID.randomUUID().toString());
        repository.save(team).block();
        return team;
    }


    public Team update(Team team){
        repository.save(team).block();
        return team;
    }

    public Player addPlayer(Player player) {

        player.setOid(UUID.randomUUID().toString());
        logger.info("Player OID : " + player.getOid() );
        repositoryPlayer.save(player).block();
        return player;
    }

    public Flux<Player> findAllPlayers( String oidTeam ) {
        return repositoryPlayer.findByOidCurrentTeam(oidTeam);
    }

    public List<Player> findPlayersByName(String name) {
        Team team = repository.findByNameURL( name ).block();
        logger.info("Team:" + team.getNameURL());
        List<Player> players = repositoryPlayer.findByOidCurrentTeam(team.getOid()).collectList().block();
        logger.info("Players:" + players.size());
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