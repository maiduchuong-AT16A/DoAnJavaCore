/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import doanjava.model.SinhVien;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static void ReadFileJson() {
        FileReader fr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //sdf.parse(currenDir);
            fr = new FileReader(PATH_JSON_FILE);
            Gson gson = new Gson();
            Type classOfT = new TypeToken<ArrayList<SinhVien>>(){}.getType();
//            SinhVien sinhVien = gson.fromJson(fr, SinhVien.class);
            ArrayList<SinhVien> list = gson.fromJson(fr, classOfT);
            for(SinhVien item : list){
                System.out.println(item.toString());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileJson.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void main(String[] args) {
        ReadFileJson();
    }
}
