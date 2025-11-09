package com.okkomastudio.arkalanoix.android.ui.compose

import android.content.Context
import androidx.compose.runtime.Composable
import com.okkomastudio.arkalanoix.core.IControllableScene

class GameWinUICompose(
    context: Context,
    gameScene: IControllableScene
) : UIPanelCompose(context, "GameWinUI", gameScene) {

    @Composable
    override fun PanelContent() {
        GameWinUI(
            onContinueClick = {
                hide()
                gameScene.nextLevel()
            }
        )
    }
}

