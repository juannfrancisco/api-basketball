package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.Taas;
import cl.ranto.basketballpro.api.repositories.TaasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TaasService {


    @Autowired
    private TaasRepository repository;

    public Flux<Taas> listAll(){
        return repository.findAll();
    }

    public Taas findById(String oid ){
        return repository.findById( oid).block();
    }


    public void deleteById( String oid ){
        repository.deleteById(oid);
    }

    public void save(Taas taas){
        repository.save(taas);
    }

}
