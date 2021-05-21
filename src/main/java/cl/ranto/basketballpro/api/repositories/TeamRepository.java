package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.Team;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface TeamRepository extends FirestoreReactiveRepository<Team> {
}
