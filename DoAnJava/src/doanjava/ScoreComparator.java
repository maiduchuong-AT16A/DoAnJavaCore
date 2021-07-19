/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import doanjava.model.SinhVien;
import java.util.Comparator;

/**
 *
 * @author ACER
 */
public class ScoreComparator implements Comparator<SinhVien>{

    @Override
    public int compare(SinhVien o1, SinhVien o2) {
        if(o1.getDiemTB()>o2.getDiemTB()){
            return -1;
        }else if(o1.getDiemTB()==o2.getDiemTB()){
            return 0;
        }
        return 1;
    }
    
}
