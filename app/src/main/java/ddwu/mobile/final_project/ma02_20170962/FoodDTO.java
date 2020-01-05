package ddwu.mobile.final_project.ma02_20170962;

import java.io.Serializable;

public class FoodDTO implements Serializable {

    private long _id;
    private String fname;
    private String fprice;
    private String rate;
    private String loc;
    private String extra;
    private String address;
    private byte[] image;

    public FoodDTO(long _id, String fname, String fprice, String rate, String food, String loc, String extra, String address, byte[] image) {
        this._id = _id;
        this.fname = fname;
        this.fprice = fprice;
        this.rate = rate;
        this.loc = loc;
        this.extra = extra;
        this.address = address;
        this.image = image;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFprice() {
        return fprice;
    }

    public void setFprice(String fprice) {
        this.fprice = fprice;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
