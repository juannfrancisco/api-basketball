package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.core.MatchStat;
import cl.ranto.basketballpro.api.services.GameStatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/matchstats")
public class GameStatController {

	@Autowired
	private GameStatServices service;

	@RequestMapping(method = RequestMethod.GET, value="/{oid}")
	public MatchStat findById(@PathVariable("oid") String oid ){
		return service.findById(oid);
	}

	@RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
	public void deteleById( @PathVariable("oid") String oid ){
		service.deleteById(oid);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void save( @RequestBody MatchStat stat){
		service.save(stat);
	}

}
