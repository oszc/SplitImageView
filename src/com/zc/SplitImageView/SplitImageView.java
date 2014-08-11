package com.zc.SplitImageView;

import android.content.Context;
import android.graphics.*;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 8/8/14  1:20 PM
 * Created by JustinZhang.
 * 点击后显示区块
 * 可单选 可多选
 */
public class SplitImageView extends ImageView implements View.OnTouchListener {

    public interface OnBlockSelectListener{
        void onSelected(List<Position> ps);
    }


    public int ALPHA = 55;
    public int RED = 0;
    public int GREEN = 255;
    public int BLUE = 0;
    private int mRows; //行
    private int mColumns; //列

    private Paint mPaint;
    private float mWidthStep;//每列宽度
    private float mHeightStep;//每行高度
    private List<Position> positions; //区块信息
    private int mBlockMax = -1; //最多选中区块

    private boolean mEnableMultiSelect = false;//允许多重选择

    private String mToast = "";//提示信息

    private Context mContext;

    //   private RectF mSelectRect;//当前选中的区块
    private OnBlockSelectListener mListener;

    public SplitImageView(Context context) {
        super(context);
        init(context);
    }

    public SplitImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SplitImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.argb(ALPHA, RED, GREEN, BLUE));
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);

        positions = new ArrayList<Position>();
        setOnTouchListener(this);

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

        if (positions != null) {
            for (Position position : positions) {

                if (position.isSelected()) {
                    canvas.drawRect(position.getmRect(), mPaint);
                }
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                float x = event.getX();
                float y = event.getY();


                if (positions != null && mEnableMultiSelect) {
                    //支持多选
                    for (Position position : positions) {
                        if (position.getmRect().contains(x, y)) {
                            //找到图片中的点
                            position.setSelected(!position.isSelected());

                            if (mBlockMax != -1 && getSelectedNum() > mBlockMax) {

                                Toast.makeText(mContext, TextUtils.isEmpty(mToast) ? "最多可选择" + mBlockMax + "块区域" : mToast, Toast.LENGTH_SHORT).show();
                                position.setSelected(false);
                                return false;
                            }
                        }
                    }
                } else if (positions != null && !mEnableMultiSelect) {
                    //只支持单选
                    for (Position p : positions) {
                        if (p.getmRect().contains(x, y)) {
                            p.setSelected(true);
                        } else {
                            p.setSelected(false);
                        }
                    }
                }
                if(mListener!=null){
                    mListener.onSelected(getSelectedBlocks());
                }
                invalidate();
                break;
        }

        return false;
    }


    /**
     * 获得区块数
     *
     * @return 最大选中区块
     */
    public int getBlockMax() {
        return mBlockMax;
    }

    /**
     * 设置最大选中区块数
     *
     * @param blockMax 最大选中区块
     */
    public void setBlockMax(int blockMax) {
        if (blockMax > mColumns * mRows) {
            throw new IllegalArgumentException("max block must less than total block");
        }
        if (blockMax <= 0) {
            throw new IllegalArgumentException("max block num must greater than 0");
        }
        this.mBlockMax = blockMax;
    }

    /**
     * 获得用户选择区块的总数
     *
     * @return int
     */
    private int getSelectedNum() {
        int count = 0;
        if (positions != null) {
            for (Position position : positions) {
                if (position.isSelected()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 设置需要划分的行列总数
     *
     * @param rows    行数
     * @param columns 列数
     */
    public void setDivideNum(final int rows, final int columns) {

        if (rows <= 0 || columns <= 0) {
            throw new IllegalStateException("rows and columns must greater than 0");
        }
        mRows = rows;
        mColumns = columns;
    }

    public boolean isEnableMultiSelect() {
        return mEnableMultiSelect;
    }

    public void setEnableMultiSelect(boolean enableMultiSelect) {
        this.mEnableMultiSelect = enableMultiSelect;
    }

    /**
     * @return String
     */
    public String getToast() {
        return mToast;
    }

    /**
     * 设置提示
     *
     * @param mToast String
     */
    public void setToast(String mToast) {
        this.mToast = mToast;
    }

    /**
     * 设置区块颜色
     *
     * @param a int
     * @param r int
     * @param g int
     * @param b int
     */
    public void setBlockColor(int a, int r, int g, int b) {
        mPaint.setColor(Color.argb(a, r, g, b));
    }


    public void setBlockSelectedListener(OnBlockSelectListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 获得选中区块
     * @return list
     */
    public List<Position> getSelectedBlocks() {
        List<Position> l = new ArrayList<Position>();
        if (positions != null) {
            for (Position p : positions) {
                if (p.isSelected()) {
                    l.add(p);
                }
            }
        }
        return l;
    }
}
