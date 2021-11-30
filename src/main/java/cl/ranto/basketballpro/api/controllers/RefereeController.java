package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Referee;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.services.RefereeService;
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
@RequestMapping("/api/v1/referees")
public class RefereeController {

    @Autowired
    private RefereeService service;

    @GetMapping()
    public Flux<Referee> listAll(){
        return service.listAll();
    }

    @GetMapping("/{oid}")
    public ResponseEntity<Referee> findById(@PathVariable("oid") String oid ){
        try {
            return new ResponseEntity<>(
                    service.findById(oid),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Referee save( @RequestBody Referee referee){
        return service.save(referee);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Referee update( @RequestBody Referee referee){
        return service.update(referee);
    }

}
