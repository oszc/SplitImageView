package com.zc.SplitImageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

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

        mImage.setDivideBlocks(3,4);

    }
}





