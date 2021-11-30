package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ChampionshipService {

    private final static Logger logger = LoggerFactory.getLogger(ChampionshipService.class);

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private FirestoreTemplate firestoreTemplate;

    @Autowired
    private GameService gameService;



    public Flux<Championship> listAll(){
        return repository.findAll();
    }

    public Flux<Championship> listAllByState(String state){
        return repository.findByState(state);
    }

    public Championship findById( String oid ){
        return repository.findById( oid).block();
    }

    public void deleteById( String oid ){
        logger.info( Constants.LOG_CHAMPIONSHIP, oid );
        repository.deleteById(oid).block();
    }

    /**
     *
     * @param championship
     * @return
     */
    public Championship save(Championship championship){
        //championship.setOid(UUID.randomUUID().toString());
        championship.setState( ChampionshipState.PENDING );
        repository.save(championship).block();
        return championship;
    }


    public Championship update(Championship championship){
        repository.save(championship).block();
        return championship;
    }

    public List<Team> findTeamsByChampionship(Championship championship) {
        logger.info(Constants.LOG_CHAMPIONSHIP, championship.getOid() );
        throw new UnsupportedOperationException();
    }

    public List<ChampionshipTeam> findTeamsStatsByChampionship(Championship championship) {
        logger.info(Constants.LOG_CHAMPIONSHIP, championship.getOid() );
        throw new UnsupportedOperationException();
    }


    /**
     *
     * @param championship
     * @return
     * @throws ServicesException
     */
    public List<GameDTO> findGamesByChampionship(Championship championship) throws ServicesException {
        try {
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference championshipRef = db.collection(Constants.COLLECTION_CHAMPIONSHIPS).document(championship.getOid());
            List<GameDTO> games = new ArrayList<>();
            ApiFuture<QuerySnapshot> qs = db.collection( Constants.COLLECTION_GAMES).whereEqualTo( "championship", championshipRef ).get();
            for (DocumentSnapshot document : qs.get().getDocuments()) {
                Game game = document.toObject(Game.class);
                games.add( new GameDTO( game ) );
            }
            return games;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al obtener la informacion", e );
        }
    }

    public void addTeam(String oid, Team team) {
        logger.info(Constants.LOG_CHAMPIONSHIP, oid );
        logger.info("team OID {} ", team.getOid() );
        throw new UnsupportedOperationException();
    }
}
