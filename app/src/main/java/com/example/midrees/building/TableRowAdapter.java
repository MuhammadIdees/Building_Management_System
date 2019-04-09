package com.example.midrees.building;

/**
 * Created by M IDREES on 2/17/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TableRowAdapter extends ArrayAdapter<TableRow> {

    AlertDialog dialog;

    int flat;
    // 1
    public TableRowAdapter(Context context, ArrayList<TableRow> tableRow) {
        super(context, 0, tableRow);
        dialog = new AlertDialog.Builder(context).create();
    }

    public View getView (final int position, View convertView, ViewGroup parent){

        View tableRow = convertView;

        if (tableRow == null){
            tableRow = LayoutInflater.from(getContext()).inflate(
                    R.layout.table_row, parent, false);
        }

        TableRow currentRow = getItem(position);

        flat = position;

        TextView floor = (TextView) tableRow.findViewById(R.id.table_row_flatno);
        floor.setText(currentRow.getFloorNo());

        ImageView flat1 = (ImageView) tableRow.findViewById(R.id.table_row_flat1);
        setStatus(flat1, currentRow.getFlat1status());

        flat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0)
                    flat = 0;
                else if (position == 1)
                    flat = 3;
                else if (position == 2)
                    flat = 6;

                dialog.setTitle("Dues");
                dialog.setMessage(BackGroundWorkerStatus.contact[position + 0 + flat]);
                System.out.println("Kuti " + BackGroundWorkerStatus.contact[position + 0 + flat]);
                System.out.println("Kuti jo put " + (position + 0 + flat));
                System.out.println("Kuti jo put " + position + 0 + flat);
//                dialog.setMessage("Kutta");
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        ImageView flat2 = (ImageView) tableRow.findViewById(R.id.table_row_flat2);
        setStatus(flat2, currentRow.getFlat2status());

        flat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0)
                    flat = 0;
                else if (position == 1)
                    flat = 3;
                else if (position == 2)
                    flat = 6;

                dialog.setTitle("Dues");
                dialog.setMessage(BackGroundWorkerStatus.contact[position + 1 + flat]);
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        ImageView flat3 = (ImageView) tableRow.findViewById(R.id.table_row_flat3);
        setStatus(flat3, currentRow.getFlat3status());

        flat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0)
                    flat = 0;
                else if (position == 1)
                    flat = 3;
                else if (position == 2)
                    flat = 6;

                dialog.setTitle("Dues");
                dialog.setMessage(BackGroundWorkerStatus.contact[position + 2 + flat]);
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        ImageView flat4 = (ImageView) tableRow.findViewById(R.id.table_row_flat4);
        setStatus(flat4, currentRow.getFlat4status());

        flat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0)
                    flat = 0;
                else if (position == 1)
                    flat = 3;
                else if (position == 2)
                    flat = 6;

                dialog.setTitle("Dues");
                dialog.setMessage(BackGroundWorkerStatus.contact[position + 3 + flat]);
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        if(position == 0)
            flat = 0;
        else if (position == 1)
            flat = 3;
        else if (position == 2)
            flat = 6;

        System.out.print(position + 0 + flat);
        System.out.print(position + 1 + flat);
        System.out.print(position + 2 + flat);
        System.out.println(position + 3 + flat);
        return tableRow;
    }

    /**
     *  To set color according to the status
     *
     * @param color
     * @param status
     */
    public void setStatus(ImageView color, String status){

        if (status.equals("0")){
            color.setBackgroundColor(Color.parseColor("#00E676"));
        }
        else if (status.equals("1")){
            color.setBackgroundColor(Color.parseColor("#FFEA00"));
        }
        else if (status.equals("2")){
            color.setBackgroundColor(Color.parseColor("#FF3D00"));
        }
    }

}

