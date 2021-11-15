package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
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
public class TeamController {


    private final static Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService service;

    @RequestMapping(method = RequestMethod.GET)
    public Flux<Team> listAll(){
        return service.listAll();
    }

    @GetMapping("/{oid}")
    public ResponseEntity<Team> findById( @PathVariable("oid") String oid ){

        try {
            return new ResponseEntity<>(
                    service.findById(oid),
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


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Team save(@RequestBody Team team){
        return service.save(team);
    }

    @PostMapping
    public ResponseEntity<Team> update(@RequestBody Team team){
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

    @RequestMapping(method = RequestMethod.PUT,value="/{oid}/players" )
    public void addPlayerTeam(@PathVariable("oid") String oid, @RequestBody Player player){
        player.setOidCurrentTeam(oid);
        service.addPlayer(player);
    }

    @GetMapping("/{oid}/players")
    public Flux<Player> findAllPlayers(@PathVariable("oid") String oid){
        return service.findAllPlayers(oid);
    }

    @GetMapping("/n/{name}")
    @CrossOrigin("*")
    public ResponseEntity<Team> findByNameURL( @PathVariable("name") String name ){
        try {
            return new ResponseEntity<>(
                    service.findByName(name),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/n/{name}/players")
    @CrossOrigin("*")
    public ResponseEntity<List<Player>>  findPlayersByNameURL(@PathVariable("name") String name ){
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

    @GetMapping("/n/{name}/championships")
    @CrossOrigin("*")
    public  ResponseEntity<List<Championship>> findChampionshipsByNameURL(@PathVariable("name") String name ){
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

    @GetMapping("/n/{name}/standingsL")
    @CrossOrigin("*")
    public  ResponseEntity<List<List<ChampionshipTeam>>> findAllStandingsByNameURL(@PathVariable("name") String name ){
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

    @GetMapping("/n/{name}/standings")
    @CrossOrigin("*")
    public ResponseEntity<List<ChampionshipTeam>> findStandingsByNameURL(@PathVariable("name") String name ){
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

    @GetMapping("/n/{name}/matches")
    @CrossOrigin("*")
    public ResponseEntity<List<Game>> findMatchesByNameURL(@PathVariable("name") String name ){

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

    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/last-match")
    @CrossOrigin("*")
    public Game findLastMatch(@PathVariable("name") String name ){
        return service.findLastMatch(name);
    }


}