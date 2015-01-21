SplitImageView
==============

手势点击图片使用透明色块标记，多用于调研选择最喜欢的某块区域<br/>
API<br/>
mSplitImageView.setDivideNum(10, 10); <br/>
        mSplitImageView.setEnableMultiSelect(true);<br/>
        mSplitImageView.setBlockMax(8);<br/>
        mSplitImageView.setBlockColor(102, 0, 255, 0);<br/>
        mSplitImageView.setMode(SplitImageView.MODE.SHORT_CLICK_TO_DRAW);<br/>
        mSplitImageView.setShowDivider(false);<br/>
        Point point = new Point();<br/>
        point.x = 0;<br/>
        point.y = 1;<br/>
        List<Point> points = Arrays.asList(point);<br/>
        mSplitImageView.setInitialBlockMark(points);<br/>
        mSplitImageView.setBlockSelectedListener(new SplitImageView.OnBlockSelectListener() {<br/>
            @Override<br/>
            public void onSelected(List<Position> ps) {<br/>
                for (Position p : ps) {<br/>
                    Log.e(TAG, p.getmPoint() + "");<br/>
                }<br/>
            }<br/>
        });<br/>
        mSplitImageView.setmOnSingleTapListener(new SplitImageView.OnTapListener() {<br/>
            @Override<br/>
            public void onSingleTap() {<br/>
                Toast.makeText(MyActivity.this, "Hello World", Toast.LENGTH_SHORT).show();<br/>
            }<br/>
        });<br/>
