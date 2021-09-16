package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.MatchStat;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.GameStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameStatServices {

    private final static Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameStatRepository repository;

    public List<MatchStat> listAll(){
        return repository.findAll().collectList().block();
    }

    public MatchStat findById(String oid ){
        return repository.findById( oid).block();
    }



    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    public MatchStat save(MatchStat game){
        game.getOid();
        //repository.save(game).block();
        return game;
    }


    public void update(GameDTO game){
    }

    public List<MatchStat> findByGame(Game game) {
        return new ArrayList<>();
    }
}
