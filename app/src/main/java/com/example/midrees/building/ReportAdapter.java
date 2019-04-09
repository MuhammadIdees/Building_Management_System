package com.example.midrees.building;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by M IDREES on 2/18/2018.
 */

public class ReportAdapter extends ArrayAdapter<ReportRow> {

    public ReportAdapter(@NonNull Context context, ArrayList<ReportRow> report) {
        super(context,0, report);
    }

    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent){

        View report = convertView;

        if (report == null){
            report = LayoutInflater.from(getContext()).inflate(
                    R.layout.report_item, parent, false);
        }

        ReportRow currentReport = getItem(position);

        TextView union = (TextView) report.findViewById(R.id.report_item_union);
        union.setText(currentReport.getReportUnion());

        TextView floor = (TextView) report.findViewById(R.id.report_item_floor);
        floor.setText(currentReport.getReportFloor());

        TextView  price = (TextView) report.findViewById(R.id.report_item_rupees);
        price.setText(currentReport.getPrice());

        LinearLayout back = (LinearLayout) report.findViewById(R.id.color);
        setStatus(back, currentReport.getStatus());

        return report;
    }

    public void setStatus(LinearLayout color, String status){

        if (status.equals("0")){
            color.setBackgroundColor(Color.parseColor("#00E676"));
        }
        else if (status.equals("1")){
            color.setBackgroundColor(Color.parseColor("#FF3D00"));
        }
    }
}
