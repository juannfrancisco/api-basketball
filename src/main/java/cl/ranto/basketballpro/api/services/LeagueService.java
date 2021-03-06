package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.core.League;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import com.google.cloud.firestore.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LeagueService {

    @Autowired
    private LeagueService repository;


    public Flux<League> listAll(){
        return repository.listAll();
    }

    public League findById( String oid ){
        return repository.findById( oid);
    }

    public void deleteById( String oid ){
        repository.deleteById(oid);
    }

    public void save(League league){
        repository.save(league);
    }

}
