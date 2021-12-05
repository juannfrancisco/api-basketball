package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.GameState;
import cl.ranto.basketballpro.api.core.Player;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.repositories.PlayerRepository;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PlayerService {


    @Autowired
    private PlayerRepository repository;

    @Autowired
    private Firestore firestore;

    public Flux<Player> listAll(){
        return repository.findAll();
    }

    public Player findById( String oid ){
        return repository.findById( oid).block();
    }


    public void deleteById( String oid ){
        repository.deleteById(oid).block();
    }

    public void save(Player player){
        repository.save(player).block();
    }

    public void update(Player player) throws ServicesException {
        try{
            DocumentReference playerRef = this.firestore.collection( Constants.COLLECTION_PLAYERS ).document( player.getOid() );
            Map<String, Object> data = new HashMap<>();
            data.put("number", player.getNumber() );
            data.put("position", player.getPosition().toString());
            data.put("name", player.getName());
            data.put("lastName", player.getLastName());
            data.put("birthdate", player.getBirthdate());
            data.put("height", player.getHeight());
            data.put("weight", player.getWeight());
            data.put("gender", player.getGender().toString());
            ApiFuture<WriteResult> writeResult = playerRef.update( data);
            writeResult.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }


}
