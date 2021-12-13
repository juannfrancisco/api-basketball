package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.GameStatPlayer;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class GameStatsPlayerDAO {

    @Autowired
    private Firestore firestore;

    private static final String COLLECTION_STATS_PLAYER = "statsPlayer";


    public GameStatPlayer addStatPlayer(String oidGame, Championship championship, GameStatPlayer stat) throws ServicesException {
        try {
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
            stat.setOid(UUID.randomUUID().toString());
            ApiFuture<WriteResult> result = gameRef.collection( COLLECTION_STATS_PLAYER ).document(stat.getOidPlayer()).set(stat);
            result.get().getUpdateTime();
            return stat;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }


    public List<GameStatPlayer> getGameStatsPlayer(String oidGame, Championship championship ){
        List<GameStatPlayer> stats = new ArrayList<>();
        DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
        DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
        CollectionReference gameStats = gameRef.collection( COLLECTION_STATS_PLAYER );
        gameStats.listDocuments().forEach( documentReference -> {
            try{
                GameStatPlayer gameStat = documentReference.get().get().toObject(GameStatPlayer.class);
                stats.add(gameStat);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return stats;
    }
}
