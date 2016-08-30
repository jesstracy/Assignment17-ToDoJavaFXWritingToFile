package sample;

/**
 * Created by jessicatracy on 8/30/16.
 */
public class ToDoItem {
    String text;
    boolean isDone;

    public ToDoItem(String text) {
        this.text = text;
        this.isDone = false;
    }

    public ToDoItem(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        if (isDone) {
            return text + " (done)";
        } else {
            return text;
        }
        // A one-line version of the logic above:
        // return text + (isDone ? " (done)" : "");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}


