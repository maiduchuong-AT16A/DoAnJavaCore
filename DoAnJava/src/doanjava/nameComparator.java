/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.util.Comparator;

/**
 *
 * @author ACER
 */
public class nameComparator implements Comparator<SinhVien> {

    @Override
    public int compare(SinhVien o1, SinhVien o2) {
        return o1.getTen().compareTo(o2.getTen());
    }

}
