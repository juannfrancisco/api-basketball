package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.Court;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface CourtRepository extends FirestoreReactiveRepository<Court> {
}
