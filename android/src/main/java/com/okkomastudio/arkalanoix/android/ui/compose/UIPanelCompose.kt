package com.okkomastudio.arkalanoix.android.ui.compose

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.okkomastudio.arkalanoix.android.Game
import com.okkomastudio.arkalanoix.core.IControllableScene
import com.okkomastudio.arkalanoix.core.IUIPanel

abstract class UIPanelCompose(
    context: Context,
    private val name: String,
    protected val gameScene: IControllableScene
) : FrameLayout(context), IUIPanel {

    private var state: Int = 0
    @Suppress("DEPRECATION")
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private val composeView: ComposeView

    init {

        // Make the FrameLayout focusable
        isFocusable = true
        isFocusableInTouchMode = true

        composeView = ComposeView(context)
        
        // Utiliser une stratégie qui ne dépend pas du ViewTreeLifecycleOwner
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
        
        addView(composeView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        
        set()

        hide()
    }

    @Composable
    protected abstract fun PanelContent()

    override fun set() {
        composeView.setContent {
            PanelContent()
        }
    }

    override fun show() {
        mainHandler.post {
            visibility = View.VISIBLE
            bringToFront()
            requestFocus()
        }
        Log.i("GameUI", "GameUI : $name show.")
    }

    override fun hide() {
        mainHandler.post {
            visibility = View.GONE
            gameScene.focus()
        }
        Log.i("GameUI", "GameUI : $name hide.")
    }

    override fun getState(): Int = state

    override fun setState(state: Int) {
        this.state = state
        // Recomposition needed when state changes
        mainHandler.post {
            set()
        }
    }
}

