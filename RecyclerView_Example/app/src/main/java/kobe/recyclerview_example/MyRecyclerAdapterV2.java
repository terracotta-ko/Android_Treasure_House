package kobe.recyclerview_example;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kobe on 02/07/2017.
 */

//>> this class is same as MyRecyclerAdapter
//>> except the implementation of Recycler.ViewHolder

public class MyRecyclerAdapterV2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int mContentLen;
    private ArrayList<Integer> mContents;

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

    public MyRecyclerAdapterV2(Context context) {
        mContext = context;

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
    }

    //>> customized RecyclerView.ViewHolder
    public class BaseViewHolderV2 extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public BaseViewHolderV2(View rootView) {
            super(rootView);

            mImageView = (ImageView) rootView.findViewById(R.id.image_view);
            mTextView = (TextView) rootView.findViewById(R.id.text_view);
        }
    }

    //>>>>>> extends RecyclerView.Adapter<RecyclerView.ViewHolder>

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //>> use content_page.xml to show the content
        return new BaseViewHolderV2(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_page,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //>> generate view
        BaseViewHolderV2 baseViewHolderV2 = (BaseViewHolderV2) holder;

        //>> setup content
        baseViewHolderV2.mImageView.setBackgroundResource(mImageID[mContents.get(position)]);
        baseViewHolderV2.mTextView.setText(mMsgArray[mContents.get(position)]);

        //>> configure size
        LayoutManager layoutManager = LayoutManager.getInstance((Activity) mContext);

        final ViewGroup.LayoutParams imgLayoutParams = baseViewHolderV2.mImageView.getLayoutParams();
        imgLayoutParams.width = layoutManager.getMetric(LayoutManager.LayoutID.IMAGE_WIDTH);
        imgLayoutParams.height = layoutManager.getMetric(LayoutManager.LayoutID.IMAGE_HEIGHT);
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    //<<<<<<
}
