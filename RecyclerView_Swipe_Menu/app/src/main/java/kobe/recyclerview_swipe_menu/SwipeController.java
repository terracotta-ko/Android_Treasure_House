package kobe.recyclerview_swipe_menu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;
import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

/**
 * Created by kobe on 21/12/2017
 */

public class SwipeController extends ItemTouchHelper.Callback {
    private static final String TAG = "KKD";

    private static final float BUTTON_WIDTH    = 300;
    private static final int BTN_STATE_GONE    = 0;
    private static final int BTN_LEFT_VISIBLE  = 1;
    private static final int BTN_RIGHT_VISIBLE = 2;

    private int mBtnState = BTN_STATE_GONE;
    private boolean mShouldSwipeBack = false;
    private RectF mBtnInstance = null;
    private MyRecyclerViewAdapter mRecyclerViewAdapter = null;
    private RecyclerView.ViewHolder mCurrentItemViewHolder = null;

    SwipeController(final MyRecyclerViewAdapter recyclerViewAdapter) {
        mRecyclerViewAdapter = recyclerViewAdapter;
    }

    void release() {
        mBtnInstance = null;
        mRecyclerViewAdapter = null;
        mCurrentItemViewHolder = null;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //>> only allow swipe left and right
        return makeMovementFlags(0, RIGHT | LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (mShouldSwipeBack) {
            //>> always swipe back when mBtnState is not BTN_STATE_GONE
            mShouldSwipeBack = mBtnState != BTN_STATE_GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(
            Canvas c,
            RecyclerView recyclerView,
            RecyclerView.ViewHolder viewHolder,
            float dX,
            float dY,
            int actionState,
            boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (mBtnState != BTN_STATE_GONE) {
                //>> fix x position according to button's state
                if (mBtnState == BTN_RIGHT_VISIBLE) {
                    dX = Math.min(dX, -BUTTON_WIDTH);
                }
                else if (mBtnState == BTN_LEFT_VISIBLE) {
                    dX = Math.max(dX, BUTTON_WIDTH);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                checkSwipeState(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive);
            }
        }

        if (mBtnState == BTN_STATE_GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        mCurrentItemViewHolder = viewHolder;
    }

    private void checkSwipeState(
            final Canvas c,
            final RecyclerView recyclerView,
            final RecyclerView.ViewHolder viewHolder,
            final float dX,
            final float dY,
            final int actionState,
            final boolean isCurrentlyActive) {

        //>> why use setOnTouchListener is because we only want to check mBtnState at
        //>> MotionEvent is CANCEL or UP
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();
                mShouldSwipeBack = action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP;

                //>> check mBtnState
                if (mShouldSwipeBack) {
                    if (dX < -BUTTON_WIDTH) {
                        mBtnState = BTN_RIGHT_VISIBLE;
                    }
                    else if (dX > BUTTON_WIDTH) {
                        mBtnState = BTN_LEFT_VISIBLE;
                    }
                }

                if (mBtnState != BTN_STATE_GONE) {
                    //>> not allow Click action when mBtnState is not BTN_STATE_NONE
                    setItemsClickable(recyclerView, false);

                    //>> set one more OnTouchListener to let user can cancel delete action by just
                    //>> tapping the screen
                    setTouchUpListener(
                            c,
                            recyclerView,
                            viewHolder,
                            dY,
                            actionState,
                            isCurrentlyActive
                    );
                }

                return false;
            }
        });
    }

    private void setTouchUpListener(
            final Canvas c,
            final RecyclerView recyclerView,
            final RecyclerView.ViewHolder viewHolder,
            final float dY,
            final int actionState,
            final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //>> reset to normal when MotionEvent is UP
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);

                    //>> empty OnTouchListener
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });

                    if (mBtnInstance != null && mBtnInstance.contains(motionEvent.getX(), motionEvent.getY()) && mRecyclerViewAdapter != null) {
                        if (mBtnState == BTN_LEFT_VISIBLE || mBtnState == BTN_RIGHT_VISIBLE) {
                            mRecyclerViewAdapter.onItemDelete(viewHolder.getAdapterPosition());
                        }
                    }

                    mShouldSwipeBack = false;
                    mBtnState = BTN_STATE_GONE;
                    mCurrentItemViewHolder = null;
                    setItemsClickable(recyclerView, true);
                }

                return false;
            }
        });
    }

    private void setItemsClickable(final RecyclerView recyclerView, final boolean isClickable) {
        final int childCnt = recyclerView.getChildCount();
        for (int i = 0; i < childCnt; ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    void onDraw(final Canvas c) {
        if (mCurrentItemViewHolder != null) {
            drawButtons(c);
        }
    }

    private void drawButtons(final Canvas c) {
        float buttonWidthWithoutPadding =  BUTTON_WIDTH - 20;
        float corners = 16;

        View itemView = mCurrentItemViewHolder.itemView;
        Paint p = new Paint();

        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(leftButton, corners, corners, p);
        drawText("DELETE", c, leftButton, p);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(rightButton, corners, corners, p);
        drawText("DELETE", c, rightButton, p);

        if (mBtnState == BTN_LEFT_VISIBLE) {
            mBtnInstance = leftButton;
        }
        else if (mBtnState == BTN_RIGHT_VISIBLE) {
            mBtnInstance = rightButton;
        }
        else {
            mBtnInstance = null;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }
}
