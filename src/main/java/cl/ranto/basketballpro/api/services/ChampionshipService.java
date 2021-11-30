package cl.ranto.basketballpro.api.services;


import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ServicesException;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.repositories.GameRepository;
import cl.ranto.basketballpro.api.services.dao.ChampionshipDAO;
import cl.ranto.basketballpro.api.services.dao.GameDAO;
import cl.ranto.basketballpro.api.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChampionshipService {

    private final static Logger logger = LoggerFactory.getLogger(ChampionshipService.class);


    @Autowired
    private ChampionshipDAO championshipDAO;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;



    public List<Championship> listAll(){
        return championshipDAO.listAll();
    }

    public List<Championship> listAllByState(String state){
        if( null == state || state.isEmpty() ){
            return this.listAll();
        }else{
            return championshipDAO.listAllByState(state);
        }
    }

    public Championship findById( String oid ){
        return championshipDAO.findById( oid);
    }

    public void deleteById( String oid ){
        championshipDAO.deleteById(oid);
    }

    /**
     *
     * @param championship
     * @return
     */
    public Championship save(Championship championship){
        championship.setState( ChampionshipState.PENDING );
        championshipDAO.save(championship);
        return championship;
    }


    public Championship update(Championship championship) throws ServicesException {
        return championshipDAO.update(championship);
    }

    public List<Team> findTeamsByChampionship(Championship championship) {
        logger.info(Constants.LOG_CHAMPIONSHIP, championship.getOid() );
        throw new UnsupportedOperationException();
    }

    public List<ChampionshipTeam> findTeamsStatsByChampionship(Championship championship) {
        logger.info(Constants.LOG_CHAMPIONSHIP, championship.getOid() );
        throw new UnsupportedOperationException();
    }


    /**
     *
     * @param championship
     * @return
     * @throws ServicesException
     */
    public List<GameDTO> findGamesByChampionship(Championship championship) throws ServicesException {
        List<GameDTO> dtos = new ArrayList<>();
        List<Game> games = gameDAO.findAllGamesByChampionship(championship);
        for(Game game: games){
            dtos.add( new GameDTO(game) );
        }
        return dtos;
    }

    public void addTeam(String oid, Team team) {
        logger.info(Constants.LOG_CHAMPIONSHIP, oid );
        logger.info("team OID {} ", team.getOid() );
        throw new UnsupportedOperationException();
    }
}
