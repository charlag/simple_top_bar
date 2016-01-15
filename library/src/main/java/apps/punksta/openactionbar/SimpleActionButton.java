package apps.punksta.openactionbar;

import android.graphics.drawable.Drawable;

/**
 * Created by punksta on 15.01.16.
 */
public class SimpleActionButton implements ActionBarButton {
    private final int id;
    private final Drawable drawable;
    private final String name;

    public SimpleActionButton(int id, Drawable drawable, String name) {
        this.id = id;
        this.drawable = drawable;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public String getName() {
        return name;
    }
}
