package net.swofty.lobby.gui;

public abstract class PaginatedGUI extends GUI {

    protected int page = 0;
    protected int maxItemsPerPage = 45;
    protected int index = 0;

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}
