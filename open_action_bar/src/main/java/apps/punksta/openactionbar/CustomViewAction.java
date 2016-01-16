package apps.punksta.openactionbar;

/**
 * Created by punksta on 15.01.16.
 */
public class CustomViewAction extends Action {
    private final int viewRes;

    public CustomViewAction(int id, String name, int viewRes) {
        super(id, name);
        this.viewRes = viewRes;
    }

    public int getViewRes() {
        return viewRes;
    }
}
