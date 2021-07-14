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
        while(true){
            int chon = sc.nextInt();
            switch (chon){
                case 1:{
                    sinhvien.Input();
                    break;
                }
                case 2:{
                    sinhvien.Show();
                    break;
                }
            }
        }
    }
    
}
