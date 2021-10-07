package cl.ranto.basketballpro.api.repositories;

import cl.ranto.basketballpro.api.core.Player;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface PlayerRepository extends FirestoreReactiveRepository<Player> {
    Flux<Player> findByOidCurrentTeam(String oid );
}
