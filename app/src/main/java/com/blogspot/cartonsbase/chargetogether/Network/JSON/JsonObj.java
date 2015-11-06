package com.blogspot.cartonsbase.chargetogether.Network.JSON;

import com.google.gson.annotations.SerializedName;

public class JsonObj {

    @SerializedName("UserId")
    public String UserId;

    @SerializedName("UserName")
    public String UserName;

    @SerializedName("gps_x")
    public double gps_x;

    @SerializedName("gps_y")
    public double gps_y;

    @SerializedName("power_bank_spec")
    public String power_bank_spec;

}
