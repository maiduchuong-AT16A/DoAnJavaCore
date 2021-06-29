/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class DoAnJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        QuanLySinhVien sinhvien = new QuanLySinhVien();
        Scanner sc = new Scanner(System.in);
        while (true) {
            int chon = sc.nextInt();
            switch (chon) {
                case 1 -> {
                    sinhvien.Input();
                    sinhvien.sortUp();
                    break;
                }
                case 2 -> {
                    sinhvien.Show();
                    break;
                }
                case 3 -> {
                    sc.nextLine();
                    System.out.println("Nhap ma sinh vien can tim: ");
                    String a = sc.nextLine();
                    sinhvien.thayThe(sinhvien.Search(a));
                    sinhvien.sortUp();
                    break;
                }
                case 4 -> {
                    sc.nextLine();
                    System.out.println("Nhap ma sinh vien can tim: ");
                    String a = sc.nextLine();
                    sinhvien.deleteSv(sinhvien.Search(a));
                    break;
                }
                case 5 -> {
                    sinhvien.Search_Show();
                    break;
                }
            }
        }
    }

}
