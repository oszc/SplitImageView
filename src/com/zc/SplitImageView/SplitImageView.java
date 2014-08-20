package com.zc.SplitImageView;

import android.content.Context;
import android.graphics.*;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
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
 * 用户不应监听长按或点击效果，设置MODE与设置相应OnBlockSelectListener、OnTapListener监听代替
 *
 */
public class SplitImageView extends TouchImageView implements View.OnTouchListener {

    private static final String TAG = "SplitImageView";
    /**
     * LONG_CLICK_TO_DRAW 模式 长按时候调用OnBlockSelectListener 短按时候调用 OnTapListener
     * SHORT_CLICK_TO_DRAW 模式（默认） 长按时候调用OnTapListener 短按时候调用OnBlockSelectListener
     */
    public enum MODE {
        LONG_CLICK_TO_DRAW, SHORT_CLICK_TO_DRAW;
    }

    public interface OnBlockSelectListener {
        void onSelected(List<Position> ps);
    }

    public interface OnTapListener {
        void onSingleTap();
    }

    public int ALPHA = 102;
    public int RED = 255;
    public int GREEN = 255;
    public int BLUE = 255;
    private int mRows; //行
    private int mColumns; //列

    private Paint mSelectPaint; //选中画笔
    private Paint mDividePaint;//边框画笔
    private float mWidthStep;//每列宽度
    private float mHeightStep;//每行高度
    private List<Position> positions; //区块信息
    private int mBlockMax = -1; //最多选中区块

    private MODE mode = MODE.SHORT_CLICK_TO_DRAW; //模式 不同模式 监听不同长按、短按状态

    private boolean mEnableMultiSelect = false;//允许多重选择

    private String mToast = "";//提示信息

    private Context mContext;
    private GestureDetector mGestureDetector;

    private boolean showDivider = true;

    private OnBlockSelectListener mOnBlockSelectListener;
    private OnTapListener mOnSingleTapListener;

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
        mSelectPaint = new Paint();
        mSelectPaint.setAntiAlias(true);
        mSelectPaint.setColor(Color.argb(ALPHA, RED, GREEN, BLUE));
        mSelectPaint.setStrokeWidth(10);
        mSelectPaint.setStyle(Paint.Style.FILL);

        mDividePaint = new Paint();
        mDividePaint.setAntiAlias(true);
        mDividePaint.setColor(Color.argb(ALPHA, RED, GREEN, BLUE));
        mDividePaint.setStrokeWidth(2);
        mDividePaint.setStyle(Paint.Style.STROKE);

        positions = new ArrayList<Position>();

        mGestureDetector = new GestureDetector(mSimpleGuestureListener);
        setOnTouchListener(this);

    }

    GestureDetector.SimpleOnGestureListener mSimpleGuestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            longClickBehavior(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            shortClickBehavior(e);
            return true;
        }
    };

    /**
     * 根据模式定义长按行为
     *
     * @param e
     */
    private void longClickBehavior(MotionEvent e) {
        if (mode == MODE.LONG_CLICK_TO_DRAW) {
            drawBlock(e);
        } else {
            if (mOnSingleTapListener != null) {
                mOnSingleTapListener.onSingleTap();
            }
        }
    }

    /**
     * 根据模式定义短按行为
     *
     * @param e
     */
    private void shortClickBehavior(MotionEvent e) {
        if (mode == MODE.SHORT_CLICK_TO_DRAW) {
            drawBlock(e);
        } else {
            if (mOnSingleTapListener != null) {
                mOnSingleTapListener.onSingleTap();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mColumns == 0 || mRows == 0) {
            return;
        }
        positions.clear();
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
                RectF curRect = scaleRect(position.getmRect(), getCurrentZoom());
                if (position.isSelected()) {
                    canvas.save();
                    canvas.translate(m[Matrix.MTRANS_X], m[Matrix.MTRANS_Y]);
                    canvas.drawRect(curRect, mSelectPaint);
                    canvas.restore();
                }
                if (isShowDivider()) {
                    canvas.save();
                    canvas.translate(m[Matrix.MTRANS_X], m[Matrix.MTRANS_Y]);
                    canvas.drawRect(curRect, mDividePaint);
                    canvas.restore();
                }
            }
        }
    }

    /**
     * 按比例缩放正方形
     * @param rect RectF
     * @param scale float
     * @return RectF
     */
    public RectF scaleRect(RectF rect, float scale) {
        RectF r = new RectF();
        r.set(rect.left * scale,
                rect.top * scale,
                rect.right * scale,
                rect.bottom * scale);
        return r;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 画实心框
     * @param event
     * @return
     */
    private boolean drawBlock(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (positions != null && mEnableMultiSelect) {
            //支持多选
            for (Position position : positions) {
                PointF imgPoint = transformCoordTouchToBitmap(x, y, false); //根据缩放比率将点击的坐标转化为图片上的坐标
                if (position.getmRect().contains(imgPoint.x, imgPoint.y)) {
                    //找到图片中的点
                    position.setSelected(!position.isSelected());
                    if (mBlockMax != -1 && getSelectedNum() > mBlockMax) {
                        Toast.makeText(mContext, TextUtils.isEmpty(mToast) ? "最多可选择" + mBlockMax + "块区域" : mToast, Toast.LENGTH_SHORT).show();
                        position.setSelected(false);
                        return true;
                    }
                }
            }
        } else if (positions != null && !mEnableMultiSelect) {
            //只支持单选
            for (Position p : positions) {
                PointF imgPoint = transformCoordTouchToBitmap(x, y, false);
                if (p.getmRect().contains(imgPoint.x, imgPoint.y)) {
                    p.setSelected(true);
                } else {
                    p.setSelected(false);
                }
            }
        }
        if (mOnBlockSelectListener != null) {
            mOnBlockSelectListener.onSelected(getSelectedBlocks());
        }
        invalidate();
        return false;
    }

    public MODE getMode() {
        return mode;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
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

    public boolean isShowDivider() {
        return showDivider;
    }

    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
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
        mSelectPaint.setColor(Color.argb(a, r, g, b));
    }

    public void setBlockSelectedListener(OnBlockSelectListener mListener) {
        this.mOnBlockSelectListener = mListener;
    }

    public void setmOnSingleTapListener(OnTapListener mOnSingleTapListener) {
        this.mOnSingleTapListener = mOnSingleTapListener;
    }

    /**
     * 获得选中区块
     *
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

    /**
     * 设置初始选中的点
     *
     * @param points List
     */
    public void setInitialBlockMark(final List<Point> points) {
        if (points == null || points.size() == 0) {
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                for (Point point : points) {
                    for (Position position : positions) {
                        if (point.equals(position.getmPoint())) {
                            position.setSelected(true);
                        }
                    }
                }
                invalidate();
            }
        });
    }
}
