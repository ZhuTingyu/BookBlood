package com.cpigeon.book.util;

import android.support.annotation.ColorRes;

import com.base.util.Lists;

import com.base.util.Utils;
import com.cpigeon.book.MyApp;
import com.cpigeon.book.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/4/18.
 */

public class KLineManager {

    public static final String LEFT_Y_LINE = "LEFT_Y_LINE";
    public static final String RIGHT_Y_LINE = "RIGHT_Y_LINE";
    public static final String X_LINE = "X_LINE";

    LineChart chart;
    public XAxis xAxis;
    public YAxis yRight;
    public YAxis yLeft;

    List<ILineDataSet> iLineDataSets;

    public KLineManager(LineChart lineChart) {
        this.chart = lineChart;
        init();
    }

    private void init() {
        chart.setDrawBorders(true);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        chart.setScaleEnabled(false);
        xAxis = chart.getXAxis();
        yRight = chart.getAxisRight();
        yLeft = chart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        yRight.setTextColor(Utils.getColor(R.color.black));
        yLeft.setTextColor(Utils.getColor(R.color.black));
        iLineDataSets = Lists.newArrayList();
        chart.getLegend().setEnabled(false);


        yLeft.setValueFormatter((v, axisBase) -> {
            if (v == 0) {
                return "0";
            } else {
                return String.valueOf(-(int) v);
            }
        });

        yRight.setValueFormatter((v, axisBase) -> {
            if (v == 0) {
                return "0";
            } else {
                return String.valueOf((int) v);
            }
        });
    }

    public void setAnimate(){
        chart.animateY(2000, Easing.EasingOption.EaseInElastic);

    }

    public void setXMaxNumber(int position) {
        chart.setVisibleXRangeMaximum(position);
        xAxis.setLabelCount(position, false);

    }

    public void setLineGone(String line) {
        if (line.equals(LEFT_Y_LINE)) {
            yLeft.setEnabled(false);
        } else if (line.equals(RIGHT_Y_LINE)) {
            yRight.setEnabled(false);
        } else if (line.equals(X_LINE)) {
            xAxis.setEnabled(false);
        }
    }

    public void setxLineValueFormat(IAxisValueFormatter iAxisValueFormatter){
        xAxis.setValueFormatter(iAxisValueFormatter);
    }

    /**
     *
     * @param entries
     * @param lineColor
     * @param lineName
     * @param axisDependency 左或右轴刻度数据
     */

    public void addLineData(List<Entry> entries, @ColorRes int lineColor, String lineName, YAxis.AxisDependency axisDependency){
        entries.add(0, new Entry(0, 0));
        LineDataSet lineDataSet = new LineDataSet(entries, lineName);
        lineDataSet.setAxisDependency(axisDependency);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setColor(Utils.getColor(lineColor));
        lineDataSet.setCircleColor(Utils.getColor(lineColor));
        lineDataSet.setHighLightColor(Utils.getColor(lineColor));
        lineDataSet.setValueTextColor(Utils.getColor(lineColor));
        lineDataSet.setValueFormatter((v, entry, i, viewPortHandler) -> {
            if(v < 0){
                return String.valueOf(-(int)v);
            }else {
                return (String.valueOf((int)v));
            }
        });
        lineDataSet.setDrawCircleHole(true);
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(12f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCircleHoleRadius(1.5f);
        lineDataSet.setCircleSize(3f);
        //设置曲线填充 lineDataSet.setDrawFilled(true);
        iLineDataSets.add(lineDataSet);
    }

    public void setDataToChart(){
        LineData lineData = new LineData(iLineDataSets);
        chart.setData(lineData);
    }

}
