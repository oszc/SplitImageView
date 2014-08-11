SplitImageView
==============

手势点击图片使用透明色块标记，多用于调研选择最喜欢的某块区域


API

iv.setDivideNum(3, 4); //设置行列 <br/>
iv.setEnableMultiSelect(true); //是否支持多选 <br/>
iv.setBlockMax(8); //多选区块数  <br/>
iv.setBlockColor(11,22,33,44);//设置区块颜色  <br/>
iv.setBlockSelectedListener(new SplitImageView.OnBlockSelectListener() {
            @Override
            public void onSelected(List<Position> ps) {
                for (Position p : ps) {
                    Log.e(TAG,p.getmPoint()+"");
                }

            }
        });
        <br/><br/>
示例如下：

<img src='https://github.com/oszc/SplitImageView/blob/master/res/drawable/demo.png'/>
