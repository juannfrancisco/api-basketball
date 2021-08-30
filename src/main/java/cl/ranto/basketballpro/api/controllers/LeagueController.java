package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.core.League;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.services.CourtService;
import cl.ranto.basketballpro.api.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {

    @Autowired
    private LeagueService service;

    @RequestMapping(method = RequestMethod.GET)
    public Flux<League> listAll(){
        return service.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}")
    public League findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public League save( @RequestBody League league){
        return service.save(league);
    }

    @RequestMapping(method = RequestMethod.POST)
    public League update( @RequestBody League league){
        return service.update(league);
    }

}
