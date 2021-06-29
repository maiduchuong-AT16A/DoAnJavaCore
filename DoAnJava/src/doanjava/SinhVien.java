/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Luc
 */
public class SinhVien {

    private String ID;
    private String hoDem;
    private String ten;
    private String cLass;

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

    public void xuatThongTin() {
        String name = String.format("%s %s", getHoDem(), getTen());
        String day = this.dayBir.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String str = String.format("%-10s%-20s%-10s%-20s%-20s%-10.2f", getID(), name, getcLass(), day, getQueQuan(), getDiemTB());
        System.out.println(str);
    }

    public void xuatDiem() {
        String str = String.format("%-15s%-5.2f%-5.2f%-5.2f%-5.2f%-5.2f%-5.2f%-5.2f%-5.2f%-5.2f%-5.2f"
                , getID(), getToanHoc(), getVatLy(), getHoaHoc(), getNguVan(), getSinhHoc(), getTinHoc()
                , getCongNghe(), getDiaLy(), getLichSu(), getGDCD());
        System.out.println(str);
    }

    public String xepLoaiHocLuc() {
        double dTB = getDiemTB();
        String danhHieu = new String();
        if (dTB >= 6.5 && dTB <= 7.9) {
            danhHieu = "Tien Tien";
        }
        if (dTB >= 8.0 && dTB <= 10) {
            danhHieu = "Gioi";
        }
        if (dTB >= 5 && dTB <= 6.4) {
            danhHieu = "Trung Binh";
        }
        if (dTB >= 0 && dTB <= 4.9) {
            danhHieu = "Yeu";
        } else {
            danhHieu = "<!>không hợp lệ";
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

    public String getHoDem() {
        return hoDem;
    }

    public void setHoDem(String hoDem) {
        this.hoDem = hoDem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getcLass() {
        return cLass;
    }

    public void setcLass(String cLass) {
        this.cLass = cLass;
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
