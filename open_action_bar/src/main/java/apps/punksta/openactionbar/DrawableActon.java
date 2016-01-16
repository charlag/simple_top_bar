package apps.punksta.openactionbar;

/**
 * Created by punksta on 15.01.16.
 */
public class DrawableActon extends Action {
    private final int drawable;

    public DrawableActon(int id, int drawable, String name) {
        super(id, name);
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }
}
