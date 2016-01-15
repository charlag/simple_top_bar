# OpenActionBar
alternative action bar with simple dinamic changing


What you can do with action bar
```
public interface IActionBar {

    ImageView getMenuIcon();
    ImageView getAppIcon();
    TextView getTitle();
    LinearLayout getActionLayout();

    
    void setTitleColor(int color);
    void setBackgroundColor(int color);

    void setGravity(Styles.Gravity gravity);
    void setViewType(Styles.ViewType viewType);
}
```

You can customize in dynamic gravity of title, image and type of visibility very simple
```
public class Styles {
    public enum Gravity {
        left, center
    }

    public enum ViewType {
        icon, title, iconTitle, menuIcon, menuTitle, menuIconTitle
    }
 }
```

menu is not supported
