package com.example.whl.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button bt_change;
    LineChart lineChartWeek;

    List<String> mListWeek = new ArrayList<>();

    List<Entry> entriesWeek = new ArrayList<>();
    LineDataSet lineDataSet;
    LineData data;

    boolean isWeek = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChartWeek = findViewById(R.id.lineChartWeek);
        bt_change = findViewById(R.id.bt_change);


        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mListWeek.clear();
                entriesWeek.clear();

                if (isWeek) {
                    isWeek = false;
                    setMonthData();

                    lineChartWeek.notifyDataSetChanged(); // let the chart know it's data changed
                    lineChartWeek.invalidate(); // refresh
                } else {
                    isWeek = true;
                    setWeekData();

                    lineChartWeek.notifyDataSetChanged(); // let the chart know it's data changed
                    lineChartWeek.invalidate(); // refresh


                }


            }
        });
        setMonthData();

        // setWeekData();
    }

    /**
     * 月份折线图
     */
    private void setMonthData() {


        //设置数据
        for (int i = 0; i < 31; i++) {
            mListWeek.add("10." + i);
            if (i == 1) {
                entriesWeek.add(new Entry(i, 2));
            } else {
                entriesWeek.add(new Entry(i, 0));
            }
        }

        initChartView();
//        //一个LineDataSet就是一条线
//        lineDataSet = new LineDataSet(entriesWeek, "时长");
//        //一个LineDataSet就是一条线
//        // LineDataSet lineDataSet = new LineDataSet(entriesMonth, "时长");
//        // lineDataSet.clear();
//        //线颜色
//        lineDataSet.setColor(getResources().getColor(R.color.gray_e6));
//        //线宽度
//        lineDataSet.setLineWidth(2f);
//
//        //设置折线图填充
//        //lineDataSet.setDrawFilled(true);
//
//        //设置曲线值的圆点是实心还是空心
//        lineDataSet.setDrawCircleHole(true);
//        lineDataSet.setCircleRadius(3f);
//        lineDataSet.setCircleColor(getResources().getColor(R.color.yellow_bg));
//
//        lineDataSet.setValueTextColor(getResources().getColor(R.color.font3));//折点文字
//        // lineDataSet.set
//        // lineDataSet.setDrawValues(false);
//        //设置显示值的字体大小
//        lineDataSet.setValueTextSize(12f);
//
//        lineDataSet.setDrawValues(false);////不显示数值
//
//        //lineDataSet.clic
//        //线模式为圆滑曲线（默认折线）
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//
//
//        LineData data = new LineData(lineDataSet);
//        lineChartWeek.setData(data);
//
//
//        lineChartWeek.getLegend().setEnabled(false);
//
////        lineChartWeek.setHighlightPerDragEnabled(false);
////        lineChartWeek.setHighlightPerTapEnabled(false);
//
//
//        Description mDescription = new Description();
//        mDescription.setText("");
//        lineChartWeek.setDescription(mDescription);
//
//
//        lineChartWeek.setMarker(new ChartMarkerView(this, R.layout.layout, "f:", "数值："));
//
//
//        //是否缩放Y轴
//        lineChartWeek.setScaleYEnabled(false);
//        //设置是否可以通过双击屏幕放大图表。默认是true
//        //lineChartWeek.setDoubleTapToZoomEnabled(false);
//        XAxis xAxis = lineChartWeek.getXAxis();//得到X轴：
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置（默认在上方）：
//        xAxis.setGranularity(1f);//设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
//        //xAxis.setLabelCount(12, true);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setGridColor(getResources().getColor(R.color.transparent));
//        xAxis.setTextColor(getResources().getColor(R.color.font2));
//
//        xAxis.setTextSize(12f);
////        //设置X轴值为字符串
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//
//                String ss = "";
//                try {
//                    ss = mListWeek.get((int) value);
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                    ss = "ha";
//                }
//                return ss; //mList为存有月份的
//            }
//        });
//
//
//        //  mLineChart.ge
//
//        YAxis leftYAxis = lineChartWeek.getAxisLeft();
//        YAxis rightYAxis = lineChartWeek.getAxisRight();
//        rightYAxis.setEnabled(false); //右侧Y轴不显示
//        leftYAxis.setEnabled(false);
    }

    /**
     * 周折线图
     */
    private void setWeekData() {

        //lineChartWeek.setDrawBorders(true); //显示边界
        //设置数据
        for (int i = 0; i < 7; i++) {
            //entriesWeek.add(new Entry(i, (float) (Math.random()) * 80));
            mListWeek.add("10.0" + i);
            if (i == 1) {
                entriesWeek.add(new Entry(i, 2));
            } else {
                entriesWeek.add(new Entry(i, 0));
            }
        }
        initChartView();

    }

    private void initChartView() {
        //一个LineDataSet就是一条线
        lineDataSet = new LineDataSet(entriesWeek, "时长");
        // lineDataSet.clear();
        //线颜色
        lineDataSet.setColor(getResources().getColor(R.color.gray_e6));
        //线宽度
        lineDataSet.setLineWidth(2f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_zhexian);
            lineDataSet.setFillDrawable(drawable);
        }
        else {
            lineDataSet.setFillColor(Color.YELLOW);
        }
        //设置折线图填充
        lineDataSet.setDrawFilled(true);

        //设置折线图填充
        //lineDataSet.setDrawFilled(true);

        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(true);//true空心,false实心;
        lineDataSet.setCircleRadius(3f);
        //lineDataSet.setc
        lineDataSet.setCircleColor(getResources().getColor(R.color.yellow_bg));//折点文字

        lineDataSet.setDrawValues(false);////不显示数值

        lineDataSet.setValueTextColor(getResources().getColor(R.color.font3));
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(12f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        data = new LineData(lineDataSet);
        lineChartWeek.setData(data);


        lineChartWeek.getLegend().setEnabled(false);

//        lineChartWeek.setHighlightPerDragEnabled(false);
//        lineChartWeek.setHighlightPerTapEnabled(false);


        Description mDescription = new Description();
        mDescription.setText("");
        lineChartWeek.setDescription(mDescription);

        lineChartWeek.setMarker(new ChartMarkerView(this, R.layout.layout, "f:", "数值："));


        //是否缩放Y轴
        lineChartWeek.setScaleYEnabled(false);
        //设置是否可以通过双击屏幕放大图表。默认是true
        //lineChartWeek.setDoubleTapToZoomEnabled(false);
        XAxis xAxis = lineChartWeek.getXAxis();//得到X轴：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置（默认在上方）：
        xAxis.setGranularity(1f);//设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        //xAxis.setLabelCount(12, true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGridColor(getResources().getColor(R.color.transparent));
        xAxis.setTextColor(getResources().getColor(R.color.font2));
        xAxis.setTextSize(12f);
//        //设置X轴值为字符串
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String ss = "";
                try {
                    ss = mListWeek.get((int) value);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    ss = "ha";
                }


                return ss; //mList为存有月份的
            }
        });


        //  mLineChart.ge

        YAxis leftYAxis = lineChartWeek.getAxisLeft();
        YAxis rightYAxis = lineChartWeek.getAxisRight();
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        leftYAxis.setEnabled(false);
    }
}
