package com.okkomastudio.arkalanoix.core;

public interface IGameUI {

    IUIPanel getGameOverPanel();
    IUIPanel getGameWinPanel();
    IUIPanel getGameMenuPanel();

    void setContainer(Object container);
}
