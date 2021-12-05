package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Player;
import cl.ranto.basketballpro.api.services.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    @Autowired
    private PlayerService service;

    private final static Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    @GetMapping
    public Flux<Player> listAll() {
        return service.listAll();
    }

    @GetMapping("/{oid}")
    public Player findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }

    @DeleteMapping("/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @PostMapping
    public ResponseEntity<Player> update( @RequestBody Player player){

        try{
            service.update(player);
            return new ResponseEntity<>(
                    HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
