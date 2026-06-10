package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MatchRecord implements Serializable, Searchable {
    private static final long serialVersionUID = 1L;

    private String matchId;
    private String date;
    private String teamA;
    private String teamB;
    private String winner;
    private MatchResult result;
    private Map<String, String> heroPicks;

    public MatchRecord() {
        this.heroPicks = new HashMap<>();
    }

    public MatchRecord(String matchId, String date, String teamA, String teamB, String winner) {
        this.matchId = matchId;
        this.date = date;
        this.teamA = teamA;
        this.teamB = teamB;
        this.winner = winner;
        this.heroPicks = new HashMap<>();
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Map<String, String> getHeroPicks() {
        return heroPicks;
    }

    public void setHeroPicks(Map<String, String> heroPicks) {
        this.heroPicks = heroPicks;
    }

    @Override
    public boolean matches(String keyword) {
        String kw = keyword.toLowerCase();
        if (matchId.toLowerCase().contains(kw)) return true;
        if (teamA.toLowerCase().contains(kw)) return true;
        if (teamB.toLowerCase().contains(kw)) return true;
        if (winner.toLowerCase().contains(kw)) return true;
        return false;
    }

    @Override
    public String toString() {
        return "MatchRecord{matchId='" + matchId + "', date='" + date + "', teamA='" + teamA + "', teamB='" + teamB + "', winner='" + winner + "'}";
    }
}
