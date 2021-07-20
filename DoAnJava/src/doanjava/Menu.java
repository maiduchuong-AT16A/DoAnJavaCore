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
    DefaultTableModel defaultTableModelInfo = new DefaultTableModel();
    DefaultTableModel defaultTableModelDiem = new DefaultTableModel();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private int flag = 1;
    private int flagMenu = 1;
    private int flagSearch = 0;
    private int flagRepair = 0;
    private int selectRows = -1;

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
        tablemenu.setComponentPopupMenu(PopUpTable);
//      this.setIconImage(new ImageIcon(getClass().getResource("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\Best.png")).getImage());
//        this.setIconImage(new ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\Best.png").getImage());
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
        show.setText("Hiển thị Bảng điểm");
    }

    private void initTableDiem() {
        String[] str = new String[]{"STT", "ID", "Maths", "Physical", "Chemistry", "Literature", "Biology", "History", "Geography", "Civic Education", "Informatics", "Technology"};
        defaultTableModelDiem.setColumnIdentifiers(str);
        table.setModel(defaultTableModelDiem);
        show.setText("Hiển thị thông tin");
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
        return true;
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

        sinhVien.setID(txtID.getText());

        sinhVien.setHoTen(txtName.getText());
        if (rdNu.isSelected()) {
            sinhVien.setGender(false);
        } else {
            sinhVien.setGender(true);
        }
        sinhVien.setLop(txtLop.getText());

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
                    txtLopRepair.setText(sinhVien.getLop());

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
        jLabel34 = new javax.swing.JLabel();
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
        jDialog1 = new javax.swing.JDialog();
        jComboBox1 = new javax.swing.JComboBox<>();
        buttonGroup2 = new javax.swing.ButtonGroup();
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
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton10 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton11 = new javax.swing.JButton();
        nameTable = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        Save = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
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
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        showMenuDiem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        locTheoLop = new javax.swing.JMenuItem();
        locTheoHocLuc = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        SortScores = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        SortAphabet = new javax.swing.JMenuItem();
        ReAlphabet = new javax.swing.JMenuItem();
        AddMenu4 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        AddMenu3 = new javax.swing.JMenuItem();
        AddMenu10 = new javax.swing.JMenuItem();
        jMenu16 = new javax.swing.JMenu();

        Add.setTitle("Thêm Sinh Viên");
        Add.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Add.setResizable(false);

        jLabel2.setText("Họ và tên");

        jLabelCn.setText("Công Nghệ");

        jLabel4.setText("Ngày Sinh");

        txtLy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLyActionPerformed(evt);
            }
        });

        txtNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayActionPerformed(evt);
            }
        });

        txtThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThangActionPerformed(evt);
            }
        });

        jLabelGdcd.setText("GDCD");

        jLabel5.setText("Quê Quán");

        txtGDCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGDCDActionPerformed(evt);
            }
        });

        txtVan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVanActionPerformed(evt);
            }
        });

        jLabelToan.setText("Toán Học");

        jLabelVan.setText("Ngữ Văn");

        jLabelHoa.setText("Hóa Học");

        jLabelLy.setText("Vật Lý");

        jLabel14.setText("Giới tính");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");
        rdNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamActionPerformed(evt);
            }
        });

        jLabelSinh.setText("Sinh Học");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        jLabelTin.setText("Tin Học");

        jLabel3.setText("Lớp");

        jLabelDia.setText("Địa Lý");

        txtLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLopActionPerformed(evt);
            }
        });

        jLabel1Su.setText("Lịch Sử");

        Add1.setText("Add");
        Add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add1ActionPerformed(evt);
            }
        });

        jButton3.setText("New");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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

        ExitAdd.setText("Exit");
        ExitAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitAddActionPerformed(evt);
            }
        });

        txtTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTinActionPerformed(evt);
            }
        });

        jLabel16.setText("        ID");

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

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("X");
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNam))
                    .addComponent(txtName)
                    .addComponent(txtLop)
                    .addComponent(TxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelToan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelLy, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelHoa, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jLabelVan))
                    .addComponent(jLabel1Su, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelCn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCongNghe))
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSu, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLy, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddLayout.createSequentialGroup()
                                .addComponent(jLabelSinh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelTin)
                                    .addComponent(jLabelGdcd)
                                    .addComponent(jLabelDia))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtGDCD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(35, 35, 35))
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
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelCn)
                                .addComponent(jLabelToan))
                            .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCongNghe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelSinh)
                            .addComponent(jLabelLy))
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelHoa)
                                    .addComponent(txtHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(AddLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelTin, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1Su)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelVan))
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtGDCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelGdcd)))))
                .addGap(29, 29, 29)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add1)
                    .addComponent(jButton3)
                    .addComponent(show)
                    .addComponent(ExitAdd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel17.setText("Họ và tên");

        jLabel18.setText("Toán Học");

        txtTinRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTinRepairActionPerformed(evt);
            }
        });

        jLabel19.setText("Ngữ Văn");

        jLabel20.setText("Hóa Học");

        jLabel21.setText("Vật Lý");

        jLabel22.setText("Giới tính");

        jLabel23.setText("        ID");

        buttonGroup1.add(rdNamRepair);
        rdNamRepair.setText("Nam");
        rdNamRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamRepairActionPerformed(evt);
            }
        });

        txtIDRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDRepairActionPerformed(evt);
            }
        });

        jLabel24.setText("Sinh");

        TxtAddressRepair.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang\t", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình\t", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên\t", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM" }));

        buttonGroup1.add(rdNuRepair);
        rdNuRepair.setText("Nữ");

        jLabel25.setText("Tin Học");

        jLabel26.setText("Lớp");

        txtDia1.setText("Địa Lý");

        txtLopRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLopRepairActionPerformed(evt);
            }
        });

        jLabel27.setText("Công Nghệ");

        jLabel28.setText("Lịch Sử");

        jLabel29.setText("Ngày Sinh");

        txtLyRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLyRepairActionPerformed(evt);
            }
        });

        txtNgayRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayRepairActionPerformed(evt);
            }
        });

        txtHoaRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoaRepairActionPerformed(evt);
            }
        });

        txtThangRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThangRepairActionPerformed(evt);
            }
        });

        jLabel30.setText("GDCD");

        jLabel31.setText("Quê Quán");

        txtGDCDRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGDCDRepairActionPerformed(evt);
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
                        .addGap(35, 35, 35)
                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
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
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RepairLayout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSinhRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(RepairLayout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addGroup(RepairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel30)
                                            .addComponent(txtDia1))
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

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

        AddToolBar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\add-user.png")); // NOI18N
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
        jToolBar2.add(jSeparator3);

        jButton10.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\info.png")); // NOI18N
        jButton10.setText("Info");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setIconTextGap(1);
        jButton10.setMargin(new java.awt.Insets(2, 16, 2, 16));
        jButton10.setMaximumSize(new java.awt.Dimension(91, 75));
        jButton10.setMinimumSize(new java.awt.Dimension(91, 75));
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton10);
        jToolBar2.add(jSeparator4);

        jButton11.setIcon(new javax.swing.ImageIcon("C:\\Users\\ACER\\Documents\\DoAnJavaCore\\images\\info.png")); // NOI18N
        jButton11.setText("OuputFile");
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setIconTextGap(1);
        jButton11.setMargin(new java.awt.Insets(2, 16, 2, 16));
        jButton11.setMaximumSize(new java.awt.Dimension(91, 75));
        jButton11.setMinimumSize(new java.awt.Dimension(91, 75));
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton11);

        nameTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameTable.setText("Bảng Thông Tin");
        nameTable.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        nameTable.setMargin(new java.awt.Insets(4, 16, 4, 16));
        nameTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTableActionPerformed(evt);
            }
        });

        jMenuBar2.setPreferredSize(new java.awt.Dimension(367, 25));

        jMenu4.setText("File");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu4.setMargin(new java.awt.Insets(0, 7, 0, 7));

        Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Save.setText("Save");
        Save.setIconTextGap(0);
        Save.setMargin(new java.awt.Insets(0, 2, 0, -27));
        Save.setPreferredSize(new java.awt.Dimension(230, 28));
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        jMenu4.add(Save);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setText("Save As");
        jMenuItem4.setIconTextGap(2);
        jMenuItem4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jMenuItem4.setPreferredSize(new java.awt.Dimension(230, 28));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);
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

        RepairMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        RepairMenu.setText("Repair");
        RepairMenu.setIconTextGap(2);
        RepairMenu.setMargin(new java.awt.Insets(0, 2, 0, -32));
        RepairMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        RepairMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepairMenuActionPerformed(evt);
            }
        });
        jMenu7.add(RepairMenu);

        DeleteMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        DeleteMenu.setText("Delete");
        DeleteMenu.setIconTextGap(2);
        DeleteMenu.setPreferredSize(new java.awt.Dimension(230, 28));
        DeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteMenuActionPerformed(evt);
            }
        });
        jMenu7.add(DeleteMenu);

        clearMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
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
        jMenu12.add(jSeparator6);

        showMenuDiem1.setText("Histori Log");
        showMenuDiem1.setIconTextGap(2);
        showMenuDiem1.setMargin(new java.awt.Insets(0, 2, 0, 0));
        showMenuDiem1.setPreferredSize(new java.awt.Dimension(230, 28));
        showMenuDiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMenuDiem1ActionPerformed(evt);
            }
        });
        jMenu12.add(showMenuDiem1);

        jMenuBar2.add(jMenu12);

        jMenu2.setText("Filter");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        locTheoLop.setText("Filter by class");
        locTheoLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locTheoLopActionPerformed(evt);
            }
        });
        jMenu2.add(locTheoLop);

        locTheoHocLuc.setText("Filter by learning");
        locTheoHocLuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locTheoHocLucActionPerformed(evt);
            }
        });
        jMenu2.add(locTheoHocLuc);
        jMenu2.add(jSeparator9);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("Rank");
        jMenu2.add(jMenuItem1);

        jMenu3.setText("jMenu3");

        buttonGroup2.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem1);

        buttonGroup2.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("jRadioButtonMenuItem2");
        jMenu3.add(jRadioButtonMenuItem2);

        jMenu2.add(jMenu3);

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

        AddMenu4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        AddMenu4.setText("Scores Sort");
        AddMenu4.setIconTextGap(2);
        AddMenu4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AddMenu4.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenu4ActionPerformed(evt);
            }
        });
        SortScores.add(AddMenu4);

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

        AddMenu10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        AddMenu10.setText("Contact");
        AddMenu10.setIconTextGap(2);
        AddMenu10.setMargin(new java.awt.Insets(0, 2, 0, 0));
        AddMenu10.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenu10ActionPerformed(evt);
            }
        });
        jMenu15.add(AddMenu10);

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
                .addComponent(jLabel1)
                .addGap(89, 89, 89))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTable, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel1))
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
            showResultInfo();
        } else {
            JOptionPane.showMessageDialog(this, "bạn chưa nhập đầy đủ thông tin");
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

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
        nameTable.setText("Bảng Điểm");
        initTableDiemMenu();
        showResultDiem();
        flagMenu = 0;
    }//GEN-LAST:event_showMenuDiemActionPerformed

    private void showMenuInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMenuInfoActionPerformed
        // TODO add your handling code here:
        flagSearch = 0;
        listSearch.clear();
        nameTable.setText("Bảng Thông Tin");
        initTableInfoMenu();
        showResultInfo();
        flagMenu = 1;
    }//GEN-LAST:event_showMenuInfoActionPerformed

    private void AddMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu4ActionPerformed
        // TODO add your handling code here:
        Collections.sort(list, new ScoreComparator());
        initTableInfoMenu();
        showResultInfo();
    }//GEN-LAST:event_AddMenu4ActionPerformed

    private void SortAphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SortAphabetActionPerformed
        // TODO add your handling code here:
        Collections.sort(list, new nameComparatorAlphabet());
        initTableInfoMenu();
        showResultInfo();
    }//GEN-LAST:event_SortAphabetActionPerformed

    private void ReAlphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReAlphabetActionPerformed
        // TODO add your handling code here:
        Collections.sort(list, new nameComparatorAlphabet());
        Collections.reverse(list);
        initTableInfoMenu();
        showResultInfo();
    }//GEN-LAST:event_ReAlphabetActionPerformed

    private void AddMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddMenu3ActionPerformed

    private void AddMenu10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddMenu10ActionPerformed

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
        Add.setSize(730, 600);
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
            Object[] str = new Object[]{
                i++, sinhvien.getID(), sinhvien.getListDiem().get(0), sinhvien.getListDiem().get(1), sinhvien.getListDiem().get(2), sinhvien.getListDiem().get(3),
                sinhvien.getListDiem().get(4), sinhvien.getListDiem().get(5), sinhvien.getListDiem().get(6), sinhvien.getListDiem().get(7),
                sinhvien.getListDiem().get(8), sinhvien.getListDiem().get(9)};
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
            nameTable.setText("Bảng Điểm");
            flagMenu++;
        } else {
            initTableInfoMenu();
            showResultInfo();
            nameTable.setText("Bảng Thông Tin");
            flagMenu++;
        }
    }//GEN-LAST:event_showToolBarActionPerformed

    private void nameTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTableActionPerformed
        // TODO add your handling code here:
        if (flagSearch == 1) {
            if (flagMenu % 2 == 1) {
                initTableDiemMenu();
                showSearchtDiem();
                nameTable.setText("Bảng Điểm");
                flagMenu++;
            } else {
                initTableInfoMenu();
                showSearchInfo();
                nameTable.setText("Bảng Thông Tin");
                flagMenu++;
            }
        }
    }//GEN-LAST:event_nameTableActionPerformed

    private void showMenuDiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMenuDiem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showMenuDiem1ActionPerformed


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

            sinhVien.setID(txtIDRepair.getText());

            sinhVien.setHoTen(txtNameRepair.getText());
            if (rdNuRepair.isSelected()) {
                sinhVien.setGender(false);
            } else {
                sinhVien.setGender(true);
            }
            sinhVien.setLop(txtLopRepair.getText());

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
            initTableInfoMenu();
            showResultInfo();
            selectRows = -1;
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

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        // TODO add your handling code here:
        Add.setVisible(false);
    }//GEN-LAST:event_jLabel34MouseClicked

    private void locTheoLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locTheoLopActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        String strSearch = JOptionPane.showInputDialog(this, "Search", "Message", JOptionPane.OK_CANCEL_OPTION);
        if (strSearch != null) {
            for (SinhVien sinhvien : list) {
                if (strSearch.equals(sinhvien.getLop())) {
                    listSearch.add(sinhvien);
                    flagSearch = 1;
                }
            }
            if (flagSearch == 0) {
                String str = String.format("Không có trong danh sách: %s", strSearch);
                JOptionPane.showMessageDialog(this, str);
            } else {
                initTableInfoMenu();
                showSearchInfo();
            }
        }
    }//GEN-LAST:event_locTheoLopActionPerformed

    private void locTheoHocLucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locTheoHocLucActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        String strSearch = JOptionPane.showInputDialog(this, "Search", "Message", JOptionPane.OK_CANCEL_OPTION);
        if (strSearch != null) {
            for (SinhVien sinhvien : list) {
                System.out.println(sinhvien.xepLoaiHocLuc());
                String b = sinhvien.xepLoaiHocLuc();
                if (strSearch.equals(b)) {
                    listSearch.add(sinhvien);
                    flagSearch = 1;
                }
            }
            if (flagSearch == 0) {
                String str = String.format("Không có trong danh sách: %s", strSearch);
                JOptionPane.showMessageDialog(this, str);
            } else {
                initTableInfoMenu();
                showSearchInfo();
            }
        }
    }//GEN-LAST:event_locTheoHocLucActionPerformed

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

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

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
    private javax.swing.JMenuItem AddMenu10;
    private javax.swing.JMenuItem AddMenu3;
    private javax.swing.JMenuItem AddMenu4;
    private javax.swing.JButton AddToolBar;
    private javax.swing.JMenuItem DeleteMenu;
    private javax.swing.JMenuItem DeletePopup;
    private javax.swing.JButton ExitAdd;
    private javax.swing.JButton ExitRepair;
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
    private javax.swing.JButton SearchToolBar;
    private javax.swing.JTextField SearchTxt;
    private javax.swing.JMenuItem SortAphabet;
    private javax.swing.JMenu SortScores;
    private javax.swing.JComboBox<String> TxtAddress;
    private javax.swing.JComboBox<String> TxtAddressRepair;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JMenuItem clearMenu;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JDialog jDialog1;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenuItem locTheoHocLuc;
    private javax.swing.JMenuItem locTheoLop;
    private javax.swing.JButton nameTable;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNamRepair;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdNuRepair;
    private javax.swing.JButton show;
    private javax.swing.JMenuItem showMenuDiem;
    private javax.swing.JMenuItem showMenuDiem1;
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
