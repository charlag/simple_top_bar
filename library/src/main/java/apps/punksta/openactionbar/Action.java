package apps.punksta.openactionbar;


import android.view.View;

/**
 * Created by punksta on 15.01.16.
 */
public abstract class Action {
    private final int id;
    private final String name;

    protected Action(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public interface OnActionClickListener {
        void onClick(View view, Action action);
    }
}
