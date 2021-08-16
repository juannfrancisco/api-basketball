package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import com.google.cloud.firestore.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class CourtService {

    private final static Logger logger = LoggerFactory.getLogger(CourtService.class);

    @Autowired
    private CourtRepository repository;


    public Flux<Court> listAll(){
        return repository.findAll();
    }

    public Court findById( String oid ){
        return repository.findById( oid).block();
    }

    public void deleteById( String oid ){
        logger.info("Court OID : " + oid );
        repository.deleteById(oid).block();
    }

    public CourtDTO save(CourtDTO court){

        Court court1 = new Court();
        court1.setOid(UUID.randomUUID().toString());
        court1.setDescription(court.getDescription());
        court1.setName(court.getName());
        court1.setSpectators(court.getSpectators());
        court1.setLocation( new GeoPoint(court.getLocation().getLatitude(), court.getLocation().getLongitude()));
        repository.save(court1).block();
        court.setOid( court1.getOid() );
        return court;
    }

}
