package com.punksta.openactoinbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.Random;

import apps.punksta.openactionbar.ActionBar;
import apps.punksta.openactionbar.Styles;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rnd = new Random();

        LinearLayout layout = (LinearLayout) findViewById(R.id.conteiner);
        for (Styles.Gravity gravity: Styles.Gravity.values())
            for (Styles.ViewType viewType : Styles.ViewType.values()) {

                ActionBar actionBar = new ActionBar(this);
                actionBar.setViewType(viewType);
                actionBar.setGravity(gravity);


                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                int titleColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                actionBar.setBackgroundColor(color);
                actionBar.setTitleColor(titleColor);

                layout.addView(actionBar);

            }
    }
}
