package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.services.GameService;
import cl.ranto.basketballpro.api.services.GameStatServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService service;

    @Autowired
    private GameStatServices StatService;


    @GetMapping( "/{oid}" )
    public ResponseEntity<GameDTO> findById( @PathVariable("oid") String oid ){
        try{
            return new ResponseEntity<>(
                   // service.findById(oid),
                    HttpStatus.OK);
      //  }catch (ObjectNotFoundException ex){
       //     return new ResponseEntity<>(
       //             HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deleteById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @PutMapping
    public ResponseEntity<GameDTO> save(@RequestBody GameDTO game){
        try{
            return new ResponseEntity<>(
                    //service.save(game),
                    HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public GameDTO update( @RequestBody GameDTO game){
        return service.update(game);
    }



    @GetMapping("/{oid}/scoreboard")
    public ResponseEntity<List<ScoreboardItem>> findScoreboard(@PathVariable("oid") String oid ){
        try{
            return new ResponseEntity<>(
     //               service.getScoreboard(oid),
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{oid}/stats-player")
    public ResponseEntity<List<GameStatPlayer>> findStatsPlayer(@PathVariable("oid") String oid ){
        try{
            return new ResponseEntity<>(
                    //service.getGameStatsPlayer(oid),
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{oid}/stats-player")
    public ResponseEntity<Object> calculateStats(@PathVariable("oid") String oid ){
        try{
            //service.calculateStats(oid);
            return new ResponseEntity<>(
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{oid}/stats")
    public List<GameStat> findStatsByGame(@PathVariable("oid") String oid ){
        return null; //service.getGameStats( oid );
    }

    @PutMapping("/{oid}/stats")
    public ResponseEntity<GameStat> save(@PathVariable("oid") String oid, @RequestBody GameStat stat){
        try{
            return new ResponseEntity<>(
                    //service.addStat(oid,stat),
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{oid}/state")
    public ResponseEntity<Object> updateState(@PathVariable("oid") String oid ,  @RequestBody Game game){
        try{
            //service.updateState(game);
            return new ResponseEntity<>(
                    HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
