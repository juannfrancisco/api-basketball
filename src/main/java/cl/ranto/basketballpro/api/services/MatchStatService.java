package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.MatchStat;
import cl.ranto.basketballpro.api.core.Player;
import cl.ranto.basketballpro.api.repositories.MatchStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchStatService {

    @Autowired
    private MatchStatRepository repository;



    public MatchStat findById(String oid ){
        return repository.findById( oid).block();
    }

    public List<MatchStat> findByMatch(Game match ){
        return new ArrayList<>();
        //return repository.findByMatch(match);
    }

    public List<MatchStat> findByPlayer(Player player){
        return new ArrayList<>();
        //return repository.findByPlayer( player );
    };

    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    public void save(MatchStat match){
        repository.save(match).block();
    }
}
