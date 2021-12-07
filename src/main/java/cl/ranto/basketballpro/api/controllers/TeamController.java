package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.controllers.interfaces.ITeamController;
import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.dto.GameTeamDTO;
import cl.ranto.basketballpro.api.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController implements ITeamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService service;

    public Flux<Team> listAll(){
        return service.listAll();
    }

    public ResponseEntity<Team> findById( String oid ){
        try {
            return new ResponseEntity<>(
                    service.findByIdPlayers(oid),
                    HttpStatus.OK);
        }
        catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene el ultimo partido jugado del equipo enviado como parametro.
     * @param oidTeam
     * @param oidChampionship
     * @return
     */
    public ResponseEntity<GameDTO> findLastGame(String oidTeam, String oidChampionship ){
        try {
            return new ResponseEntity<>(
                    service.findLastGame(oidTeam, oidChampionship),
                    HttpStatus.OK);
        }
        catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene el proximo partido a disputar por el equipo.
     * @param oidTeam
     * @param oidChampionship
     * @return
     */
    public ResponseEntity<GameDTO> findNextGame(String oidTeam, String oidChampionship ){
        try {
            return new ResponseEntity<>(
                    service.findNextGame(oidTeam, oidChampionship),
                    HttpStatus.OK);
        }
        catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Game findLastGameByName(String name ){
        throw new UnsupportedOperationException();
    }

    public void deteleById( String oid ){
        service.deleteById(oid);
    }

    public ResponseEntity<Team> save( Team team){
        try {
            return new ResponseEntity<>(
                    service.save(team),
                    HttpStatus.OK);
        }
        catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Team> update( Team team){
        try {
            return new ResponseEntity<>(
                    service.update(team),
                    HttpStatus.OK);
        }
        catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void addPlayerTeam( String oid, Player player){
        player.setOidCurrentTeam(oid);
        service.addPlayer(player);
    }

    public Flux<Player> findAllPlayers(String oid){
        return service.findAllPlayers(oid);
    }

    public ResponseEntity<Team> findByNameURL( String name ){
        try {
            return new ResponseEntity<>(
                    service.findByNameURL(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Player>>  findPlayersByNameURL(String name ){
        try {
            return new ResponseEntity<>(
                    service.findPlayersByName(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public  ResponseEntity<List<Championship>> findChampionshipsByNameURL(String name ){
        try {
            return new ResponseEntity<>(
                    service.findChampionshipsByName(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public  ResponseEntity<List<List<ChampionshipTeam>>> findAllStandingsByNameURL(String name ){
        try {
            return new ResponseEntity<>(
                    service.findAllStandingsByName(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public ResponseEntity<List<ChampionshipTeam>> findStandingsByNameURL(String name ){
        try {
            return new ResponseEntity<>(
                    service.findStandingsByName(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<GameTeamDTO>> findGamesByNameURL(String name ){

        try {
            return new ResponseEntity<>(
                    service.findGamesByName(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<GameTeamDTO>> findGamesByID(
            String oid,
            String state
    ){
        try {
            return new ResponseEntity<>(
                    service.findGamesById(oid, state),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}