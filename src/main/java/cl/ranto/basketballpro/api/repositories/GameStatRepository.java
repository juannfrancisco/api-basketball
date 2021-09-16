package cl.ranto.basketballpro.api.repositories;



import cl.ranto.basketballpro.api.core.MatchStat;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface GameStatRepository extends FirestoreReactiveRepository<MatchStat> {
}
