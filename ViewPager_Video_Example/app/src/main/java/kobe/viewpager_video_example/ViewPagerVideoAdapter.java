package kobe.viewpager_video_example;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kobe on 10/06/2017.
 */

public class ViewPagerVideoAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private ArrayList<VideoPage> mVideoPageList;
    private MediaPlayer mMediaPlayer;
    private int mCurrPageIdx;
    private int mNextPageIdx;
    private boolean mIsPaused;

    public ViewPagerVideoAdapter(Context context) {
        //>> initialize
        mContext = context;
        mCurrPageIdx = 0;
        mNextPageIdx = 0;
        mIsPaused = false;

        final String[] fileArray = new String[] {
                "gg.mp4",
                "ka.mp4",
                "sm.mp4",
                "ww.mp4"
        };

        //>> create MediaPlayer
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //>> seekTo must be called after prepare
                mMediaPlayer.seekTo(mVideoPageList.get(mCurrPageIdx).getVideoStartTimeMs());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }
        });

        //>> create mVideoPageList
        mVideoPageList = new ArrayList<>();
        for (final String f : fileArray) {
            mVideoPageList.add(new VideoPage(context, f, mMediaPlayer));
        }
    }

    public void resume() {
        if (mIsPaused) {
            VideoPage videoPage = mVideoPageList.get(mCurrPageIdx);
            if (videoPage.isSurfaceDestroyed()) {
                //>> if the Surface is destroyed
                //>> then play video after surface is available
                videoPage.setFirstPage(true);
            }
            else {
                playVideo(mCurrPageIdx);
            }
            mIsPaused = false;
        }
    }

    public void pause() {
        if (!mIsPaused) {
            stopVideo(mCurrPageIdx);
            mIsPaused = true;
        }
    }

    public void release() {
        if (mVideoPageList != null) {
            for (final VideoPage v: mVideoPageList) {
                v.release();
            }

            mVideoPageList.clear();
            mVideoPageList = null;
        }

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        mContext = null;
    }

    final public int getCurrPageIdx() {
        return mCurrPageIdx;
    }

    final public int getCurrVideoStartTime() {
        return mVideoPageList.get(mCurrPageIdx).getVideoStartTimeMs();
    }

    public void setFirstPage(final int firstPageIdx, final int startTimeMs) {
        mCurrPageIdx = firstPageIdx;
        mVideoPageList.get(firstPageIdx).setFirstPage(true);

        if (startTimeMs != -1) {
            mVideoPageList.get(firstPageIdx).setVideoStartTimeMs(startTimeMs);
        }
    }

    //>>>>>> extends PagerAdapter

    //>> override this function to use customized view(VideoPage)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        VideoPage videoPage = mVideoPageList.get(position);
        container.addView(videoPage);
        return videoPage;
    }

    @Override
    public int getCount() {
        return mVideoPageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //>> override this function to use customized view(VideoPage)
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position == mCurrPageIdx && mMediaPlayer != null) {
            //>> destroyItem is called before onPageScrollStateChanged
            mMediaPlayer.pause();
            mVideoPageList.get(position).updateCoverBeforeDestroy(mMediaPlayer.getCurrentPosition());
        }

        container.removeView((View) object);
    }

    //<<<<<<


    //>>>>>> implements ViewPager.OnPageChangeListener

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mNextPageIdx = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE && mCurrPageIdx != mNextPageIdx) {
            //>> stop current video
            stopVideo(mCurrPageIdx);

            //>> play video on next page
            playVideo(mNextPageIdx);

            mCurrPageIdx = mNextPageIdx;
        }
    }

    //<<<<<<

    private void playVideo(final int idx) {
        VideoPage videoPage = mVideoPageList.get(idx);

        AssetFileDescriptor afd = null;

        try {
            //>> open next video file
            afd = mContext.getAssets().openFd(videoPage.getFileName());

            //>> setup MediaPlayer
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.setSurface(videoPage.getSurface());
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
    }

    private void stopVideo(final int idx) {
        VideoPage videoPage = mVideoPageList.get(idx);

        //>> pause current video
        mMediaPlayer.pause();
        videoPage.setVideoStartTimeMs(mMediaPlayer.getCurrentPosition());

        //>> update cover and make cover visible
        videoPage.updateCover();

        //>> stop and reset MediaPlayer
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }
}
