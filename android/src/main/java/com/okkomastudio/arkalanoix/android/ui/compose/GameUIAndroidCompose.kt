package com.okkomastudio.arkalanoix.android.ui.compose

import com.okkomastudio.arkalanoix.core.IControllableScene
import com.okkomastudio.arkalanoix.core.IGameUI
import com.okkomastudio.arkalanoix.core.IUIPanel

import android.view.ViewGroup
import android.content.Context

import android.util.Log

class GameUIAndroidCompose(
    context: Context,
    gameScene: IControllableScene
) : IGameUI {

    private val gameOver: GameOverUICompose
    private val gameWin: GameWinUICompose
    private val gameMenu: GameMenuUICompose

    init {
        gameOver = GameOverUICompose(context, gameScene)
        Log.i("GameUIAndroidCompose", "init gameOver")
        gameWin = GameWinUICompose(context, gameScene)
        Log.i("GameUIAndroidCompose", "init gameWin")
        gameMenu = GameMenuUICompose(context, gameScene)
        Log.i("GameUIAndroidCompose", "init gameMenu")

        // Configuration des layouts pour qu'ils prennent toute la place
        setupPanelLayout(gameOver)
        setupPanelLayout(gameWin)
        setupPanelLayout(gameMenu)
    }

    private fun setupPanelLayout(panel: UIPanelCompose) {
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        panel.layoutParams = params
    }

    private fun hideAllPanels() {
        gameOver.hide()
        gameWin.hide()
        gameMenu.hide()
    }

    override fun setContainer(viewgroup: Any) {
        val container: ViewGroup? = viewgroup as? ViewGroup
        if (container != null) {
            container.addView(gameOver)
            container.addView(gameWin)
            container.addView(gameMenu)
        }
        hideAllPanels()
    }

    override fun getGameOverPanel(): IUIPanel = gameOver

    override fun getGameWinPanel(): IUIPanel = gameWin

    override fun getGameMenuPanel(): IUIPanel = gameMenu
}

