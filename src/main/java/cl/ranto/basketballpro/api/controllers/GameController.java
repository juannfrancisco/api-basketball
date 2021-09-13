package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Championship;
import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.services.ChampionshipService;
import cl.ranto.basketballpro.api.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@RestController
@RequestMapping("/api/v1/matches")
public class GameController {


    @Autowired
    private GameService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<GameDTO> listAll(){
        return service.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}")
    public GameDTO findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public GameDTO save(@RequestBody GameDTO game){
        return service.save(game);
    }

    @RequestMapping(method = RequestMethod.POST)
    public GameDTO update( @RequestBody GameDTO game){
        return service.update(game);
    }



}
