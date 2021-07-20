/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import doanjava.model.SinhVien;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class FileJson {

    private static final String currenDir = System.getProperty("user.dir");
    private static final String separator = File.separator;

    private static final String PATH_JSON_FILE = currenDir + separator + "data" + separator + "FileData.json";

    public static ArrayList<SinhVien> ReadFileJson() {
        FileReader fr = null;
        ArrayList<SinhVien> list = new ArrayList<>();
        try {
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //sdf.parse(currenDir);
            fr = new FileReader(PATH_JSON_FILE);
            Gson gson = new Gson();
            Type classOfT = new TypeToken<ArrayList<SinhVien>>() {
            }.getType();
            list = gson.fromJson(fr, classOfT);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileJson.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public static void WriteFileJson(ArrayList<SinhVien> list) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(PATH_JSON_FILE);
            Gson gson = new Gson();
            Type classOfT = new TypeToken<ArrayList<SinhVien>>() {
            }.getType();
            gson.toJson(list, classOfT, fw);
        } catch (IOException ex) {
            Logger.getLogger(FileJson.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(FileJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
