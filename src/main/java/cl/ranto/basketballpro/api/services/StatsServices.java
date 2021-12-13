package cl.ranto.basketballpro.api.services;

import cl.ranto.basketballpro.api.core.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatsServices {

    public Map<Integer, ScoreboardItem> calculateScoreboard(List<GameStat> stats){
        Map<Integer,ScoreboardItem> mapScoreboard = new HashMap<>();
        stats.forEach( (stat) -> {
            if(!mapScoreboard.containsKey( stat.getQuarter() ) ){
                mapScoreboard.put( stat.getQuarter(), new ScoreboardItem( 0, 0, stat.getQuarter().toString() ) );
            }
            ScoreboardItem scoreboardItem = mapScoreboard.get( stat.getQuarter() );
            if (  stat.getType().equals( TypeStat.PTS )){
                if(stat.getTypeTeam().equals(TypeTeam.LOCAL)){
                    scoreboardItem.setLocalPoints((int) (scoreboardItem.getLocalPoints() + stat.getValue()));
                }
                else{
                    scoreboardItem.setVisitorPoints((int) (scoreboardItem.getVisitorPoints() + stat.getValue()));
                }
            }
        });
        return mapScoreboard;
    }

    public List<TeamStat> calculateTeamStats(List<GameStat> stats){
        List<TeamStat> teamStats = new ArrayList<>();
        TeamStat local = new TeamStat();
        TeamStat visitor = new TeamStat();
        stats.forEach( (stat) -> {
            if(stat.getTypeTeam().equals(TypeTeam.LOCAL)){
                stat.getType().addTeamStat( local, (int) stat.getValue() );
                local.setOidTeam( stat.getTeamOid() );
            }else{
                stat.getType().addTeamStat( visitor, (int) stat.getValue() );
                visitor.setOidTeam( stat.getTeamOid() );
            }
        });
        teamStats.add(local);
        teamStats.add(visitor);
        return teamStats;
    }

    public Map<String,GameStatPlayer> calculateGameStatsPlayer(List<GameStat> stats){
        Map<String,GameStatPlayer> map = new HashMap<>();
        stats.forEach( (stat) -> {
            if(!map.containsKey( stat.getOidPlayer() ) ){
                map.put( stat.getOidPlayer(), new GameStatPlayer( stat.getOidPlayer(), stat.getOidPlayer(), stat.getTeamOid() ) );
            }
            GameStatPlayer statPlayer = map.get(stat.getOidPlayer());
            stat.getType().addGameStatPlayer( statPlayer, (int) stat.getValue() );
        });
        return map;
    }

    public HiStatPlayer findMaxPoints( List<GameStatPlayer> gameStatPlayers, String oidTeam){
        GameStatPlayer statPlayer = gameStatPlayers.stream()
                .filter( gameStatPlayer ->  gameStatPlayer.getOidTeam().equals( oidTeam ) )
                .max(Comparator.comparing(GameStatPlayer::getPTS))
                .get();
        HiStatPlayer hiStatPlayer = null;
        if(statPlayer.getPTS() != 0){
            hiStatPlayer = new HiStatPlayer(statPlayer.getOidPlayer(), TypeStat.PTS, statPlayer.getPTS() );
        }
        return hiStatPlayer;

    }

    public HiStatPlayer findMaxAssist( List<GameStatPlayer> gameStatPlayers, String oidTeam){
        GameStatPlayer statPlayer = gameStatPlayers.stream()
                .filter( gameStatPlayer ->  gameStatPlayer.getOidTeam().equals( oidTeam ) )
                .max(Comparator.comparing(GameStatPlayer::getAST))
                .get();
        HiStatPlayer hiStatPlayer = null;
        if(statPlayer.getAST() != 0){
            hiStatPlayer = new HiStatPlayer(statPlayer.getOidPlayer(), TypeStat.AST, statPlayer.getAST() );
        }
        return hiStatPlayer;
    }

    public HiStatPlayer findMaxRebounds( List<GameStatPlayer> gameStatPlayers, String oidTeam){
        GameStatPlayer statPlayer = gameStatPlayers.stream()
                .filter( gameStatPlayer ->  gameStatPlayer.getOidTeam().equals( oidTeam ) )
                .map(  gameStatPlayer -> {
                    gameStatPlayer.setREB( gameStatPlayer.getOR() + gameStatPlayer.getDR() );
                    return gameStatPlayer;
                })
                .max(Comparator.comparing(GameStatPlayer::getREB))
                .get();
        HiStatPlayer hiStatPlayer = null;
        if(statPlayer.getOR() + statPlayer.getDR() != 0){
            hiStatPlayer = new HiStatPlayer(statPlayer.getOidPlayer(), TypeStat.REB, statPlayer.getOR() + statPlayer.getDR() );
        }
        return hiStatPlayer;
    }

}
