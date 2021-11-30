package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.GameState;
import cl.ranto.basketballpro.api.core.Team;
import cl.ranto.basketballpro.api.core.TypeTeam;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.core.refereences.GameTeam;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class GameTeamDAO {

    @Autowired
    private Firestore firestore;



    public void addGameRef( Game game , Team local, Team visitor) throws ServicesException {

        try {
            DocumentReference gameRef = this.firestore.collection(Constants.COLLECTION_GAMES).document(game.getOid());
            GameTeam gameLocal = new GameTeam(game.getOid(), gameRef, game.getDate(), TypeTeam.LOCAL, visitor.getName(), GameState.PENDING);
            GameTeam gameVisitor = new GameTeam(game.getOid(), gameRef, game.getDate(), TypeTeam.VISITOR, local.getName(), GameState.PENDING);

            ApiFuture<DocumentReference> refLocal = game.getLocal().collection( Constants.COLLECTION_GAMES ).add(gameLocal);
            ApiFuture<DocumentReference> refVisitor = game.getVisitor().collection( Constants.COLLECTION_GAMES ).add(gameVisitor);
            refLocal.get().getId();
            refVisitor.get().getId();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }


}
