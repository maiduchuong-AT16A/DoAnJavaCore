/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Luc
 */
public class SinhVien {

    private String ID;
    private String hoTen;
    private boolean gender;
    private String Lop;

    private LocalDate dayBir;

    private String queQuan;

    private double toanHoc;
    private double vatLy;
    private double hoaHoc;
    private double sinhHoc;
    private double tinHoc;
    private double congNghe;
    private double nguVan;
    private double diaLy;
    private double lichSu;
    private double GDCD;

    public SinhVien() {
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
        double dTB = (this.toanHoc + this.vatLy + this.hoaHoc + this.sinhHoc + this.tinHoc + this.congNghe + this.nguVan + this.diaLy + this.lichSu + this.GDCD) / 10;
        return dTB;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
        return Lop;
    }

    public void setLop(String Lop) {
        this.Lop = Lop;
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

    public double getToanHoc() {
        return toanHoc;
    }

    public void setToanHoc(double toanHoc) {
        this.toanHoc = toanHoc;
    }

    public double getVatLy() {
        return vatLy;
    }

    public void setVatLy(double vatLy) {
        this.vatLy = vatLy;
    }

    public double getHoaHoc() {
        return hoaHoc;
    }

    public void setHoaHoc(double hoaHoc) {
        this.hoaHoc = hoaHoc;
    }

    public double getSinhHoc() {
        return sinhHoc;
    }

    public void setSinhHoc(double sinhHoc) {
        this.sinhHoc = sinhHoc;
    }

    public double getTinHoc() {
        return tinHoc;
    }

    public void setTinHoc(double tinHoc) {
        this.tinHoc = tinHoc;
    }

    public double getCongNghe() {
        return congNghe;
    }

    public void setCongNghe(double congNghe) {
        this.congNghe = congNghe;
    }

    public double getNguVan() {
        return nguVan;
    }

    public void setNguVan(double nguVan) {
        this.nguVan = nguVan;
    }

    public double getDiaLy() {
        return diaLy;
    }

    public void setDiaLy(double diaLy) {
        this.diaLy = diaLy;
    }

    public double getLichSu() {
        return lichSu;
    }

    public void setLichSu(double lichSu) {
        this.lichSu = lichSu;
    }

    public double getGDCD() {
        return GDCD;
    }

    public void setGDCD(double GDCD) {
        this.GDCD = GDCD;
    }

}
