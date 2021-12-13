package cl.ranto.basketballpro.api.services.dao;

import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.GameState;
import cl.ranto.basketballpro.api.core.Team;
import cl.ranto.basketballpro.api.core.TypeTeam;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.core.refereences.GameTeam;
import cl.ranto.basketballpro.api.dto.GameTeamDTO;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class GameTeamDAO {

    @Autowired
    private Firestore firestore;

    public void update( Game game ) throws ServicesException {
        try {
            DocumentReference documentLocalSnapshot = game.getLocal().collection( Constants.COLLECTION_GAMES ).document( game.getOid() );
            DocumentReference documentVisitorSnapshot = game.getVisitor().collection( Constants.COLLECTION_GAMES ).document( game.getOid() );

            Map<String, Object> data = new HashMap<>();
            data.put("state", GameState.FINALIZED.toString());
            data.put("localScore", game.getLocalScore());
            data.put("visitorScore", game.getVisitorScore());

            ApiFuture<WriteResult> writeResultLocal = documentLocalSnapshot.update( data);
            ApiFuture<WriteResult> writeResultVisitor = documentVisitorSnapshot.update( data);

            writeResultLocal.get().getUpdateTime();
            writeResultVisitor.get().getUpdateTime();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }


    public void addGameRef( Game game , Team local, Team visitor) throws ServicesException {
        DocumentReference gameRef = this.firestore.collection(Constants.COLLECTION_GAMES).document(game.getOid());
        GameTeam gameLocal = new GameTeam(game.getOid(), gameRef, game.getDate(), TypeTeam.LOCAL, visitor.getOid(), visitor.getName(), GameState.PENDING);
        GameTeam gameVisitor = new GameTeam(game.getOid(), gameRef, game.getDate(), TypeTeam.VISITOR, local.getOid(), local.getName(), GameState.PENDING);
        this.addGameTeam(local, gameLocal);
        this.addGameTeam(visitor, gameVisitor);
    }


    public void addGameTeam( Team team, GameTeam gameTeam ) throws ServicesException {

        try {
            DocumentReference teamRef = this.firestore.collection( Constants.COLLECTION_TEAMS ).document( team.getOid() );
            ApiFuture<WriteResult> result = teamRef.collection( Constants.COLLECTION_GAMES ).document(gameTeam.getOid()).set( gameTeam );
            result.get().getUpdateTime();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }




    public List<GameTeamDTO> findGames(Team team ) throws ServicesException {
        DocumentReference teamRef = this.firestore.collection( Constants.COLLECTION_TEAMS ).document( team.getOid() );
        ApiFuture<QuerySnapshot> queryGames = teamRef.collection( Constants.COLLECTION_GAMES ).orderBy("date").get();
        return this.getDTOs(queryGames);
    }

    public List<GameTeamDTO> findGamesByState(Team team, GameState state) throws ServicesException {
        DocumentReference teamRef = this.firestore.collection( Constants.COLLECTION_TEAMS ).document( team.getOid() );
        ApiFuture<QuerySnapshot> queryGames = teamRef.collection( Constants.COLLECTION_GAMES ).whereEqualTo( "state", state ).get();
        return this.getDTOs(queryGames);
    }

    /**
     *
     * @param queryGames
     * @return
     */
    public List<GameTeamDTO> getDTOs(ApiFuture<QuerySnapshot> queryGames) throws ServicesException {
        try{
            List<GameTeamDTO> gamesRef = new ArrayList<>();
            for (DocumentSnapshot document : queryGames.get().getDocuments()) {
                GameTeam gameRef = document.toObject(GameTeam.class);
                gamesRef.add( objectToDto(gameRef) );
            }
            return gamesRef;
        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException("", e);
        }

    }

    /**
     *
     * @param gameRef
     * @return
     */
    public GameTeamDTO objectToDto( GameTeam gameRef ){
        GameTeamDTO dto = new GameTeamDTO();
        dto.setOid( gameRef.getOid() );
        dto.setDate( gameRef.getDate());
        dto.setType( gameRef.getType() );
        dto.setState( gameRef.getState() );
        dto.setNameTeam( gameRef.getNameTeam() );
        dto.setGame( gameRef.getGame().getId() );
        dto.setLocalScore( gameRef.getLocalScore() );
        dto.setVisitorScore( gameRef.getVisitorScore() );
        return dto;
    }



}
