package net.swofty.lobby.manager;

public interface HypixelPlayer {
    String getRank();

    String getRankPrefix();

    String getRankColour();

    void sendMessage(String s);

    void setRank(String rank);

    void addXP(int XP);
}
