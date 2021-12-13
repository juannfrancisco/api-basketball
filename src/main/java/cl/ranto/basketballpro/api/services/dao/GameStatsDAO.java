package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.GameStat;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class GameStatsDAO {

    @Autowired
    private Firestore firestore;

    public GameStat save(String oidGame, Championship championship, GameStat stat) throws ServicesException {
        try {
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
            CollectionReference gameStats = gameRef.collection( Constants.COLLECTION_STATS);
            ApiFuture<DocumentReference> ref =gameStats.add(stat);
            stat.setOid( ref.get().getId() );
            return stat;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }

    public List<GameStat> getGameStats(String oidGame, Championship championship){

        List<GameStat> stats = new ArrayList<>();
        DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
        DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
        CollectionReference gameStats = gameRef.collection( Constants.COLLECTION_STATS);
        gameStats.listDocuments().forEach( documentReference -> {
            try{
                GameStat gameStat = documentReference.get().get().toObject(GameStat.class);
                stats.add(gameStat);
            }catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        } );
        return stats;
    }

    public List<GameStat> getGameStatsByQuarter(String oidGame, Championship championship, Integer quarter) throws ServicesException {

        try{
            List<GameStat> stats = new ArrayList<>();
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oidGame );
            ApiFuture<QuerySnapshot> query = gameRef.collection( Constants.COLLECTION_STATS).whereEqualTo("quarter",quarter).get();
            for (DocumentSnapshot document : query.get().getDocuments()) {
                stats.add( document.toObject(GameStat.class) );
            }
            return stats;
        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException("", e);
        }
    }
}
