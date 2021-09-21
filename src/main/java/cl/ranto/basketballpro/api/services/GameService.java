package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.ChampionshipRepository;
import cl.ranto.basketballpro.api.repositories.CourtRepository;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import cl.ranto.basketballpro.api.repositories.TeamRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class GameService {

    private final static Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private FirestoreTemplate firestoreTemplate;


    public List<GameDTO> listAll(){
        List<Game> games = repository.findAll().collectList().block();
        List<GameDTO> gamesDTO = new ArrayList<>();
        games.forEach( game -> {
            gamesDTO.add( this.toDTO( game ) );

        });
        return gamesDTO;
    }

    public List<GameDTO> findByChampionship(String oidChampionship){

        List<GameDTO> gamesDTO = new ArrayList<>();
        try
        {
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            Query query = db.collection("games").whereEqualTo( "championship", oidChampionship );
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                document.toObject(Game.class);
            }
        }
        catch (Exception ex){

        }
        return gamesDTO;
    }

    public GameDTO findById( String oid ){
        Game game =repository.findById( oid).block();
        Championship championship = championshipRepository.findById( game.getChampionship().getId() ).block();
        Court court = courtRepository.findById( game.getCourt().getId() ).block();
        Team teamVisitor = teamRepository.findById( game.getVisitor().getId() ).block();
        Team  teamLocal = teamRepository.findById( game.getLocal().getId() ).block();
        GameDTO gameDTO = new GameDTO(game, new CourtDTO(court), championship, teamLocal ,teamVisitor );
        return gameDTO;
    }

    public GameDTO toDTO(Game game){
        GameDTO gameDTO = new GameDTO(game);
        gameDTO.setChampionship( championshipRepository.findById( game.getChampionship().getId() ).block() );
        gameDTO.setCourt( new CourtDTO( courtRepository.findById( game.getCourt().getId() ).block() ));
        gameDTO.setVisitor( teamRepository.findById( game.getVisitor().getId() ).block() );
        gameDTO.setLocal( teamRepository.findById( game.getLocal().getId() ).block() );
        return gameDTO;
    }

    public void deleteById( String oid ){
        logger.info("championship OID : " + oid );
        repository.deleteById(oid).block();
    }

    public GameDTO save(GameDTO game){
        Firestore db= FirestoreOptions.getDefaultInstance().getService();
        DocumentReference visitorRef = db.collection("teams").document(game.getVisitor().getOid());
        DocumentReference localRef = db.collection("teams").document(game.getLocal().getOid());
        DocumentReference courtRef = db.collection("courts").document(game.getCourt().getOid());
        DocumentReference championshipRef = db.collection("championships").document(game.getChampionship().getOid());

        Game gameDocument = new Game();
        gameDocument.setChampionship( championshipRef );
        gameDocument.setCourt( courtRef );
        gameDocument.setVisitor( visitorRef );
        gameDocument.setLocal( localRef );
        gameDocument.setOid(UUID.randomUUID().toString());
        gameDocument.setDate( game.getDate() );
        gameDocument.setState( GameState.PENDING );
        repository.save(gameDocument).block();
        return game;
    }


    public GameDTO update(GameDTO game){

        return game;
    }


    public void updateState(Game game) {

        try{
            Firestore db= FirestoreOptions.getDefaultInstance().getService();
            DocumentReference gameRef = db.collection("games").document(game.getOid());
            Map<String, Object> data = new HashMap<>();
            data.put("state", GameState.FINALIZED.toString());
            data.put("scoreLocal", game.getScoreLocal());
            data.put("scoreVisitor", game.getScoreVisitor());

            //asynchronously update doc
            ApiFuture<WriteResult> writeResult = gameRef.update( data);

            logger.info("Update time : " + writeResult.get().getUpdateTime());
            /**
             Game gameRepo = repository.findById( game.getOid() ).block();
             gameRepo.setScoreLocal( game.getScoreLocal() );
             gameRepo.setScoreVisitor(game.getScoreVisitor());
             gameRepo.setState(GameState.FINALIZED);
             repository.save(gameRepo).block();
             **/

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }
}
