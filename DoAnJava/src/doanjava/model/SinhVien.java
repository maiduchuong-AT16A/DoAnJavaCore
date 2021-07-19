package doanjava.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class SinhVien {

    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("hoTen")
    @Expose
    private String hoTen;
    @SerializedName("gender")
    @Expose
    private boolean gender;
    @SerializedName("Lop")
    @Expose
    private String lop;
    @SerializedName("dayBir")
    @Expose
    private LocalDate dayBir;
    @SerializedName("queQuan")
    @Expose
    private String queQuan;
    @SerializedName("MonHoc")
    @Expose
    private List<MonHoc> listDiem = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SinhVien() {
    }

    /**
     *
     * @param gender
     * @param id
     * @param hoTen
     * @param lop
     * @param dayBir
     * @param queQuan
     * @param monHoc
     */
    public SinhVien(String id, String hoTen, boolean gender, String lop, LocalDate dayBir, String queQuan, List<MonHoc> monHoc) {
        super();
        this.ID = id;
        this.hoTen = hoTen;
        this.gender = gender;
        this.lop = lop;
        this.dayBir = dayBir;
        this.queQuan = queQuan;
        this.listDiem = monHoc;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public LocalDate getDayBir() {
        return dayBir;
    }

    public void setDayBir(LocalDate dayBir) {
        this.dayBir = dayBir;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public List<MonHoc> getListDiem() {
        return listDiem;
    }

    public void setListDiem(List<MonHoc> listDiem) {
        this.listDiem = listDiem;
    }

    public String xepLoaiHocLuc() {
        double dTB = getDiemTB();
        String danhHieu = new String();
        if (dTB >= 6.5 && dTB <= 7.9) {
            danhHieu = "Tiên Tiến";
        } else if (dTB >= 8.0 && dTB <= 10) {
            danhHieu = "Giỏi";
        } else if (dTB >= 5 && dTB <= 6.4) {
            danhHieu = "Trung Bình";
        } else if (dTB >= 0 && dTB <= 4.9) {
            danhHieu = "Yếu";
        } else {
            danhHieu = "<!>Lỗi";
        }
        return danhHieu;
    }

    public double getDiemTB() {
        double dTB = 0;
        for (MonHoc item : listDiem) {
            dTB += item.getDiem();
        }
        return dTB / 10;
    }

    @Override
    public String toString() {
        return "SinhVien{" + "ID=" + ID + ", hoTen=" + hoTen + ", gender=" + gender + ", lop=" + lop + ", dayBir=" + dayBir + ", queQuan=" + queQuan + ", listDiem=" + listDiem + '}';
    }

}
