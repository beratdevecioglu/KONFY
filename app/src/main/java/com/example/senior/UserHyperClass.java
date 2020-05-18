package com.example.senior;

public class UserHyperClass {


    String FDname, FDusername, FDphonenumber, FDemail, FDpassword;

    public UserHyperClass() {
    }

    public UserHyperClass(String FDname, String FDusername, String FDphonenumber, String FDemail, String FDpassword) {
        this.FDname = FDname;
        this.FDusername = FDusername;
        this.FDphonenumber = FDphonenumber;
        this.FDemail = FDemail;
        this.FDpassword = FDpassword;
    }

    public String getFDname() {
        return FDname;
    }

    public void setFDname(String FDname) {
        this.FDname = FDname;
    }

    public String getFDusername() {
        return FDusername;
    }

    public void setFDusername(String FDusername) {
        this.FDusername = FDusername;
    }

    public String getFDphonenumber() {
        return FDphonenumber;
    }

    public void setFDphonenumber(String FDusername) {
        this.FDusername = FDphonenumber;
    }

    public String getFDemail() {
        return FDemail;
    }

    public void setFDemail(String FDemail) {
        this.FDemail = FDemail;
    }

    public String getFDpassword() {
        return FDpassword;
    }

    public void setFDpassword(String FDpassword) {
        this.FDpassword = FDpassword;
    }
}
