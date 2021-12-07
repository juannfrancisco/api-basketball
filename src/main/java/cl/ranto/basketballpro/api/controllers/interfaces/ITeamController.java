package cl.ranto.basketballpro.api.controllers.interfaces;
import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.dto.GameDTO;
import cl.ranto.basketballpro.api.dto.GameTeamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RequestMapping("/api/v1/teams")
public interface ITeamController {

    @GetMapping
    Flux<Team> listAll();

    @GetMapping("/{oid}")
    ResponseEntity<Team> findById(
            @PathVariable("oid") String oid );

    @RequestMapping(method = RequestMethod.DELETE, value="/{oid}")
    void deteleById(
            @PathVariable("oid") String oid );

    @PutMapping
    ResponseEntity<Team> save(
            @RequestBody Team team);

    @PostMapping
    ResponseEntity<Team> update(
            @RequestBody Team team);


    @GetMapping("/{oid}/last-game")
    @CrossOrigin("*")
    ResponseEntity<GameDTO> findLastGame(
            @PathVariable("oid") String oidTeam ,
            @RequestParam( name ="oidChampionship", required = true) String oidChampionship);


    @GetMapping("/{oid}/next-game")
    @CrossOrigin("*")
    ResponseEntity<GameDTO> findNextGame(
            @PathVariable("oid") String oidTeam ,
            @RequestParam( name ="oidChampionship", required = true) String oidChampionship);


        @RequestMapping(method = RequestMethod.PUT,value="/{oid}/players" )
        void addPlayerTeam(
                @PathVariable("oid") String oid,
                @RequestBody Player player);

        @GetMapping("/{oid}/players")
        Flux<Player> findAllPlayers(
                @PathVariable("oid") String oid);


        @GetMapping("/{oid}/games")
        @CrossOrigin("*")
        ResponseEntity<List<GameTeamDTO>> findGamesByID(
                @PathVariable("oid") String oid,
                @RequestParam(required = false) String state);


        @GetMapping("/n/{name}")
        @CrossOrigin("*")
        ResponseEntity<Team> findByNameURL(
                @PathVariable("name") String name );

        @RequestMapping(method = RequestMethod.GET, value="/n/{name}/last-match")
        @CrossOrigin("*")
        Game findLastGameByName(
                @PathVariable("name") String name );

        @GetMapping("/n/{name}/players")
        @CrossOrigin("*")
        ResponseEntity<List<Player>>  findPlayersByNameURL(
                @PathVariable("name") String name );

        @GetMapping("/n/{name}/championships")
        @CrossOrigin("*")
        ResponseEntity<List<Championship>> findChampionshipsByNameURL(
                @PathVariable("name") String name );

        @GetMapping("/n/{name}/standingsL")
        @CrossOrigin("*")
        ResponseEntity<List<List<ChampionshipTeam>>> findAllStandingsByNameURL(
                @PathVariable("name") String name );

        @GetMapping("/n/{name}/standings")
        @CrossOrigin("*")
        ResponseEntity<List<ChampionshipTeam>> findStandingsByNameURL(
                @PathVariable("name") String name );

        @GetMapping("/n/{name}/games")
        @CrossOrigin("*")
        ResponseEntity<List<GameTeamDTO>> findGamesByNameURL(
                @PathVariable("name") String name );

    }

