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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Fragment that displays members.
 */
public class MembersFragment extends Fragment {

    ListView listView;
    String[] data;
    String[] name, contact = {"16CS44","16CS78","16CS26"},  flat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_members, container, false);

         listView = (ListView) rootView.findViewById(R.id.list_item);

        ArrayList<ReportRow> memberRow = new ArrayList<ReportRow>();

        BackGroundWorkerList workerList = new BackGroundWorkerList(this);

        try {
            data = workerList.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        name = BackGroundWorkerList.name;
        flat = BackGroundWorkerList.floor;
        //contact = BackGroundWorkerList.contact;


        for (int i = 0; i < 3; i++)
            memberRow.add(new ReportRow(name[i], flat[i], contact[i]));

        memberRow.add(new ReportRow("Asghar", "104", "16CS58"));


//        name = BackGroundWorkerList.name;

        MemberAdapter membersAdapter = new MemberAdapter(getContext(),memberRow);

        listView.setAdapter(membersAdapter);

        return rootView;
    }
}
