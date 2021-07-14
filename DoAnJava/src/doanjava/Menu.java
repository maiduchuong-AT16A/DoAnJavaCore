/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanjava;

import java.awt.MouseInfo;
import java.awt.Panel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class Menu extends javax.swing.JFrame {

    private ArrayList<SinhVien> list = new ArrayList<>();
    private ArrayList<SinhVien> listSearch = new ArrayList<>();
    DefaultTableModel defaultTableModelInfo = new DefaultTableModel();
    DefaultTableModel defaultTableModelDiem = new DefaultTableModel();
    private int flag = 1;
    private int flagMenu = 1;
    private int flagSearch = 0;
    private int flagRepair = 0;

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        this.setLocationRelativeTo(null);
        initTableInfoMenu();
        initTableInfo();
        showResultInfo();
        tablemenu.setComponentPopupMenu(PopUpTable);
    }

    private void initTableInfoMenu() {
        String[] str = new String[]{"STT", "ID", "Họ và Tên", "Giới tính", "Ngày Sinh", "Quê Quán", "Lớp", "Diểm Trung Bình"};
        defaultTableModelInfo.setColumnIdentifiers(str);
        tablemenu.setModel(defaultTableModelInfo);
        show.setText("Hiển thị Bảng điểm");
    }

    private void initTableDiemMenu() {
        String[] str = new String[]{"STT", "ID", "Toán", "Lý", "Hóa", "Văn", "Sinh", "Sử", "Địa", "GDCD", "Tin Hoc", "Công nghệ"};
        defaultTableModelDiem.setColumnIdentifiers(str);
        tablemenu.setModel(defaultTableModelDiem);
        show.setText("Hiển thị thông tin");
    }

    @SuppressWarnings("empty-statement")
    private void initTableInfo() {
        String[] str = new String[]{"STT", "ID", "Họ và Tên", "Giới tính", "Ngày Sinh", "Quê Quán", "Lớp", "Diểm Trung Bình"};
        defaultTableModelInfo.setColumnIdentifiers(str);
        table.setModel(defaultTableModelInfo);
        show.setText("Hiển thị Bảng điểm");
    }

    private void initTableDiem() {
        String[] str = new String[]{"STT", "ID", "Toán", "Lý", "Hóa", "Văn", "Sinh", "Sử", "Địa", "GDCD", "Tin Hoc", "Công nghệ"};
        defaultTableModelDiem.setColumnIdentifiers(str);
        table.setModel(defaultTableModelDiem);
        show.setText("Hiển thị thông tin");
    }

    private void showResultDiem() {
        int i = 1;
        defaultTableModelDiem.setRowCount(0);
        for (SinhVien sinhvien : list) {

            Object[] str = new Object[]{
                i++, sinhvien.getID(), sinhvien.getToanHoc(), sinhvien.getVatLy(), sinhvien.getHoaHoc(),
                sinhvien.getNguVan(), sinhvien.getSinhHoc(), sinhvien.getLichSu(), sinhvien.getDiaLy(), sinhvien.getGDCD(),
                sinhvien.getTinHoc(), sinhvien.getCongNghe()};
            defaultTableModelDiem.addRow(str);
        }
        defaultTableModelDiem.fireTableDataChanged();
    }

    private void showResultInfo() {
        int i = 1;
        defaultTableModelInfo.setRowCount(0);
        for (SinhVien sinhvien : list) {
            String name = String.format("%s %s", sinhvien.getHoDem(), sinhvien.getTen());
            String dateString = sinhvien.getDayBir().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String diemTB = String.format("%.2f", sinhvien.getDiemTB());
            Object[] str = new Object[]{i++, sinhvien.getID(), name, sinhvien.isGender() ? "Nam" : "Nữ", dateString, sinhvien.getQueQuan(), sinhvien.getLop(), diemTB};
            defaultTableModelInfo.addRow(str);
        }
        defaultTableModelInfo.fireTableDataChanged();
    }

    public boolean validateForm() {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty()
                || txtNgay.getText().isEmpty() || txtThang.getText().isEmpty() || txtNam.getText().isEmpty()
                || txtGDCD.getText().isEmpty() || txtDialy.getText().isEmpty() || txtCongNghe.getText().isEmpty()
                || txtHoa.getText().isEmpty() || txtLy.getText().isEmpty() || txtSinh.getText().isEmpty()
                || txtTin.getText().isEmpty() || txtToan.getText().isEmpty() || txtVan.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public SinhVien getNhap() {

        SinhVien sinhVien = new SinhVien();

        sinhVien.setID(txtID.getText());

        sinhVien.setHoDem(txtName.getText().substring(0, txtName.getText().lastIndexOf(" ")));
        sinhVien.setTen(txtName.getText().substring(1 + txtName.getText().lastIndexOf(" ")));
        if (rdNu.isSelected()) {
            sinhVien.setGender(false);
        } else {
            sinhVien.setGender(true);
        }
        sinhVien.setLop(txtLop.getText());

        int day = Integer.parseInt(txtNgay.getText());
        int month = Integer.parseInt(this.txtThang.getText());
        int year = Integer.parseInt(this.txtNam.getText());
        LocalDate a = LocalDate.of(year, month, day);
        sinhVien.setDayBir(a);

        sinhVien.setQueQuan((String) TxtAddress.getSelectedItem());

        //sinhVien.setQueQuan(TxtAddress.getText());
        sinhVien.setToanHoc(Double.parseDouble(txtToan.getText()));
        sinhVien.setVatLy(Double.parseDouble(txtLy.getText()));
        sinhVien.setHoaHoc(Double.parseDouble(txtHoa.getText()));
        sinhVien.setSinhHoc(Double.parseDouble(txtSinh.getText()));
        sinhVien.setTinHoc(Double.parseDouble(txtTin.getText()));
        sinhVien.setCongNghe(Double.parseDouble(txtCongNghe.getText()));
        sinhVien.setNguVan(Double.parseDouble(txtVan.getText()));
        sinhVien.setDiaLy(Double.parseDouble(txtDialy.getText()));
        sinhVien.setLichSu(Double.parseDouble(txtSu.getText()));
        sinhVien.setGDCD(Double.parseDouble(txtGDCD.getText()));
        return sinhVien;
    }

    private void RepairTableMenu() {
        int selectRows = tablemenu.getSelectedRow();
        if (selectRows >= 0) {
            String ID1 = (String) tablemenu.getValueAt(selectRows, 1);
            for (flagRepair = 0; flagRepair < list.size(); flagRepair++) {
                SinhVien sinhVien = list.get(flagRepair);
                if (ID1.equals(sinhVien.getID())) {
                    txtIDRepair.setText(ID1);
                    String name = String.format("%s %s", sinhVien.getHoDem(), sinhVien.getTen());
                    txtNameRepair.setText(name);
                    txtLopRepair.setText(sinhVien.getLop());
                    
                    if(sinhVien.isGender()==false)
                    {
                        rdNuRepair.setSelected(true);
                    }else{
                        rdNamRepair.setSelected(true);
                    }

                    LocalDate a = sinhVien.getDayBir();
                    String dateString = a.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    String[] b = dateString.split("-");
                    txtNgayRepair.setText(b[0]);
                    txtThangRepair.setText(b[1]);
                    txtNamRepair.setText(b[2]);
                    TxtAddressRepair.setSelectedItem(sinhVien.getQueQuan());
                    txtToanRepair.setText(sinhVien.getToanHoc() + "");
                    txtLyRepair.setText(sinhVien.getVatLy() + "");
                    txtHoaRepair.setText(sinhVien.getHoaHoc() + "");
                    txtSinhRepair.setText(sinhVien.getSinhHoc() + "");
                    txtTinRepair.setText(sinhVien.getTinHoc() + "");
                    txtCongNgheRepair.setText(sinhVien.getCongNghe() + "");
                    txtVanRepair.setText(sinhVien.getNguVan() + "");
                    txtDialyRepair.setText(sinhVien.getDiaLy() + "");
                    txtSuRepair.setText(sinhVien.getLichSu() + "");
                    txtGDCDRepair.setText(sinhVien.getGDCD() + "");
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
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLy = new javax.swing.JTextField();
        txtNgay = new javax.swing.JTextField();
        txtDialy = new javax.swing.JTextField();
        txtThang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtGDCD = new javax.swing.JTextField();
        txtVan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rdNu = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDia = new javax.swing.JLabel();
        txtLop = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
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
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        TxtAddress = new javax.swing.JComboBox<>();
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
        nameTable = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
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
        jMenu13 = new javax.swing.JMenu();
        jMenu17 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        AddMenu5 = new javax.swing.JMenuItem();
        AddMenu6 = new javax.swing.JMenuItem();
        AddMenu4 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        AddMenu3 = new javax.swing.JMenuItem();
        AddMenu10 = new javax.swing.JMenuItem();
        jMenu16 = new javax.swing.JMenu();

        Add.setTitle("Thêm Sinh Viên");
        Add.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Add.setResizable(false);

        jLabel2.setText("Họ và tên");

        jLabel12.setText("Công Nghệ");

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

        jLabel11.setText("GDCD");

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

        jLabel6.setText("Toán Học");

        jLabel13.setText("Ngữ Văn");

        jLabel7.setText("Hóa Học");

        jLabel8.setText("Vật Lý");

        jLabel14.setText("Giới tính");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");
        rdNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNamActionPerformed(evt);
            }
        });

        jLabel9.setText("Sinh");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        jLabel10.setText("Tin Học");

        jLabel3.setText("Lớp");

        txtDia.setText("Địa Lý");

        txtLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLopActionPerformed(evt);
            }
        });

        jLabel15.setText("Lịch Sử");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jLabel16.setText("        ID");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        TxtAddress.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang\t", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình\t", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên\t", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM" }));

        javax.swing.GroupLayout AddLayout = new javax.swing.GroupLayout(Add.getContentPane());
        Add.getContentPane().setLayout(AddLayout);
        AddLayout.setHorizontalGroup(
            AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AddLayout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Add1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addComponent(rdNam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdNu))
                            .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNam, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                            .addComponent(txtName)
                            .addComponent(txtLop)
                            .addComponent(TxtAddress, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(123, 123, 123)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
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
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(AddLayout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(txtDia))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtGDCD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtDialy, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(57, 57, 57))
                    .addGroup(AddLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ExitAdd)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        AddLayout.setVerticalGroup(
            AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(jLabel6))
                            .addComponent(txtToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCongNghe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(AddLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDialy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtGDCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))))
                    .addGroup(AddLayout.createSequentialGroup()
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(3, 3, 3)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(rdNu)
                            .addComponent(rdNam))
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel5))
                            .addGroup(AddLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGap(26, 26, 26)
                .addGroup(AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add1)
                    .addComponent(jButton3)
                    .addComponent(show)
                    .addComponent(ExitAdd))
                .addGap(7, 7, 7)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        DeletePopup.setText("Delete");
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Phần Mềm Quản Lý Học Sinh");

        tablemenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
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

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Save");
        jMenuItem2.setIconTextGap(0);
        jMenuItem2.setMargin(new java.awt.Insets(0, 2, 0, -27));
        jMenuItem2.setPreferredSize(new java.awt.Dimension(230, 28));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

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

        jMenu13.setText("Statistic");
        jMenu13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu13.setMargin(new java.awt.Insets(0, 7, 0, 7));
        jMenuBar2.add(jMenu13);

        jMenu17.setText("Sort");
        jMenu17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu17.setMargin(new java.awt.Insets(0, 7, 0, 7));

        jMenu1.setText("Name Sort");
        jMenu1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jMenu1.setPreferredSize(new java.awt.Dimension(230, 28));

        AddMenu5.setText("Alphabet ");
        AddMenu5.setIconTextGap(2);
        AddMenu5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AddMenu5.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenu5ActionPerformed(evt);
            }
        });
        jMenu1.add(AddMenu5);

        AddMenu6.setText("Scores Sort");
        AddMenu6.setIconTextGap(2);
        AddMenu6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AddMenu6.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenu6ActionPerformed(evt);
            }
        });
        jMenu1.add(AddMenu6);

        jMenu17.add(jMenu1);

        AddMenu4.setText("Scores Sort");
        AddMenu4.setIconTextGap(2);
        AddMenu4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AddMenu4.setPreferredSize(new java.awt.Dimension(230, 28));
        AddMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMenu4ActionPerformed(evt);
            }
        });
        jMenu17.add(AddMenu4);

        jMenuBar2.add(jMenu17);

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
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nameTable, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(89, 89, 89))
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
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
        txtDialy.setText("");
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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
    }//GEN-LAST:event_DeleteMenuActionPerformed

    private void RefreshMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshMenuActionPerformed
        // TODO add your handling code here:
        showResultInfo();
    }//GEN-LAST:event_RefreshMenuActionPerformed

    private void showMenuDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMenuDiemActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        nameTable.setText("Bảng Điểm");
        initTableDiemMenu();
        showResultDiem();
        flagMenu = 0;
    }//GEN-LAST:event_showMenuDiemActionPerformed

    private void showMenuInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMenuInfoActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        nameTable.setText("Bảng Thông Tin");
        initTableInfoMenu();
        showResultInfo();
        flagMenu = 1;
    }//GEN-LAST:event_showMenuInfoActionPerformed

    private void AddMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddMenu4ActionPerformed

    private void AddMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddMenu5ActionPerformed

    private void AddMenu6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMenu6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddMenu6ActionPerformed

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
        Add.setSize(800, 600);
        Add.setLocationRelativeTo(null);
        Add.setVisible(true);
    }//GEN-LAST:event_AddToolBarActionPerformed

    private int ConditionSearch(SinhVien sinhvien, String str) {
        String dateString = sinhvien.getDayBir().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String nameString = String.format("%s %s", sinhvien.getHoDem(), sinhvien.getTen());
        if (str.equalsIgnoreCase(sinhvien.getLop()) || str.equalsIgnoreCase(sinhvien.getID())
                || str.equalsIgnoreCase(dateString) || str.equalsIgnoreCase(sinhvien.getQueQuan())
                || str.equalsIgnoreCase(nameString)) {
            return 1;
        }
        return 0;
    }

    private void showSearchInfo() {
        int i = 0;
        defaultTableModelInfo.setRowCount(0);
        for (SinhVien sinhvien : listSearch) {
            String name = String.format("%s %s", sinhvien.getHoDem(), sinhvien.getTen());
            String dateString = sinhvien.getDayBir().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String diemTB = String.format("%.2f", sinhvien.getDiemTB());
            Object[] str = new Object[]{i++, sinhvien.getID(), name, sinhvien.isGender() ? "Nam" : "Nữ", dateString, sinhvien.getQueQuan(), sinhvien.getLop(), diemTB};
            defaultTableModelInfo.addRow(str);
        }
        defaultTableModelInfo.fireTableDataChanged();
    }

    private void showSearchtDiem() {
        int i = 1;
        defaultTableModelDiem.setRowCount(0);
        for (SinhVien sinhvien : listSearch) {
            Object[] str = new Object[]{
                i++, sinhvien.getID(), sinhvien.getToanHoc(), sinhvien.getVatLy(), sinhvien.getHoaHoc(),
                sinhvien.getNguVan(), sinhvien.getSinhHoc(), sinhvien.getLichSu(), sinhvien.getDiaLy(), sinhvien.getGDCD(),
                sinhvien.getTinHoc(), sinhvien.getCongNghe()};
            defaultTableModelDiem.addRow(str);
        }
        defaultTableModelDiem.fireTableDataChanged();
    }

    private void SearchToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchToolBarActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
        flagSearch = 0;
        String strSearch = JOptionPane.showInputDialog(this, "Search", "Message", JOptionPane.OK_CANCEL_OPTION);
        for (SinhVien sinhvien : list) {
            if (ConditionSearch(sinhvien, strSearch) == 1) {
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
    }//GEN-LAST:event_SearchToolBarActionPerformed

    private void showToolBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToolBarActionPerformed
        // TODO add your handling code here:
        listSearch.clear();
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

    private void txtNgayRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayRepairActionPerformed

    private void txtLyRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLyRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLyRepairActionPerformed

    private void txtLopRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLopRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLopRepairActionPerformed

    private void txtIDRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDRepairActionPerformed

    private void rdNamRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNamRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdNamRepairActionPerformed

    private void txtTinRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTinRepairActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTinRepairActionPerformed

    private void RepairDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepairDialogActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh sách?", "Notification", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            SinhVien sinhVien = new SinhVien();

            sinhVien.setID(txtIDRepair.getText());

            sinhVien.setHoDem(txtNameRepair.getText().substring(0, txtNameRepair.getText().lastIndexOf(" ")));
            sinhVien.setTen(txtNameRepair.getText().substring(1 + txtNameRepair.getText().lastIndexOf(" ")));
            if (rdNuRepair.isSelected()) {
                sinhVien.setGender(false);
            } else {
                sinhVien.setGender(true);
            }
            sinhVien.setLop(txtLopRepair.getText());

            int day = Integer.parseInt(txtNgayRepair.getText());
            int month = Integer.parseInt(this.txtThangRepair.getText());
            int year = Integer.parseInt(this.txtNamRepair.getText());
            LocalDate a = LocalDate.of(year, month, day);
            sinhVien.setDayBir(a);

            sinhVien.setQueQuan((String) TxtAddressRepair.getSelectedItem());

            sinhVien.setToanHoc(Double.parseDouble(txtToanRepair.getText()));
            sinhVien.setVatLy(Double.parseDouble(txtLyRepair.getText()));
            sinhVien.setHoaHoc(Double.parseDouble(txtHoaRepair.getText()));
            sinhVien.setSinhHoc(Double.parseDouble(txtSinhRepair.getText()));
            sinhVien.setTinHoc(Double.parseDouble(txtTinRepair.getText()));
            sinhVien.setCongNghe(Double.parseDouble(txtCongNgheRepair.getText()));
            sinhVien.setNguVan(Double.parseDouble(txtVanRepair.getText()));
            sinhVien.setDiaLy(Double.parseDouble(txtDialyRepair.getText()));
            sinhVien.setLichSu(Double.parseDouble(txtSuRepair.getText()));
            sinhVien.setGDCD(Double.parseDouble(txtGDCDRepair.getText()));
            list.set(flagRepair, sinhVien);
            showActionPerformed(evt);
            ExitRepairActionPerformed(evt);
        }
    }//GEN-LAST:event_RepairDialogActionPerformed

    private void ExitRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitRepairActionPerformed
        // TODO add your handling code here:
        Repair.setVisible(false);
    }//GEN-LAST:event_ExitRepairActionPerformed

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
    private javax.swing.JMenuItem AddMenu5;
    private javax.swing.JMenuItem AddMenu6;
    private javax.swing.JButton AddToolBar;
    private javax.swing.JMenuItem DeleteMenu;
    private javax.swing.JMenuItem DeletePopup;
    private javax.swing.JButton ExitAdd;
    private javax.swing.JButton ExitRepair;
    private javax.swing.JPopupMenu PopUpTable;
    private javax.swing.JMenuItem RefreshMenu;
    private javax.swing.JDialog Repair;
    private javax.swing.JButton RepairDialog;
    private javax.swing.JMenuItem RepairMenu;
    private javax.swing.JMenuItem RepairPopup;
    private javax.swing.JButton RepairToolBar;
    private javax.swing.JButton SearchToolBar;
    private javax.swing.JComboBox<String> TxtAddress;
    private javax.swing.JComboBox<String> TxtAddressRepair;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem clearMenu;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JToolBar jToolBar2;
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
    private javax.swing.JLabel txtDia;
    private javax.swing.JLabel txtDia1;
    private javax.swing.JTextField txtDialy;
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
