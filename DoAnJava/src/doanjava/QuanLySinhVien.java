/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Luc
 */
public class QuanLySinhVien {
    ArrayList<SinhVien> list = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    
    @SuppressWarnings("empty-statement")
    public void Input() {
        SinhVien sv = new SinhVien();
        System.out.println("ho va ten:");
        String s = sc.nextLine();
        //tách chuoi con lấy ten và họ đệm
        sv.setHoDem(s.substring(0, s.lastIndexOf(" ")));
        sv.setTen(s.substring(1 + s.lastIndexOf(" ")));

        //mã sinh viên không được trùng
        String masv;
        while (true) {
            int flag = 0;
            System.out.println("ma sinh vien:");
            masv = sc.nextLine();
            for (SinhVien sinhvien : list) {
                if (masv.equals(sinhvien.getID())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                System.err.println("<!>Ma sinh vien khong duoc trung...");
            } else {
                break;
            }
        };
        sv.setID(masv);

        System.out.println("Lop tin chi:");
        sv.setLopSv(sc.nextLine());
        
        //Nhập ngày tháng nam sinh bằng localdate
        System.out.println("Ngay Thang Nam Sinh:");
        int day = sc.nextInt();
        int mot = sc.nextInt();
        int year = sc.nextInt();
        sc.nextLine();
        LocalDate date = LocalDate.of(year,mot,day);
        sv.setDayBir(date);
        
        System.out.println("Que quan:");
        sv.setQueQuan(sc.nextLine());

        //thêm vào Danh sách
        list.add(sv);
    }
    
    public void Show(){
        for(SinhVien sinhvien : list){
            sinhvien.xuatThongTin();
        }
    }
}
