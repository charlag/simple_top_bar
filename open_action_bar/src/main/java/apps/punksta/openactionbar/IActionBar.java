package apps.punksta.openactionbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by punksta on 15.01.16.
 */
public interface IActionBar {

    /**
     * Returns left menu icon view
     * @return left menu icon view
     */
    ImageView getMenuIcon();

    /**
     * Returns app logo view
     * @return app logo view
     */
    ImageView getAppIcon();

    /**
     * Returns app title view
     * @return app title view
     */
    TextView getTitle();

    /**
     * Returns layout of action buttons
     * @return layout of action buttons
     */
    LinearLayout getActionLayout();


    /**
     * Set color of title
     * @param color
     */
    void setTitleColor(int color);

    /**
     * Set background color of title
     * @param color
     */
    void setBackgroundColor(int color);

    /**
     * Set up action buttons
     * @param actionButtons buttons
     * @param sameColorWithTitle accept color filter to drawables
     */
    void setActions(List<? extends Action> actionButtons, boolean sameColorWithTitle);

    /**
     * Returns view of action
     * @param action
     * @return Nullable view with action
     */
    View getView(Action action);

    /**
     * Returns current view type
     * @return viewType
     */
    Styles.ViewType getViewType();

    /**
     * Set view type. Changes of visibility of app logo, title, menu
     * @param viewType
     */
    void setViewType(Styles.ViewType viewType);

    /**
     * Returns current titleGravity
     * @return titleGravity
     */
    Styles.TitleGravity getTitleGravity();

    /**
     * Set title and app logo gravity. Can be placed on left or center of action bar
     * @param titleGravity
     */
    void setTitleGravity(Styles.TitleGravity titleGravity);

}
