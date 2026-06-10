package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable, Searchable {
    private static final long serialVersionUID = 1L;

    private String teamId;
    private String name;
    private List<String> memberIds;
    private int totalMatches;
    private int wins;

    public Team() {
        this.memberIds = new ArrayList<>();
    }

    public Team(String teamId, String name) {
        this.teamId = teamId;
        this.name = name;
        this.memberIds = new ArrayList<>();
        this.totalMatches = 0;
        this.wins = 0;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public boolean matches(String keyword) {
        String kw = keyword.toLowerCase();
        if (teamId.toLowerCase().contains(kw)) return true;
        if (name.toLowerCase().contains(kw)) return true;
        for (String member : memberIds) {
            if (member.toLowerCase().contains(kw)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Team{teamId='" + teamId + "', name='" + name + "', totalMatches=" + totalMatches + ", wins=" + wins + "}";
    }
}
