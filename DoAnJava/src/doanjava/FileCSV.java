/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class FileCSV {

    private static final String currenDir = System.getProperty("user.dir");
    private static final String separator = File.separator;

    private static final String PATH_CSV_FILE = currenDir + separator + "data" + separator + "FileData.csv";
    private static final String PATH_CSV_FILE_INFO = currenDir + separator + "data" + separator + "FileDataInfo.csv";
    private static final String PATH_CSV_FILE_SCORE = currenDir + separator + "data" + separator + "FileDataScore.csv";

    public void ReadFileCSV(ArrayList<SinhVien> list) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(PATH_CSV_FILE));
            String line = null;
            try {
                line = bf.readLine();
            } catch (IOException ex) {
                Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while ((line = bf.readLine()) != null) {
                    String[] rd = line.split(",");
                    SinhVien sinhVien = new SinhVien();
                    sinhVien.setID(rd[1]);

                    sinhVien.setHoTen(rd[2]);

                    if (rd[3].equalsIgnoreCase("Nam")) {
                        sinhVien.setGender(true);
                    } else if (rd[2].equalsIgnoreCase("Nữ")) {
                        sinhVien.setGender(false);
                    }

                    String[] date = rd[4].split("-");
                    int day = Integer.parseInt(date[0]);
                    int mot = Integer.parseInt(date[1]);
                    int yea = Integer.parseInt(date[2]);
                    LocalDate a = LocalDate.of(day, mot, yea);
                    sinhVien.setDayBir(a);

                    sinhVien.setQueQuan(rd[5]);

                    sinhVien.setLop(rd[6]);

                    sinhVien.setToanHoc(Double.parseDouble(rd[7]));
                    sinhVien.setVatLy(Double.parseDouble(rd[8]));
                    sinhVien.setHoaHoc(Double.parseDouble(rd[9]));
                    sinhVien.setNguVan(Double.parseDouble(rd[10]));
                    sinhVien.setSinhHoc(Double.parseDouble(rd[11]));
                    sinhVien.setLichSu(Double.parseDouble(rd[12]));
                    sinhVien.setDiaLy(Double.parseDouble(rd[13]));
                    sinhVien.setGDCD(Double.parseDouble(rd[14]));
                    sinhVien.setTinHoc(Double.parseDouble(rd[15]));
                    sinhVien.setCongNghe(Double.parseDouble(rd[16]));
                    list.add(sinhVien);
                }
            } catch (IOException ex) {
                Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void WriteFileCSVInfo(ArrayList<SinhVien> list) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(PATH_CSV_FILE_INFO);
            String tilte = "STT,ID,Họ Và Tên,Giới Tính,Ngày Sinh,Quê Quán,Lớp,Điểm TB,Xếp Loại";
            fw.write(tilte);
            fw.write("\n");
            int i=1;
            for (SinhVien sinhVien : list) {
                String gender = sinhVien.isGender() == true ? "Nam" : "Nữ";
                String dateString = sinhVien.getDayBir().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String f = String.format("%02d,%s,%s,%s,%s,%s,%s,%.2f,%s",i++, sinhVien.getID(), sinhVien.getHoTen(),
                        gender, sinhVien.getDayBir(), sinhVien.getQueQuan(), sinhVien.getLop(), sinhVien.getDiemTB(), sinhVien.xepLoaiHocLuc());
                fw.write(f);
                fw.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void WriteFileCSVScore(ArrayList<SinhVien> list) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(PATH_CSV_FILE_SCORE);
            String tilte = "STT,ID,Toán Học,Vật Lý,Hóa Học,Văn Học,Sinh Học,Lịch Sử,Địa Lý,GDCD,Tin Học,Công nghệ";
            fw.write(tilte);
            fw.write("\n");
            int i = 1;
            for (SinhVien sinhVien : list) {
                String gender = sinhVien.isGender() == true ? "Nam" : "Nữ";
                String dateString = sinhVien.getDayBir().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String f = String.format("%02d,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", i++, sinhVien.getID(), sinhVien.getToanHoc(), sinhVien.getVatLy(), sinhVien.getHoaHoc(),
                        sinhVien.getNguVan(), sinhVien.getSinhHoc(), sinhVien.getLichSu(), sinhVien.getDiaLy(), sinhVien.getGDCD(), sinhVien.getTinHoc(), sinhVien.getCongNghe());
                fw.write(f);
                fw.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void WriteFileCSV(ArrayList<SinhVien> list) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(PATH_CSV_FILE);
            String tilte = "STT,ID,Họ Và Tên,Giới Tính,Ngày Sinh,Quê Quán,Lớp,Toán Học,Vật Lý,Hóa Học,Văn Học,Sinh Học,Lịch Sử,Địa Lý,GDCD,Tin Học,Công nghệ";
            fw.write(tilte);
            fw.write("\n");
            int i = 1;
            for (SinhVien sinhVien : list) {
                String gender = sinhVien.isGender() == true ? "Nam" : "Nữ";
                String dateString = sinhVien.getDayBir().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String f = String.format("%02d,%s,%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", i++, sinhVien.getID(), sinhVien.getHoTen(),
                        gender, sinhVien.getDayBir(), sinhVien.getQueQuan(), sinhVien.getLop(), sinhVien.getToanHoc(), sinhVien.getVatLy(), sinhVien.getHoaHoc(),
                        sinhVien.getNguVan(), sinhVien.getSinhHoc(), sinhVien.getLichSu(), sinhVien.getDiaLy(), sinhVien.getGDCD(), sinhVien.getTinHoc(), sinhVien.getCongNghe());
                fw.write(f);
                fw.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
