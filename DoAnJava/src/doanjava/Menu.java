/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import doanjava.model.MonHoc;
import doanjava.model.SinhVien;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class Menu extends javax.swing.JFrame {

    private FileCSV fileCSV = new FileCSV();
    private FileJson fileJSON = new FileJson();
    private ArrayList<SinhVien> list = new ArrayList<>();
    private ArrayList<SinhVien> listSearch = new ArrayList<>();
    private ArrayList<SinhVien> listLop = new ArrayList<>();
    DefaultTableModel defaultTableModelInfo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
    };
    DefaultTableModel defaultTableModelDiem = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
    };
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private int flag = 1;
    private int flagMenu = 1;
    private int flagSearch = 0;
    private int flagRepair = 0;
    private int flagFilter = 0;
    private int selectRows = -1;
    private int flagSort = 0;

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        list = fileJSON.ReadFileJson();
        this.setLocationRelativeTo(null);
        initTableInfoMenu();
        initTableInfo();
        showResultInfo();
        displayNoneComboBox();
        filterByClass();
        tablemenu.setComponentPopupMenu(PopUpTable);
    }

    private void displayNoneComboBox() {
        classComBoBox.setVisible(false);
        queQuan.setVisible(false);
        hocLuc.setVisible(false);
    }

    private void initTableInfoMenu() {
        String[] str = new String[]{"STT", "ID", "Name", "Gender", "Birth day", "Address", "Class", "Medium score", "Classification"};
        defaultTableModelInfo.setColumnIdentifiers(str);
        tablemenu.setModel(defaultTableModelInfo);
        show.setText("Show scoreboard");
    }

    private void initTableDiemMenu() {
        String[] str = new String[]{"STT", "ID", "Maths", "Physical", "Chemistry", "Literature", "Biology", "History", "Geography", "Civic Education", "Informatics", "Technology"};
        defaultTableModelDiem.setColumnIdentifiers(str);
        tablemenu.setModel(defaultTableModelDiem);
        show.setText("Info panel display");
    }

    @SuppressWarnings("empty-statement")
    private void initTableInfo() {
        String[] str = new String[]{"STT", "ID", "Name", "Gender", "Birth day", "Address", "Class", "Medium score", "Classification"};
        defaultTableModelInfo.setColumnIdentifiers(str);
        table.setModel(defaultTableModelInfo);
        show.setText("Show scoreboard");
    }

    private void initTableDiem() {
        String[] str = new String[]{"STT", "ID", "Maths", "Physical", "Chemistry", "Literature", "Biology", "History", "Geography", "Civic Education", "Informatics", "Technology"};
        defaultTableModelDiem.setColumnIdentifiers(str);
        table.setModel(defaultTableModelDiem);
        show.setText("Info panel display");
    }

    private void showResultDiem() {
        int i = 1;
        defaultTableModelDiem.setRowCount(0);
        for (SinhVien sinhvien : list) {
            String diem = "";
            for (MonHoc item : sinhvien.getListDiem()) {
                diem += String.format("%.2f", item.getDiem());
                if (diem.equals(sinhvien.getListDiem().get(sinhvien.getListDiem().size() - 1))) {
                    break;
                }
                diem += ",";
            }
            String[] diemMon = diem.split(",");
            Object[] str = new Object[]{
                i++, sinhvien.getID(), diemMon[0], diemMon[1], diemMon[2], diemMon[3], diemMon[4], diemMon[5], diemMon[6], diemMon[7], diemMon[8], diemMon[9]};
            defaultTableModelDiem.addRow(str);
        }
        defaultTableModelDiem.fireTableDataChanged();
    }

    private void showResultInfo() {
        int i = 1;
        defaultTableModelInfo.setRowCount(0);
        for (SinhVien sinhvien : list) {
            String dateString = sdf.format(sinhvien.getDayBir());
            String diemTB = String.format("%.2f", sinhvien.getDiemTB());
            Object[] str = new Object[]{i++, sinhvien.getID(), sinhvien.getHoTen(), sinhvien.isGender() ? "Nam" : "Nữ", dateString, sinhvien.getQueQuan(), sinhvien.getLop(), diemTB, sinhvien.xepLoaiHocLuc()};
            defaultTableModelInfo.addRow(str);
        }
        defaultTableModelInfo.fireTableDataChanged();
    }

    public boolean validateForm() {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty()
                || txtNgay.getText().isEmpty() || txtThang.getText().isEmpty() || txtNam.getText().isEmpty()
                || txtGDCD.getText().isEmpty() || txtDia.getText().isEmpty() || txtCongNghe.getText().isEmpty()
                || txtHoa.getText().isEmpty() || txtLy.getText().isEmpty() || txtSinh.getText().isEmpty()
                || txtTin.getText().isEmpty() || txtToan.getText().isEmpty() || txtVan.getText().isEmpty()) {
            return false;
        }
        for (SinhVien sinhVien : list) {
            if (txtID.getText().equalsIgnoreCase(sinhVien.getID())) {
                txtID.setText("");
                return false;
            }
        }
        return true;
    }

    private boolean kt(String lop, int n) {
        for (int i = 0; i < n; i++) {
            SinhVien sinhVien = list.get(i);
            if (lop.equalsIgnoreCase(sinhVien.getLop())) {
                return false;
            }
        }
        return true;
    }

    private void filterByClass() {
        int i = -1;
        listLop.clear();
        for (SinhVien sinhVien : list) {
            i++;
            if (kt(sinhVien.getLop(), i) == true) {
                listLop.add(sinhVien);
            }
        }
        listLop.sort(new ClassComparator());
        classComBoBox.removeAllItems();
        classComBoBox.addItem("Class");
        for (SinhVien sinhVien : listLop) {
            classComBoBox.addItem(sinhVien.getLop());
        }
    }

    public String formatName(String str) {
        String namString = "";
        String[] nameStrings = str.split(" ");
        int i = -1;
        for (String string : nameStrings) {
            i++;
            char[] n = string.toCharArray();
            String string1 = String.format("%c", n[0]);
            n[0] = string1.toUpperCase().charAt(0);
            for (int j = 1; j < n.length; j++) {
                String string2 = String.format("%c", n[j]);
                n[j] = string2.toLowerCase().charAt(0);
            }
            nameStrings[i] = String.valueOf(n);
        }
        i = -1;
        for (String string : nameStrings) {
            i++;
            namString += string;
            if (i != nameStrings.length - 1) {
                namString += " ";
            }
        }
        return namString;
    }

    public SinhVien getNhap() {

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

        sinhVien.setID(txtID.getText().toUpperCase());
//Tên

        sinhVien.setHoTen(formatName(txtName.getText()));
        if (rdNu.isSelected()) {
            sinhVien.setGender(false);
        } else {
            sinhVien.setGender(true);
        }
        sinhVien.setLop(txtLop.getText().toUpperCase());

        int day = Integer.parseInt(txtNgay.getText());
        int month = Integer.parseInt(this.txtThang.getText());
        int year = Integer.parseInt(this.txtNam.getText());
        String dateString = String.format("%d/%d/%d", day, month, year);
        Date a = null;
        try {
            a = sdf.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        sinhVien.setDayBir(a);

        sinhVien.setQueQuan((String) TxtAddress.getSelectedItem());

//        monHoc[0].setMonHoc(jLabelToan.getText());
//        monHoc[0].setDiem(Double.parseDouble(txtToan.getText()));
//        listMH.add(monHoc[0]);
        toanMonHoc.setMonHoc(jLabelToan.getText());
        toanMonHoc.setDiem(Double.parseDouble(txtToan.getText()));
        listMH.add(toanMonHoc);
        lyMonHoc.setMonHoc(jLabelLy.getText());
        lyMonHoc.setDiem(Double.parseDouble(txtLy.getText()));
        listMH.add(lyMonHoc);
        hoaMonHoc.setMonHoc(jLabelHoa.getText());
        hoaMonHoc.setDiem(Double.parseDouble(txtHoa.getText()));
        listMH.add(hoaMonHoc);
        vanMonHoc.setMonHoc(jLabelVan.getText());
        vanMonHoc.setDiem(Double.parseDouble(txtVan.getText()));
        listMH.add(vanMonHoc);
        sinhMonHoc.setMonHoc(jLabelSinh.getText());
        sinhMonHoc.setDiem(Double.parseDouble(txtSinh.getText()));
        listMH.add(sinhMonHoc);
        suMonHoc.setMonHoc(jLabel1Su.getText());
        suMonHoc.setDiem(Double.parseDouble(txtSu.getText()));
        listMH.add(suMonHoc);
        diaMonHoc.setMonHoc(jLabelDia.getText());
        diaMonHoc.setDiem(Double.parseDouble(txtDia.getText()));
        listMH.add(diaMonHoc);
        gdcdMonHoc.setMonHoc(jLabelGdcd.getText());
        gdcdMonHoc.setDiem(Double.parseDouble(txtGDCD.getText()));
        listMH.add(gdcdMonHoc);
        tinMonHoc.setMonHoc(jLabelTin.getText());
        tinMonHoc.setDiem(Double.parseDouble(txtTin.getText()));
        listMH.add(tinMonHoc);
        cnMonHoc.setMonHoc(jLabelCn.getText());
        cnMonHoc.setDiem(Double.parseDouble(txtCongNghe.getText()));
        listMH.add(cnMonHoc);

        sinhVien.setListDiem(listMH);
        return sinhVien;
    }

    private void RepairTableMenu() {
        selectRows = tablemenu.getSelectedRow();
        if (selectRows >= 0) {
            String ID1 = (String) tablemenu.getValueAt(selectRows, 1);
            for (flagRepair = 0; flagRepair < list.size(); flagRepair++) {
                SinhVien sinhVien = list.get(flagRepair);
                if (ID1.equals(sinhVien.getID())) {

                    txtIDRepair.setText(ID1);
                    txtNameRepair.setText(sinhVien.getHoTen());
                    txtLopRepair.setText(sinhVien.getLop().toUpperCase());

                    if (sinhVien.isGender() == false) {
                        rdNuRepair.setSelected(true);
                    } else {
                        rdNamRepair.setSelected(true);
                    }

                    String dateString = sdf.format(sinhVien.getDayBir());
                    String[] b = dateString.split("/");
                    txtNgayRepair.setText(b[0]);
                    txtThangRepair.setText(b[1]);
                    txtNamRepair.setText(b[2]);
                    TxtAddressRepair.setSelectedItem(sinhVien.getQueQuan());

                    String diem = "";
                    for (MonHoc item : sinhVien.getListDiem()) {
                        diem += String.format("%.2f", item.getDiem());
                        if (diem.equals(sinhVien.getListDiem().get(sinhVien.getListDiem().size() - 1))) {
                            break;
                        }
                        diem += ",";
                    }
                    String[] diemMon = diem.split(",");
                    txtToanRepair.setText(diemMon[0] + "");
                    txtLyRepair.setText(diemMon[1] + "");
                    txtHoaRepair.setText(diemMon[2] + "");
                    txtVanRepair.setText(diemMon[3] + "");
                    txtSinhRepair.setText(diemMon[4] + "");
                    txtSuRepair.setText(diemMon[5] + "");
                    txtDialyRepair.setText(diemMon[6] + "");
                    txtGDCDRepair.setText(diemMon[7] + "");
                    txtTinRepair.setText(diemMon[8] + "");
                    txtCongNgheRepair.setText(diemMon[9] + "");
                    break;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Add = new javax.swing.JDialog();
        txtName = new javax.swing.JTextField();
        txtCongNghe = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabelCn = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLy = new javax.swing.JTextField();
        txtNgay = new javax.swing.JTextField();
        txtDia = new javax.swing.JTextField();
        txtThang = new javax.swing.JTextField();
        jLabelGdcd = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtGDCD = new javax.swing.JTextField();
        txtVan = new javax.swing.JTextField();
        jLabelToan = new javax.swing.JLabel();
        jLabelVan = new javax.swing.JLabel();
        jLabelHoa = new javax.swing.JLabel();
        jLabelLy = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        jLabelSinh = new javax.swing.JLabel();
        rdNu = new javax.swing.JRadioButton();
        jLabelTin = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelDia = new javax.swing.JLabel();
        txtLop = new javax.swing.JTextField();
        jLabel1Su = new javax.swing.JLabel();
        Add1 = new javax.swing.JToggleButton();
        txtToan = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        txtHoa = new javax.swing.JTextField();
        show = new javax.swing.JButton();
        txtSinh = new javax.swing.JTextField();
        ExitAdd = new javax.swing.JButton();
        txtSu = new javax.swing.JTextField();
        txtNam = new javax.swing.JTextField();
        txtTin = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        TxtAddress = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        PopUpTable = new javax.swing.JPopupMenu();
        DeletePopup = new javax.swing.JMenuItem();
        RepairPopup = new javax.swing.JMenuItem();
        Repair = new javax.swing.JDialog();
        txtNameRepair = new javax.swing.JTextField();
        txtSuRepair = new javax.swing.JTextField();
        txtCongNgheRepair = new javax.swing.JTextField();
        txtNamRepair = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTinRepair = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        rdNamRepair = new javax.swing.JRadioButton();
        txtIDRepair = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        TxtAddressRepair = new javax.swing.JComboBox<>();
        rdNuRepair = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtDia1 = new javax.swing.JLabel();
        txtLopRepair = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtLyRepair = new javax.swing.JTextField();
        txtToanRepair = new javax.swing.JTextField();
        txtNgayRepair = new javax.swing.JTextField();
        txtDialyRepair = new javax.swing.JTextField();
        txtHoaRepair = new javax.swing.JTextField();
        txtThangRepair = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtSinhRepair = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtGDCDRepair = new javax.swing.JTextField();
        txtVanRepair = new javax.swing.JTextField();
        RepairDialog = new javax.swing.JButton();
        ExitRepair = new javax.swing.JButton();
        Search = new javax.swing.JDialog();
        SearchTxt = new javax.swing.JTextField();
        SearchBotton = new javax.swing.JButton();
        HelpDialog = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablemenu = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        AddToolBar = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        RepairToolBar = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        showToolBar = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        SearchToolBar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        FilterToolBar = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        ExportFile = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton10 = new javax.swing.JButton();
        nameTable = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        hocLuc = new javax.swing.JComboBox<>();
        classComBoBox = new javax.swing.JComboBox<>();
        queQuan = new javax.swing.JComboBox<>();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        Save = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        AddMenu = new javax.swing.JMenuItem();
        RepairMenu = new javax.swing.JMenuItem();
        DeleteMenu = new javax.swing.JMenuItem();
        clearMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        RefreshMenu = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        showMenuDiem = new javax.swing.JMenuItem();
        showMenuInfo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        FilterMenu = new javax.swing.JMenuItem();
        SearchMenu = new javax.swing.JMenuItem();
        SortScores = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        SortAphabet = new javax.swing.JMenuItem();
        ReAlphabet = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        decrease = new javax.swing.JMenuItem();
        ascending = new javax.swing.JMenuItem();
        ClassSort = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        AddMenu3 = new javax.swing.JMenuItem();
        jMenu16 = new javax.swing.JMenu();

        Add.setTitle("Thêm Sinh Viên");
        Add.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Add.setResizable(false);

        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });

        txtCongNghe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCongNgheFocusLost(evt);
            }
        });

        jLabel2.setText("Name");

        jLabelCn.setText("Technology");

        jLabel4.setText("Day Bir");

        txtLy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLyFocusLost(evt);
            }
        });
        txtLy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLyActionPerformed(evt);
            }
        });

        txtNgay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayFocusLost(evt);
            }
        });
        txtNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayActionPerformed(evt);
            }
        });

        txtDia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiaFocusLost(evt);
            }
        });

        txtThang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtThangFocusLost(evt);
            }
        });
        txtThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThangActionPerformed(evt);
            }
        });

        jLabelGdcd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelGdcd.setText("C.Education");

        jLabel5.setText("Address");

        txtGDCD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGDCDFocusLost(evt);
            }
        });
        txtGDCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGDCDActionPerformed(evt);
            }
        });

        txtVan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVanFocusLost(evt);
            }
        });
        txtVan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVanActionPerformed(evt);
            }
        });

        jLabelToan.setText("Maths");

        jLabelVan.setText("Literature");

        jLabelHoa.setText("Chemistry");

        jLabelLy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelLy.setText("Physical");

        jLabel14.setText("Gender");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");
        rdNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamActionPerformed(evt);
            }
        });

        jLabelSinh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSinh.setText("Biology");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        jLabelTin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTin.setText("Informatics");

        jLabel3.setText("Class");

        jLabelDia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelDia.setText("Geography");

        txtLop.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLopFocusLost(evt);
            }
        });
        txtLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLopActionPerformed(evt);
            }
        });

        jLabel1Su.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1Su.setText("History");

        Add1.setText("Add");
        Add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add1ActionPerformed(evt);
            }
        });

        txtToan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtToanFocusLost(evt);
            }
        });

        jButton3.setText("New");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtHoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoaFocusLost(evt);
            }
        });
        txtHoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoaActionPerformed(evt);
            }
        });

        show.setText("Hiển Thị Bảng Điểm");
        show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showActionPerformed(evt);
            }
        });

        txtSinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSinhFocusLost(evt);
            }
        });

        ExitAdd.setText("Exit");
        ExitAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitAddActionPerformed(evt);
            }
        });

        txtSu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSuFocusLost(evt);
            }
        });

        txtNam.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamFocusLost(evt);
            }
        });

        txtTin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTinFocusLost(evt);
            }
        });
        txtTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTinActionPerformed(evt);
            }
        });

        jLabel16.setText("        ID");

        txtID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIDFocusLost(evt);
            }
        });
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        TxtAddress.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang\t", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình\t", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên\t", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM" }));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Thêm thông tin học sinh");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Ngày sinh", "Quê", "Lớp", "ĐiểmTB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout AddLayout = new javax.swing.GroupLayout(Add.getContentPane());
        Add.getContentPane().setLayout(AddLayout);
        AddLayout.setHorizontalGroup(
            AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AddLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(Add1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ExitAdd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(AddLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addComponent(rdNam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdNu))
                    .addComponent(txtID)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNam))
                    .addComponent(txtName)
                    .addComponent(txtLop)
                    .addComponent(TxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelToan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelHoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelVan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1Su, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelLy, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSu, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLy, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addComponent(jLabelSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddLayout.createSequentialGroup()
                                .addComponent(jLabelCn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCongNghe, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(AddLayout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addComponent(jLabelGdcd))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabelDia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTin, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtGDCD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(87, 87, 87))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        AddLayout.setVerticalGroup(
            AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(3, 3, 3)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdNu)
                            .addComponent(rdNam)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelToan)
                            .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelLy))
                        .addGap(14, 14, 14)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelHoa)
                            .addComponent(txtHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1Su))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelVan)))
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCn)
                            .addComponent(txtCongNghe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelSinh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTin, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGDCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelGdcd))))
                .addGap(29, 29, 29)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add1)
                    .addComponent(jButton3)
                    .addComponent(show)
                    .addComponent(ExitAdd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Add.getAccessibleContext().setAccessibleDescription("");

        DeletePopup.setText("Delete");
        DeletePopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletePopupActionPerformed(evt);
            }
        });
        PopUpTable.add(DeletePopup);

        RepairPopup.setText("Repair");
        RepairPopup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RepairPopupMouseClicked(evt);
            }
        });
        RepairPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepairPopupActionPerformed(evt);
            }
        });
        PopUpTable.add(RepairPopup);

        Repair.setTitle("Repair");
        Repair.setIconImage(null);

        txtNameRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameRepairFocusLost(evt);
            }
        });

        txtSuRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSuRepairFocusLost(evt);
            }
        });

        txtCongNgheRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCongNgheRepairFocusLost(evt);
            }
        });

        txtNamRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNamRepairFocusLost(evt);
            }
        });

        jLabel17.setText("Name");

        jLabel18.setText("Maths");

        txtTinRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTinRepairFocusLost(evt);
            }
        });
        txtTinRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTinRepairActionPerformed(evt);
            }
        });

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Literature");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Chemistry");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Physical");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Gender");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("        ID");

        buttonGroup1.add(rdNamRepair);
        rdNamRepair.setText("Nam");
        rdNamRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamRepairActionPerformed(evt);
            }
        });

        txtIDRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIDRepairFocusLost(evt);
            }
        });
        txtIDRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDRepairActionPerformed(evt);
            }
        });

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Biology");

        TxtAddressRepair.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang\t", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình\t", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên\t", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM" }));

        buttonGroup1.add(rdNuRepair);
        rdNuRepair.setText("Nữ");

        jLabel25.setText("Informatics");

        jLabel26.setText("Class");

        txtDia1.setText("Geography");

        txtLopRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLopRepairFocusLost(evt);
            }
        });
        txtLopRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLopRepairActionPerformed(evt);
            }
        });

        jLabel27.setText("Technology");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("History");

        jLabel29.setText("Day Bir");

        txtLyRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLyRepairFocusLost(evt);
            }
        });
        txtLyRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLyRepairActionPerformed(evt);
            }
        });

        txtToanRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtToanRepairFocusLost(evt);
            }
        });
        txtToanRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtToanRepairActionPerformed(evt);
            }
        });

        txtNgayRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayRepairFocusLost(evt);
            }
        });
        txtNgayRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayRepairActionPerformed(evt);
            }
        });

        txtDialyRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDialyRepairFocusLost(evt);
            }
        });

        txtHoaRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoaRepairFocusLost(evt);
            }
        });
        txtHoaRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoaRepairActionPerformed(evt);
            }
        });

        txtThangRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtThangRepairFocusLost(evt);
            }
        });
        txtThangRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThangRepairActionPerformed(evt);
            }
        });

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("C.Education");

        txtSinhRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSinhRepairFocusLost(evt);
            }
        });

        jLabel31.setText("Address");

        txtGDCDRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGDCDRepairFocusLost(evt);
            }
        });
        txtGDCDRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGDCDRepairActionPerformed(evt);
            }
        });

        txtVanRepair.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVanRepairFocusLost(evt);
            }
        });
        txtVanRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVanRepairActionPerformed(evt);
            }
        });

        RepairDialog.setText("Repair");
        RepairDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepairDialogActionPerformed(evt);
            }
        });

        ExitRepair.setText("Exit");
        ExitRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitRepairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RepairLayout = new javax.swing.GroupLayout(Repair.getContentPane());
        Repair.getContentPane().setLayout(RepairLayout);
        RepairLayout.setHorizontalGroup(
            RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RepairLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RepairLayout.createSequentialGroup()
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel26)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNameRepair)
                            .addGroup(RepairLayout.createSequentialGroup()
                                .addComponent(rdNamRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdNuRepair))
                            .addGroup(RepairLayout.createSequentialGroup()
                                .addComponent(txtNgayRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtThangRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNamRepair))
                            .addComponent(TxtAddressRepair, 0, 155, Short.MAX_VALUE)
                            .addComponent(txtLopRepair)
                            .addComponent(txtIDRepair))
                        .addGap(18, 18, 18)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel18)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(RepairLayout.createSequentialGroup()
                                .addComponent(txtToanRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCongNgheRepair))
                            .addGroup(RepairLayout.createSequentialGroup()
                                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVanRepair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSuRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLyRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoaRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RepairLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSinhRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(RepairLayout.createSequentialGroup()
                                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(RepairLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtDia1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(RepairLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTinRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDialyRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGDCDRepair, javax.swing.GroupLayout.Alignment.TRAILING))))))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RepairLayout.createSequentialGroup()
                        .addComponent(RepairDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ExitRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188))))
        );
        RepairLayout.setVerticalGroup(
            RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RepairLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RepairLayout.createSequentialGroup()
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27)
                                .addComponent(jLabel18))
                            .addComponent(txtToanRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCongNgheRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLyRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSinhRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel21))
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RepairLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(txtHoaRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(RepairLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDialyRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTinRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSuRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtVanRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19))
                            .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtGDCDRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30))))
                    .addGroup(RepairLayout.createSequentialGroup()
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNameRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(3, 3, 3)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(rdNuRepair)
                            .addComponent(rdNamRepair))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtAddressRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addGap(10, 10, 10)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(txtNamRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThangRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLopRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))))
                .addGap(18, 18, 18)
                .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RepairDialog)
                    .addComponent(ExitRepair))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rdNamRepair.getAccessibleContext().setAccessibleDescription("");

        Search.setTitle("Search");

        SearchBotton.setText("Search");
        SearchBotton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBottonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SearchLayout = new javax.swing.GroupLayout(Search.getContentPane());
        Search.getContentPane().setLayout(SearchLayout);
        SearchLayout.setHorizontalGroup(
            SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(SearchTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchBotton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        SearchLayout.setVerticalGroup(
            SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBotton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("\n\nSave : Lưu file                 \tCtrl+S.\nAdd  : Thêm học sinh      \tCtrl - Shift+A.\nRepair:Sửa Thông tin     \tCtrl+G.\nDelete :Xóa học sinh       \tCtrl+D.\nClear  :Xóa Danh Sách.\nRefresh:Cập nhật.\nView(xem):\n\t+ Scores Table:Bảng Điểm  \t\t\tCtrl - Shift +P \n\t+ Infomation Table:Bảng Thông Tin Học Sinh\t\tCtrl - Shift +I  \nFilter(lọc) :(Lưu ý: có thể lọc nhiều trường)\n\t+ Filter by class:Lọc theo lớp \n\t+ Fiilter by address: Lọc theo quê quán.\n\t+ Filter by learning:Lọc theo học lực \nSearch: Tìm kiếm học sinh\nExport file :xuất file    ");
        jTextArea1.setEnabled(false);
        jScrollPane3.setViewportView(jTextArea1);

        jPanel1.setBackground(new java.awt.Color(51, 204, 0));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Hướng dẫn sử dụng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        javax.swing.GroupLayout HelpDialogLayout = new javax.swing.GroupLayout(HelpDialog.getContentPane());
        HelpDialog.getContentPane().setLayout(HelpDialogLayout);
        HelpDialogLayout.setHorizontalGroup(
            HelpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        HelpDialogLayout.setVerticalGroup(
            HelpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpDialogLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
        );

        jFileChooser1.setCurrentDirectory(new java.io.File("C:\\Users\\ACER\\Desktop"));
        jFileChooser1.setDialogTitle("");
        jFileChooser1.setFileHidingEnabled(false);
        jFileChooser1.setMinimumSize(new java.awt.Dimension(425, 400));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Phần Mềm Quản Lý Học Sinh");
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tablemenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablemenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablemenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablemenu);

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);
        jToolBar2.setEnabled(false);

        AddToolBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AddToolBar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore - Copy\\images\\add-user.png")); // NOI18N
        AddToolBar.setText("Add");
        AddToolBar.setFocusable(false);
        AddToolBar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddToolBar.setIconTextGap(1);
        AddToolBar.setMargin(new java.awt.Insets(2, 16, 2, 16));
        AddToolBar.setMaximumSize(new java.awt.Dimension(90, 75));
        AddToolBar.setMinimumSize(new java.awt.Dimension(91, 75));
        AddToolBar.setPreferredSize(new java.awt.Dimension(100, 100));
        AddToolBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        AddToolBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToolBarActionPerformed(evt);
            }
        });
        jToolBar2.add(AddToolBar);
        jToolBar2.add(jSeparator5);

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\remove-user.png")); // NOI18N
        jButton6.setText("Delete");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setIconTextGap(0);
        jButton6.setMargin(new java.awt.Insets(2, 16, 2, 16));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);
        jToolBar2.add(jSeparator7);

        RepairToolBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        RepairToolBar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\settings.png")); // NOI18N
        RepairToolBar.setText("Repair");
        RepairToolBar.setFocusable(false);
        RepairToolBar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RepairToolBar.setIconTextGap(1);
        RepairToolBar.setMargin(new java.awt.Insets(2, 16, 2, 16));
        RepairToolBar.setMaximumSize(new java.awt.Dimension(91, 75));
        RepairToolBar.setMinimumSize(new java.awt.Dimension(91, 75));
        RepairToolBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        RepairToolBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepairToolBarActionPerformed(evt);
            }
        });
        jToolBar2.add(RepairToolBar);
        jToolBar2.add(jSeparator8);

        showToolBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        showToolBar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\view.png")); // NOI18N
        showToolBar.setText("Show");
        showToolBar.setFocusable(false);
        showToolBar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showToolBar.setIconTextGap(1);
        showToolBar.setMargin(new java.awt.Insets(2, 16, 2, 16));
        showToolBar.setMaximumSize(new java.awt.Dimension(91, 75));
        showToolBar.setMinimumSize(new java.awt.Dimension(91, 75));
        showToolBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showToolBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToolBarActionPerformed(evt);
            }
        });
        jToolBar2.add(showToolBar);
        jToolBar2.add(jSeparator10);

        SearchToolBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SearchToolBar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\loupe.png")); // NOI18N
        SearchToolBar.setText("Search");
        SearchToolBar.setFocusable(false);
        SearchToolBar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SearchToolBar.setIconTextGap(1);
        SearchToolBar.setMargin(new java.awt.Insets(2, 16, 2, 16));
        SearchToolBar.setMaximumSize(new java.awt.Dimension(91, 75));
        SearchToolBar.setMinimumSize(new java.awt.Dimension(91, 75));
        SearchToolBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        SearchToolBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchToolBarMouseClicked(evt);
            }
        });
        SearchToolBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchToolBarActionPerformed(evt);
            }
        });
        jToolBar2.add(SearchToolBar);
        jToolBar2.add(jSeparator4);

        FilterToolBar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        FilterToolBar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore - Copy\\images\\filter.png")); // NOI18N
        FilterToolBar.setText("Filter");
        FilterToolBar.setFocusable(false);
        FilterToolBar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FilterToolBar.setIconTextGap(1);
        FilterToolBar.setMargin(new java.awt.Insets(2, 16, 2, 16));
        FilterToolBar.setMaximumSize(new java.awt.Dimension(91, 75));
        FilterToolBar.setMinimumSize(new java.awt.Dimension(91, 75));
        FilterToolBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FilterToolBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterToolBarActionPerformed(evt);
            }
        });
        jToolBar2.add(FilterToolBar);
        jToolBar2.add(jSeparator11);

        ExportFile.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ExportFile.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore - Copy\\images\\log-out-1.png")); // NOI18N
        ExportFile.setText("Export file");
        ExportFile.setFocusable(false);
        ExportFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ExportFile.setIconTextGap(1);
        ExportFile.setMargin(new java.awt.Insets(2, 16, 2, 16));
        ExportFile.setMaximumSize(new java.awt.Dimension(91, 75));
        ExportFile.setMinimumSize(new java.awt.Dimension(91, 75));
        ExportFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ExportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportFileActionPerformed(evt);
            }
        });
        jToolBar2.add(ExportFile);
        jToolBar2.add(jSeparator6);

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\info.png")); // NOI18N
        jButton10.setText("Info");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setIconTextGap(1);
        jButton10.setMargin(new java.awt.Insets(2, 16, 2, 16));
        jButton10.setMaximumSize(new java.awt.Dimension(91, 75));
        jButton10.setMinimumSize(new java.awt.Dimension(91, 75));
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton10);

        nameTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameTable.setText("Infomation Table");
        nameTable.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        nameTable.setMargin(new java.awt.Insets(4, 16, 4, 16));
        nameTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTableActionPerformed(evt);
            }
        });

        hocLuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Academic ability", "Giỏi", "Tiên tiến", "Trung Bình", "Yếu" }));
        hocLuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hocLucActionPerformed(evt);
            }
        });

        classComBoBox.setMaximumRowCount(12);
        classComBoBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Class" }));
        classComBoBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classComBoBoxActionPerformed(evt);
            }
        });

        queQuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Address", "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM" }));
        queQuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queQuanActionPerformed(evt);
            }
        });

        jMenuBar2.setPreferredSize(new java.awt.Dimension(367, 25));

        jMenu4.setText("File");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu4.setMargin(new java.awt.Insets(0, 7, 0, 7));
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });

        Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Save.setText("Save");
        Save.setIconTextGap(0);
        Save.setPreferredSize(new java.awt.Dimension(230, 28));
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        jMenu4.add(Save);
        jMenu4.add(jSeparator1);

        jMenuItem5.setText("Exit");
        jMenuItem5.setIconTextGap(2);
        jMenuItem5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jMenuItem5.setPreferredSize(new java.awt.Dimension(230, 28));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuBar2.add(jMenu4);

        jMenu7.setText("Edit");
        jMenu7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu7.setMargin(new java.awt.Insets(0, 7, 0, 7));

        AddMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        AddMenu.setText("Add");
        AddMenu.setIconTextGap(2);
        AddMenu.setMargin(new java.awt.Insets(0, 2, 0, 0));
        AddMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenuActionPerformed(evt);
            }
        });
        jMenu7.add(AddMenu);

        RepairMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        RepairMenu.setText("Repair");
        RepairMenu.setIconTextGap(2);
        RepairMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        RepairMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepairMenuActionPerformed(evt);
            }
        });
        jMenu7.add(RepairMenu);

        DeleteMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        DeleteMenu.setText("Delete");
        DeleteMenu.setIconTextGap(2);
        DeleteMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        DeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteMenuActionPerformed(evt);
            }
        });
        jMenu7.add(DeleteMenu);

        clearMenu.setText("Clear");
        clearMenu.setIconTextGap(2);
        clearMenu.setMargin(new java.awt.Insets(0, 2, 0, -27));
        clearMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        clearMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearMenuActionPerformed(evt);
            }
        });
        jMenu7.add(clearMenu);
        jMenu7.add(jSeparator2);

        RefreshMenu.setText("Refresh");
        RefreshMenu.setIconTextGap(2);
        RefreshMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        RefreshMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshMenuActionPerformed(evt);
            }
        });
        jMenu7.add(RefreshMenu);

        jMenuBar2.add(jMenu7);

        jMenu12.setText("View");
        jMenu12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu12.setMargin(new java.awt.Insets(0, 7, 0, 7));

        showMenuDiem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        showMenuDiem.setText("Scores Table");
        showMenuDiem.setIconTextGap(2);
        showMenuDiem.setMargin(new java.awt.Insets(0, 2, 0, 0));
        showMenuDiem.setPreferredSize(new java.awt.Dimension(230, 28));
        showMenuDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMenuDiemActionPerformed(evt);
            }
        });
        jMenu12.add(showMenuDiem);

        showMenuInfo.setText("Infomation Table");
        showMenuInfo.setIconTextGap(2);
        showMenuInfo.setMargin(new java.awt.Insets(0, 2, 0, 0));
        showMenuInfo.setPreferredSize(new java.awt.Dimension(230, 28));
        showMenuInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMenuInfoActionPerformed(evt);
            }
        });
        jMenu12.add(showMenuInfo);

        jMenuBar2.add(jMenu12);

        jMenu2.setText("Filter");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        FilterMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        FilterMenu.setText("Filter");
        FilterMenu.setIconTextGap(2);
        FilterMenu.setMargin(new java.awt.Insets(0, 2, 0, 0));
        FilterMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        FilterMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterMenuActionPerformed(evt);
            }
        });
        jMenu2.add(FilterMenu);

        SearchMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        SearchMenu.setText("Search");
        SearchMenu.setToolTipText("");
        SearchMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SearchMenu.setIconTextGap(2);
        SearchMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        SearchMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchMenuActionPerformed(evt);
            }
        });
        jMenu2.add(SearchMenu);

        jMenuBar2.add(jMenu2);

        SortScores.setText("Sort");
        SortScores.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SortScores.setMargin(new java.awt.Insets(0, 7, 0, 7));

        jMenu1.setText("Name Sort");
        jMenu1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jMenu1.setPreferredSize(new java.awt.Dimension(230, 28));

        SortAphabet.setText("Alphabet ");
        SortAphabet.setIconTextGap(2);
        SortAphabet.setMargin(new java.awt.Insets(0, 0, 0, 0));
        SortAphabet.setPreferredSize(new java.awt.Dimension(230, 28));
        SortAphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SortAphabetActionPerformed(evt);
            }
        });
        jMenu1.add(SortAphabet);

        ReAlphabet.setText("Reverse Alphabet ");
        ReAlphabet.setIconTextGap(2);
        ReAlphabet.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ReAlphabet.setPreferredSize(new java.awt.Dimension(230, 28));
        ReAlphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReAlphabetActionPerformed(evt);
            }
        });
        jMenu1.add(ReAlphabet);

        SortScores.add(jMenu1);

        jMenu3.setText("Score Sort");

        decrease.setText("Decrease");
        decrease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decreaseActionPerformed(evt);
            }
        });
        jMenu3.add(decrease);

        ascending.setText("Ascending");
        ascending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ascendingActionPerformed(evt);
            }
        });
        jMenu3.add(ascending);

        SortScores.add(jMenu3);

        ClassSort.setText("Class Sort");
        ClassSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassSortActionPerformed(evt);
            }
        });
        SortScores.add(ClassSort);

        jMenuBar2.add(SortScores);

        jMenu15.setText("Help");
        jMenu15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu15.setMargin(new java.awt.Insets(0, 7, 0, 7));

        AddMenu3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        AddMenu3.setText("Infomation");
        AddMenu3.setIconTextGap(2);
        AddMenu3.setMargin(new java.awt.Insets(0, 2, 0, 0));
        AddMenu3.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenu3ActionPerformed(evt);
            }
        });
        jMenu15.add(AddMenu3);

        jMenuBar2.add(jMenu15);

        jMenu16.setText("Exit");
        jMenu16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu16.setMargin(new java.awt.Insets(0, 7, 0, 7));
        jMenuBar2.add(jMenu16);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(nameTable, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(89, 89, 89))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(classComBoBox, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(queQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hocLuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(queQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hocLuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(classComBoBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameTable, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLyActionPerformed

    private void txtNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayActionPerformed

    private void txtThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThangActionPerformed

    private void txtGDCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGDCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGDCDActionPerformed

    private void txtVanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVanActionPerformed

    private void rdNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNamActionPerformed

    private void txtLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLopActionPerformed

    private void Add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add1ActionPerformed
        if (validateForm()) {
            SinhVien sv = getNhap();
            list.add(sv);
            filterByClass();
            showResultInfo();
        } else {
            JOptionPane.showMessageDialog(this, "Error", "Massege", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Add1ActionPerformed

    private void resetInput() {
        txtID.setText("");
        TxtAddress.setPrototypeDisplayValue("An Giang");
        txtName.setText("");
        rdNam.setSelected(true);
        txtCongNghe.setText("");
        txtGDCD.setText("");
        txtHoa.setText("");
        txtLy.setText("");
        txtLop.setText("");
        txtSinh.setText("");
        txtSu.setText("");
        txtTin.setText("");
        txtToan.setText("");
        txtVan.setText("");
        txtDia.setText("");
        txtNgay.setText("");
        txtThang.setText("");
        txtNam.setText("");
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        resetInput();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtHoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoaActionPerformed

    private void showActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showActionPerformed
        // TODO add your handling code here:
        if (flag % 2 == 1) {
            initTableDiem();
            showResultDiem();
            flag++;
        } else {
            initTableInfo();
            showResultInfo();
            flag++;
        }
    }//GEN-LAST:event_showActionPerformed

    private void ExitAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitAddActionPerformed
        // TODO add your handling code here:
        Add.setVisible(false);
    }//GEN-LAST:event_ExitAddActionPerformed

    private void txtTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTinActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed

    }//GEN-LAST:event_txtIDActionPerformed

    private void tablemenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemenuMouseClicked
        // TODO add your handling code here:
//        Add.setSize(800, 600);
//        Add.setLocationRelativeTo(null);
//        Add.setVisible(true);
        RepairTableMenu();
    }//GEN-LAST:event_tablemenuMouseClicked

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        // TODO add your handling code here:
        fileCSV.WriteFileCSV(list);
        fileCSV.WriteFileCSVInfo(list);
        fileCSV.WriteFileCSVScore(list);
        fileJSON.WriteFileJson(list);
        JOptionPane.showMessageDialog(this, "you have saved the file!", "Message", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_SaveActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void AddMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenuActionPerformed
        // TODO add your handling code here:
        Add.setSize(800, 600);
        Add.setLocationRelativeTo(null);
        Add.setVisible(true);
    }//GEN-LAST:event_AddMenuActionPerformed

    private void RepairMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepairMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RepairMenuActionPerformed

    private void DeleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteMenuActionPerformed
        // TODO add your handling code here:
        DeletePopupActionPerformed(evt);
    }//GEN-LAST:event_DeleteMenuActionPerformed

    private void RefreshMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshMenuActionPerformed
        // TODO add your handling code here:
        showResultInfo();
    }//GEN-LAST:event_RefreshMenuActionPerformed

    private void showMenuDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMenuDiemActionPerformed
        // TODO add your handling code here:
        flagSearch = 0;
        listSearch.clear();
        nameTable.setText("Scores Table");
        initTableDiemMenu();
        showResultDiem();
        flagMenu = 0;
    }//GEN-LAST:event_showMenuDiemActionPerformed

    private void showMenuInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMenuInfoActionPerformed
        // TODO add your handling code here:
        flagSearch = 0;
        listSearch.clear();
        nameTable.setText("Infomation Table");
        initTableInfoMenu();
        showResultInfo();
        flagMenu = 1;
    }//GEN-LAST:event_showMenuInfoActionPerformed

    private void SortAphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SortAphabetActionPerformed
        // TODO add your handling code here:
        if (flagSearch == 1) {
            Collections.sort(listSearch, new nameComparatorAlphabet());
            initTableInfoMenu();
            showSearchInfo();
            flagSort = 0;
        } else {
            Collections.sort(list, new nameComparatorAlphabet());
            initTableInfoMenu();
            showResultInfo();
            flagSort = 1;
        }
    }//GEN-LAST:event_SortAphabetActionPerformed

    private void ReAlphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReAlphabetActionPerformed
        // TODO add your handling code here:
        if (flagSearch == 1) {
            Collections.sort(listSearch, new nameComparatorAlphabet());
            Collections.reverse(listSearch);
            initTableInfoMenu();
            showSearchInfo();
        } else {
            Collections.sort(list, new nameComparatorAlphabet());
            Collections.reverse(list);
            initTableInfoMenu();
            showResultInfo();
            flagSort = 1;
        }
    }//GEN-LAST:event_ReAlphabetActionPerformed

    private void AddMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddMenu3ActionPerformed

    private void clearMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMenuActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh sách?", "<!>Waiting!", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            list.clear();
            JOptionPane.showMessageDialog(this, "Bạn đã xóa danh sách!");
            showResultInfo();
        }
    }//GEN-LAST:event_clearMenuActionPerformed

    private void AddToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToolBarActionPerformed
        // TODO add your handling code here:
        Add.setSize(730, 700);
        Add.setLocationRelativeTo(null);
        Add.setVisible(true);
    }//GEN-LAST:event_AddToolBarActionPerformed

    private int ConditionSearch(SinhVien sinhvien, String str) {
        String name = sinhvien.getHoTen().substring(1 + sinhvien.getHoTen().lastIndexOf(" "));
        String dateString = sdf.format(sinhvien.getDayBir());
        if (str.equalsIgnoreCase(sinhvien.getLop()) || str.equalsIgnoreCase(sinhvien.getID())
                || str.equalsIgnoreCase(dateString) || str.equalsIgnoreCase(sinhvien.getHoTen()) || str.equalsIgnoreCase(name)) {
            return 1;
        }
        return 0;
    }

    private void showSearchInfo() {
        int i = 0;
        defaultTableModelInfo.setRowCount(0);
        for (SinhVien sinhvien : listSearch) {
            String dateString = sdf.format(sinhvien.getDayBir());
            String diemTB = String.format("%.2f", sinhvien.getDiemTB());
            Object[] str = new Object[]{i++, sinhvien.getID(), sinhvien.getHoTen(), sinhvien.isGender() ? "Nam" : "Nữ", dateString, sinhvien.getQueQuan(), sinhvien.getLop(), diemTB, sinhvien.xepLoaiHocLuc()};
            defaultTableModelInfo.addRow(str);
        }
        defaultTableModelInfo.fireTableDataChanged();
    }

    private void showSearchtDiem() {
        int i = 1;
        defaultTableModelDiem.setRowCount(0);
        for (SinhVien sinhvien : listSearch) {
            String diem = "";
            for (MonHoc item : sinhvien.getListDiem()) {
                diem += String.format("%.2f", item.getDiem());
                if (diem.equals(sinhvien.getListDiem().get(sinhvien.getListDiem().size() - 1))) {
                    break;
                }
                diem += ",";
            }
            String[] diemMon = diem.split(",");
            Object[] str = new Object[]{
                i++, sinhvien.getID(), diemMon[0], diemMon[1], diemMon[2], diemMon[3], diemMon[4], diemMon[5], diemMon[6], diemMon[7], diemMon[8], diemMon[9]};
            defaultTableModelDiem.addRow(str);
        }
        defaultTableModelDiem.fireTableDataChanged();
    }

    private void SearchToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchToolBarActionPerformed

        Search.setSize(320, 105);
        Search.setLocationRelativeTo(null);
        Search.setVisible(true);
        SearchTxt.setText("");
    }//GEN-LAST:event_SearchToolBarActionPerformed

    private void showToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToolBarActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        if (flagMenu % 2 == 1) {
            initTableDiemMenu();
            showResultDiem();
            nameTable.setText("Scores Table");
            flagMenu++;
        } else {
            initTableInfoMenu();
            showResultInfo();
            nameTable.setText("Infomation Table");
            flagMenu++;
        }
    }//GEN-LAST:event_showToolBarActionPerformed

    private void nameTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTableActionPerformed
        // TODO add your handling code here:
        if (flagSearch == 1) {
            if (flagMenu % 2 == 1) {
                initTableDiemMenu();
                showSearchtDiem();
                nameTable.setText("Scores Table");
                flagMenu++;
            } else {
                initTableInfoMenu();
                showSearchInfo();
                nameTable.setText("Infomation Table");
                flagMenu++;
            }
        }
    }//GEN-LAST:event_nameTableActionPerformed


    private void RepairToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepairToolBarActionPerformed
        // TODO add your handling code here:
        Repair.setSize(590, 330);
        Repair.setLocationRelativeTo(null);
        Repair.setVisible(true);
    }//GEN-LAST:event_RepairToolBarActionPerformed

    private void RepairPopupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RepairPopupMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_RepairPopupMouseClicked

    private void RepairPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepairPopupActionPerformed
        // TODO add your handling code here:
        Repair.setSize(590, 330);
        Repair.setLocationRelativeTo(null);
        Repair.setVisible(true);
    }//GEN-LAST:event_RepairPopupActionPerformed

    private void SearchToolBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchToolBarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchToolBarMouseClicked

    private void txtIDRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDRepairActionPerformed

    private void rdNamRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNamRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNamRepairActionPerformed

    private void ExitRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitRepairActionPerformed
        // TODO add your handling code here:
        Repair.setVisible(false);
    }//GEN-LAST:event_ExitRepairActionPerformed

    private void RepairDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepairDialogActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa danh sách?", "Notification", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            ArrayList<MonHoc> listMH = new ArrayList<>();
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

            sinhVien.setID(txtIDRepair.getText().toUpperCase());

            sinhVien.setHoTen(formatName(txtNameRepair.getText()));
            if (rdNuRepair.isSelected()) {
                sinhVien.setGender(false);
            } else {
                sinhVien.setGender(true);
            }
            sinhVien.setLop(txtLopRepair.getText().toUpperCase());

            int day = Integer.parseInt(this.txtNgayRepair.getText());
            int month = Integer.parseInt(this.txtThangRepair.getText());
            int year = Integer.parseInt(this.txtNamRepair.getText());
            String dateString = String.format("%d/%d/%d", day, month, year);
            Date a = null;
            try {
                a = sdf.parse(dateString);
            } catch (ParseException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            sinhVien.setDayBir(a);

            sinhVien.setQueQuan((String) TxtAddressRepair.getSelectedItem());

            toanMonHoc.setMonHoc(jLabelToan.getText());
            toanMonHoc.setDiem(Double.parseDouble(txtToanRepair.getText()));
            listMH.add(toanMonHoc);
            lyMonHoc.setMonHoc(jLabelLy.getText());
            lyMonHoc.setDiem(Double.parseDouble(txtLyRepair.getText()));
            listMH.add(lyMonHoc);
            hoaMonHoc.setMonHoc(jLabelHoa.getText());
            hoaMonHoc.setDiem(Double.parseDouble(txtHoaRepair.getText()));
            listMH.add(hoaMonHoc);
            vanMonHoc.setMonHoc(jLabelVan.getText());
            vanMonHoc.setDiem(Double.parseDouble(txtVanRepair.getText()));
            listMH.add(vanMonHoc);
            sinhMonHoc.setMonHoc(jLabelSinh.getText());
            sinhMonHoc.setDiem(Double.parseDouble(txtSinhRepair.getText()));
            listMH.add(sinhMonHoc);
            suMonHoc.setMonHoc(jLabel1Su.getText());
            suMonHoc.setDiem(Double.parseDouble(txtSuRepair.getText()));
            listMH.add(suMonHoc);
            diaMonHoc.setMonHoc(jLabelDia.getText());
            diaMonHoc.setDiem(Double.parseDouble(txtDialyRepair.getText()));
            listMH.add(diaMonHoc);
            gdcdMonHoc.setMonHoc(jLabelGdcd.getText());
            gdcdMonHoc.setDiem(Double.parseDouble(txtGDCDRepair.getText()));
            listMH.add(gdcdMonHoc);
            tinMonHoc.setMonHoc(jLabelTin.getText());
            tinMonHoc.setDiem(Double.parseDouble(txtTinRepair.getText()));
            listMH.add(tinMonHoc);
            cnMonHoc.setMonHoc(jLabelCn.getText());
            cnMonHoc.setDiem(Double.parseDouble(txtCongNgheRepair.getText()));
            listMH.add(cnMonHoc);
            sinhVien.setListDiem(listMH);

            list.set(flagRepair, sinhVien);
            filterByClass();
            initTableInfoMenu();
            showResultInfo();
            ExitRepairActionPerformed(evt);
        }
    }//GEN-LAST:event_RepairDialogActionPerformed

    private void txtVanRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVanRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVanRepairActionPerformed

    private void txtGDCDRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGDCDRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGDCDRepairActionPerformed

    private void txtThangRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThangRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThangRepairActionPerformed

    private void txtHoaRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoaRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoaRepairActionPerformed

    private void txtTinRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTinRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTinRepairActionPerformed

    private void txtNgayRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayRepairActionPerformed

    private void txtLyRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLyRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLyRepairActionPerformed

    private void txtLopRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLopRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLopRepairActionPerformed

    private void SearchBottonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBottonActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        if (SearchTxt.getText() != "") {
            for (SinhVien sinhvien : list) {
                if (ConditionSearch(sinhvien, SearchTxt.getText()) == 1) {
                    listSearch.add(sinhvien);
                    flagSearch = 1;
                }
            }
            if (flagSearch == 0) {
                String str = String.format("Not Found %s", SearchTxt.getText());
                JOptionPane.showMessageDialog(this, str, "Waining", JOptionPane.WARNING_MESSAGE);
            } else {
                initTableInfoMenu();
                showSearchInfo();
                Search.setVisible(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error entering search data", "Erorr", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SearchBottonActionPerformed

    private void DeletePopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletePopupActionPerformed
        // TODO add your handling code here:
        int selectRows = tablemenu.getSelectedRow();
        if (selectRows >= 0) {
            String ID1 = (String) tablemenu.getValueAt(selectRows, 1);

            int n = JOptionPane.showConfirmDialog(this, "Có muốn xóa không?", "Message", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                for (SinhVien hs : list) {
                    if (hs.getID().equals(ID1)) {
                        list.remove(hs);
                        filterByClass();
                        initTableInfoMenu();
                        showResultInfo();
                        break;
                    }
                }
            }
        }
    }//GEN-LAST:event_DeletePopupActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        DeletePopupActionPerformed(evt);
    }//GEN-LAST:event_jButton6ActionPerformed


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        String str = String.format("Want to save your changes to your file?");
        int n = JOptionPane.showConfirmDialog(this, str, "Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (n == JOptionPane.YES_OPTION) {
            fileCSV.WriteFileCSV(list);
            fileCSV.WriteFileCSVInfo(list);
            fileCSV.WriteFileCSVScore(list);
            fileJSON.WriteFileJson(list);
        }
    }//GEN-LAST:event_formWindowClosing

    private void txtIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIDFocusLost
        // TODO add your handling code here:
        String reg = "^[A-Za-z]{2}+[0-9]{6}$";
        String id = txtID.getText();
        if (!txtID.getText().equals("")) {
            if (!id.matches(reg)) {
                txtID.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng ID,nhập lại ID, Vd: AT123456", "Massege", JOptionPane.ERROR_MESSAGE);
            }
            for (SinhVien sinhVien : list) {
                if (txtID.getText().equalsIgnoreCase(sinhVien.getID())) {
                    txtID.setText("");
                    JOptionPane.showMessageDialog(this, "ID Đã Tồn Tại,Nhập lại!", "Massege", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_txtIDFocusLost

    private void txtLopFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLopFocusLost
        // TODO add your handling code here:
        String Lop = txtLop.getText();
        String reg = "^\\d{2}\\w{1}\\d{1}";
        if (!txtLop.getText().isEmpty()) {
            if (!Lop.matches(reg)) {
                txtLop.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng,nhập lại Lớp, VD: 12a1");
            }
        }
    }//GEN-LAST:event_txtLopFocusLost

    private boolean ConditionScore(String string) {
        String reg = "^\\d{1}(?:0|)(?:.\\d{1,3}|)$";
        if (!string.isEmpty()) {
            if (string.matches(reg)) {
                System.out.println(txtToanRepair.getText());
                double Toan = Double.parseDouble(txtToan.getText());
                System.out.println(Toan);
                if (Toan > 10.0 || Toan < 0.0) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    private void txtToanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtToanFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtToan.getText()) == false) {
            txtToan.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm toán");
        }
    }//GEN-LAST:event_txtToanFocusLost

    private void txtLyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLyFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtLy.getText()) == false) {
            txtLy.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Lý");
        }
    }//GEN-LAST:event_txtLyFocusLost

    private void txtHoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoaFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtHoa.getText()) == false) {
            txtHoa.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Hóa");
        }
    }//GEN-LAST:event_txtHoaFocusLost

    private void txtSuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtSu.getText()) == false) {
            txtSu.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Sử");
        }
    }//GEN-LAST:event_txtSuFocusLost

    private void txtVanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVanFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtVan.getText()) == false) {
            txtVan.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Văn");
        }
    }//GEN-LAST:event_txtVanFocusLost

    private void txtCongNgheFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCongNgheFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtCongNghe.getText()) == false) {
            txtCongNghe.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Công Nghệ");
        }
    }//GEN-LAST:event_txtCongNgheFocusLost

    private void txtSinhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSinhFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtSinh.getText()) == false) {
            txtSinh.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Sinh");
        }
    }//GEN-LAST:event_txtSinhFocusLost

    private void txtDiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtDia.getText()) == false) {
            txtDia.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Địa");
        }
    }//GEN-LAST:event_txtDiaFocusLost

    private void txtTinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTinFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtTin.getText()) == false) {
            txtTin.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Tin");
        }
    }//GEN-LAST:event_txtTinFocusLost

    private void txtGDCDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGDCDFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtGDCD.getText()) == false) {
            txtGDCD.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Giao Dục Công Dân");
        }
    }//GEN-LAST:event_txtGDCDFocusLost

    private void ExportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportFileActionPerformed
        if (listSearch.size() != 0) {
            int n = jFileChooser1.showSaveDialog(this);
            if (n == JFileChooser.APPROVE_OPTION) {
                String fileString = String.format("%s.csv", jFileChooser1.getSelectedFile());
                System.out.println(fileString);
                fileCSV.WriteFileCSVOutPut(listSearch, fileString);
                JOptionPane.showMessageDialog(this, "You exported the file!", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (list.size() != 0 && flagSort == 1) {
            int n = jFileChooser1.showSaveDialog(this);
            if (n == JFileChooser.APPROVE_OPTION) {
                String fileString = String.format("%s.csv", jFileChooser1.getSelectedFile());
                System.out.println(fileString);
                fileCSV.WriteFileCSVOutPut(list, fileString);
                JOptionPane.showMessageDialog(this, "You exported the file!", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_ExportFileActionPerformed

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        // TODO add your handling code here:
        String Ten = txtName.getText();
        String reg = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_ ]+";
        if (txtName.getText().isEmpty()) {

        } else {
            if (!Ten.matches(reg)) {
                txtName.setText("");
                JOptionPane.showMessageDialog(rootPane, "sai định dạng tên, nhập lại tên ");
            }
        }
    }//GEN-LAST:event_txtNameFocusLost

    private void ascendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ascendingActionPerformed
        // TODO add your handling code here:         
        if (flagSearch == 1) {
            Collections.sort(listSearch, new ScoreComparator());
            Collections.reverse(listSearch);
            initTableInfoMenu();
            showSearchInfo();
        } else {
            Collections.sort(list, new ScoreComparator());
            Collections.reverse(list);
            initTableInfoMenu();
            showResultInfo();
            flagSort = 1;
        }
    }//GEN-LAST:event_ascendingActionPerformed

    private void decreaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decreaseActionPerformed
        // TODO add your handling code here:
        if (flagSearch == 1) {
            Collections.sort(listSearch, new ScoreComparator());
            initTableInfoMenu();
            showSearchInfo();

        } else {
            Collections.sort(list, new ScoreComparator());
            initTableInfoMenu();
            showResultInfo();
            flagSort = 1;
        }
    }//GEN-LAST:event_decreaseActionPerformed

    private void ClassSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassSortActionPerformed
        // TODO add your handling code here:
        if (flagSearch == 1) {
            Collections.sort(listSearch, new ClassComparator());
            initTableInfoMenu();
            showSearchInfo();
        } else {
            Collections.sort(list, new ClassComparator());
            initTableInfoMenu();
            showResultInfo();
            flagSort = 1;
        }
    }//GEN-LAST:event_ClassSortActionPerformed

    private void ConditionFilter() {
        String lop = String.valueOf(classComBoBox.getSelectedItem());
        String queString = String.valueOf(queQuan.getSelectedItem());
        String hocLucString = String.valueOf(this.hocLuc.getSelectedItem());
        if (hocLucString != String.valueOf(hocLuc.getItemAt(0))) {
            if (lop != String.valueOf(classComBoBox.getItemAt(0))) {
                for (SinhVien sinhvien : list) {
                    if (lop.equalsIgnoreCase(sinhvien.getLop()) && hocLucString.equalsIgnoreCase(sinhvien.xepLoaiHocLuc())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            } else if (queString != String.valueOf(queQuan.getItemAt(0))) {
                for (SinhVien sinhvien : list) {
                    if (queString.equalsIgnoreCase(sinhvien.getQueQuan()) && hocLucString.equalsIgnoreCase(sinhvien.xepLoaiHocLuc())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            } else {
                for (SinhVien sinhvien : list) {
                    if (hocLucString.equalsIgnoreCase(sinhvien.xepLoaiHocLuc())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            }
        } else if (lop != String.valueOf(classComBoBox.getItemAt(0))) {
            if (queString != String.valueOf(queQuan.getItemAt(0))) {
                for (SinhVien sinhvien : list) {
                    if (queString.equalsIgnoreCase(sinhvien.getQueQuan()) && lop.equalsIgnoreCase(sinhvien.getLop())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            } else if (hocLucString != String.valueOf(hocLuc.getItemAt(0))) {
                for (SinhVien sinhvien : list) {
                    if (hocLucString.equalsIgnoreCase(sinhvien.xepLoaiHocLuc()) && lop.equalsIgnoreCase(sinhvien.getLop())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            } else {
                for (SinhVien sinhvien : list) {
                    if (lop.equalsIgnoreCase(sinhvien.getLop())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            }
        } else {
            if (lop != String.valueOf(classComBoBox.getItemAt(0))) {
                for (SinhVien sinhvien : list) {
                    if (lop.equalsIgnoreCase(sinhvien.getLop()) && queString.equalsIgnoreCase(sinhvien.getQueQuan())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            } else if (hocLucString != String.valueOf(hocLuc.getItemAt(0))) {
                for (SinhVien sinhvien : list) {
                    if (hocLucString.equalsIgnoreCase(sinhvien.xepLoaiHocLuc()) && queString.equalsIgnoreCase(sinhvien.getQueQuan())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            } else {
                for (SinhVien sinhvien : list) {
                    if (queString.equalsIgnoreCase(sinhvien.getQueQuan())) {
                        listSearch.add(sinhvien);
                        flagSearch = 1;
                    }
                }
            }
        }
        if (hocLucString == String.valueOf(hocLuc.getItemAt(0)) && lop == String.valueOf(classComBoBox.getItemAt(0)) && queString == String.valueOf(queQuan.getItemAt(0))) {
            listSearch.clear();
            initTableInfoMenu();
            showResultInfo();
        } else {
            initTableInfoMenu();
            showSearchInfo();
            if (flagSearch == 0) {
                String str = String.format("The list is empty");
                JOptionPane.showMessageDialog(this, str);
            }
        }
    }
    private void hocLucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hocLucActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        ConditionFilter();
    }//GEN-LAST:event_hocLucActionPerformed

    private void classComBoBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classComBoBoxActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        ConditionFilter();
    }//GEN-LAST:event_classComBoBoxActionPerformed

    private void queQuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queQuanActionPerformed
        // TODO add your handling code here
        listSearch.clear();
        flagSearch = 0;
        ConditionFilter();
    }//GEN-LAST:event_queQuanActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void FilterToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterToolBarActionPerformed
        // TODO add your handling code here:
        if (flagFilter == 0) {
            classComBoBox.setVisible(true);
            queQuan.setVisible(true);
            hocLuc.setVisible(true);
            flagFilter = 1;
        } else {
            displayNoneComboBox();
            flagFilter = 0;
        }
    }//GEN-LAST:event_FilterToolBarActionPerformed

    private void FilterMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterMenuActionPerformed
        // TODO add your handling code here:
        if (flagFilter == 0) {
            classComBoBox.setVisible(true);
            queQuan.setVisible(true);
            hocLuc.setVisible(true);
            flagFilter = 1;
        } else {
            displayNoneComboBox();
            flagFilter = 0;
        }
    }//GEN-LAST:event_FilterMenuActionPerformed

    private void SearchMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchMenuActionPerformed

    public boolean checkDay(int day, int thang) {
        if (thang == 2) {
            if (day > 28 || day <= 0) {
                txtNgay.setText("");
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
                return false;
            }
        } else if (thang == 4 || thang == 6
                || thang == 9 || thang == 11) {
            if (day > 30 || day <= 0) {
                txtNgay.setText("");
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
                return false;
            }
        } else {
            if (day > 31 || day <= 0) {
                txtNgay.setText("");
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
                return false;
            }
        }

        return true;
    }

    private boolean checkyearnhuan(int day, int thang) {
        if (thang == 2) {
            if (day > 29 || day <= 0) {
                txtNgay.setText("");
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
                return false;
            }
        } else if (thang == 4 || thang == 6
                || thang == 9 || thang == 11) {
            if (day > 30 || day <= 0) {
                txtNgay.setText("");
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
                return false;
            }
        } else {
            if (day > 31 || day <= 0) {
                txtNgay.setText("");
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
                return false;
            }
        }

        return true;
    }

    private void txtNgayFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayFocusLost
        // TODO add your handling code here:
        String reg = "[0-9]{1,2}";
        if (!txtNgay.getText().isEmpty()) {
            if (!txtNgay.getText().matches(reg)) {
                txtNgay.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
            } else {
                if (!txtThang.getText().equals("") && !txtNam.getText().equals("")) {
                    int day = Integer.parseInt(txtNgay.getText());
                    int thang = Integer.parseInt(txtThang.getText());
                    int nam = Integer.parseInt(txtNam.getText());
                    if (nam % 4 != 0) {
                        checkDay(day, thang);
                    } else {
                        if (nam % 100 == 0 && nam % 400 != 0) {
                            checkDay(day, thang);
                        } else if (nam % 400 == 0) {
                            checkyearnhuan(day, thang);
                        } else {
                            checkyearnhuan(day, thang);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtNgayFocusLost

    private void txtThangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtThangFocusLost
        // TODO add your handling code here:
        String reg = "[0-9]{1,2}";
        if (!txtThang.getText().isEmpty()) {
            if (!txtThang.getText().matches(reg)) {
                txtThang.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Tháng");
            } else {
                int thang = Integer.parseInt(txtThang.getText());
                if (thang > 12 || thang < 0) {
                    JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Tháng");
                }
                if (!txtNgay.getText().equals("") && !txtNam.getText().equals("")) {
                    int day = Integer.parseInt(txtNgay.getText());
                    int nam = Integer.parseInt(txtNam.getText());
                    if (nam % 4 != 0) {
                        checkDay(day, thang);
                    } else {
                        if (nam % 100 == 0 && nam % 400 != 0) {
                            checkDay(day, thang);
                        } else if (nam % 400 == 0) {
                            checkyearnhuan(day, thang);
                        } else {
                            checkyearnhuan(day, thang);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtThangFocusLost

    private void txtNamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamFocusLost
        // TODO add your handling code here:
        String reg = "[0-9]{4}";
        if (!txtNam.getText().isEmpty()) {
            if (!txtNam.getText().matches(reg)) {
                txtNam.setText("");
                JOptionPane.showMessageDialog(this, "Sai định dạng, Nhập lại năm", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int nam = Integer.parseInt(txtNam.getText());
                if (!txtThang.getText().isEmpty()) {
                    if (!txtNgay.getText().equals("") && !txtNam.getText().equals("")) {
                        int day = Integer.parseInt(txtNgay.getText());
                        int thang = Integer.parseInt(txtThang.getText());
                        if (nam % 4 != 0) {
                            checkDay(day, thang);
                        } else {
                            if (nam % 100 == 0 && nam % 400 != 0) {
                                checkDay(day, thang);
                            } else if (nam % 400 == 0) {
                                checkyearnhuan(day, thang);
                            } else {
                                checkyearnhuan(day, thang);
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtNamFocusLost

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        HelpDialog.setSize(600, 400);
        HelpDialog.setLocationRelativeTo(null);
        HelpDialog.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void txtIDRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIDRepairFocusLost
        // TODO add your handling code here:
        String reg = "^[A-Za-z]{2}+[0-9]{6}$";
        String id = txtIDRepair.getText();
        if (!txtIDRepair.getText().equals("")) {
            if (!id.matches(reg)) {
                txtIDRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng ID,nhập lại ID, Vd: AT123456");
            }
            for (int i = 0; i < list.size(); i++) {
                SinhVien sinhVien = list.get(i);
                if (txtIDRepair.getText().equalsIgnoreCase(sinhVien.getID()) && i != flagRepair) {
                    txtIDRepair.setText("");
                    JOptionPane.showMessageDialog(this, "ID Đã Tồn Tại,Nhập lại!", "Massege", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_txtIDRepairFocusLost

    private void txtNameRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameRepairFocusLost
        // TODO add your handling code here:
        String Ten = txtNameRepair.getText();
        String reg = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_ ]+";
        if (txtNameRepair.getText().isEmpty()) {

        } else {
            if (!Ten.matches(reg)) {
                txtNameRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "sai định dạng tên, nhập lại tên ");
            }
        }
    }//GEN-LAST:event_txtNameRepairFocusLost

    private void txtNgayRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayRepairFocusLost
        // TODO add your handling code here:
        String reg = "[0-9]{1,2}";
        if (!txtNgayRepair.getText().isEmpty()) {
            if (!txtNgayRepair.getText().matches(reg)) {
                txtNgayRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Ngày");
            } else {
                if (!txtThangRepair.getText().equals("") && !txtNamRepair.getText().equals("")) {
                    int day = Integer.parseInt(txtNgayRepair.getText());
                    int thang = Integer.parseInt(txtThangRepair.getText());
                    int nam = Integer.parseInt(txtNamRepair.getText());
                    if (nam % 4 != 0) {
                        checkDay(day, thang);
                    } else {
                        if (nam % 100 == 0 && nam % 400 != 0) {
                            checkDay(day, thang);
                        } else if (nam % 400 == 0) {
                            checkyearnhuan(day, thang);
                        } else {
                            checkyearnhuan(day, thang);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtNgayRepairFocusLost

    private void txtThangRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtThangRepairFocusLost
        // TODO add your handling code here:
        String reg = "[0-9]{1,2}";
        if (!txtThangRepair.getText().isEmpty()) {
            if (!txtThangRepair.getText().matches(reg)) {
                txtThangRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Tháng");
            } else {
                int thang = Integer.parseInt(txtThangRepair.getText());
                if (thang > 12 || thang < 0) {
                    txtThangRepair.setText("");
                    JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại Tháng");
                }
                if (!txtNgayRepair.getText().equals("") && !txtNamRepair.getText().equals("")) {
                    int day = Integer.parseInt(txtNgayRepair.getText());
                    int nam = Integer.parseInt(txtNamRepair.getText());
                    if (nam % 4 != 0) {
                        checkDay(day, thang);
                    } else {
                        if (nam % 100 == 0 && nam % 400 != 0) {
                            checkDay(day, thang);
                        } else if (nam % 400 == 0) {
                            checkyearnhuan(day, thang);
                        } else {
                            checkyearnhuan(day, thang);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtThangRepairFocusLost

    private void txtNamRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNamRepairFocusLost
        // TODO add your handling code here:
        String reg = "[0-9]{4}";
        if (!txtNamRepair.getText().isEmpty()) {
            if (!txtNamRepair.getText().matches(reg)) {
                txtNamRepair.setText("");
                JOptionPane.showMessageDialog(this, "Sai định dạng, Nhập lại năm", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int nam = Integer.parseInt(txtNamRepair.getText());
                if (!txtThangRepair.getText().isEmpty()) {
                    if (!txtNgayRepair.getText().equals("") && !txtNamRepair.getText().equals("")) {
                        int day = Integer.parseInt(txtNgayRepair.getText());
                        int thang = Integer.parseInt(txtThangRepair.getText());
                        if (nam % 4 != 0) {
                            checkDay(day, thang);
                        } else {
                            if (nam % 100 == 0 && nam % 400 != 0) {
                                checkDay(day, thang);
                            } else if (nam % 400 == 0) {
                                checkyearnhuan(day, thang);
                            } else {
                                checkyearnhuan(day, thang);
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtNamRepairFocusLost

    private void txtLopRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLopRepairFocusLost
        // TODO add your handling code here:
        String Lop = txtLopRepair.getText();
        String reg = "^\\d{2}\\w{1}\\d{1,2}";
        if (!txtLopRepair.getText().isEmpty()) {
            if (!Lop.matches(reg)) {
                txtLopRepair.setText("");
                JOptionPane.showMessageDialog(rootPane, "Sai định dạng,nhập lại Lớp, VD: 12a1");
            }
        }
    }//GEN-LAST:event_txtLopRepairFocusLost

    private void txtToanRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtToanRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtToanRepair.getText()) == false) {
            txtToanRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm toán");
        }
    }//GEN-LAST:event_txtToanRepairFocusLost

    private void txtToanRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToanRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToanRepairActionPerformed

    private void txtLyRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLyRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtLyRepair.getText()) == false) {
            txtLyRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Lý");
        }
    }//GEN-LAST:event_txtLyRepairFocusLost

    private void txtHoaRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoaRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtHoaRepair.getText()) == false) {
            txtHoaRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Hóa");
        }
    }//GEN-LAST:event_txtHoaRepairFocusLost

    private void txtSuRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtSuRepair.getText()) == false) {
            txtSuRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Sử");
        }
    }//GEN-LAST:event_txtSuRepairFocusLost

    private void txtVanRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVanRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtVanRepair.getText()) == false) {
            txtVanRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Văn");
        }
    }//GEN-LAST:event_txtVanRepairFocusLost

    private void txtCongNgheRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCongNgheRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtCongNgheRepair.getText()) == false) {
            txtCongNgheRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Công Nghệ");
        }
    }//GEN-LAST:event_txtCongNgheRepairFocusLost

    private void txtSinhRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSinhRepairFocusLost
        if (ConditionScore(txtSinhRepair.getText()) == false) {
            txtSinhRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Sinh");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtSinhRepairFocusLost

    private void txtDialyRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDialyRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtDialyRepair.getText()) == false) {
            txtDialyRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Địa Lý");
        }
    }//GEN-LAST:event_txtDialyRepairFocusLost

    private void txtTinRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTinRepairFocusLost
        // TODO add your handling code here:
        if (ConditionScore(txtTinRepair.getText()) == false) {
            txtTinRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Tin");
        }
    }//GEN-LAST:event_txtTinRepairFocusLost

    private void txtGDCDRepairFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGDCDRepairFocusLost
        if (ConditionScore(txtGDCDRepair.getText()) == false) {
            txtGDCDRepair.setText("");
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng , nhập lại điểm Giáo Dục CÔng Dân");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtGDCDRepairFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Add;
    private javax.swing.JToggleButton Add1;
    private javax.swing.JMenuItem AddMenu;
    private javax.swing.JMenuItem AddMenu3;
    private javax.swing.JButton AddToolBar;
    private javax.swing.JMenuItem ClassSort;
    private javax.swing.JMenuItem DeleteMenu;
    private javax.swing.JMenuItem DeletePopup;
    private javax.swing.JButton ExitAdd;
    private javax.swing.JButton ExitRepair;
    private javax.swing.JButton ExportFile;
    private javax.swing.JMenuItem FilterMenu;
    private javax.swing.JButton FilterToolBar;
    private javax.swing.JDialog HelpDialog;
    private javax.swing.JPopupMenu PopUpTable;
    private javax.swing.JMenuItem ReAlphabet;
    private javax.swing.JMenuItem RefreshMenu;
    private javax.swing.JDialog Repair;
    private javax.swing.JButton RepairDialog;
    private javax.swing.JMenuItem RepairMenu;
    private javax.swing.JMenuItem RepairPopup;
    private javax.swing.JButton RepairToolBar;
    private javax.swing.JMenuItem Save;
    private javax.swing.JDialog Search;
    private javax.swing.JButton SearchBotton;
    private javax.swing.JMenuItem SearchMenu;
    private javax.swing.JButton SearchToolBar;
    private javax.swing.JTextField SearchTxt;
    private javax.swing.JMenuItem SortAphabet;
    private javax.swing.JMenu SortScores;
    private javax.swing.JComboBox<String> TxtAddress;
    private javax.swing.JComboBox<String> TxtAddressRepair;
    private javax.swing.JMenuItem ascending;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> classComBoBox;
    private javax.swing.JMenuItem clearMenu;
    private javax.swing.JMenuItem decrease;
    private javax.swing.JComboBox<String> hocLuc;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel1Su;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelCn;
    private javax.swing.JLabel jLabelDia;
    private javax.swing.JLabel jLabelGdcd;
    private javax.swing.JLabel jLabelHoa;
    private javax.swing.JLabel jLabelLy;
    private javax.swing.JLabel jLabelSinh;
    private javax.swing.JLabel jLabelTin;
    private javax.swing.JLabel jLabelToan;
    private javax.swing.JLabel jLabelVan;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton nameTable;
    private javax.swing.JComboBox<String> queQuan;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNamRepair;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdNuRepair;
    private javax.swing.JButton show;
    private javax.swing.JMenuItem showMenuDiem;
    private javax.swing.JMenuItem showMenuInfo;
    private javax.swing.JButton showToolBar;
    private javax.swing.JTable table;
    private javax.swing.JTable tablemenu;
    private javax.swing.JTextField txtCongNghe;
    private javax.swing.JTextField txtCongNgheRepair;
    private javax.swing.JTextField txtDia;
    private javax.swing.JLabel txtDia1;
    private javax.swing.JTextField txtDialyRepair;
    private javax.swing.JTextField txtGDCD;
    private javax.swing.JTextField txtGDCDRepair;
    private javax.swing.JTextField txtHoa;
    private javax.swing.JTextField txtHoaRepair;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDRepair;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtLopRepair;
    private javax.swing.JTextField txtLy;
    private javax.swing.JTextField txtLyRepair;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtNamRepair;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameRepair;
    private javax.swing.JTextField txtNgay;
    private javax.swing.JTextField txtNgayRepair;
    private javax.swing.JTextField txtSinh;
    private javax.swing.JTextField txtSinhRepair;
    private javax.swing.JTextField txtSu;
    private javax.swing.JTextField txtSuRepair;
    private javax.swing.JTextField txtThang;
    private javax.swing.JTextField txtThangRepair;
    private javax.swing.JTextField txtTin;
    private javax.swing.JTextField txtTinRepair;
    private javax.swing.JTextField txtToan;
    private javax.swing.JTextField txtToanRepair;
    private javax.swing.JTextField txtVan;
    private javax.swing.JTextField txtVanRepair;
    // End of variables declaration//GEN-END:variables
}
