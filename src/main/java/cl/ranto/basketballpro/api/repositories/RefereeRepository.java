package cl.ranto.basketballpro.api.repositories;

import cl.ranto.basketballpro.api.core.Referee;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface RefereeRepository extends FirestoreReactiveRepository<Referee> {
}
