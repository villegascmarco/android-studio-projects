package com.example.vcimpregression;

import android.content.Context;
import android.util.Size;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class DynamicTable {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView txtCell;
    private Regression regression;
    private int indexC;
    private int indexR;

    private double sumX;
    private double sumY;
    private double sumX2;
    private double sumXY;
    private double sumY2;

    private double xAvg;
    private double yAvg;

    public DynamicTable(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    public int getDataSize() {
        return data.size() - 1;
    }

    public double getSumX() {
        return sumX;
    }

    public double getSumY() {
        return sumY;
    }

    public double getSumX2() {
        return sumX2;
    }

    public double getSumXY() {
        return sumXY;
    }

    public double getSumY2() {
        return sumY2;
    }

    public double getxAvg() {
        return xAvg;
    }

    public double getyAvg() {
        return yAvg;
    }

    public void addHeader(String[] header) {
        this.header = header;
        createHeader();
    }

    public void addData(ArrayList<String[]> data) {
        this.data = data;
        createDataTable();
    }

    private void newRow() {
        tableRow = new TableRow(context);
    }

    private void newCell() {
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(25);
        txtCell.setTextSize(12);
    }

    private void createHeader() {
        indexC = 0;
        newRow();
        while (indexC < header.length) {
            newCell();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable() {
        String info;
        for (indexR = 1; indexR <= data.size(); indexR++) {
            newRow();
            for (indexC = 0; indexC < header.length; indexC++) {
                newCell();
                String[] row = data.get(indexR - 1);
                info = (indexC < row.length) ? row[indexC] : "";
                txtCell.setText(info);
                tableRow.addView(txtCell, newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }

    private TableRow.LayoutParams newTableRowParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        return params;
    }

    public void addItem(Regression item) {
        String info;

        String newItem[] = new String[6];
        newItem[0] = "" + data.size();
        newItem[1] = "" + item.getxValue();
        newItem[2] = "" + item.getyValue();
        newItem[3] = "" + item.getxSquared();
        newItem[4] = "" + item.getxTimesY();
        newItem[5] = "" + item.getySquared();

        data.add(newItem);
        newRow();
        indexC = 0;
        while (indexC < header.length) {
            newCell();
            info = (indexC < newItem.length) ? newItem[indexC++] : "";
            txtCell.setText(info);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow, data.size() - 1);

        updateTotalAmount(newItem);
    }


    public void updateTotalAmount(String[] newItem) {
        String info = "";
        indexC = 0;
        tableLayout.removeViewAt(data.size());//data.size = last column
        newRow();
        while (indexC < header.length) {
            newCell();
            switch (indexC) {
                case 0:
                    info = "Total";
                    break;
                case 1:
                    info = "" + (sumX += Double.parseDouble(newItem[indexC]));
                    break;
                case 2:
                    info = "" + (sumY += Double.parseDouble(newItem[indexC]));
                    break;
                case 3:
                    info = "" + (sumX2 += Double.parseDouble(newItem[indexC]));
                    break;
                case 4:
                    info = "" + (sumXY += Double.parseDouble(newItem[indexC]));
                    break;
                case 5:
                    info = "" + (sumY2 += Double.parseDouble(newItem[indexC]));
                    break;

            }
            indexC++;
            txtCell.setText(info);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow, data.size());//data.size = last column
        updateAverage();
    }

    private void updateAverage() {
        int size = data.size() - 1;
        xAvg = (double) sumX / (double) size;
        yAvg = (double) sumY / (double) size;
    }

}