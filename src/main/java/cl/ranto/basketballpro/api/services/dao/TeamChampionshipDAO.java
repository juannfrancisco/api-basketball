package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.refereences.TeamChampionship;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TeamChampionshipDAO {


    private static final Logger LOGGER = LoggerFactory.getLogger(TeamChampionshipDAO.class);

    @Autowired
    private CourtRepository repository;


    /**
     *
     * @param championship
     * @return
     */
    public List<TeamChampionship> listAll(Championship championship){
        List<TeamChampionship> teams = new ArrayList<>();
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        DocumentReference championshipRef = db.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
        CollectionReference teamsCollection = championshipRef.collection( Constants.COLLECTION_TEAMS );
        teamsCollection.listDocuments().forEach( documentReference -> {
            try{
                TeamChampionship teamChampionship = documentReference.get().get().toObject(TeamChampionship.class);
                teams.add(teamChampionship);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return teams;
    }

    public TeamChampionship findById( Championship championship, String oid ){
        return null;
    }

    public void deleteById( Championship championship, String oid ){
        throw new UnsupportedOperationException();
    }

    public TeamChampionship save(TeamChampionship teamChampionship){
        throw new UnsupportedOperationException();
    }

    public TeamChampionship update(TeamChampionship teamChampionship){
        throw new UnsupportedOperationException();
    }
}
