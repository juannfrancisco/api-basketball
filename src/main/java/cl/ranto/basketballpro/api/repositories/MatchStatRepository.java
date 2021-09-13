package cl.ranto.basketballpro.api.repositories;



import cl.ranto.basketballpro.api.core.MatchStat;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface MatchStatRepository extends FirestoreReactiveRepository<MatchStat> {
}
