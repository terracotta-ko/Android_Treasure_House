package kobe.recyclerview_swipe_menu;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kobe on 19/12/2017
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.BaseViewHolder> {

    private static final String TAG = "KKD";
    private Context mContext;
    private ArrayList<ItemData> mData;

    MyRecyclerViewAdapter(final Context context) {
        mContext = context;
        mData = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            mData.add(new ItemData("Text " + i));
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        BaseViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.tv_content);
        }
    }

    void onItemDelete(final int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    void release() {
        mData.clear();
        mData = null;
        mContext = null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_content, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
