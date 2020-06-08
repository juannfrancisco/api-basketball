package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.services.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@RestController
@RequestMapping("/api/v1/courts")
public class CourtController {


    @Autowired
    private CourtService service;



    @RequestMapping(method = RequestMethod.GET)
    public Flux<Court> listAll(){
        return service.listAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{oid}")
    public Court findById( @PathVariable("oid") String oid ){
        return service.findById(oid);
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void save( @RequestBody CourtDTO court){
        service.save(court);
    }
}
