package apps.punksta.openactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by punksta on 13.01.16.
 */
public class ActionBar extends RelativeLayout implements IActionBar {
    private final ImageView menu;
    private final TextView title;
    private final ImageView appIcon;
    private final LinearLayout actions;
    private final LinearLayout titleLayout;
    private Styles.Gravity gravity;
    private Styles.ViewType viewType;


    private void parseAtr(AttributeSet set, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                set,
                R.styleable.ActionBar,
                defStyleAttr,
                0);
        try {
            int viewNum = a.getInteger(R.styleable.ActionBar_ui_mode, 2);
            int gravityNum = a.getInteger(R.styleable.ActionBar_title_gravity, 0);

            Styles.ViewType viewType = Styles.ViewType.values()[viewNum];
            Styles.Gravity gravity =  Styles.Gravity.values()[gravityNum];

            setGravity(gravity);
            setViewType(viewType);
        } finally {
            a.recycle();
        }
    }

    @Override
    public ImageView getMenuIcon() {
        return menu;
    }

    @Override
    public ImageView getAppIcon() {
        return appIcon;
    }

    @Override
    public TextView getTitle() {
        return title;
    }

    @Override
    public LinearLayout getActionLayout() {
        return actions;
    }

    @Override
    public void setGravity(Styles.Gravity gravity) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleLayout.getLayoutParams();
        switch (gravity) {
            case left:
                params.addRule(RelativeLayout.END_OF, R.id.action_bar_menu);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                break;
            case center:
                params.addRule(RelativeLayout.END_OF, 0);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                break;
        }
        this.gravity = gravity;
        updateMargins();
    }

    void updateMargins() {
        if (gravity == null)
            return;
        LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) appIcon.getLayoutParams();
        int margin = (int) getContext().getResources().getDimension(R.dimen.horizontal_margin);
        switch (gravity) {
            case left:
                imageParams.setMarginEnd(margin);
                break;
            case center:
                if (title.getVisibility() == GONE)
                    imageParams.setMarginEnd(0);
                else
                    imageParams.setMarginEnd(margin);
        }
    }

    public void setViewType(Styles.ViewType viewType) {
        switch (viewType) {
            case iconTitle:
                appIcon.setVisibility(VISIBLE);
                menu.setVisibility(GONE);
                title.setVisibility(VISIBLE);
                break;
            case icon:
                appIcon.setVisibility(VISIBLE);
                menu.setVisibility(GONE);
                title.setVisibility(GONE);
                break;
            case menuTitle:
                appIcon.setVisibility(GONE);
                menu.setVisibility(VISIBLE);
                title.setVisibility(VISIBLE);
                break;
            case menuIcon:
                appIcon.setVisibility(VISIBLE);
                menu.setVisibility(VISIBLE);
                title.setVisibility(GONE);
                break;
            case menuIconTitle:
                appIcon.setVisibility(VISIBLE);
                menu.setVisibility(VISIBLE);
                title.setVisibility(VISIBLE);
                break;
            case title:
                appIcon.setVisibility(GONE);
                menu.setVisibility(GONE);
                title.setVisibility(VISIBLE);
                break;
        }
        this.viewType = viewType;
        updateMargins();
    }

    @Override
    public void setTitleColor(int color) {
        title.setTextColor(color);
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        menu.setColorFilter(colorFilter);
    }

    @Override
    public final void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    public ActionBar(Context context) {
        super(context);
        init();
        menu = (ImageView) findViewById(R.id.action_bar_menu);
        title = (TextView) findViewById(R.id.action_bar_title);
        appIcon = (ImageView) findViewById(R.id.action_bar_app_icon);
        actions = (LinearLayout) findViewById(R.id.action_bar_actions);
        titleLayout = (LinearLayout) findViewById(R.id.action_bar_title_layout);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        menu = (ImageView) findViewById(R.id.action_bar_menu);
        title = (TextView) findViewById(R.id.action_bar_title);
        actions = (LinearLayout) findViewById(R.id.action_bar_actions);
        appIcon = (ImageView) findViewById(R.id.action_bar_app_icon);
        titleLayout = (LinearLayout) findViewById(R.id.action_bar_title_layout);

        parseAtr(attrs, 0);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        menu = (ImageView) findViewById(R.id.action_bar_menu);
        title = (TextView) findViewById(R.id.action_bar_title);
        actions = (LinearLayout) findViewById(R.id.action_bar_actions);
        appIcon = (ImageView) findViewById(R.id.action_bar_app_icon);
        titleLayout = (LinearLayout) findViewById(R.id.action_bar_title_layout);

        parseAtr(attrs, defStyleAttr);
    }

    private void init() {
        int margin = (int) getContext().getResources().getDimension(R.dimen.horizontal_margin);
        setPadding(margin, 0, margin, 0);

        LayoutInflater.from(getContext()).inflate(R.layout.action_bar, this, true);
        float elevation = pxFromDp(getContext(), 4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(elevation);
        }
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
