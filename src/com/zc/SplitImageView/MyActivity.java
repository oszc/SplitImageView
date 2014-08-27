package com.zc.SplitImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Arrays;
import java.util.List;

public class MyActivity extends Activity {

    private static final String TAG = "MyActivity";
    @InjectView(R.id.split_image_view)
    SplitImageView mSplitImageView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_one);
        ButterKnife.inject(this);

        String imgUrl = "https://raw.githubusercontent.com/oszc/SplitImageView/master/res/drawable-xhdpi/we.jpg";

        DisplayImageOptions options = new DisplayImageOptions.Builder().build();
        ImageLoaderConfiguration aDefault = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(aDefault);

        ImageLoader.getInstance().displayImage(imgUrl, mSplitImageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                Log.e(TAG,"onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.e(TAG,"onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(final String s, final View view, final Bitmap bitmap) {
                Log.e(TAG,"onLoadingComplete");
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        int imgWidth = bitmap.getWidth();
                        int imgHeight = bitmap.getHeight();
                        int preferHeight = (int) Math.ceil((double) imgHeight / imgWidth * view.getWidth());
                        //AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
                        //layoutParams.height = preferHeight;
                        //Bitmap newBmp = new Bitmap();

                        //这里非常重要，如果你想缩小view，那么相应的请缩小bitmap!!!
                        Bitmap scaledBitmap= Bitmap.createScaledBitmap(bitmap,(int)(view.getWidth() * 2 / (float) 3), (int)(preferHeight * 2 / (float) 3),false);
                        mSplitImageView.setImageBitmap(scaledBitmap);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (view.getWidth() * 2 / (float) 3), (int) (preferHeight * 2 / (float) 3));
                        layoutParams.topMargin = 10;
                        layoutParams.bottomMargin = 10;
                        Log.e(TAG, "imgWidth:" + imgWidth + "   imgHeight:" + imgHeight + "  viewWidth:" + view.getWidth() + "  preferHeight:" + preferHeight + " view:" + view + " s:" + s);
                        view.setLayoutParams(layoutParams);


                        mSplitImageView.setDivideNum(5, 5);
                        mSplitImageView.setEnableMultiSelect(false);
                        mSplitImageView.setBlockMax(8);
                        mSplitImageView.setBlockColor(102, 0, 255, 0);
                        mSplitImageView.setMode(SplitImageView.MODE.SHORT_CLICK_TO_DRAW);
                        mSplitImageView.setShowDivider(true);


                        mSplitImageView.setBlockSelectedListener(new SplitImageView.OnBlockSelectListener() {
                            @Override
                            public void onSelected(List<Position> ps) {
                                for (Position p : ps) {
                                    Log.e(TAG, p.getmPoint() + "");
                                }
                            }
                        });
                        mSplitImageView.setmOnSingleTapListener(new SplitImageView.OnTapListener() {
                            @Override
                            public void onSingleTap() {
                                Toast.makeText(MyActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });



            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                Log.e(TAG,"onLoadingCancelled");
            }
        });




        /*
        Point point = new Point();
        point.x = 0;
        point.y = 1;
        List<Point> points = Arrays.asList(point);
        mSplitImageView.setInitialBlockMark(points);
        //    mSplitImageView.setBlockColor(11,22,33,44);

        */


    }
}





