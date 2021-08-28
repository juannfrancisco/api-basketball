package cl.ranto.basketballpro.api.controllers;


import cl.ranto.basketballpro.api.core.Player;
import cl.ranto.basketballpro.api.core.Team;
import cl.ranto.basketballpro.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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





}