package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.GameRepository;
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
import java.util.UUID;
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

    public Championship findById( String oid ){
        return repository.findById( oid).block();
    }

    public void deleteById( String oid ){
        logger.info("championship OID : " + oid );
        repository.deleteById(oid).block();
    }

    public Championship save(Championship championship){
        championship.setOid(UUID.randomUUID().toString());
        repository.save(championship).block();
        return championship;
    }


    public Championship update(Championship championship){
        repository.save(championship).block();
        return championship;
    }

    public List<Team> findTeamsByChampionship(Championship championship) {
        return new ArrayList<>();
    }

    public List<ChampionshipTeam> findTeamsStatsByChampionship(Championship championship) {
        return new ArrayList<>();
    }

    public List<GameDTO> findMatchesByChampionship(Championship championship) {
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        DocumentReference championshipRef = db.collection("championships").document(championship.getOid());

        List<GameDTO> games = new ArrayList<>();

        ApiFuture<QuerySnapshot> qs = db.collection("games").whereEqualTo( "championship", championshipRef ).get();
        try {
            QuerySnapshot gamesSnapshot = qs.get();
            List<QueryDocumentSnapshot> gamesDoc = gamesSnapshot.getDocuments();
            games.add( gameService.findById(gamesDoc.get(0).getId()) );

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //return gameRepository.findByChampionship( championshipRef ).collectList().block();
        return games;
    }

    public void addTeam(String oid, Team team) {
    }
}
