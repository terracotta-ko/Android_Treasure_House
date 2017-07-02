package kobe.recyclerview_example;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kobe on 02/07/2017.
 */

class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int mContentLen;
    private ArrayList<Integer> mContents;
    private Handler mHandler;
    private final int THRESHOLD;

    //>> resource ID
    private int[] mImageID = new int[]{
            R.drawable.batman,
            R.drawable.captainamerica,
            R.drawable.deadpool,
            R.drawable.flash,
            R.drawable.greenletain,
            R.drawable.hulk,
            R.drawable.ironman,
            R.drawable.spider,
            R.drawable.superman,
            R.drawable.wonderwoman
    };

    //>> content's message
    private String[] mMsgArray = new String[] {
            "Because I'm batman",
            "I can do this all day",
            "Let mt get this straight",
            "I'm late",
            "In brightest day...",
            "SMASH!!!!!!",
            "Awesome facial hair bros!",
            "Uncle Ben!!!!",
            "Save Martha...",
            "I don't think you've ever known a woman like me"
    };

    public MyRecyclerAdapter(Context context) {
        mContext = context;
        mHandler = new Handler();
        THRESHOLD = 15;

        //>> create contents
        mContents = new ArrayList<>();
        mContentLen = mImageID.length;

        final int initNum = mContentLen * 3;
        for (int i = 0; i < initNum; i++) {
            mContents.add(i % mContentLen);
        }
    }

    public void release() {
        if (mContents != null) {
            mContents.clear();
            mContents = null;
        }

        if (mHandler != null) {
            mHandler = null;
        }
    }

    //>> customized RecyclerView.ViewHolder
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRelativeLayout;

        public BaseViewHolder(View rootView) {
            super(rootView);
            mRelativeLayout = (RelativeLayout) rootView;
        }
    }

    //>>>>>> extends RecyclerView.Adapter<RecyclerView.ViewHolder>

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(new RelativeLayout(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //>> generate view

        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        LayoutManager layoutManager = LayoutManager.getInstance((Activity) mContext);

        //>> create a new ImageView
        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundResource(mImageID[mContents.get(position)]);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                layoutManager.getMetric(LayoutManager.LayoutID.IMAGE_WIDTH),
                layoutManager.getMetric(LayoutManager.LayoutID.IMAGE_HEIGHT)
        ));

        imageView.setId(View.generateViewId());

        baseViewHolder.mRelativeLayout.addView(imageView);

        //>> create a new TextView
        TextView textView = new TextView(mContext);
        textView.setText(mMsgArray[mContents.get(position)]);

        RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        textViewLayoutParams.setMarginStart(layoutManager.getMetric(LayoutManager.LayoutID.TEXT_VIEW_LEFT_MARGIN));
        textViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        textViewLayoutParams.addRule(RelativeLayout.END_OF, imageView.getId());

        textView.setLayoutParams(textViewLayoutParams);

        baseViewHolder.mRelativeLayout.addView(textView);

        //>> following codes make the RecyclerView become an endless RecyclerView
        if ((position + THRESHOLD) >= mContents.size()) {
            loadMoreContents();
        }
    }

    //>> this method must be override because RelativeLayout of each holder
    //>> will always has new ImageView and new TextView when onBindViewHolder is called
    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof BaseViewHolder) {
            ((BaseViewHolder) holder).mRelativeLayout.removeAllViews();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    //<<<<<<

    private void loadMoreContents() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                final int moreNum = mContentLen * 3;
                for (int i = 0; i < moreNum; i++) {
                    mContents.add(i % mContentLen);
                }

                notifyItemRangeInserted(mContents.size(), moreNum);
            }
        });
    }
}
