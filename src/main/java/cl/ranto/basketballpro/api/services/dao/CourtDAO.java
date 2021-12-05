package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourtDAO {

    @Autowired
    private CourtRepository repository;

    public List<Court> listAll(){
        return repository.findAll().collectList().block();
    }

    public Court findById( String oid ) throws ObjectNotFoundException {
        Court court = repository.findById( oid).block();
        if(court != null)
            return court;
        else
            throw new ObjectNotFoundException();
    }

    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    public Court save(Court court){
        repository.save(court).block();
        court.setOid( court.getOid() );
        return court;
    }


    public Court update(Court court){
        repository.save(court).block();
        court.setOid( court.getOid() );
        return court;
    }
}
