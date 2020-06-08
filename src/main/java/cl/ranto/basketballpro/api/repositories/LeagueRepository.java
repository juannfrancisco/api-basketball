package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.League;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface LeagueRepository extends FirestoreReactiveRepository<League> {
}
