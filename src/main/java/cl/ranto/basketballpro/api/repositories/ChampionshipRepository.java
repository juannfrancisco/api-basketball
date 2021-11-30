package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.Championship;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface ChampionshipRepository extends FirestoreReactiveRepository<Championship> {

    Flux<Championship> findByState(String state );
}
