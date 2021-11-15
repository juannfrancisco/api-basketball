package cl.ranto.basketballpro.api.controllers;


import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.ChampionshipTeam;
import cl.ranto.basketballpro.api.core.Team;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.services.ChampionshipService;
import cl.ranto.basketballpro.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@RestController
@RequestMapping("/api/v1/championships")
public class ChampionshipController {

    @Autowired
    private ChampionshipService service;

    @Autowired
    private TeamService teamService;

    @RequestMapping(method = RequestMethod.GET)
    public Flux<Championship> listAll(){
        return service.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}")
    public Championship findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deleteById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Championship save( @RequestBody Championship championship){
        return service.save(championship);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Championship update( @RequestBody Championship championship){
        return service.update(championship);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{oid}/teams")
    public void addTeam( @PathVariable("oid") String oid , @RequestBody Team team){
        service.addTeam(oid, team);
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}/teams")
    public List<Team> getTeams(@PathVariable("oid") String oid ){
        return teamService.findByChampionship(oid);
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}/teams-stats")
    public List<ChampionshipTeam> getTeamsStats(@PathVariable("oid") String oid ){
        return service.findTeamsStatsByChampionship(new Championship(oid));
    }

    @GetMapping("/{oid}/matches")
    public ResponseEntity<List<GameDTO>> getGames(@PathVariable("oid") String oid ){
        try {
            return new ResponseEntity<>(
                    service.findGamesByChampionship(new Championship(oid)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
