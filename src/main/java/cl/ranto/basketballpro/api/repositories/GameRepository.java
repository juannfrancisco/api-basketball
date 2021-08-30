package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.Game;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends FirestoreReactiveRepository<Game> {

    Flux<Game> findByChampionship(Championship championship);
}
