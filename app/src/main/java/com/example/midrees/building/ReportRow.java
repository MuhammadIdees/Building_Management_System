package com.example.midrees.building;

/**
 * Created by M IDREES on 2/18/2018.
 */

public class ReportRow {

    /**
     *  For report list item
     */
    private String mUnion;
    private String mReportFloor;
    private String mPrice;
    private String mContact;
    private String mStatus;
    private String mDescription;
    private String mDate;

    public ReportRow (String union, String reportfloor, String price, String status, String des, String date){
        mUnion = union;
        mReportFloor = reportfloor;
        mPrice = "" + price;
        mStatus = status;
        mDescription = des;
        mDate = date;
    }

    public ReportRow (String union, String reportfloor, String contact){
        mUnion = union;
        mReportFloor = reportfloor;
        mContact = contact;
    }

    public String getReportFloor(){ return mReportFloor; }

    public String getReportUnion(){ return  mUnion; }

    public String getPrice(){ return mPrice; }

    public String getContact() { return mContact; }

    public String getStatus() { return mStatus; }

    public String getDescription() { return mDescription; }

    public String getDate() { return mDate; }
}
