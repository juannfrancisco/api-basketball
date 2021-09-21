package cl.ranto.basketballpro.api.repositories;


import cl.ranto.basketballpro.api.core.Team;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeamRepository extends FirestoreReactiveRepository<Team> {

    Mono<Team> findByNameURL(String nameURL);
    Flux<Team> findByOidChampionship(String oidChampionship);
}
