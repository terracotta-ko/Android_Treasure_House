package kobe.recyclerview_swipe_menu;

/**
 * Created by kobe on 04/01/2018
 */

public class ItemData {
    private String mText;

    ItemData(final String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String text) {
        mText = text;
    }
}
