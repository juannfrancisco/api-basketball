package cl.ranto.basketballpro.api.services.dao;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
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
public class GameDAO {

    @Autowired
    private Firestore firestore;

    public Game findById(String oid, Championship championship) throws ServicesException {
        try{
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            DocumentReference gameRef = championshipRef.collection( Constants.COLLECTION_GAMES ).document( oid );
            return gameRef.get().get().toObject( Game.class );
        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param championship
     * @return
     * @throws ServicesException
     */
    public List<Game> findAllGamesByChampionship(Championship championship) throws ServicesException {
        try{
            List<Game> games = new ArrayList<>();
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            CollectionReference gamesCollection = championshipRef.collection( Constants.COLLECTION_GAMES );
            for( DocumentReference ref :  gamesCollection.listDocuments() ){
                games.add( ref.get().get().toObject(Game.class) );
            }
            return games;
        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException(e.getMessage(), e);
        }
    }



    /**
     *
     * @param championship
     * @return
     * @throws ServicesException
     */
    public List<Game> findAllGamesByState( GameState state, Championship championship) throws ServicesException {
        try{
            List<Game> games = new ArrayList<>();
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            Query query = championshipRef.collection( Constants.COLLECTION_GAMES ).whereEqualTo("state", state );

            for (DocumentSnapshot document : query.get().get().getDocuments()){
                games.add( document.toObject(Game.class) );
            }
            return games;
        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param championship
     * @param game
     * @return
     * @throws ServicesException
     */
    public Game save(Championship championship, Game game) throws ServicesException {
        try{
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            ApiFuture<WriteResult> result = championshipRef.collection(Constants.COLLECTION_GAMES).document(game.getOid()).set(game);
            result.get().getUpdateTime();
            return game;

        }catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException(e.getMessage(), e);
        }
    }


    public void deleteById(String oid) {
        throw new UnsupportedOperationException();
    }


    /**
     *
     * @param game
     */
    public void updateState(Game game, Championship championship) throws ServicesException {
        try{
            DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document( championship.getOid() );
            DocumentReference gameRef = championshipRef.collection(Constants.COLLECTION_GAMES).document(game.getOid());
            Map<String, Object> data = new HashMap<>();
            data.put("state", GameState.FINALIZED.toString());
            data.put("localScore", game.getLocalScore());
            data.put("visitorScore", game.getVisitorScore());
            ApiFuture<WriteResult> writeResult = gameRef.update( data);
            writeResult.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al guardar la informacion", e );
        }
    }




    /**
     *
     * @param game
     * @return
     */
    public GameDTO objectToDTO(Game game){

        GameDTO gameDTO = new GameDTO();
        gameDTO.setOid( game.getOid() );
        gameDTO.setLocalScore( game.getLocalScore() );
        gameDTO.setVisitorScore( game.getVisitorScore() );
        gameDTO.setState( game.getState() );
        gameDTO.setDate( game.getDate() );
        gameDTO.setChampionship( new Championship(game.getChampionship().getId()) );
        gameDTO.setCourt( new CourtDTO( game.getCourt().getId()  ));
        gameDTO.setVisitor( new Team( game.getVisitor().getId() ) );
        gameDTO.setLocal( new Team( game.getLocal().getId() ) );
        return gameDTO;
    }

    /**
     *
     * @return
     */
    public Game DTOtoObject(GameDTO dto){

        DocumentReference visitorRef = this.firestore.collection( Constants.COLLECTION_TEAMS ).document(dto.getVisitor().getOid());
        DocumentReference localRef = this.firestore.collection( Constants.COLLECTION_TEAMS ).document(dto.getLocal().getOid());
        DocumentReference courtRef = this.firestore.collection( Constants.COLLECTION_COURTS ).document(dto.getCourt().getOid());
        DocumentReference championshipRef = this.firestore.collection( Constants.COLLECTION_CHAMPIONSHIPS ).document(dto.getChampionship().getOid());
        Game game = new Game();
        game.setOid( dto.getOid() );
        game.setChampionship( championshipRef );
        game.setCourt( courtRef );
        game.setVisitor( visitorRef );
        game.setLocal( localRef );
        game.setDate( dto.getDate() );
        game.setState( GameState.PENDING );
        game.setStats( new ArrayList<>() );
        return game;
    }

}
