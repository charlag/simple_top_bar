package apps.punksta.openactionbar;

import android.graphics.drawable.Drawable;

/**
 * Created by punksta on 15.01.16.
 */
public class DrawableActon extends Action {
    private final Drawable drawable;

    public DrawableActon(int id, Drawable drawable, String name) {
        super(id, name);
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
