package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.Player;
import cl.ranto.basketballpro.api.core.Team;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
        return repository.findById( oid).block();
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
}
