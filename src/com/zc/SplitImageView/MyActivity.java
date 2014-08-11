package com.zc.SplitImageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;

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
        ButterKnife.inject(this);
        mImage.setDivideNum(3, 4);
        mImage.setEnableMultiSelect(true);
        mImage.setBlockMax(8);
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





