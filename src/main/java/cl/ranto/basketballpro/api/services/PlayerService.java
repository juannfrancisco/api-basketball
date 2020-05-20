package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.Player;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PlayerService {


    @Autowired
    private PlayerRepository repository;

    public Flux<Player> listAll(){
        return repository.findAll();
    }

    public Player findById( String oid ){
        return repository.findById( oid).block();
    }


    public void deleteById( String oid ){
        repository.deleteById(oid);
    }

    public void save(Player player){
        repository.save(player);
    }

}
