package com.zc.SplitImageView;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 8/8/14  1:20 PM
 * Created by JustinZhang.
 * 点击后显示区块
 * 可单选 可多选
 */
public class SplitImageView extends ImageView implements View.OnTouchListener {

    public int ALPHA = 55;
    public int RED = 0;
    public int GREEN = 255;
    public int BLUE = 0;
    private int mRows; //行
    private int mColumns; //列

    private Paint mPaint;
    private float mWidthStep;//每列宽度
    private float mHeightStep;//每行高度
    private List<Position> positions;

    private boolean mEnableMultiSelect;

    private RectF mSelectRect;


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
        mPaint.setColor(Color.argb(ALPHA, RED, GREEN, BLUE));
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);

        positions = new ArrayList<Position>();
        setOnTouchListener(this);

    }

    /**
     * 设置需要划分的行列总数
     *
     * @param rows    行数
     * @param columns 列数
     */
    public void setDivideBlocks(final int rows, final int columns) {

        if(rows<=0 || columns <= 0){
            throw new IllegalStateException("rows and columns must greater than 0");
        }
        mRows = rows;
        mColumns = columns;


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mColumns == 0 || mRows == 0) {
            throw new IllegalStateException("You must specify rows and columns!!!");
        }
        mWidthStep = (float) w / mColumns;
        mHeightStep = (float) h / mRows;

        //获得每个区块的坐标位置
        for (int row = 0; row < mRows; row++) {

            for (int column = 0; column < mColumns; column++) {

                //计算得到坐标
                Position position = new Position();
                Point point = new Point(row, column);
                position.setmPoint(point);
                RectF rectF = new RectF();
                rectF.left = column * mWidthStep;
                rectF.top = row * mHeightStep;
                rectF.right = rectF.left + mWidthStep;
                rectF.bottom = rectF.top + mHeightStep;
                position.setmRect(rectF);
                positions.add(position);
            }

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSelectRect != null) {
            canvas.drawRect(mSelectRect, mPaint);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        for (Position position : positions) {
            if (position.getmRect().contains(x, y)) {
                //找到图片中的点
                position.setSelected(true);
                mSelectRect = position.getmRect();
            } else {
                position.setSelected(false);
            }
        }
        invalidate();
        return false;
    }
}
