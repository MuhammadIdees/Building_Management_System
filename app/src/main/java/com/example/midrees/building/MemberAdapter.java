package com.example.midrees.building;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by M IDREES on 2/18/2018.
 */

public class MemberAdapter extends ArrayAdapter<ReportRow> {

    public MemberAdapter(@NonNull Context context, ArrayList<ReportRow> report) {
        super(context,0, report);
    }

    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent){

        View member = convertView;

        if (member == null){
            member = LayoutInflater.from(getContext()).inflate(
                    R.layout.union_item, parent, false);
        }

        ReportRow currentReport = getItem(position);

        TextView union = (TextView) member.findViewById(R.id.union_item_name);
        union.setText(currentReport.getReportUnion());

//        TextView floor = (TextView) member.findViewById(R.id.union_item_floor);
//        floor.setText(currentReport.getReportFloor());

        TextView  price = (TextView) member.findViewById(R.id.union_item_contact);
        price.setText(currentReport.getContact());

        return member;
    }
}
