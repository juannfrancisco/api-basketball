package cl.ranto.basketballpro.api.repositories;

import cl.ranto.basketballpro.api.core.Game;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends FirestoreReactiveRepository<Game> {

    Flux<Game> findByChampionship(DocumentReference championship);
}
