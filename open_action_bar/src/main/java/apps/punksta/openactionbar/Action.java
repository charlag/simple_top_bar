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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (id != action.id) return false;
        return name.equals(action.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
