package com.zc.SplitImageView;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

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
        mSplitImageView.setDivideNum(10, 10);
        mSplitImageView.setEnableMultiSelect(true);
        mSplitImageView.setBlockMax(8);
        mSplitImageView.setBlockColor(102, 0, 255, 0);
        mSplitImageView.setMode(SplitImageView.MODE.SHORT_CLICK_TO_DRAW);
        mSplitImageView.setShowDivider(true);
        Point point = new Point();
        point.x = 0;
        point.y = 1;
        List<Point> points = Arrays.asList(point);
        mSplitImageView.setInitialBlockMark(points);
        //    mSplitImageView.setBlockColor(11,22,33,44);
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
}





