package com.zc.SplitImageView;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import java.util.Arrays;
import java.util.List;

public class MyActivity extends Activity {

    private static final String TAG = "MyActivity";
    @InjectView(R.id.image)
    SplitImageView mImage;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_one);
        ButterKnife.inject(this);
        mImage.setDivideNum(3, 4);
        mImage.setEnableMultiSelect(true);
        mImage.setBlockMax(8);
        Point point = new Point();
        point.x = 0;
        point.y = 1;
        List<Point> points = Arrays.asList(point);
        mImage.setInitialBlockMark(points);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyActivity.this,"Hello World",Toast.LENGTH_SHORT).show();
            }
        });
        //    mImage.setBlockColor(11,22,33,44);

        mImage.setBlockSelectedListener(new SplitImageView.OnBlockSelectListener() {
            @Override
            public void onSelected(List<Position> ps) {
                for (Position p : ps) {
                    Log.e(TAG,p.getmPoint()+"");
                }

            }
        });


    }
}





