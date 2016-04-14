package apps.punksta.openactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by punksta on 13.01.16.
 */
public class SimpleTopBar extends RelativeLayout implements
        IActionBar,
        View.OnClickListener {
    private final ImageView menu;
    private final TextView title;
    private final ImageView appIcon;
    private final LinearLayout actionsLayout;
    private final LinearLayout titleLayout;
    private final List<View> actions;

    private Styles.TitleGravity titleGravity;
    private Styles.ViewType viewType;
    private Action.OnActionClickListener listener;
    private int horisontalMargin;
    private boolean paintDrawableActionsToTitleColor;

    private int maxIconWidth;
    private int maxIconWidthWithoutTitle;

    {
        actions = new ArrayList<>();
        paintDrawableActionsToTitleColor = true;
    }

    public SimpleTopBar(Context context) {
        super(context);
        init();
        menu = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_menu);
        title = (TextView) findViewById(apps.punksta.openactionbar.R.id.action_bar_title);
        appIcon = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_app_icon);
        actionsLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_actions);
        titleLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_title_layout);
    }

    public SimpleTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        menu = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_menu);
        title = (TextView) findViewById(apps.punksta.openactionbar.R.id.action_bar_title);
        actionsLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_actions);
        appIcon = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_app_icon);
        titleLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_title_layout);

        parseAtr(attrs, 0);
    }

    public SimpleTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        menu = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_menu);
        title = (TextView) findViewById(apps.punksta.openactionbar.R.id.action_bar_title);
        actionsLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_actions);
        appIcon = (ImageView) findViewById(apps.punksta.openactionbar.R.id.action_bar_app_icon);
        titleLayout = (LinearLayout) findViewById(apps.punksta.openactionbar.R.id.action_bar_title_layout);

        parseAtr(attrs, defStyleAttr);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    private void parseAtr(AttributeSet set, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                set,
                apps.punksta.openactionbar.R.styleable.SimpleTopBar,
                defStyleAttr,
                0);
        try {
            int viewNum = a.getInteger(apps.punksta.openactionbar.R.styleable.SimpleTopBar_ui_mode, 2);
            int gravityNum = a.getInteger(apps.punksta.openactionbar.R.styleable.SimpleTopBar_title_gravity, 0);

            Styles.ViewType viewType = Styles.ViewType.values()[viewNum];
            Styles.TitleGravity titleGravity = Styles.TitleGravity.values()[gravityNum];

            setTitleGravity(titleGravity);
            setViewType(viewType);

            int logoRes = a.getResourceId(R.styleable.SimpleTopBar_logo_res, 0);
            int menuColor = a.getColor(R.styleable.SimpleTopBar_menu_color, Color.BLACK);
            int titleColor = a.getColor(R.styleable.SimpleTopBar_menu_color, Color.BLACK);

            if (logoRes != 0)
                appIcon.setImageResource(logoRes);

            menu.setColorFilter(menuColor, PorterDuff.Mode.SRC_ATOP);
            title.setTextColor(titleColor);

            String titleStr = a.getString(R.styleable.SimpleTopBar_title_text);

            if (titleStr != null)
                title.setText(titleStr);
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

    void updateMargins() {
        if (titleGravity == null)
            return;
        LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) appIcon.getLayoutParams();
        RelativeLayout.LayoutParams params = (LayoutParams) titleLayout.getLayoutParams();
        params.leftMargin = 0;

        int margin = horisontalMargin;
        switch (titleGravity) {
            case left:
                imageParams.setMarginEnd(margin);
                if (menu.getVisibility() == GONE) {
                    params.leftMargin = horisontalMargin;
                }
                break;
            case center:
                if (title.getVisibility() == GONE)
                    imageParams.setMarginEnd(0);
                else
                    imageParams.setMarginEnd(margin);
        }
        appIcon.setLayoutParams(imageParams);
        titleLayout.setLayoutParams(params);
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

    private void init() {
        horisontalMargin = (int) getContext().getResources().getDimension(R.dimen.open_action_bar_horizontal_margin);
        maxIconWidth = (int) getContext().getResources().getDimension(R.dimen.open_action_bar_image_max_width);
        maxIconWidthWithoutTitle = (int) getContext().getResources().getDimension(R.dimen.open_action_bar_image_max_width_without_title);


        LayoutInflater.from(getContext()).inflate(apps.punksta.openactionbar.R.layout.action_bar_layout, this, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float elevation = pxFromDp(getContext(), 4);
            setElevation(elevation);
        }
    }

    public void setMaxIconWidthWithoutTitle(int maxIconWidthWithoutTitle) {
        this.maxIconWidthWithoutTitle = maxIconWidthWithoutTitle;
    }

    public void setMaxIconWidth(int maxIconWidth) {
        this.maxIconWidth = maxIconWidth;
    }

    public void setHorisontalMargin(int horisontalMargin) {
        this.horisontalMargin = horisontalMargin;
    }

    @Override
    public void setActions(List<? extends Action> buttons, boolean sameColorWithTitle) {
        this.actions.clear();
        this.actionsLayout.removeAllViews();
        List<View> result = ActionBuilder.fillLayout(getContext(), buttons, actionsLayout);
        actions.addAll(result);
        for (View v : result) {
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
    public Styles.ViewType getViewType() {
        return viewType;
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
        if (appIcon.getVisibility() == VISIBLE && title.getVisibility() == VISIBLE) {
            appIcon.getLayoutParams().width = (maxIconWidth);
        } else {
            appIcon.getLayoutParams().width = (maxIconWidthWithoutTitle);
        }
        this.viewType = viewType;
        updateMargins();
    }

    @Override
    public Styles.TitleGravity getTitleGravity() {
        return titleGravity;
    }

    @Override
    public void setTitleGravity(Styles.TitleGravity titleGravity) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        switch (titleGravity) {
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
        this.titleGravity = titleGravity;
        updateMargins();
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
