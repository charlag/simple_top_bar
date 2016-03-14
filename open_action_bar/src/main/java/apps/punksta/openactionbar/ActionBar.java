package apps.punksta.openactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by punksta on 13.01.16.
 */
public class ActionBar extends RelativeLayout implements
        IActionBar,
        View.OnClickListener {
    private final ImageView menu;
    private final TextView title;
    private final ImageView appIcon;
    private final LinearLayout actionsLayout;
    private final LinearLayout titleLayout;
    private final List<View> actions;

    private Styles.Gravity gravity;
    private Styles.ViewType viewType;
    private Action.OnActionClickListener listener;

    private boolean paintDrawableActionsToTitleColor;

    {
        actions = new ArrayList<>();
        paintDrawableActionsToTitleColor = true;
    }

    private void parseAtr(AttributeSet set, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                set,
                apps.punksta.openactionbar.R.styleable.ActionBar,
                defStyleAttr,
                0);
        try {
            int viewNum = a.getInteger(apps.punksta.openactionbar.R.styleable.ActionBar_ui_mode, 2);
            int gravityNum = a.getInteger(apps.punksta.openactionbar.R.styleable.ActionBar_title_gravity, 0);

            Styles.ViewType viewType = Styles.ViewType.values()[viewNum];
            Styles.Gravity gravity = Styles.Gravity.values()[gravityNum];

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
        return actionsLayout;
    }

    @Override
    public void setGravity(Styles.Gravity gravity) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (gravity) {
            case left:
                params.addRule(RelativeLayout.END_OF, apps.punksta.openactionbar.R.id.action_bar_menu);
                params.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.START_OF, R.id.action_bar_actions);
                params.width = title.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case center:
                params.removeRule(RelativeLayout.END_OF);
                params.removeRule(RelativeLayout.START_OF);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                params.width = title.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;

                int maxWidth = Math.max(actionsLayout.getWidth(), menu.getWidth());
                params.leftMargin = params.rightMargin = maxWidth;

                break;
        }

        titleLayout.setLayoutParams(params);
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
        appIcon.setLayoutParams(imageParams);
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
        if (paintDrawableActionsToTitleColor)
            paintActions(color);
    }

    private void paintActions(int color) {
        for (View view: actions) {
            if (view.getTag() instanceof DrawableActon) {
                Drawable d = ((ImageView)view).getDrawable();
                if (d != null)
                    d.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public final void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    public ActionBar(Context context) {
        super(context);
        init();
        menu = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_menu);
        title = (TextView) findViewById(apps.punksta.openactionbar.R.id.action_bar_title);
        appIcon = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_app_icon);
        actionsLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_actions);
        titleLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_title_layout);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        menu = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_menu);
        title = (TextView) findViewById(apps.punksta.openactionbar.R.id.action_bar_title);
        actionsLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_actions);
        appIcon = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_app_icon);
        titleLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_title_layout);

        parseAtr(attrs, 0);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        menu = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_menu);
        title = (TextView) findViewById(apps.punksta.openactionbar.R.id.action_bar_title);
        actionsLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_actions);
        appIcon = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_app_icon);
        titleLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_title_layout);

        parseAtr(attrs, defStyleAttr);
    }

    private void init() {
        int margin = (int) getContext().getResources().getDimension(apps.punksta.openactionbar.R.dimen.horizontal_margin);
        setPadding(margin, 0, margin, 0);

        LayoutInflater.from(getContext()).inflate(apps.punksta.openactionbar.R.layout.action_bar_layout, this, true);
        float elevation = pxFromDp(getContext(), 4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(elevation);
        }
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void setActions(List<? extends Action> buttons, boolean sameColorWithTitle) {
        this.actions.clear();
        this.actionsLayout.removeAllViews();
        List<View> result = ActionBuilder.fillLayout(getContext(), buttons, actionsLayout);
        actions.addAll(result);
        for (View v: result) {
            v.setOnClickListener(this);
        }
        if (sameColorWithTitle)
            paintActions(title.getCurrentTextColor());
    }

    @Override
    public View getView(Action action) {
       for (View view : actions) {
           if (view.getTag().equals(action))
               return view;
       }
        throw new IllegalArgumentException(action + " is not added to actions");
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(getContext(), "onClick"+v.getId(), Toast.LENGTH_LONG).show();
        Action action = (Action) v.getTag();
        if (listener != null && action != null) {
            listener.onClick(v, action);
        }
    }

    public void setListener(Action.OnActionClickListener listener) {
        this.listener = listener;
    }

    public boolean isPaintDrawableActionsToTitleColor() {
        return paintDrawableActionsToTitleColor;
    }

    public void setPaintDrawableActionsToTitleColor(boolean paintDrawableActionsToTitleColor) {
        this.paintDrawableActionsToTitleColor = paintDrawableActionsToTitleColor;
    }
}
