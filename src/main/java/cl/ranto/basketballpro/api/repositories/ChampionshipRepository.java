package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.Championship;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface ChampionshipRepository extends FirestoreReactiveRepository<Championship> {
}
