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
public class nameComparatorAlphabet implements Comparator<SinhVien> {

    @Override
    public int compare(SinhVien o1, SinhVien o2) {
        String s1 = o1.getHoTen().substring(1 + o1.getHoTen().lastIndexOf(" "));
        String s2 = o2.getHoTen().substring(1 + o2.getHoTen().lastIndexOf(" "));
        return s1.compareTo(s2);
    }

}
