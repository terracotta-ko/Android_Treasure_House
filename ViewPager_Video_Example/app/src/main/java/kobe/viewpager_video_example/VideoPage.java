package kobe.viewpager_video_example;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;


/**
 * Created by kobe on 10/06/2017.
 */

public class VideoPage extends RelativeLayout implements TextureView.SurfaceTextureListener {
    static final String TAG = "VideoPage";
    private Context mContext;
    private Surface mSurface;
    private final String mFileName;
    private int mVideoPosInMs;
    private TextureView mTextureView;
    private ImageView mCoverImg;
    private boolean mIsFirstPage;
    private boolean mIsCoverVisible;
    private boolean mIsSurfaceDestroyed;
    private MediaPlayer mMediaPlayer;

    final int TEXTURE_VIEW_WIDTH;
    final int TEXTURE_VIEW_HEIGHT;
    final int FIRST_VIDEO_START_TIME_MS;

    public VideoPage(Context context, String fileName, MediaPlayer mediaPlayer) {
        super(context);
        mContext = context;
        mFileName = fileName;
        FIRST_VIDEO_START_TIME_MS = 10;
        mVideoPosInMs = FIRST_VIDEO_START_TIME_MS;
        mIsFirstPage = false;
        mIsCoverVisible = true;
        mIsSurfaceDestroyed = true;
        mMediaPlayer = mediaPlayer;

        LayoutManager layoutManager = LayoutManager.getInstance((Activity) mContext);
        TEXTURE_VIEW_WIDTH = layoutManager.getMetric(LayoutManager.LayoutID.VIDEO_WIDTH);
        TEXTURE_VIEW_HEIGHT = layoutManager.getMetric(LayoutManager.LayoutID.VIDEO_HEIGHT);

        generateView();
    }

    final public Surface getSurface() {
        return mSurface;
    }

    final public String getFileName() {
        return mFileName;
    }

    final public void setVideoStartTimeMs(int startTimeMs) {
        mVideoPosInMs = startTimeMs;
    }

    final public int getVideoStartTimeMs() {
        return mVideoPosInMs;
    }

    final public void setFirstPage(boolean isFirst) {
        mIsFirstPage = isFirst;
    }

    final public boolean isSurfaceDestroyed() {
        return mIsSurfaceDestroyed;
    }

    private void generateView() {
        //>> generate mTextureView
        mTextureView = new TextureView(mContext);

        mTextureView.setSurfaceTextureListener(this);

        RelativeLayout.LayoutParams textureViewParams = new RelativeLayout.LayoutParams(TEXTURE_VIEW_WIDTH, TEXTURE_VIEW_HEIGHT);
        textureViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTextureView.setLayoutParams(textureViewParams);

        addView(mTextureView);

        //>> generate mCoverImg
        mCoverImg = new ImageView(mContext);
        mCoverImg.setLayoutParams(textureViewParams);

        //>> get first cover image
        createCoverFromAsset(mVideoPosInMs);

        addView(mCoverImg);
    }

    public void release() {
        if (mSurface != null) {
            mSurface.release();
            mSurface = null ;
        }

        if (mCoverImg != null) {
            Bitmap b = ((BitmapDrawable) mCoverImg.getBackground()).getBitmap();
            if (b != null) {
                b.recycle();
            }
            mCoverImg = null;
        }

        mContext = null;
        mMediaPlayer = null;
    }

    public void updateCover() {
        if (!mIsCoverVisible && mTextureView.isAvailable()) {
            releaseOldCover();

            mCoverImg.setBackground(new BitmapDrawable(mContext.getResources(),
                    mTextureView.getBitmap(TEXTURE_VIEW_WIDTH / 2, TEXTURE_VIEW_HEIGHT / 2)));

            makeCoverVisible();
        }
    }

    public void updateCoverBeforeDestroy(final int videoPosInMs) {
        if (!mIsCoverVisible && mTextureView.isAvailable()) {
            releaseOldCover();
            createCoverFromAsset(videoPosInMs);
            makeCoverVisible();
        }
    }

    private void makeCoverVisible() {
        mCoverImg.setVisibility(VISIBLE);
        mIsCoverVisible = true;
    }

    private void releaseOldCover() {
        BitmapDrawable bd = ((BitmapDrawable) mCoverImg.getBackground());
        if (bd != null) {
            bd.getBitmap().recycle();
        }
    }

    private void createCoverFromAsset(final int videoPosInMs) {
        AssetFileDescriptor afd = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        BitmapDrawable cover = null;

        try {
            afd = mContext.getAssets().openFd(mFileName);
            retriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

            cover = new BitmapDrawable(mContext.getResources(), Bitmap.createBitmap(
                    retriever.getFrameAtTime(videoPosInMs * 1000L,
                            MediaMetadataRetriever.OPTION_CLOSEST)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (afd != null) {
                try {
                    afd.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            retriever.release();
        }

        if (cover != null) {
            mCoverImg.setBackground(cover);
        }
        else {
            Log.e(TAG, "cover is null");
        }
    }

    //>>>>>> implements TextureView.SurfaceTextureListener

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);

        if (mIsSurfaceDestroyed) {
            mIsSurfaceDestroyed = false;
        }

        if (mIsFirstPage) {
            //>> play video on next page
            AssetFileDescriptor afd = null;

            try {
                //>> open next video file
                afd = mContext.getAssets().openFd(mFileName);

                //>> setup MediaPlayer
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.setSurface(mSurface);
                mMediaPlayer.prepare();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    afd.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mIsFirstPage = false;
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mIsSurfaceDestroyed = true;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (mIsCoverVisible && mMediaPlayer.isPlaying()) {
            mCoverImg.setVisibility(GONE);
            mIsCoverVisible = false;
        }
    }

    //<<<<<<
}
