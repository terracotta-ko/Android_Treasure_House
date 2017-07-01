package kobe.textureview_video_example;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private MediaPlayer mMediaPlayer = null;
    private TextureView mTextureView = null;
    private ImageView mCoverImg = null;
    private Button mPlayBtn = null;

    //>> constants
    private int TEXTURE_VIEW_VIDEO_WIDTH;
    private int TEXTURE_VIEW_VIDEO_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //>> init variables
        mMediaPlayer = new MediaPlayer();

        LayoutManager layoutManager = LayoutManager.getInstance(this);
        TEXTURE_VIEW_VIDEO_WIDTH = layoutManager.getMetric(LayoutManager.LayoutID.VIDEO_WIDTH);
        TEXTURE_VIEW_VIDEO_HEIGHT = layoutManager.getMetric(LayoutManager.LayoutID.VIDEO_HEIGHT);

        //>> find views
        mTextureView = (TextureView) findViewById(R.id.textureView);
        mCoverImg = (ImageView) findViewById(R.id.coverImg);
        mPlayBtn = (Button) findViewById(R.id.playBtn);

        setting();
    }

    private void setting() {
        //>> TextureView
        mTextureView.setSurfaceTextureListener(this);
        mTextureView.getLayoutParams().width = TEXTURE_VIEW_VIDEO_WIDTH;
        mTextureView.getLayoutParams().height = TEXTURE_VIEW_VIDEO_HEIGHT;

        //>> MediaPlayer
        AssetFileDescriptor afd = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        try {
            //>> get video data from assets
            afd = getAssets().openFd("ww.mp4");

            //>> get video data for MediaPlayer
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.prepare();

            //>> get video data for MediaMetadataRetriever
            retriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (afd != null) {
                    afd.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.setLooping(true);
            }
        });

        //>> ImageView

        //>> create cover
        BitmapDrawable bitmapDrawable =
                new BitmapDrawable(this.getResources(),
                        retriever.getFrameAtTime(10 * 1000L,
                                MediaMetadataRetriever.OPTION_CLOSEST_SYNC));

        mCoverImg.getLayoutParams().width = TEXTURE_VIEW_VIDEO_WIDTH;
        mCoverImg.getLayoutParams().height = TEXTURE_VIEW_VIDEO_HEIGHT;

        mCoverImg.setBackground(bitmapDrawable);

        retriever.release();

        //>> Button
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mPlayBtn.setText("Play");
                    mMediaPlayer.pause();
                }
                else {
                    mMediaPlayer.start();
                    mPlayBtn.setText("Pause");
                }
            }
        });
    }


    //>>>>>> implements TextureView.SurfaceTextureListener

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        mMediaPlayer.setSurface(new Surface(surfaceTexture));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (mMediaPlayer.isPlaying()) {
            mCoverImg.setVisibility(View.GONE);
        }
    }

    //<<<<<<
}
