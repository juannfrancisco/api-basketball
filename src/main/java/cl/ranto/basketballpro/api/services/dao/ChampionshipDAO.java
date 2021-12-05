package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ChampionshipDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipDAO.class);


    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private Firestore firestore;


    public List<Championship> listAll(){
        return repository.findAll().collectList().block();
    }


    /**
     *
     * @param state
     * @return
     */
    public List<Championship> listAllByState(String state){
        return repository.findByState(state).collectList().block();
    }

    public Championship findById( String oid ) throws ObjectNotFoundException {
        Championship championship = repository.findById( oid).block();
        if(null != championship){
            return championship;
        }else{
            throw new ObjectNotFoundException();
        }
    }

    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    public Championship save(Championship championship){
        repository.save(championship).block();
        return championship;
    }


    /**
     *
     * @param championship
     * @return
     * @throws ServicesException
     */
    public Championship update(Championship championship) throws ServicesException {
        try{
            DocumentReference docRef = this.firestore.collection(Constants.COLLECTION_CHAMPIONSHIPS).document(championship.getOid());
            Map<String, Object> data = new HashMap<>();
            data.put("name", championship.getName());
            data.put("description", championship.getDescription() );
            ApiFuture<WriteResult> writeResult = docRef.update( data );
            LOGGER.info("Update time : {}" ,writeResult.get().getUpdateTime());
            return championship;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al actualizar la informacion", e );
        }
    }

}
