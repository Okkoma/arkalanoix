package com.okkomastudio.arkalanoix.android;

import com.okkomastudio.arkalanoix.core.IUIPanel;

import android.os.Looper;
import android.os.Handler;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public abstract class UIPanelAndroid extends FrameLayout implements IUIPanel {

    private String name_;
    private int state_ = 0;

    protected GameSceneAndroid gameScene_;

    private static Handler mainHandler_;

    public UIPanelAndroid(Context context, String name, GameSceneAndroid gameScene) {
        super(context);
        gameScene_ = gameScene;
        name_ = name;

        // Make the panel focusable
        setFocusable(true);
        setFocusableInTouchMode(true);

        // Create a Handler on the main thread (for ui update)
        if (mainHandler_ == null) {
            mainHandler_ = new Handler(Looper.getMainLooper());
        }

        // TODO : ajouter un overlay fond alpha noir
        set();

        hide();
    }

    public abstract void set();

    @Override
    public void show() {
        mainHandler_.post(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.VISIBLE);
                bringToFront();

                // Centrer le menu sur l'écran
                if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                    params.gravity = Gravity.CENTER;
                    setLayoutParams(params);
                }

                boolean focus = requestFocus();
            }
        });
        System.out.printf("GameUI : %s show.\n", name_);
    }

    @Override
    public void hide() {
        mainHandler_.post(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
                gameScene_.requestFocus();
            }
        });
        System.out.printf("GameUI : %s hide.\n", name_);
    }

    @Override
    public int getState() {
        return state_;
    }

    @Override
    public void setState(int state) {
        state_ = state;
    }
}
