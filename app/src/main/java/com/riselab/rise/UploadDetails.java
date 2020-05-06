package com.riselab.rise;

public class UploadDetails {

    public String doneby;
    public String time;

    public UploadDetails(String doneby, String time, String response) {
        this.doneby = doneby;
        this.time = time;
        this.response = response;
    }

    public String getDoneby() {
        return doneby;
    }

    public void setDoneby(String doneby) {
        this.doneby = doneby;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String response ;


    public UploadDetails (){

    }
}
