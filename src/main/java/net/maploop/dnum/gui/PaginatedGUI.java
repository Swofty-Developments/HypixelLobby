package net.maploop.dnum.gui;

public abstract class PaginatedGUI extends GUI {
    public PaginatedGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    protected int page = 0;
    protected int maxItemsPerPage = 45;
    protected int index = 0;

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}
