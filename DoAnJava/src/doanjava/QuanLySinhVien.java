/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Luc
 */
public class QuanLySinhVien {

    ArrayList<SinhVien> list = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
<<<<<<< HEAD
    
=======

>>>>>>> c98a6ebcb400e9a14d34ec4848aaa16bb6ad0f40
    public void Input() {
// nhập thông tin của sinh viên và điểm sinh viên
        SinhVien sinhvien = new SinhVien();
        System.out.println("Họ và Tên:");
        String hoten = sc.nextLine();
        //phần tiếp theo tách tên lấy ra họ và tên đệm
        sinhvien.setHoDem(hoten.substring(0, hoten.lastIndexOf(" ")));
        sinhvien.setTen(hoten.substring(1 + hoten.lastIndexOf(" ")));
        //mã sinh viên

        String masv;
        while (true) {
            int flag = 0;
            System.out.println("Nhập mã sinh viên:");
            masv = sc.nextLine();
            //Nhập đúng định dạng [AT160160]
            Pattern p = Pattern.compile("^[A-Z]{2}+[0-9]{6}$");
            if (p.matcher(masv).find()) {

            } else {
                System.err.println("<!>Mã sinh viên không hợp lệ, vui lòng nhập lại");
                continue;
            }

            //Xét ID không trùng nhau [key]
            for (SinhVien xet : list) {
                if (masv.equals(xet.getID())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                System.err.println("<!> Mã sinh viên không được trùng nhau, vui lòng nhập lại");
            } else {
                break;
            }
        }
        sinhvien.setID(masv);

        // nhập quê quán
        System.out.println("Quê Quán:");
        sinhvien.setQueQuan(sc.nextLine());

        // nhập lớp sinh viên
        String lop;
        while (true) {
            System.out.println("Class [10A1,10D1]:");
            lop = sc.nextLine();
            Pattern p = Pattern.compile("^[0-9]{2}+[A-Z]{1}+[0-9]{1}$");
            if (p.matcher(lop).find()) {
                break;
            } else {
                System.err.println("<!>Lớp sinh viên không hợp lệ, vui lòng nhập lại!");
            }
        }
        sinhvien.setcLass(lop);

<<<<<<< HEAD
        System.out.println("Lop tin chi:");
        sv.setLopSv(sc.nextLine());
        
=======
>>>>>>> c98a6ebcb400e9a14d34ec4848aaa16bb6ad0f40
        System.out.println("Ngay Thang Nam Sinh:");
        int day = sc.nextInt();
        int mot = sc.nextInt();
        int year = sc.nextInt();
        sc.nextLine();
        LocalDate date = LocalDate.of(year, mot, day);
        sinhvien.setDayBir(date);
        
        //nhập điểm từng môn
        System.out.println("Nhập điểm: ");
        System.out.printf("Toán:");
        double toan = sc.nextDouble();
        sinhvien.setToanHoc(toan);

        System.out.printf("Vật Lý:");
        double vatly = sc.nextDouble();
        sinhvien.setVatLy(vatly);

        System.out.printf("Hóa Học:");
        double hoahoc = sc.nextDouble();
        sinhvien.setHoaHoc(hoahoc);

        System.out.printf("Sinh Học:");
        double sinhhoc = sc.nextDouble();
        sinhvien.setSinhHoc(sinhhoc);

        System.out.printf("Tin Học:");
        double tinhoc = sc.nextDouble();
        sinhvien.setTinHoc(tinhoc);

        System.out.printf("Công Nghệ:");
        double congnghe = sc.nextDouble();
        sinhvien.setCongNghe(congnghe);

        System.out.printf("Ngữ Văn:");
        double nguvan = sc.nextDouble();
        sinhvien.setNguVan(nguvan);

        System.out.printf("Địa Lý:");
        double dialy = sc.nextDouble();
        sinhvien.setDiaLy(dialy);

        System.out.printf("Lịch Sử:");
        double lichsu = sc.nextDouble();
        sinhvien.setLichSu(lichsu);

        System.out.printf("Giáo Dục Công Dân:");
        double gdcd = sc.nextInt();
        sinhvien.setGDCD(gdcd);

        sc.nextLine();
        // thêm vào danh sách sinh viên
        list.add(sinhvien);
    }

    public void Show() {
        int chon = sc.nextInt();
        switch (chon) {
            case 1 -> {
                for (SinhVien sv : list) {
                    sv.xuatThongTin();
                }
            }
            case 2 -> {
                for (SinhVien sv : list) {
                    sv.xuatDiem();
                }
                break;
            }
        }
    }

    public int Search(String a) {
        int i;
        for (i = 0; i < list.size(); i++) {
            SinhVien sinhVien = list.get(i);
            if (a.equals(sinhVien.getID())) {
                return i;
            }
        }
        return -1;
    }

    public void thayThe(int a) {
        SinhVien sinhvien = new SinhVien();
        if (a == -1) {
            System.out.printf("<!>Không Mã %s\n", a);
        } else {
            System.out.println("Họ và Tên:");
            String hoten = sc.nextLine();
            //phần tiếp theo tách tên lấy ra họ và tên đệm
            sinhvien.setHoDem(hoten.substring(0, hoten.lastIndexOf(" ")));
            sinhvien.setTen(hoten.substring(1 + hoten.lastIndexOf(" ")));
            //mã sinh viên
            String masv;

            while (true) {
                int flag = 0;
                System.out.println("Nhập mã sinh viên:");
                masv = sc.nextLine();
                //Nhập đúng định dạng [AT160160]
                Pattern p = Pattern.compile("^[A-Z]{2}+[0-9]{6}$");
                if (p.matcher(masv).find()) {

                } else {
                    System.err.println("<!>Mã sinh viên không hợp lệ, vui lòng nhập lại");
                    continue;
                }

                //Xét ID không trùng nhau [key]
                for (SinhVien xet : list) {
                    if (masv.equals(xet.getID())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    System.err.println("<!> Mã sinh viên không được trùng nhau, vui lòng nhập lại");
                } else {
                    break;
                }
            }
            sinhvien.setID(masv);

            // nhập quê quán
            System.out.println("Quê Quán:");
            sinhvien.setQueQuan(sc.nextLine());
            // nhập lớp sinh viên
            String lop;
            while (true) {
                System.out.println("Class [10A1,10D1]:");
                lop = sc.nextLine();
                Pattern p = Pattern.compile("^[0-9]{2}+[A-Z]{1}+[0-9]{1}$");
                if (p.matcher(lop).find()) {
                    break;
                } else {
                    System.err.println("<!>Lớp sinh viên không hợp lệ, vui lòng nhập lại!");
                }
            }
            sinhvien.setcLass(lop);

            System.out.println("Ngay Thang Nam Sinh:");
            int day = sc.nextInt();
            int mot = sc.nextInt();
            int year = sc.nextInt();
            sc.nextLine();
            LocalDate date = LocalDate.of(year, mot, day);
            sinhvien.setDayBir(date);
            //nhập điểm từng môn
            System.out.println("Nhập điểm: ");
            System.out.printf("Toán:");
            double toan = sc.nextDouble();
            sinhvien.setToanHoc(toan);

            System.out.printf("Vật Lý:");
            double vatly = sc.nextDouble();
            sinhvien.setVatLy(vatly);

            System.out.printf("Hóa Học:");
            double hoahoc = sc.nextDouble();
            sinhvien.setHoaHoc(hoahoc);

            System.out.printf("Sinh Học:");
            double sinhhoc = sc.nextDouble();
            sinhvien.setSinhHoc(sinhhoc);

            System.out.printf("Tin Học:");
            double tinhoc = sc.nextDouble();
            sinhvien.setTinHoc(tinhoc);

            System.out.printf("Công Nghệ:");
            double congnghe = sc.nextDouble();
            sinhvien.setCongNghe(congnghe);

            System.out.printf("Ngữ Văn:");
            double nguvan = sc.nextDouble();
            sinhvien.setNguVan(nguvan);

            System.out.printf("Địa Lý:");
            double dialy = sc.nextDouble();
            sinhvien.setDiaLy(dialy);

            System.out.printf("Lịch Sử:");
            double lichsu = sc.nextDouble();
            sinhvien.setLichSu(lichsu);

            System.out.printf("Giáo Dục Công Dân:");
            double gdcd = sc.nextInt();
            sinhvien.setGDCD(gdcd);

            sc.nextLine();

            list.set(a, sinhvien);
        }
    }

    public void deleteSv(int n) {
        list.remove(n);
    }

    public void sortUp() {
        Collections.sort(list, new nameComparator());
    }

    public void Search_Show() {
        System.out.println("Class:");
        String nameClass = sc.nextLine();

        int i, flag = 0;
        System.out.printf("Danh sách sinh viên lớp %s:\n", nameClass);
        for (i = 0; i < list.size(); i++) {
            SinhVien sinhvien = list.get(i);
            if (nameClass.equals(sinhvien.getcLass())) {
                flag = 1;
                sinhvien.xuatThongTin();
            }
        }
        if (flag == 0) {
            System.out.printf("<!>Class %s không tồn tại!\n", nameClass);
        }
    }
}
