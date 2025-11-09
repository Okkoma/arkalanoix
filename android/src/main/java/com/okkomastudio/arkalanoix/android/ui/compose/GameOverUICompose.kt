package com.okkomastudio.arkalanoix.android.ui.compose

import com.okkomastudio.arkalanoix.core.IControllableScene

import android.content.Context
import androidx.compose.runtime.Composable

class GameOverUICompose(
    context: Context,
    gameScene: IControllableScene
) : UIPanelCompose(context, "GameOverUI", gameScene) {

    @Composable
    override fun PanelContent() {
        GameOverUI(
            onReplayClick = {
                hide()
                gameScene.restartGame()
            },
            onQuitClick = {
                if (context is android.app.Activity) {
                    (context as android.app.Activity).finish()
                }
                System.exit(0)
            }
        )
    }
}

