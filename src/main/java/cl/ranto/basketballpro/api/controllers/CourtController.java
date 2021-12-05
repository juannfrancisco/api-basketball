package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.dto.CourtDTO;
import cl.ranto.basketballpro.api.services.CourtService;
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
@RequestMapping("/api/v1/courts")
public class CourtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourtController.class);

    @Autowired
    private CourtService service;




    @GetMapping
    public List<Court> listAll(){
        return service.listAll();
    }

    @GetMapping("/{oid}")
    public ResponseEntity<Court>   findById(@PathVariable("oid") String oid ){
        try {
            return new ResponseEntity<>(
                    service.findById(oid),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            LOGGER.error(e.getMessage(),e);
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    public void deteleById( @PathVariable("oid") String oid ){
        service.deleteById(oid);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public CourtDTO save( @RequestBody CourtDTO court){
        return service.save(court);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CourtDTO update( @RequestBody CourtDTO court){
        return service.update(court);
    }

}
