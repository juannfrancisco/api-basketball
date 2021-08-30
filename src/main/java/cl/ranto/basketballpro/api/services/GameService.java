package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.GameState;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class GameService {

    private final static Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private CourtRepository courtRepository;


    public Flux<Game> listAll(){
        return repository.findAll();
    }

    public Game findById( String oid ){
        return repository.findById( oid).block();
    }

    public void deleteById( String oid ){
        logger.info("championship OID : " + oid );
        repository.deleteById(oid).block();
    }

    public GameDTO save(GameDTO game){
        Game gameDocument = new Game();
        gameDocument.setChampionship( championshipRepository.findById(game.getChampionship().getOid()).block() );
        gameDocument.setCourt( courtRepository.findById( game.getCourt().getOid() ).block() );
        gameDocument.setVisitor( teamRepository.findById(game.getVisitor().getOid()).block() );
        gameDocument.setLocal( teamRepository.findById(game.getLocal().getOid()).block() );
        gameDocument.setOid(UUID.randomUUID().toString());
        gameDocument.setDate( game.getDate() );
        gameDocument.setState( GameState.PENDING );
        repository.save(gameDocument).block();
        return game;
    }


    public Game update(Game game){
        repository.save(game).block();
        return game;
    }

}
