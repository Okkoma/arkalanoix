package com.okkomastudio.arkalanoix.android.ui.compose

import com.okkomastudio.arkalanoix.core.IControllableScene

import android.content.Context
import androidx.compose.runtime.Composable

class GameMenuUICompose(
    context: Context,
    gameScene: IControllableScene
) : UIPanelCompose(context, "GameMenuUI", gameScene) {

    @Composable
    override fun PanelContent() {
        GameMenuUI(
            state = getState(),
            onStartClick = {
                hide()
                if (getState() == 0) {
                    gameScene.restartGame()
                } else {
                    gameScene.togglePause()
                }
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

