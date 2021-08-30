package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import com.google.cloud.firestore.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChampionshipService {

    private final static Logger logger = LoggerFactory.getLogger(ChampionshipService.class);

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private GameRepository gameRepository;

    public Flux<Championship> listAll(){
        return repository.findAll();
    }

    public Championship findById( String oid ){
        return repository.findById( oid).block();
    }

    public void deleteById( String oid ){
        logger.info("championship OID : " + oid );
        repository.deleteById(oid).block();
    }

    public Championship save(Championship championship){
        championship.setOid(UUID.randomUUID().toString());
        repository.save(championship).block();
        return championship;
    }


    public Championship update(Championship championship){
        repository.save(championship).block();
        return championship;
    }

    public List<Team> findTeamsByChampionship(Championship championship) {
        return new ArrayList<>();
    }

    public List<ChampionshipTeam> findTeamsStatsByChampionship(Championship championship) {
        return new ArrayList<>();
    }

    public List<Game> findMatchesByChampionship(Championship championship) {

        return gameRepository.findByChampionship(repository.findById(championship.getOid()).block()).collectList().block();
    }

    public void addTeam(String oid, Team team) {
    }
}
