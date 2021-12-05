package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.ScoreboardItem;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ScoreboardDAO {

    @Autowired
    private Firestore firestore;


    /**
     *
     * @param oidGame
     * @param championship
     * @return
     */
    public List<ScoreboardItem> getScoreboard(String oidGame, Championship championship ){

        DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
        DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
        CollectionReference scoreboard = gameRef.collection( "scoreboard" );
        List<ScoreboardItem> scoreboardItems = new ArrayList<>();
        scoreboard.listDocuments().forEach( documentReference -> {
            try{
                ScoreboardItem scoreboardItem = documentReference.get().get().toObject(ScoreboardItem.class);
                scoreboardItems.add(scoreboardItem);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return scoreboardItems;
    }


    /**
     *
     * @param oidGame
     * @param championship
     * @param scoreboardItem
     * @return
     * @throws ServicesException
     */
    public ScoreboardItem addScoreboardItem(String oidGame, Championship championship, ScoreboardItem scoreboardItem) throws ServicesException {
        try {
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
            ApiFuture<DocumentReference> ref = gameRef.collection( "scoreboard" ).add(scoreboardItem);
            scoreboardItem.setOid( ref.get().getId() );
            return scoreboardItem;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }
}
