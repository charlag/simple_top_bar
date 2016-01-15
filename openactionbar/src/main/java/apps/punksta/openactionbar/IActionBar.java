package apps.punksta.openactionbar;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by punksta on 15.01.16.
 */
public interface IActionBar {

    ImageView getMenuIcon();
    ImageView getAppIcon();
    TextView getTitle();
    LinearLayout getActionLayout();

    void setGravity(Styles.Gravity gravity);
    void setViewType(Styles.ViewType viewType);

    void setTitleColor(int color);
    void setBackgroundColor(int color);


}
