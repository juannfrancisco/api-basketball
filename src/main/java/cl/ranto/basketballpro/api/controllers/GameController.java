package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Game;
import cl.ranto.basketballpro.api.core.GameStat;
import cl.ranto.basketballpro.api.core.MatchStat;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.services.GameService;
import cl.ranto.basketballpro.api.services.GameStatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    @Autowired
    private GameService service;

    @Autowired
    private GameStatServices StatService;

    @RequestMapping(method = RequestMethod.GET)
    public List<GameDTO> listAll(){
        return service.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}")
    public GameDTO findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deleteById( @PathVariable("oid") String oid ){
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

    @RequestMapping(method = RequestMethod.GET, value="/{oid}/stats")
    public List<GameStat> findStatsByGame(@PathVariable("oid") String oid ){
        return service.getGameStats( oid );
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{oid}/stats")
    public GameStat save(@PathVariable("oid") String oid, @RequestBody GameStat stat){
        try{
            return service.addStat(oid,stat);
        }catch (Exception ex){
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.POST, value="/{oid}/state")
    public void updateState(@PathVariable("oid") String oid ,  @RequestBody Game game){
        service.updateState(game);
    }
}
