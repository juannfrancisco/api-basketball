package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.services.dao.CourtDAO;
import com.google.cloud.firestore.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Service
public class CourtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourtService.class);

    @Autowired
    private CourtDAO dao;


    public List<Court> listAll(){
        return dao.listAll();
    }

    public Court findById( String oid ) throws ObjectNotFoundException {
        return dao.findById( oid);
    }

    public void deleteById( String oid ){
        dao.deleteById(oid);
    }

    public CourtDTO save(CourtDTO court){
        Court court1 = new Court();
        court1.setOid(UUID.randomUUID().toString());
        court1.setDescription(court.getDescription());
        court1.setName(court.getName());
        court1.setSpectators(court.getSpectators());
        court1.setLocation( new GeoPoint(court.getLocation().getLatitude(), court.getLocation().getLongitude()));
        court.setOid( dao.save(court1).getOid() );
        return court;
    }


    public CourtDTO update(CourtDTO court){
        Court court1 = new Court();
        court1.setOid(court.getOid());
        court1.setDescription(court.getDescription());
        court1.setName(court.getName());
        court1.setSpectators(court.getSpectators());
        court1.setLocation( new GeoPoint(court.getLocation().getLatitude(), court.getLocation().getLongitude()));
        court.setOid( dao.save(court1).getOid() );
        return court;
    }

}
