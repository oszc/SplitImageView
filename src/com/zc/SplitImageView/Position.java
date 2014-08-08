package com.zc.SplitImageView;

import android.graphics.Point;
import android.graphics.RectF;

/**
 * 8/8/14  8:53 PM
 * Created by JustinZhang.
 */
public class Position {
    private Point mPoint; //坐标 比如 (1,2) 代表 第一行 第二列

    private RectF mRect;  //矩形

    public Position() {
        mPoint = new Point();
        mRect = new RectF();
    }

    private boolean selected; //是否被点击

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Point getmPoint() {
        return mPoint;
    }

    public RectF getmRect() {
        return mRect;
    }

    public void setmPoint(Point mPoint) {
        this.mPoint = mPoint;
    }

    public void setmRect(RectF mRect) {
        this.mRect = mRect;
    }
}
