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

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Fragment that displays "Tuesday".
 */
public class ReportFragment extends Fragment {

    AlertDialog dialog;
    FloatingActionButton fab_report;
    Dialog myDialog;
    static String reday, remon, reyear;
    static String redate;
    static String[] restatus;
    public static String[] refloor;
    public static String[] readmin;
    public static String[] reExp;
    public static String[] reCollect;
    public static String[] reDesc;
    public static String[] reDate;
    int exp = 0, cost = 0;
    ArrayList<ReportRow> reportItem = new ArrayList<ReportRow>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View reportView =  inflater.inflate(R.layout.fragment_report, container, false);

        BackgroundReport workerList = new BackgroundReport(this);

        try {
            restatus = workerList.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) reportView.findViewById(R.id.report);
        TextView expamse = (TextView) reportView.findViewById(R.id.exp);
        TextView collect = (TextView) reportView.findViewById(R.id.collect);

        for (int i = restatus.length - 1; i >= 0; i--) {
            if (restatus[i].equals("0")) {
                reportItem.add(new ReportRow(BackgroundReport.readmin[i], "Floor No. " + BackgroundReport.refloor[i],
                        BackgroundReport.reCollect[i], BackgroundReport.restatus[i], BackgroundReport.reDesc[i], BackgroundReport.reDate[i]));
                    cost += Integer.parseInt(BackgroundReport.reCollect[i]);
            }
            else {
                reportItem.add(new ReportRow(BackgroundReport.readmin[i], "Floor No. " + BackgroundReport.refloor[i],
                        BackgroundReport.reExp[i], BackgroundReport.restatus[i], BackgroundReport.reDesc[i], BackgroundReport.reDate[i]));
                exp += Integer.parseInt(BackgroundReport.reExp[i]);
            }
            }

            expamse.setText("Total Expanse: " + exp);
            collect.setText("Total Collection: " + cost);
//        ArrayAdapter<String> reportAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, reportItemw);
        ReportAdapter reportAdapter = new ReportAdapter(getContext(), reportItem);
        dialog = new AlertDialog.Builder(getContext()).create();

        listView.setAdapter(reportAdapter);

        fab_report = (FloatingActionButton) reportView.findViewById(R.id.fab_report);

        if (!BackgroundWorker.getUser())
            fab_report.setVisibility(View.GONE);

        fab_report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                callLoginDialog();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReportRow reportRow = reportItem.get(i);
                String desc = reportRow.getDescription() + "\n\n\nDate:   " + reportRow.getDate();
                dialog.setTitle("Description");
                dialog.setMessage(desc);
                dialog.show();
            }
        });

        return reportView;
    }

    private void callLoginDialog()
    {
        myDialog = new Dialog(getContext());
        myDialog.setContentView(R.layout.report);
        myDialog.setCancelable(true);
        Button login = (Button) myDialog.findViewById(R.id.re_enter_button);
        final EditText rupees = (EditText) myDialog.findViewById(R.id.text_input_price_ex);
        final EditText desp = (EditText) myDialog.findViewById(R.id.text_input_exp);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePicker date = (DatePicker) myDialog.findViewById(R.id.datePicker_report);

                reday = "" + date.getDayOfMonth();
                remon = "" + (1+date.getMonth());
                reyear = "" +date.getYear();

                redate = reyear + "-" + remon + "-"+ reday;


                String repees = rupees.getText().toString().trim();
                String disc = desp.getText().toString().trim();
                System.out.println("Idrees");
                String type = "update";

                BackgroundExpanse backgroundUpdate = new BackgroundExpanse(getContext());
                backgroundUpdate.execute(type,MainActivity.input,repees, disc, redate);
            }
        });


    }
}
