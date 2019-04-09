/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.midrees.building;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Fragment that displays "Monday".
 */
public class FlatFragment extends Fragment {

    View inflatedFlat;
    TextView userFlat, adminFlat;
    FloatingActionButton fab_flat;
    Dialog myDialog;
    ListView flatTable;
    ArrayList<TableRow> rows = new ArrayList<TableRow>();
    public  static String flat_no;
    String data[];
    RelativeLayout backColor;
    String statusNO;
    String dues, admin;
    static String fday, fmon, fyear;
    static String fdate;
    static String fDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflatedFlat = inflater.inflate(R.layout.fragment_flat, container, false);

        BackGroundWorkerStatus workerList = new BackGroundWorkerStatus(this);

        try {
            data = workerList.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < data.length; i++) {
                rows.add(new TableRow("Floor No. " + BackGroundWorkerStatus.floorNo[i], data[i].trim(), data[i+1].trim(), data[i+2].trim(), data[i+3].trim()));

            System.out.println(i + " " +(i +1) +" "+ (i +2) +" "+ (i + 3));
            for(int j = 0; j < 3 && i < data.length; j++)
                    i+=j;

                //i-- ;
        }

        for (int i = 0; i < data.length; i++) {
            if (BackGroundWorkerStatus.floor[i].equals(MainActivity.input)) {
                statusNO = data[i];
                dues = BackGroundWorkerStatus.contact[i];
                admin = BackGroundWorkerStatus.admin[i];
            }
        }

        flatTable = (ListView) inflatedFlat.findViewById(R.id.flat_list_view);

        TableRowAdapter rowAdapter = new TableRowAdapter(getContext(), rows);

        flatTable.setAdapter(rowAdapter);

        fab_flat = (FloatingActionButton) inflatedFlat.findViewById(R.id.fab_flat);
        userFlat = (TextView) inflatedFlat.findViewById(R.id.user_flat_no);
        backColor = (RelativeLayout) inflatedFlat.findViewById(R.id.user_flat_status);
        TextView does = (TextView) inflatedFlat.findViewById(R.id.dues);
        adminFlat = (TextView) inflatedFlat.findViewById(R.id.admin_flatno);

        adminFlat.setText(admin);
        does.setText("Dues = " +  dues);

        setStatus(backColor, statusNO);
        userFlat.setText(MainActivity.input);

        if (!BackgroundWorker.getUser())
            fab_flat.setVisibility(View.GONE);

        fab_flat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                callLoginDialog();
            }
        });
        return inflatedFlat;
    }

    private void callLoginDialog()
    {
        myDialog = new Dialog(getContext());
        myDialog.setContentView(R.layout.password);
        myDialog.setCancelable(true);
        Button login = (Button) myDialog.findViewById(R.id.enter_button);
        final EditText flatinput = (EditText) myDialog.findViewById(R.id.text_input_flat);
        final EditText et_dues = (EditText) myDialog.findViewById(R.id.text_input_password);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker date = (DatePicker) myDialog.findViewById(R.id.datePicker_password);

                fday = "" + date.getDayOfMonth();
                fmon = "" + (1+date.getMonth());
                fyear = "" +date.getYear();

                fdate = fyear + "-" + fmon + "-"+ fday;

                flat_no = flatinput.getText().toString().trim();
                String dues = et_dues.getText().toString().trim();

                String type = "update";

                fDesc = "Maintainance fee of " + flat_no;
                BackgroundUpdate backgroundUpdate = new BackgroundUpdate(getContext());
                backgroundUpdate.execute(type,flat_no,dues,fdate,admin,fDesc);
            }
        });


    }

    public void setStatus(RelativeLayout color, String status){

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
