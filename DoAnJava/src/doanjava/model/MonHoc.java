
package doanjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class MonHoc {
    
    @SerializedName("monHoc")
    @Expose
    private String monHoc;
    @SerializedName("diemMon")
    @Expose
    private double diem;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MonHoc() {
    }

    /**
     * 
     * @param diemMon
     * @param monHoc
     */
    public MonHoc(String monHoc, double diemMon) {
        super();
        this.monHoc = monHoc;
        this.diem = diemMon;
    }

    public String getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(String monHoc) {
        this.monHoc = monHoc;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diemMon) {
        this.diem = diemMon;
    }

}
