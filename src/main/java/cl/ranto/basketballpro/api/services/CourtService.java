package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import com.google.cloud.firestore.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CourtService {

    @Autowired
    private CourtRepository repository;


    public Flux<Court> listAll(){
        return repository.findAll();
    }

    public Court findById( String oid ){
        return repository.findById( oid).block();
    }

    public void deleteById( String oid ){
        repository.deleteById(oid);
    }

    public void save(CourtDTO court){
        Court court1 = new Court();
        court1.setOid(court.getOid());
        court1.setDescription(court.getDescription());
        court1.setName(court.getName());
        court1.setSpectators(court.getSpectators());
        court1.setLocation( new GeoPoint(court.getLocation().getLatitude(), court.getLocation().getLongitude()));
        Court cresp =  repository.save(court1).block();
        System.out.println(cresp.getOid());
    }

}
