package kobe.recyclerview_cardview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kobe on 13/11/2017
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder> {

    private Context mContext;
    private ArrayList<CardContent> mCardContents = new ArrayList<>();
    private LayoutManager mLayoutManager;

    private final int[] mImgIds = {R.mipmap.batman, R.mipmap.superman, R.mipmap.wonderwoman};

    RecyclerViewAdapter(final Context context) {
        mContext = context;
        mLayoutManager = LayoutManager.getInstance((Activity) mContext);

        for (int i = 0; i < 20; ++i) {
            mCardContents.add(new CardContent(i % 3, "Hero " + (i + 1)));
        }
    }

    void release() {
        if (mCardContents != null) {
            mCardContents.clear();
            mCardContents = null;
        }

        mLayoutManager = null;
        mContext = null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_card,parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.mImageView.setBackgroundResource(mImgIds[mCardContents.get(position).getImgId()]);
        holder.mTextView.setText(mCardContents.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mCardContents.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        BaseViewHolder(View rootView) {
            super(rootView);
            mImageView = rootView.findViewById(R.id.card_image);
            mTextView = rootView.findViewById(R.id.card_text);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    mLayoutManager.getMetric(LayoutManager.LayoutID.IMG_WIDTH),
                    mLayoutManager.getMetric(LayoutManager.LayoutID.IMG_HEIGHT)
            );
            mImageView.setLayoutParams(layoutParams);
        }
    }
}
