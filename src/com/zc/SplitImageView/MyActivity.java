package com.zc.SplitImageView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyActivity extends Activity {

    @InjectView(R.id.image)
    SplitImageView mImage;
    @InjectView(R.id.text)
    TextView mText;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout_one);
        ButterKnife.inject(this);

        mImage.setDivideBlocks(10,10);

    }
}





