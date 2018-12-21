package com.example.whl.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.whl.myapplication.bean.VtDateValueBean;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button bt_change, bt_change_zhu;
    LineChart lineChartWeek;
    ChartMarkerView mChartMarkerView;


    private BarChart mBarChart;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    List<VtDateValueBean> dateValueList;
    int barNum = 30;


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
        mBarChart = findViewById(R.id.barChart);
        bt_change = findViewById(R.id.bt_change);
        bt_change_zhu = findViewById(R.id.bt_change_zhu);


        initBarChart(mBarChart);


        setBarChart(barNum);

        bt_change_zhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barNum--;
                setBarChart(barNum);
                if (barNum == 0) {
                    barNum = 30;
                }
            }
        });


        initLineChartView();
        setMonthData();

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mListWeek.clear();
                entriesWeek.clear();

                if (isWeek) {
                    isWeek = false;
                    setMonthData();


                    //  setHightLimitLine(1.0f,null, getResources().getColor(R.color.red));


                } else {
                    isWeek = true;
                    setWeekData();


//                    //setHightLimitLine(0.0f,null, getResources().getColor(R.color.red));
//                    lineChartWeek.notifyDataSetChanged(); // let the chart know it's data changed
//                    lineChartWeek.invalidate(); // refresh

                }
            }
        });


    }

    private void setBarChart(int num) {
        dateValueList = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            VtDateValueBean mVtDateValueBean = new VtDateValueBean();
            mVtDateValueBean.setfValue(1 + i);
            dateValueList.add(mVtDateValueBean);
        }

        // Collections.reverse(dateValueList);//将集合 逆序排列，转换成需要的顺序

        showBarChart(dateValueList, "净资产收益率（%）", getResources().getColor(R.color.yellow_bg));
    }


    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (TextUtils.isEmpty(name)) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(2f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        // 获得左侧侧坐标轴
        YAxis leftAxis = lineChartWeek.getAxisLeft();
        leftAxis.addLimitLine(hightLimit);
        // lineChartWeek.invalidate();
    }


    /**
     * 初始化BarChart图表（柱形图）
     */
    private void initBarChart(BarChart barChart) {
        /***图表设置***/


        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框/边界
        barChart.setDrawBorders(false);
        //设置动画效果
        barChart.animateY(1000, Easing.EasingOption.Linear);
        barChart.animateX(1000, Easing.EasingOption.Linear);

        //图表描述
        barChart.getDescription().setText("单词个数");

        //barChart.setse

        //折线图例 标签 设置
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);//图示 标签的形状。
        legend.setTextSize(11f);
        legend.setEnabled(false);//是否显示
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);


        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);//设置最小的区间，避免标签的迅速增多
        xAxis.setDrawGridLines(false);//设置竖状的线是否显示
        xAxis.setCenterAxisLabels(false);//设置标签是否居中
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(1f);


        // xAxis.set
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String ss = "";
                try {
                    ss = "10." + dateValueList.get((int) value).getfValue();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    ss = " ";
                }


                return ss; //mList为存有月份的
            }
        });

        leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);//设置横状的线是否显示

        leftAxis.enableGridDashedLine(6f, 3f, 0);//虚线
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setEnabled(false);////显示true 或 隐藏false 左边轴和数字
        leftAxis.setGridColor(0xacb3e5fc);
        //   leftAxis.setTextColor(0xb3e5fc);//设置左边Y轴文字的颜色
        //   leftAxis.setAxisLineColor(0xb3e5fc);//设置左边Y轴的颜色

        rightAxis = barChart.getAxisRight();
        // rightAxis.setDrawGridLines(false);//设置横状的线是否显示
        rightAxis.setEnabled(false);//隐藏右边轴和数字

        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        barChart.setDoubleTapToZoomEnabled(false); // 设置为false以禁止通过在其上双击缩放图表。


        //barChart.set

        barChart.setMarker(new ChartMarkerView(this, R.layout.chart_text_layout, "f:", "数值："));

        // mBarChart.setBorderWidth(15);//设置边界宽度

        /***折线图例 标签 设置***/
//        legend = barChart.getLegend();
//        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTextSize(11f);
//        //显示位置
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        //是否绘制在图表里面
//        legend.setDrawInside(true);


    }

    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color, 50);////设置直方图的颜色


        //barDataSet.setBarBorderWidth(50f);

        barDataSet.setHighLightColor(color);//设置点击之后显示的颜色
        // barDataSet.setHighLightColor(Color.RED);//设置点击之后显示的颜色


        // barDataSet.


//        barDataSet.setColors(new int[]{//第一个颜色会从顶部开始显示
//                    Color.parseColor("#00CDCD"),
//                    Color.parseColor("#00EEEE"),
//                    Color.parseColor("#00FFFF"),
//                    Color.parseColor("#3398DB")
//            });


        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //是否显示柱状图顶部值
        barDataSet.setDrawValues(false);///是否显示文字数值
        // barDataSet.setValueTextSize(10f);
//        barDataSet.setValueTextColor(color);
    }

    public void showBarChart(List<VtDateValueBean> dateValueList, String name, int color) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        int dateValueListSize = dateValueList.size();
        for (int i = 0; i < dateValueListSize; i++) {
            /**
             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
             * 即可设置柱状图顶部的 icon展示
             */
            BarEntry barEntry = new BarEntry(i, (float) dateValueList.get(i).getfValue());
            entries.add(barEntry);
        }
        // 每一个BarDataSet代表一类柱状图

        BarDataSet barDataSet = new BarDataSet(entries, name);
        //  barDataSet.setLabel("");
        //barDataSet.setColor(colours.get(i));
        //barDataSet.setValueTextColor(colours.get(i));
        barDataSet.setValueTextSize(10f);
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        initBarDataSet(barDataSet, color);


