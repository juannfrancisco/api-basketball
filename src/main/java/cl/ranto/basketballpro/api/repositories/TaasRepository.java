package cl.ranto.basketballpro.api.repositories;

import cl.ranto.basketballpro.api.core.Taas;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface TaasRepository extends FirestoreReactiveRepository<Taas> {
}
