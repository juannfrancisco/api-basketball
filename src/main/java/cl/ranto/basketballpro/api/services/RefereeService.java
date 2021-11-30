package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.Referee;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.repositories.RefereeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RefereeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefereeService.class);

    @Autowired
    private RefereeRepository repository;
    public Flux<Referee> listAll(){
        return repository.findAll();
    }

    /**
     *
     * @param oid
     * @return
     * @throws ObjectNotFoundException
     */
    public Referee findById( String oid ) throws ObjectNotFoundException {
        Referee referee = repository.findById( oid).block();
        if(null != referee){
            return repository.findById( oid).block();
        }else{
            throw new ObjectNotFoundException();
        }
    }

    public void deleteById( String oid ){
        LOGGER.info("Referee OID : {}", oid );
        repository.deleteById(oid).block();
    }

    public Referee save(Referee referee){
        repository.save(referee).block();
        return referee;
    }

    public Referee update(Referee court){
        throw new UnsupportedOperationException();
    }
}