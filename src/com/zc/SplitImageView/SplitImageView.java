package com.zc.SplitImageView;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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

    private int mRows; //行
    private int mColumns; //列

    private Paint mPaint;
    private float mWidthStep;//每列宽度
    private float mHeightStep;//每行高度
    private List<Position> positions;

    private boolean mEnableMultiSelect ;

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
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(100);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
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

        final ViewTreeObserver observer= getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                mRows = rows;
                mColumns = columns;

                mWidthStep = (float) getWidth() / columns;
                mHeightStep = (float) getHeight() / rows;

                //获得每个区块的坐标位置
                for(int row = 0 ; row < rows; row++){

                    for(int column =0 ; column < columns; column++){

                        //计算得到坐标
                        Position position = new Position();
                        Point point = new Point(row,column);
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

            //    getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });


    }

    public void drawBlock() {


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      //  canvas.drawText("hello", 100, 100, mPaint);
     //   canvas.drawRect(100, 100, 200, 200, mPaint);
        if(mSelectRect != null){
            canvas.drawRect(mSelectRect,mPaint);
        }

    }

    private void calculateTouchedBlock() {
        if (mRows == 0 || mColumns == 0) {
            throw new IllegalStateException("You must set rows and colums");
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        for(Position position:positions){
           if(position.getmRect().contains(x,y)){
               //找到图片中的点
               position.setSelected(true);
               mSelectRect = position.getmRect();

           }else{

               position.setSelected(false);
           }
        }


        invalidate();
        return false;
    }
}
