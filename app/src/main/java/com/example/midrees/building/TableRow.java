package com.example.midrees.building;

/**
 *  Object of the table row
 */

public class TableRow {

    /**
     *  For table date
     */
    private String floorNo;
    private String    flat1status;
    private String    flat2status;
    private String    flat3status;
    private String    flat4status;


    public TableRow (String floor, String status1, String status2, String status3, String status4){
        floorNo = floor;
        flat1status = status1;
        flat2status = status2;
        flat3status = status3;
        flat4status = status4;
    }


    public String getFloorNo () { return floorNo; }

    public String getFlat1status(){ return flat1status; }

    public String getFlat2status(){ return flat2status; }

    public String getFlat3status(){ return flat3status; }

    public String getFlat4status(){ return flat4status; }

}
