package cl.ranto.basketballpro.api.services.dao;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import cl.ranto.basketballpro.api.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class GameDAO {

    @Autowired
    private Firestore firestore;


    @Autowired
    private GameRepository repository;

    /**
     *
     * @param championship
     * @return
     * @throws ServicesException
     */
    public List<Game> findAllGamesByChampionship(Championship championship) throws ServicesException {
        try {
            DocumentReference championshipRef = this.firestore.collection(Constants.COLLECTION_CHAMPIONSHIPS).document(championship.getOid());
            List<Game> games = new ArrayList<>();
            ApiFuture<QuerySnapshot> qs = this.firestore.collection( Constants.COLLECTION_GAMES).whereEqualTo( "championship", championshipRef ).get();
            for (DocumentSnapshot document : qs.get().getDocuments()) {
                games.add( document.toObject(Game.class) );
            }
            return games;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ServicesException( "Ocurrio un error al obtener la informacion", e );
        }
    }


    /**
     *
     * @param game
     * @return
     */
    public Game save(Game game){
        return repository.save(game).block();
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
