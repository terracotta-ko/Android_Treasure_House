package kobe.recyclerview_cardview;

/**
 * Created by kobe on 13/11/2017
 */

class CardContent {
    private int mImgId;
    private String mText;

    CardContent(final int imgId, final String text) {
        mImgId = imgId;
        mText = text;
    }

    int getImgId() {
        return mImgId;
    }

    String getText() {
        return mText;
    }
}
