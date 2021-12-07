package cl.ranto.basketballpro.api.controllers;

import cl.ranto.basketballpro.api.controllers.interfaces.IChampionshipController;
import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.core.exceptions.ObjectNotFoundException;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.services.ChampionshipService;
import cl.ranto.basketballpro.api.services.GameService;
import cl.ranto.basketballpro.api.services.TeamService;
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
@RequestMapping("/api/v1/championships")
public class ChampionshipController implements IChampionshipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionshipController.class);

    @Autowired
    private ChampionshipService service;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;


    public ResponseEntity<List<Championship>> listAll(String state ){
        try {
            return new ResponseEntity<>(
                    service.listAllByState(state),
                    HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Championship> findById( String oid ){
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


    public void deleteById( String oid ){
        service.deleteById(oid);
    }

    public Championship save( Championship championship){
        return service.save(championship);
    }

    public Championship update(Championship championship) {
        try{
            return service.update(championship);
        }
        catch (Exception ex){
            return null;
        }

    }
    public void addTeam( String oid ,Team team){
        service.addTeam(oid, team);
    }

    public List<Team> getTeams(String oid ){
        return teamService.findByChampionship(oid);
    }

    public List<ChampionshipTeam> getTeamsStats(String oid ){
        return service.findTeamsStatsByChampionship(new Championship(oid));
    }

    public List<GameStat> findStatsByGame(String oid, String oidGame ){
        return gameService.getGameStats( oidGame, oid  );
    }

    public List<GameStat> findStatsByGame(String oid, String oidGame , Integer quarter){
        try {
            return gameService.getGameStats( oidGame, oid, quarter  );
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<GameStat> save(String oid,
                                         String oidGame,
                                         GameStat stat){
        try{
            return new ResponseEntity<>(
                    gameService.addStat(oidGame,oid,stat),
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<GameDTO> getGame(
            String oid,
            String oidGame ){
        try {
            return new ResponseEntity<>(
                    gameService.findById(oidGame, oid),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<GameDTO>> getGames(String state, String oid ){
        try {
            return new ResponseEntity<>(
                    service.findGamesByChampionship(new Championship(oid),state),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<GameDTO> save(String oid , GameDTO game){
        try{
            return new ResponseEntity<>(
                    gameService.save( new Championship(oid),  game),
                    HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ScoreboardItem>> findScoreboard(String oidChampionship, String oidGame ){
        try{
            return new ResponseEntity<>(
                    gameService.getScoreboard(oidChampionship, oidGame),
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> updateState(String oid ,
                                              String oidGame,
                                              Game game){
        try{
            gameService.updateState( game , oid);
            return new ResponseEntity<>(
                    HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<GameStatPlayer>> findStatsPlayer(String oid,
                                                                String oidGame ){
        try{
            return new ResponseEntity<>(
                    gameService.getGameStatsPlayer(oidGame, oid),
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> calculateStats(String oid, String oidGame ){
        try{
            gameService.calculateStats(oidGame, oid);
            return new ResponseEntity<>(
                    HttpStatus.OK);

        }catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
