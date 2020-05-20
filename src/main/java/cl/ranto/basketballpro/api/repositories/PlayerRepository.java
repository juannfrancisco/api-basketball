package cl.ranto.basketballpro.api.repositories;

import cl.ranto.basketballpro.api.core.Player;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface PlayerRepository extends FirestoreReactiveRepository<Player> {
}
