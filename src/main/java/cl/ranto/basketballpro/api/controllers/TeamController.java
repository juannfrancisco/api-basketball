package cl.ranto.basketballpro.api.controllers;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {


    @Autowired
    private TeamService service;

    @RequestMapping(method = RequestMethod.GET)
    public Flux<Team> listAll(){
        return service.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}")
    public Team findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Team save(@RequestBody Team team){
        return service.save(team);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Team update( @RequestBody Team team){
        return service.update(team);
    }

    @RequestMapping(method = RequestMethod.PUT,value="/{oid}/players" )
    public void addPlayerTeam(@PathVariable("oid") String oid, @RequestBody Player player){
        player.setOidCurrentTeam(oid);
        service.addPlayer(player);
    }


    @RequestMapping(method = RequestMethod.GET,value="/{oid}/players" )
    public Flux<Player> findAllPlayers(@PathVariable("oid") String oid){
        return service.findAllPlayers(oid);
    }





    @RequestMapping(method = RequestMethod.GET, value="/n/{name}")
    @CrossOrigin("*")
    public Team findByNameURL( @PathVariable("name") String name ){
        return service.findByName(name);
    }


    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/players")
    @CrossOrigin("*")
    public List<Player> findPlayersByNameURL(@PathVariable("name") String name ){
        return service.findPlayersByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/championships")
    @CrossOrigin("*")
    public  List<Championship> findChampionshipsByNameURL(@PathVariable("name") String name ){
        return service.findChampionshipsByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/standingsL")
    @CrossOrigin("*")
    public  List<List<ChampionshipTeam>> findAllStandingsByNameURL(@PathVariable("name") String name ){
        return service.findAllStandingsByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/standings")
    @CrossOrigin("*")
    public List<ChampionshipTeam> findStandingsByNameURL(@PathVariable("name") String name ){
        return service.findStandingsByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/matches")
    @CrossOrigin("*")
    public List<Game> findMatchesByNameURL(@PathVariable("name") String name ){
        return service.findMatchesByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/last-match")
    @CrossOrigin("*")
    public Game findLastMatch(@PathVariable("name") String name ){
        return service.findLastMatch(name);
    }


    /**
    @RequestMapping(method = RequestMethod.GET, value="/n/{name}/statistics")
    @CrossOrigin("*")
    public TeamStatistics findStatisticsLastSeason(@PathVariable("name") String name ){
        return service.findStatisticsLastSeason(name);
    }
    */


}