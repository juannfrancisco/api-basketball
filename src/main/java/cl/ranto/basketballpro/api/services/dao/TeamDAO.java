package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Team;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamDAO {

    @Autowired
    private Firestore firestore;


    @Autowired
    private TeamRepository repository;



    /**
     *
     * @param oid
     * @return
     * @throws ObjectNotFoundException
     */
    public Team findById(String oid ) throws ObjectNotFoundException {
        Team team = repository.findById( oid).block();
        if(null != team){
            return team;
        }else{
            throw new ObjectNotFoundException();
        }
    }

}
