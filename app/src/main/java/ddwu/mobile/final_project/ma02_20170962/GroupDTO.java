package ddwu.mobile.final_project.ma02_20170962;

public class GroupDTO {

    private long _id;
    private String gname;
    private String gfood1;
    private String gfood2;
    private String gfood3;
    private String gprice;
    private String grate;
    private String gextra;
    private byte[] gimage;

    public GroupDTO(long _id, String gname, String gfood1, String gfood2, String gfood3, String gprice, String grate, String gextra, byte[] gimage) {
        this._id = _id;
        this.gname = gname;
        this.gfood1 = gfood1;
        this.gfood2 = gfood2;
        this.gfood3 = gfood3;
        this.gprice = gprice;
        this.grate = grate;
        this.gextra = gextra;
        this.gimage = gimage;
    }


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGfood1() {
        return gfood1;
    }

    public void setGfood1(String gfood1) {
        this.gfood1 = gfood1;
    }

    public String getGfood2() {
        return gfood2;
    }

    public void setGfood2(String gfood2) {
        this.gfood2 = gfood2;
    }

    public String getGfood3() {
        return gfood3;
    }

    public void setGfood3(String gfood3) {
        this.gfood3 = gfood3;
    }

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public String getGrate() {
        return grate;
    }

    public void setGrate(String grate) {
        this.grate = grate;
    }

    public String getGextra() {
        return gextra;
    }

    public void setGextra(String gextra) {
        this.gextra = gextra;
    }

    public byte[] getGimage() {
        return gimage;
    }

    public void setGimage(byte[] gimage) {
        this.gimage = gimage;
    }
}
