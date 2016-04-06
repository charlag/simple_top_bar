# SimpleTopBar
alternative to action bar with a simple changing of title, logo, action buttons.


Public funstions placed in an interface
```
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
     * Set title and app logo gravity. Can be placed on left or center of action bar
     * @param titleGravity
     */
    void setTitleGravity(Styles.TitleGravity titleGravity);

    /**
     * Set view type. Changes of visibility of app logo, title, menu
     * @param viewType
     */
    void setViewType(Styles.ViewType viewType);

    /**
     * Set up action buttons
     * @param actionButtons buttons
     * @param sameColorWithTitle accept color filter to drawables 
     */
    void setActions(List<? extends Action> actionButtons, boolean sameColorWithTitle);

    /**
     * Returns view of action
     * @param action
     * @return @Nullable view with action
     */
    View getView(Action action);

    /**
     * Returns current view type
     * @return viewType
     */
    Styles.ViewType getViewType();

    /**
     * Returns current titleGravity
     * @return titleGravity
     */
    Styles.TitleGravity getTitleGravity();

}
```

```
public class Styles {
    /**
     * Gravity of logo and title in TopBar
     */
    public enum TitleGravity {
        left, center
    }

    /**
     * Visibility state of icon, title, menu
     * menuIcon: only menu and icon is visible
     *
     */
    public enum ViewType {
        icon, title, iconTitle, menuIcon, menuTitle, menuIconTitle
    }
}
```
