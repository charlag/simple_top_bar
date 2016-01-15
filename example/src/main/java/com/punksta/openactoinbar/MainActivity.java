package com.punksta.openactoinbar;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import apps.punksta.openactionbar.Action;
import apps.punksta.openactionbar.ActionBar;
import apps.punksta.openactionbar.CustomViewAction;
import apps.punksta.openactionbar.DrawableActon;
import apps.punksta.openactionbar.Styles;

public class MainActivity extends AppCompatActivity implements Action.OnActionClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rnd = new Random();
        Drawable drawable = getResources().getDrawable(android.R.drawable.star_on);

        LinearLayout layout = (LinearLayout) findViewById(R.id.conteiner);

        List<Action> actionList = Arrays.asList(new DrawableActon(1, drawable, "1"),
                new CustomViewAction(2, "2", R.layout.sample_view));

        for (Styles.Gravity gravity: Styles.Gravity.values())
            for (Styles.ViewType viewType : Styles.ViewType.values()) {
                ActionBar actionBar = new ActionBar(this);
                actionBar.setViewType(viewType);
                actionBar.setGravity(gravity);
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                int titleColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                actionBar.setBackgroundColor(color);
                actionBar.setTitleColor(titleColor);
                actionBar.setActions(actionList, true);
                actionBar.setListener(this);
                layout.addView(actionBar);
            }
    }

    @Override
    public void onClick(View view, Action action) {
        view.animate().rotationYBy(900).setDuration(5000).start();
    }
}
