/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import com.sun.source.tree.BreakTree;
import doanjava.model.MonHoc;
import doanjava.model.SinhVien;
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
    private static final String PATH_CSV_FILE_OUPUT = currenDir + separator + "data" + separator + "FileDataOuput.csv";

    public void ReadFileCSV(ArrayList<SinhVien> list) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(PATH_CSV_FILE));
            String line = null;
            String[] titel = new String[10];
            try {
                line = bf.readLine();
                titel = line.split(",");

            } catch (IOException ex) {
                Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while ((line = bf.readLine()) != null) {
                    String[] rd = line.split(",");
                    SinhVien sinhVien = new SinhVien();
                    MonHoc toanMonHoc = new MonHoc();
                    MonHoc lyMonHoc = new MonHoc();
                    MonHoc hoaMonHoc = new MonHoc();
                    MonHoc vanMonHoc = new MonHoc();
                    MonHoc sinhMonHoc = new MonHoc();
                    MonHoc suMonHoc = new MonHoc();
                    MonHoc diaMonHoc = new MonHoc();
                    MonHoc gdcdMonHoc = new MonHoc();
                    MonHoc tinMonHoc = new MonHoc();
                    MonHoc cnMonHoc = new MonHoc();
                    ArrayList<MonHoc> listMH = new ArrayList<>();

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

                    toanMonHoc.setMonHoc(titel[0]);
                    toanMonHoc.setDiem(Double.parseDouble(rd[7]));
                    listMH.add(toanMonHoc);
                    lyMonHoc.setMonHoc(titel[1]);
                    lyMonHoc.setDiem(Double.parseDouble(rd[8]));
                    listMH.add(lyMonHoc);
                    hoaMonHoc.setMonHoc(titel[2]);
                    hoaMonHoc.setDiem(Double.parseDouble(rd[9]));
                    listMH.add(hoaMonHoc);
                    vanMonHoc.setMonHoc(titel[3]);
                    vanMonHoc.setDiem(Double.parseDouble(rd[10]));
                    listMH.add(vanMonHoc);
                    sinhMonHoc.setMonHoc(titel[4]);
                    sinhMonHoc.setDiem(Double.parseDouble(rd[11]));
                    listMH.add(sinhMonHoc);
                    suMonHoc.setMonHoc(titel[5]);
                    suMonHoc.setDiem(Double.parseDouble(rd[12]));
                    listMH.add(suMonHoc);
                    diaMonHoc.setMonHoc(titel[6]);
                    diaMonHoc.setDiem(Double.parseDouble(rd[13]));
                    listMH.add(diaMonHoc);
                    gdcdMonHoc.setMonHoc(titel[7]);
                    gdcdMonHoc.setDiem(Double.parseDouble(rd[14]));
                    listMH.add(gdcdMonHoc);
                    tinMonHoc.setMonHoc(titel[8]);
                    tinMonHoc.setDiem(Double.parseDouble(rd[15]));
                    listMH.add(tinMonHoc);
                    cnMonHoc.setMonHoc(titel[9]);
                    cnMonHoc.setDiem(Double.parseDouble(rd[16]));
                    listMH.add(cnMonHoc);
                    sinhVien.setListDiem(listMH);

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
            int i = 1;
            for (SinhVien sinhVien : list) {
                String gender = sinhVien.isGender() == true ? "Nam" : "Nữ";
                String dateString = sinhVien.getDayBir().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String f = String.format("%02d,%s,%s,%s,%s,%s,%s,%.2f,%s", i++, sinhVien.getID(), sinhVien.getHoTen(),
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
                String diem = "";
                for (MonHoc item : sinhVien.getListDiem()) {
                    diem += String.format(",%.2f", item.getDiem());
                }
                String f = String.format("%02d,%s%s", i++, sinhVien.getID(), diem);
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
                String diem = "";
                for (MonHoc item : sinhVien.getListDiem()) {
                    diem += String.format(",%.2f", item.getDiem());
                }
                String f = String.format("%02d,%s,%s,%s,%s,%s,%s%s", i++, sinhVien.getID(), sinhVien.getHoTen(),
                        gender, sinhVien.getDayBir(), sinhVien.getQueQuan(), sinhVien.getLop(), diem);
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

    public void WriteFileCSVOutPut(ArrayList<SinhVien> list) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(PATH_CSV_FILE_OUPUT);
            String tilte = "STT,ID,Họ Và Tên,Giới Tính,Ngày Sinh,Quê Quán,Lớp,Toán Học,Vật Lý,Hóa Học,Văn Học,Sinh Học,Lịch Sử,Địa Lý,GDCD,Tin Học,Công nghệ";
            fw.write(tilte);
            fw.write("\n");
            int i = 1;
            for (SinhVien sinhVien : list) {
                String gender = sinhVien.isGender() == true ? "Nam" : "Nữ";
                String dateString = sinhVien.getDayBir().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String diem = "";
                for (MonHoc item : sinhVien.getListDiem()) {
                    diem += String.format(",%.2f", item.getDiem());
                }
                String f = String.format("%02d,%s,%s,%s,%s,%s,%s%s", i++, sinhVien.getID(), sinhVien.getHoTen(),
                        gender, sinhVien.getDayBir(), sinhVien.getQueQuan(), sinhVien.getLop(), diem);
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
