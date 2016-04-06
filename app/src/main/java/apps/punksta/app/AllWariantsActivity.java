package apps.punksta.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Arrays;

import apps.punksta.openactionbar.Action;
import apps.punksta.openactionbar.SimpleTopBar;
import apps.punksta.openactionbar.DrawableActon;
import apps.punksta.openactionbar.Styles;

/**
 * Created by punksta on 06.04.16.
 */
public class AllWariantsActivity extends AppCompatActivity {

    private static final int[] colors = new int[] {
            Color.parseColor("#c2185b"),
            Color.parseColor("#03a9f4"),
            Color.parseColor("#512da8"),
            Color.parseColor("#ffc107"),
            Color.parseColor("#795548"),
            Color.parseColor("#d32f2f")
    };
    private static final int[] background_colors = new int[] {
            Color.parseColor("#03a9f4"),
            Color.parseColor("#c2185b"),
            Color.parseColor("#ffc107"),
            Color.parseColor("#512da8"),
            Color.parseColor("#d32f2f"),
            Color.parseColor("#795548")
    };
    private int colorIndex = 0;
    private int backgroundIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        scrollView.addView(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Action actions[][] = new Action[][]{
                new Action[0],
//                new Action[]{
//                        new DrawableActon(1, android.R.drawable.btn_plus, "info")
//                }
        };

        for (Styles.ViewType viewType : new Styles.ViewType[]{Styles.ViewType.icon, Styles.ViewType.menuIcon, Styles.ViewType.title, Styles.ViewType.iconTitle, Styles.ViewType.menuTitle, Styles.ViewType.menuIconTitle})
            for (Styles.TitleGravity titleGravity : Styles.TitleGravity.values())
                for (Action[] actionList : actions) {
                    SimpleTopBar simpleTopBar = new SimpleTopBar(this);
                    simpleTopBar.setTitleColor(getColorTitle());
                    simpleTopBar.setBackgroundColor(getBackgroundColor());
                    simpleTopBar.setActions(Arrays.asList(actionList), false);
                    simpleTopBar.setViewType(viewType);
                    simpleTopBar.setTitleGravity(titleGravity);
                    simpleTopBar.getTitle().setText(titleGravity.name() + " " + viewType.name());
                    simpleTopBar.getAppIcon().setImageResource(android.R.drawable.ic_dialog_info);
                    linearLayout.addView(simpleTopBar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
                        {
                            bottomMargin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                        }
                    });
                }

        setContentView(scrollView);
    }

    private int getBackgroundColor() {
        return background_colors[backgroundIndex ++ % background_colors.length];
    }

    private int getColorTitle() {
        return Color.WHITE;
    }
}
