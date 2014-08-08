package com.zc.SplitImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 8/8/14  1:20 PM
 * Created by JustinZhang.
 * 点击后显示区块
 * 可单选 可多选
 */
public class SplitImageView extends ImageView {

    private int mRows; //行
    private int mColumns; //列

    private Paint mPaint;
    private float mWidthStep;//每列宽度
    private float mHeightStep;//每行高度

    private boolean mEnableMultiSelect ;

    public SplitImageView(Context context) {
        super(context);
        init();
    }

    public SplitImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SplitImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(100);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

    }

    /**
     * 设置需要划分的行列总数
     *
     * @param rows    行数
     * @param columns 列数
     */
    public void setDivideBlocks(int rows, int columns) {

        mRows = rows;
        mColumns = columns;

        mWidthStep = (float) getWidth() / columns;
        mHeightStep = (float) getHeight() / rows;

        //获得每个区块的坐标位置
        for(int row = 0 ; row < rows; row++){

            for(int column =0 ; column < columns; column++){

                //得到坐标


            }

        }


    }

    public void drawBlock() {


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("hello", 100, 100, mPaint);
        canvas.drawRect(100, 100, 200, 200, mPaint);

    }

    private void calculateTouchedBlock() {
        if (mRows == 0 || mColumns == 0) {
            throw new IllegalStateException("You must set rows and colums");
        }


    }
}