//        // 添加多个BarDataSet时
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(barDataSet);
//        BarData data = new BarData(dataSets);

        BarData data = new BarData(barDataSet);
        if (dateValueListSize < 5) {
            data.setBarWidth(0.15f * dateValueListSize);//设置柱体宽度
        }


        mBarChart.setData(data);


        if (dateValueList.isEmpty() || dateValueList.size() == 0) {
            // Toast.makeText(MainActivity.this,"你还没有记录数据",Toast.LENGTH_LONG).show();
            mBarChart.clear();
            mBarChart.setNoDataText("您还没有记录数据");
            mBarChart.setNoDataTextColor(Color.BLACK);
        }


        //mBarChart.set
        mBarChart.notifyDataSetChanged();
        mBarChart.invalidate();

        Highlight highlight1 = new Highlight(6.0f, 0.0f, 0);//主要是x轴，就认x的值，y值默认0就好
        // Highlight highlight2 = new Highlight(9.0f, 9.0f, 0);
        // mBarChart.highlightValues(new Highlight[]{highlight1, highlight2});
        mBarChart.highlightValue(highlight1);//设置一进来的时候就显示MarkView
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

        showLineChartView();


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
        //  initChartView();

    }

    /**
     * 初始化折线图
     */
    private void initLineChartView() {

        lineChartWeek.getLegend().setEnabled(false);

//        lineChartWeek.setHighlightPerDragEnabled(false);
//        lineChartWeek.setHighlightPerTapEnabled(false);
        Description mDescription = new Description();
        mDescription.setText("得分记录（分数）");
        lineChartWeek.setDescription(mDescription);


        mChartMarkerView = new ChartMarkerView(this, R.layout.chart_text_layout, "f:", "数值：");


        lineChartWeek.setMarker(mChartMarkerView);


        //   lineChartWeek.getEntryByTouchPoint(1.0f,2.0f);

        // lineChartWeek.getHighlightByTouchPoint(1.0f, 2.0f);

        lineChartWeek.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("logcat", "点击事件Entry=" + e.toString() + ";高亮Highlight=" + h.toString());


            }

            @Override
            public void onNothingSelected() {
                Log.e("logcat", "onNothingSelected");

            }
        });

        //  setOnChartValueSelectedListener

        lineChartWeek.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        // lineChartWeek.select


        //是否缩放Y轴
        lineChartWeek.setScaleYEnabled(false);
        //设置是否可以通过双击屏幕放大图表。默认是true
        //lineChartWeek.setDoubleTapToZoomEnabled(false);
        XAxis xAxis = lineChartWeek.getXAxis();//得到X轴：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置（默认在上方）：
        xAxis.setGranularity(1f);//设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        //xAxis.setLabelCount(12, true);
        xAxis.setDrawGridLines(false);//设置竖状的线是否显示
        xAxis.setCenterAxisLabels(false);//设置标签是否居中

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
                    ss = " ";
                }


                return ss; //mList为存有月份的
            }
        });


        //  mLineChart.ge

        YAxis leftYAxis = lineChartWeek.getAxisLeft();
        YAxis rightYAxis = lineChartWeek.getAxisRight();
        rightYAxis.setEnabled(false); //右侧Y轴是否显示
        leftYAxis.setEnabled(false); //左侧Y轴是否显示


        showLineChartView();
    }

    /**
     * 显示折线图
     */
    private void showLineChartView() {
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
        } else {
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

        lineDataSet.setDrawValues(false);////是否显示文字数值

        lineDataSet.setValueTextColor(getResources().getColor(R.color.font3));
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(12f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        data = new LineData(lineDataSet);

        lineChartWeek.setData(data);


        setHightLimitLine(1.0f, null, Color.RED);

        if (entriesWeek.isEmpty()) {
            // Toast.makeText(MainActivity.this,"你还没有记录数据",Toast.LENGTH_LONG).show();
            lineChartWeek.clear();
            lineChartWeek.setNoDataText("您还没有记录数据");
            lineChartWeek.setNoDataTextColor(Color.BLACK);
        } else {

//        Highlight mHighlight = new Highlight(0, 0);//new Highlight(1.0f, 2.0f, 0);
//        Entry mEntry = new Entry(1.0f, 2.0f);
//        lineChartWeek.highlightValue(mHighlight);

            Highlight highlight1 = new Highlight(0.0f, 0.0f, 0);//只要是x轴，y默认是0就好。
            // Highlight highlight2 = new Highlight(9.0f, 0.0f, 0);

            Highlight[] highLight = new Highlight[1];
            highLight[0] = highlight1;

            lineChartWeek.highlightValues(highLight);//设置一进来的时候就显示MarkView

            //lineChartWeek.highlightValue(highlight1);
        }


//        if (mChartMarkerView != null) {
        // mChartMarkerView.refreshContent(mEntry, mHighlight);
        // mChartMarkerView.refreshDrawableState();

        //lineChartWeek.getMarker().refreshContent(mEntry, mHighlight);
//        }

        lineChartWeek.notifyDataSetChanged(); // let the chart know it's data changed
        lineChartWeek.invalidate(); // refresh


        //lineChartWeek.getMarker().refreshContent(mEntry, mHighlight);

    }
}
