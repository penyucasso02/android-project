package com.example.partyeventapp;

public class DataClass {
    private String dataImage;
    private String dataTittle;
    private String dataDesc;
    private String dataTimeDate;


    public String getDataImage() {
        return dataImage;
    }

    public String getDataTittle() {
        return dataTittle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataTimeDate() {
        return dataTimeDate;
    }


    public DataClass(String dataTittle, String dataDesc, String dataTimeDate) {
        this.dataTittle = dataTittle;
        this.dataDesc = dataDesc;
        this.dataTimeDate = dataTimeDate;
        this.dataImage = dataImage;
    }

}
