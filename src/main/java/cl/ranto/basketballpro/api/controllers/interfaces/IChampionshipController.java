package cl.ranto.basketballpro.api.controllers.interfaces;

import cl.ranto.basketballpro.api.core.*;
import cl.ranto.basketballpro.api.dto.GameDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/championships")
public interface IChampionshipController {

    @GetMapping
    ResponseEntity<List<Championship>> listAll(
            @RequestParam( name ="state", required = false) String state );

    @GetMapping("/{oid}")
    ResponseEntity<Championship> findById(
            @PathVariable("oid") String oid );

    @DeleteMapping("/{oid}")
    void deleteById(
            @PathVariable("oid") String oid );

    @PutMapping
    Championship save(
            @RequestBody Championship championship);

    @PostMapping
    Championship update(
            @RequestBody Championship championship);

    @PutMapping("/{oid}/teams")
    void addTeam(
            @PathVariable("oid") String oid ,
            @RequestBody Team team);

    @GetMapping("/{oid}/teams")
    List<Team> getTeams(@PathVariable("oid") String oid );

    @GetMapping("/{oid}/teams-stats")
    List<ChampionshipTeam> getTeamsStats(
            @PathVariable("oid") String oid );

    @GetMapping("/{oid}/games/{oidGame}/stats")
    List<GameStat> findStatsByGame(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame );

    @GetMapping("/{oid}/games/{oidGame}/stats/{quarter}")
    List<GameStat> findStatsByGame(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame ,
            @PathVariable("quarter") Integer quarter);

    @PutMapping("/{oid}/games/{oidGame}/stats")
    ResponseEntity<GameStat> save(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame,
            @RequestBody GameStat stat);

    @GetMapping("/{oid}/games")
    ResponseEntity<List<GameDTO>> getGames(
            @RequestParam( name ="state", required = false) String state,
            @PathVariable("oid") String oid);

    @GetMapping("/{oid}/games/{oidGame}")
    ResponseEntity<GameDTO> getGame(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame );

    @PutMapping("/{oid}/games")
    ResponseEntity<GameDTO> save(
            @PathVariable("oid") String oid ,
            @RequestBody GameDTO game);


    @GetMapping("/{oid}/games/{oidGame}/scoreboard")
    ResponseEntity<List<ScoreboardItem>> findScoreboard(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame );


    @PostMapping("/{oid}/games/{oidGame}/state")
    ResponseEntity<Object> updateState(
            @PathVariable("oid") String oid ,
            @PathVariable("oidGame") String oidGame,
            @RequestBody Game game);

    @GetMapping("/{oid}/games/{oidGame}/stats-player")
    ResponseEntity<List<GameStatPlayer>> findStatsPlayer(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame );

    @PostMapping("/{oid}/games/{oidGame}/stats-player")
    ResponseEntity<Object> calculateStats(
            @PathVariable("oid") String oid,
            @PathVariable("oidGame") String oidGame );


    @GetMapping("/{oid}/games/{oidGame}/stats-team")
    ResponseEntity<List<TeamStat>> findTeamStats(
            @PathVariable("oid") String oidChampionship,
            @PathVariable("oidGame") String oidGame );
}
