package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.MatchStat;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.GameStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameStatServices {

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
        throw new UnsupportedOperationException();
    }


    public void update(GameDTO game){
        throw new UnsupportedOperationException();
    }

    public List<MatchStat> findByGame(Game game) {
        throw new UnsupportedOperationException();
    }
}
