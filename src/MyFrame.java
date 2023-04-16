import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MyFrame extends JFrame {

    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result = null;
    int id;
    String customerNameS;
    String productNameS;
    String buff;
    ArrayList<String> purchaseCustomerNamesList = new ArrayList<>();
    ArrayList<String> purchaseCustomerProductsList = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();

    // nav
    JTabbedPane nav = new JTabbedPane();

    // list of panels
    JPanel customersPanel = new JPanel();
    JPanel productsPanel = new JPanel();
    JPanel purchasesPanel = new JPanel();
    JPanel inquiryPanel = new JPanel();

    // customer panels
    JPanel customersUpPanel = new JPanel();
    JPanel customersMidPanel = new JPanel();
    JPanel customersRegularBTPanel = new JPanel();
    JPanel customersSearchBTPanel = new JPanel();
    JPanel customersDownPanel = new JPanel();

    // product panels
    JPanel productsUpPanel = new JPanel();
    JPanel productsMidPanel = new JPanel();
    JPanel productsRegularBTPanel = new JPanel();
    JPanel productsSearchBTPanel = new JPanel();
    JPanel productsDownPanel = new JPanel();

    // purchase panels
    JPanel purchasesUpPanel = new JPanel();
    JPanel purchasesMidPanel = new JPanel();
    JPanel purchaseRegularBTPanel = new JPanel();
    JPanel purchaseSearchBTPanel = new JPanel();
    JPanel purchasesDownPanel = new JPanel();

    // inquiry panels
    JPanel inquiryUpPanel = new JPanel();
    JPanel inquiryMidPanel = new JPanel();
    JPanel inquiryDownPanel = new JPanel();

    // customer labels
    JLabel firstNameLabel = new JLabel("Име:");
    JLabel lastNameLabel = new JLabel("Фамилия:");
    JLabel phoneLabel = new JLabel("Телефонен номер:");
    JLabel addressLabel = new JLabel("Адрес:");

    // product labels
    JLabel productBrandLabel = new JLabel("Марка:");
    JLabel productModelLabel = new JLabel("Модел:");
    JLabel productPriceLabel = new JLabel("Цена:");
    JLabel productStorageLabel = new JLabel("Размер на памет за съхранение:");
    JLabel productColorLabel = new JLabel("Цвят:");

    // purchase labels
    JLabel quantityLabel = new JLabel("Количество:");
    JLabel customerNameLabel = new JLabel("Клиенти:");
    JLabel productNameLabel = new JLabel("Продукти:");
    JLabel purchaseDateLabel = new JLabel("Дата на покупка:");

    //  inquiry labels
    JLabel inquiryAddressLabel = new JLabel("Адрес:");
    JLabel inquiryModelLabel = new JLabel("Модел:");

    // customer text fields
    JTextField firstNameTF = new JTextField();
    JTextField lastNameTF = new JTextField();
    JTextField phoneTF = new JTextField();
    JTextField addressTF = new JTextField();

    // product text fields
    JTextField productModelTF = new JTextField();
    JTextField productPriceTF = new JTextField();
    JTextField productStorageTF = new JTextField();
    JTextField productColorTF = new JTextField();

    // purchases text fields
    JTextField quantityTF = new JTextField();
    JTextField purchaseDateTF = new JTextField();

    // inquiry text fields
    JTextField inquiryAddressTF = new JTextField();
    JTextField inquiryModelTF = new JTextField();

    // combo boxes
    String[] brands = {"","Samsung Galaxy", "Apple iPhone", "Sony Xperia", "Google Pixel"};
    JComboBox<String> customerCombo = new JComboBox<>();
    JComboBox<String> productCombo = new JComboBox<>();
    JComboBox<String> brandsCombo = new JComboBox<>(brands);

    // customer buttons
    JButton customersAddBt = new JButton("Добавяне");
    JButton customersDeleteBt = new JButton("Изтриване");
    JButton customersEditBt = new JButton("Редактиране");
    JButton customersRefreshBt = new JButton("Обнови");
    JButton customersPhoneBt = new JButton("Търсене по телефонен номер");
    JButton customersAddressBt = new JButton("Търсене по адрес");

    // product buttons
    JButton productAddBt = new JButton("Добавяне");
    JButton productEditBt = new JButton("Редактиране");
    JButton productDeleteBt = new JButton("Изтриване");
    JButton productRefreshBt = new JButton("Обнови");
    JButton productBrandBt = new JButton("Търсене по марка");
    JButton productModelBt = new JButton("Търсене по модел");

    // purchase buttons
    JButton purchaseAddBt = new JButton("Купуване");
    JButton purchaseEditBt = new JButton("Редактиране");
    JButton purchaseDeleteBt = new JButton("Изтриване");
    JButton purchaseRefreshBt = new JButton("Обнови");
    JButton purchaseCustomerBt = new JButton("Търсене по клиент");
    JButton purchaseProductBt = new JButton("Търсене по продукт");
    JButton purchaseDateBt = new JButton("Търсене по дата");

    // inquiry buttons
    JButton inquirySearchBt = new JButton("Търсене");
    JButton inquiryRefreshBt = new JButton("Обнови");

    // tables
    JTable customersTable = new JTable();
    JTable productsTable = new JTable();
    JScrollPane customersScroll = new JScrollPane(customersTable);
    JScrollPane productsScroll = new JScrollPane(productsTable);
    JTable purchaseTable = new JTable();
    JScrollPane purchaseScroll = new JScrollPane(purchaseTable);
    JTable inquiryTable = new JTable();
    JScrollPane inquiryScroll = new JScrollPane(inquiryTable);

    // fonts
    Font fontTF = new Font("Arial", Font.PLAIN, 18);
    Font fontCombo = new Font("Arial", Font.PLAIN, 18);

    // error windows
    static JFrame frame = new JFrame("Error");

    public MyFrame() {
        this.setSize(900,700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Phone4You");
        ImageIcon img = new ImageIcon("./img/phone icon.png");
        this.setIconImage(img.getImage());
        this.setBackground(new Color(60, 71, 112));

        nav.addChangeListener(e -> clearForms());
        nav.setForeground(new Color(235, 124, 163));
        nav.setBackground(new Color(60, 71, 112));
        this.add(nav);

        // Customer panel -----------------------------------------------------------------------
        customersPanel.setLayout(new GridLayout(3, 1));
        customersPanel.setBackground(new Color(60, 71, 112));
        nav.add(customersPanel, "Клиенти");

        // customersUpPanel --------------------------------------
        customersUpPanel.setLayout(new GridLayout(4,2));
        customersUpPanel.setBackground(new Color(60, 71, 112));
        customersUpPanel.add(firstNameLabel);
        firstNameLabel.setForeground(new Color(235, 124, 163));
        customersUpPanel.add(firstNameTF);
        firstNameTF.setBackground(new Color(85, 94, 133));
        firstNameTF.setForeground(new Color(255, 255, 255));
        firstNameTF.setFont(fontTF);
        customersUpPanel.add(lastNameLabel);
        lastNameLabel.setForeground(new Color(235, 124, 163));
        customersUpPanel.add(lastNameTF);
        lastNameTF.setBackground(new Color(85, 94, 133));
        lastNameTF.setForeground(new Color(255, 255, 255));
        lastNameTF.setFont(fontTF);
        customersUpPanel.add(phoneLabel);
        phoneLabel.setForeground(new Color(235, 124, 163));
        customersUpPanel.add(phoneTF);
        phoneTF.setBackground(new Color(85, 94, 133));
        phoneTF.setForeground(new Color(255, 255, 255));
        phoneTF.setFont(fontTF);
        customersUpPanel.add(addressLabel);
        addressLabel.setForeground(new Color(235, 124, 163));
        customersUpPanel.add(addressTF);
        addressTF.setBackground(new Color(85, 94, 133));
        addressTF.setForeground(new Color(255, 255, 255));
        addressTF.setFont(fontTF);
        customersPanel.add(customersUpPanel);

        // customersMidPanel --------------------------------------
        customersMidPanel.setLayout(new GridLayout(2,1));
        customersMidPanel.setBackground(new Color(60, 71, 112));
        customersRegularBTPanel.setBackground(new Color(60, 71, 112));
        customersSearchBTPanel.setBackground(new Color(60, 71, 112));
        customersRegularBTPanel.add(customersAddBt);
        customersAddBt.setBackground(new Color(100, 104, 176));
        customersAddBt.setForeground(new Color(255, 255, 255));
        customersRegularBTPanel.add(customersDeleteBt);
        customersDeleteBt.setBackground(new Color(100, 104, 176));
        customersDeleteBt.setForeground(new Color(255, 255, 255));
        customersRegularBTPanel.add(customersEditBt);
        customersEditBt.setBackground(new Color(100, 104, 176));
        customersEditBt.setForeground(new Color(255, 255, 255));
        customersRegularBTPanel.add(customersRefreshBt);
        customersRefreshBt.setBackground(new Color(100, 104, 176));
        customersRefreshBt.setForeground(new Color(255, 255, 255));
        customersSearchBTPanel.add(customersPhoneBt);
        customersPhoneBt.setBackground(new Color(100, 104, 176));
        customersPhoneBt.setForeground(new Color(255, 255, 255));
        customersSearchBTPanel.add(customersAddressBt);
        customersAddressBt.setBackground(new Color(100, 104, 176));
        customersAddressBt.setForeground(new Color(255, 255, 255));
        customersAddBt.addActionListener(new CustomersAddAction());
        customersEditBt.addActionListener(new CustomersEditAction());
        customersDeleteBt.addActionListener(new CustomersDeleteAction());
        customersRefreshBt.addActionListener(new CustomersRefreshAction());
        customersPhoneBt.addActionListener(new CustomersSearchActionPhone());
        customersAddressBt.addActionListener(new CustomersSearchActionAddress());
        customersMidPanel.add(customersRegularBTPanel);
        customersMidPanel.add(customersSearchBTPanel);
        customersPanel.add(customersMidPanel);

        // customersDownPanel ----------------------
        customersDownPanel.setBackground(new Color(60, 71, 112));
        customersScroll.setPreferredSize(new Dimension(500,150));
        customersTable.setBackground(new Color(223, 224, 230));
        customersTable.addMouseListener(new CustomersMouseAction());
        customersDownPanel.add(customersScroll);
        customersPanel.add(customersDownPanel);

        refreshCustomersTable();
        refreshCustomersCombo();

        // Product panel -----------------------------------------------------------------------
        productsPanel.setLayout(new GridLayout(3, 1));
        productsPanel.setBackground(new Color(60, 71, 112));
        nav.add(productsPanel, "Продукти");

        // productsUpPanel --------------------------------------
        productsUpPanel.setLayout(new GridLayout(5,2));
        productsUpPanel.setBackground(new Color(60, 71, 112));
        productsUpPanel.add(productBrandLabel);
        productBrandLabel.setForeground(new Color(235, 124, 163));
        productsUpPanel.add(brandsCombo);
        brandsCombo.setBackground(new Color(85, 94, 133));
        brandsCombo.setForeground(new Color(255, 255, 255));
        brandsCombo.setFont(fontCombo);
        productsUpPanel.add(productModelLabel);
        productModelLabel.setForeground(new Color(235, 124, 163));
        productsUpPanel.add(productModelTF);
        productModelTF.setBackground(new Color(85, 94, 133));
        productModelTF.setForeground(new Color(255, 255, 255));
        productModelTF.setFont(fontTF);
        productsUpPanel.add(productPriceLabel);
        productPriceLabel.setForeground(new Color(235, 124, 163));
        productsUpPanel.add(productPriceTF);
        productPriceTF.setBackground(new Color(85, 94, 133));
        productPriceTF.setForeground(new Color(255, 255, 255));
        productPriceTF.setFont(fontTF);
        productsUpPanel.add(productStorageLabel);
        productStorageLabel.setForeground(new Color(235, 124, 163));
        productsUpPanel.add(productStorageTF);
        productStorageTF.setBackground(new Color(85, 94, 133));
        productStorageTF.setForeground(new Color(255, 255, 255));
        productStorageTF.setFont(fontTF);
        productsUpPanel.add(productColorLabel);
        productColorLabel.setForeground(new Color(235, 124, 163));
        productsUpPanel.add(productColorTF);
        productColorTF.setBackground(new Color(85, 94, 133));
        productColorTF.setForeground(new Color(255, 255, 255));
        productColorTF.setFont(fontTF);
        productsPanel.add(productsUpPanel);

        // productsMidPanel-----------------------------------
        productsMidPanel.setLayout(new GridLayout(2, 1));
        productsMidPanel.setBackground(new Color(60, 71, 112));
        productsRegularBTPanel.setBackground(new Color(60, 71, 112));
        productsSearchBTPanel.setBackground(new Color(60, 71, 112));
        productsRegularBTPanel.add(productAddBt);
        productAddBt.setBackground(new Color(100, 104, 176));
        productAddBt.setForeground(new Color(255, 255, 255));
        productAddBt.addActionListener(new ProductsAddAction());
        productsRegularBTPanel.add(productDeleteBt);
        productDeleteBt.setBackground(new Color(100, 104, 176));
        productDeleteBt.setForeground(new Color(255, 255, 255));
        productDeleteBt.addActionListener(new ProductsDeleteAction());
        productsRegularBTPanel.add(productEditBt);
        productEditBt.setBackground(new Color(100, 104, 176));
        productEditBt.setForeground(new Color(255, 255, 255));
        productEditBt.addActionListener(new ProductsEditAction());
        productsRegularBTPanel.add(productRefreshBt);
        productRefreshBt.setBackground(new Color(100, 104, 176));
        productRefreshBt.setForeground(new Color(255, 255, 255));
        productRefreshBt.addActionListener(new ProductsRefreshAction());
        productsSearchBTPanel.add(productBrandBt);
        productBrandBt.setBackground(new Color(100, 104, 176));
        productBrandBt.setForeground(new Color(255, 255, 255));
        productBrandBt.addActionListener(new ProductsSearchActionBrand());
        productsSearchBTPanel.add(productModelBt);
        productModelBt.setBackground(new Color(100, 104, 176));
        productModelBt.setForeground(new Color(255, 255, 255));
        productModelBt.addActionListener(new ProductsSearchActionModel());
        productsMidPanel.add(productsRegularBTPanel);
        productsMidPanel.add(productsSearchBTPanel);
        productsPanel.add(productsMidPanel);

        // productsDownPanel-----------------------------------
        productsDownPanel.setBackground(new Color(60, 71, 112));
        productsTable.setBackground(new Color(223, 224, 230));
        productsTable.addMouseListener(new ProductsMouseAction());
        productsScroll.setPreferredSize(new Dimension(600,150));
        productsDownPanel.add(productsScroll);
        productsPanel.add(productsDownPanel);

        refreshProductsTable();
        refreshProductsCombo();

        // Purchase panel -----------------------------------------------------------------------
        purchasesPanel.setLayout(new GridLayout(3, 1));
        purchasesPanel.setBackground(new Color(60, 71, 112));
        nav.add(purchasesPanel, "Поръчки");

        // purchaseUpPanel-----------------------------------------------------------------------
        purchasesUpPanel.setLayout(new GridLayout(4,2));
        purchasesUpPanel.setBackground(new Color(60, 71, 112));
        purchasesUpPanel.add(customerNameLabel);
        customerNameLabel.setForeground(new Color(235, 124, 163));
        customerCombo.setPreferredSize(new Dimension(780, 25));
        purchasesUpPanel.add(customerCombo);
        customerCombo.setBackground(new Color(85, 94, 133));
        customerCombo.setForeground(new Color(255, 255, 255));
        customerCombo.setFont(fontCombo);
        purchasesUpPanel.add(productNameLabel);
        productNameLabel.setForeground(new Color(235, 124, 163));
        productCombo.setPreferredSize(new Dimension(780, 25));
        purchasesUpPanel.add(productCombo);
        productCombo.setBackground(new Color(85, 94, 133));
        productCombo.setForeground(new Color(255, 255, 255));
        productCombo.setFont(fontCombo);
        purchasesUpPanel.add(quantityLabel);
        quantityLabel.setForeground(new Color(235, 124, 163));
        quantityTF.setPreferredSize(new Dimension(780, 25));
        purchasesUpPanel.add(quantityTF);
        quantityTF.setBackground(new Color(85, 94, 133));
        quantityTF.setForeground(new Color(255, 255, 255));
        quantityTF.setFont(fontTF);
        purchasesUpPanel.add(purchaseDateLabel);
        purchaseDateLabel.setForeground(new Color(235, 124, 163));
        purchaseDateTF.setPreferredSize(new Dimension(800, 25));
        purchasesUpPanel.add(purchaseDateTF);
        purchaseDateTF.setBackground(new Color(85, 94, 133));
        purchaseDateTF.setForeground(new Color(255, 255, 255));
        purchaseDateTF.setFont(fontTF);
        purchasesPanel.add(purchasesUpPanel);

        // purchaseMidPanel------------------------------------------------
        purchasesMidPanel.setLayout(new GridLayout(2,1));
        purchaseRegularBTPanel.setBackground(new Color(60, 71, 112));
        purchaseSearchBTPanel.setBackground(new Color(60, 71, 112));
        purchasesMidPanel.setBackground(new Color(60, 71, 112));
        purchaseRegularBTPanel.add(purchaseAddBt);
        purchaseAddBt.setBackground(new Color(100, 104, 176));
        purchaseAddBt.setForeground(new Color(255, 255, 255));
        purchaseAddBt.addActionListener(new PurchaseAddAction());
        purchaseRegularBTPanel.add(purchaseDeleteBt);
        purchaseDeleteBt.setBackground(new Color(100, 104, 176));
        purchaseDeleteBt.setForeground(new Color(255, 255, 255));
        purchaseDeleteBt.addActionListener(new PurchasesDeleteAction());
        purchaseRegularBTPanel.add(purchaseEditBt);
        purchaseEditBt.setBackground(new Color(100, 104, 176));
        purchaseEditBt.setForeground(new Color(255, 255, 255));
        purchaseEditBt.addActionListener(new PurchaseEditAction());
        purchaseRegularBTPanel.add(purchaseRefreshBt);
        purchaseRefreshBt.setBackground(new Color(100, 104, 176));
        purchaseRefreshBt.setForeground(new Color(255, 255, 255));
        purchaseRefreshBt.addActionListener(new PurchasesRefreshAction());
        purchaseSearchBTPanel.add(purchaseCustomerBt);
        purchaseCustomerBt.setBackground(new Color(100, 104, 176));
        purchaseCustomerBt.setForeground(new Color(255, 255, 255));
        purchaseCustomerBt.addActionListener(new PurchasesSearchActionCustomer());
        purchaseSearchBTPanel.add(purchaseProductBt);
        purchaseProductBt.setBackground(new Color(100, 104, 176));
        purchaseProductBt.setForeground(new Color(255, 255, 255));
        purchaseProductBt.addActionListener(new PurchasesSearchActionProduct());
        purchaseSearchBTPanel.add(purchaseDateBt);
        purchaseDateBt.setBackground(new Color(100, 104, 176));
        purchaseDateBt.setForeground(new Color(255, 255, 255));
        purchaseDateBt.addActionListener(new PurchasesSearchActionDate());
        purchasesMidPanel.add(purchaseRegularBTPanel);
        purchasesMidPanel.add(purchaseSearchBTPanel);
        purchasesPanel.add(purchasesMidPanel);

        // purchasesDownPanel--------------------------------------------
        purchasesDownPanel.setBackground(new Color(60, 71, 112));
        purchaseTable.setBackground(new Color(223, 224, 230));
        purchaseTable.addMouseListener(new PurchasesMouseAction());
        purchaseScroll.setPreferredSize(new Dimension(800,150));
        purchasesDownPanel.add(purchaseScroll);
        purchasesPanel.add(purchasesDownPanel);

        refreshPurchasesTable();
        checkCustomerPurchaseTable();
        checkProductPurchaseTable();

        // Inquiry panel--------------------------------------------------------------
        inquiryPanel.setLayout(new GridLayout(3, 1));
        inquiryPanel.setBackground(new Color(60, 71, 112));
        nav.add(inquiryPanel, "Справка");

        // inquiry up panel
        inquiryUpPanel.setBackground(new Color(60, 71, 112));
        inquiryUpPanel.add(inquiryAddressLabel);
        inquiryAddressLabel.setForeground(new Color(235, 124, 163));
        inquiryUpPanel.add(inquiryAddressTF);
        inquiryAddressTF.setBackground(new Color(85, 94, 133));
        inquiryAddressTF.setForeground(new Color(255, 255, 255));
        inquiryAddressTF.setFont(fontTF);
        inquiryAddressTF.setPreferredSize(new Dimension(800, 35));
        inquiryUpPanel.add(inquiryModelLabel);
        inquiryModelLabel.setForeground(new Color(235, 124, 163));
        inquiryUpPanel.add(inquiryModelTF);
        inquiryModelTF.setBackground(new Color(85, 94, 133));
        inquiryModelTF.setForeground(new Color(255, 255, 255));
        inquiryModelTF.setFont(fontTF);
        inquiryModelTF.setPreferredSize(new Dimension(800, 35));
        inquiryPanel.add(inquiryUpPanel);

        // inquiry mid panel
        inquiryMidPanel.setBackground(new Color(60, 71, 112));
        inquiryMidPanel.add(inquirySearchBt);
        inquirySearchBt.setBackground(new Color(100, 104, 176));
        inquirySearchBt.setForeground(new Color(255, 255, 255));
        inquirySearchBt.addActionListener(new InquirySearchAction());
        inquiryMidPanel.add(inquiryRefreshBt);
        inquiryRefreshBt.setBackground(new Color(100, 104, 176));
        inquiryRefreshBt.setForeground(new Color(255, 255, 255));
        inquiryRefreshBt.addActionListener(new InquiryRefreshAction());
        inquiryPanel.add(inquiryMidPanel);

        // inquiry down panel
        inquiryDownPanel.setBackground(new Color(60, 71, 112));
        inquiryTable.setBackground(new Color(223, 224, 230));
        inquiryTable.addMouseListener(new InquiryMouseAction());
        inquiryScroll.setPreferredSize(new Dimension(800,150));
        inquiryDownPanel.add(inquiryScroll);
        inquiryPanel.add(inquiryDownPanel);
    }

    public void refreshCustomersTable() {
        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select * from customers");
            result = state.executeQuery();
            customersTable.setModel(new MyModel(result));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshProductsTable() {
        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select * from products");
            result = state.executeQuery();
            productsTable.setModel(new MyModel(result));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshPurchasesTable() {
        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select pu.purchase_id, pu.customer_name, c.address, pu.product_name, " +
                    "pu.quantity, pu.purchase_date from purchases pu join customers c on c.f_name || ' ' || c.l_name = pu.customer_name" +
                    " join products pr on pr.brand || ' ' || pr.model = pu.product_name");
            result = state.executeQuery();
            purchaseTable.setModel(new MyModel(result));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCustomerPurchaseTable(){
        conn = DBConnection.getConnection();
        String sql = "select customer_name from purchases";

        refreshList(sql, purchaseCustomerNamesList);
    }

    public void checkProductPurchaseTable(){
        conn = DBConnection.getConnection();
        String sql = "select product_name from purchases";

        refreshList(sql, purchaseCustomerProductsList);
    }

    private void refreshList(String sql, ArrayList<String> purchaseList) {
        if(purchaseList.size() > 0){
            for(int i = 0; i < purchaseList.size(); i++){
                purchaseList.remove(i);
                i--;
            }
        }

        try {
            state = conn.prepareStatement(sql);
            result = state.executeQuery();
            while(result.next()) {
                buff = result.getObject(1).toString();
                purchaseList.add(buff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshCustomersCombo() {
        customerCombo.removeAllItems();
        conn = DBConnection.getConnection();
        String sql = "select f_name, l_name from customers";

        fillCombo(sql, customerCombo);
    }

    public void refreshProductsCombo() {
        productCombo.removeAllItems();
        conn = DBConnection.getConnection();
        String sql = "select brand, model from products";

        fillCombo(sql, productCombo);
    }

    private void fillCombo(String sql, JComboBox<String> combo) {
        try {
            state = conn.prepareStatement(sql);
            result = state.executeQuery();

            while(result.next()) {
                buff = result.getObject(1).toString() + " " + result.getObject(2).toString();
                items.add(buff);
            }

            sortCombo(combo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortCombo(JComboBox<String> combo){
        Collections.sort(items);
        combo.addItem("");

        for (String item : items) {
            combo.addItem(item);
        }

        for(int i = 0; i < items.size(); i++){
            items.remove(i);
            i--;
        }
    }

    public void clearForms() {
        firstNameTF.setText("");
        lastNameTF.setText("");
        phoneTF.setText("");
        addressTF.setText("");
        productModelTF.setText("");
        productPriceTF.setText("");
        productStorageTF.setText("");
        productColorTF.setText("");
        quantityTF.setText("");
        purchaseDateTF.setText("");
        inquiryAddressTF.setText("");
        inquiryModelTF.setText("");
    }

    class CustomersMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = customersTable.getSelectedRow();
            id = Integer.parseInt(customersTable.getValueAt(row, 0).toString());
            firstNameTF.setText(customersTable.getValueAt(row,1).toString());
            lastNameTF.setText(customersTable.getValueAt(row,2).toString());
            phoneTF.setText(customersTable.getValueAt(row,3).toString());
            addressTF.setText(customersTable.getValueAt(row,4).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    class ProductsMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = productsTable.getSelectedRow();
            id = Integer.parseInt(productsTable.getValueAt(row, 0).toString());
            brandsCombo.setSelectedItem(productsTable.getValueAt(row, 1).toString());
            productModelTF.setText(productsTable.getValueAt(row, 2).toString());
            productPriceTF.setText(productsTable.getValueAt(row,3).toString());
            productStorageTF.setText(productsTable.getValueAt(row,4).toString());
            productColorTF.setText(productsTable.getValueAt(row,5).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class PurchasesMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = purchaseTable.getSelectedRow();
            id = Integer.parseInt(purchaseTable.getValueAt(row, 0).toString());
            customerCombo.setSelectedItem(purchaseTable.getValueAt(row, 1).toString());
            productCombo.setSelectedItem(purchaseTable.getValueAt(row, 3).toString());
            quantityTF.setText(purchaseTable.getValueAt(row, 4).toString());
            purchaseDateTF.setText(purchaseTable.getValueAt(row, 5).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class InquiryMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = inquiryTable.getSelectedRow();
            inquiryAddressTF.setText(inquiryTable.getValueAt(row, 2).toString());
            inquiryModelTF.setText(inquiryTable.getValueAt(row, 4).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class CustomersAddAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "insert into customers(f_name, l_name, phone, address) values(?,?,?,?)";
            try {
                if(firstNameTF.getText().equals("") || lastNameTF.getText().equals("") ||
                        phoneTF.getText().equals("")|| addressTF.getText().equals("")){
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                    return;
                }

                state = conn.prepareStatement(sql);
                state.setString(1, firstNameTF.getText());
                state.setString(2, lastNameTF.getText());
                state.setString(3, phoneTF.getText());
                state.setString(4, addressTF.getText());

                state.execute();
                refreshCustomersTable();
                refreshCustomersCombo();
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class ProductsAddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "insert into products(brand, model, price, storage, color) values(?,?,?,?,?)";
            try {
                if(Objects.requireNonNull(brandsCombo.getSelectedItem()).toString().equals("") || productModelTF.getText().equals("") ||
                        productColorTF.getText().equals("")){
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                    return;
                }

                state = conn.prepareStatement(sql);
                state.setString(1, Objects.requireNonNull(brandsCombo.getSelectedItem()).toString());
                state.setString(2, productModelTF.getText());
                state.setFloat(3, Float.parseFloat(productPriceTF.getText()));
                state.setInt(4, Integer.parseInt(productStorageTF.getText()));
                state.setString(5, productColorTF.getText());

                state.execute();
                refreshProductsTable();
                refreshProductsCombo();
                clearForms();

            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class PurchaseAddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql ="insert into purchases(customer_name, product_name, quantity, purchase_date) values(?,?,?,?)";
            try {
                if(Objects.requireNonNull(customerCombo.getSelectedItem()).toString().equals("") ||
                   Objects.requireNonNull(productCombo.getSelectedItem()).toString().equals("")){
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                    return;
                }

                state = conn.prepareStatement(sql);
                state.setString(1, Objects.requireNonNull(customerCombo.getSelectedItem()).toString());
                state.setString(2, Objects.requireNonNull(productCombo.getSelectedItem()).toString());
                state.setInt(3, Integer.parseInt(quantityTF.getText()));
                state.setString(4, purchaseDateTF.getText());

                state.execute();
                refreshPurchasesTable();
                checkCustomerPurchaseTable();
                checkProductPurchaseTable();
                clearForms();

            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class CustomersEditAction implements ActionListener {
        public void actionPerformed (ActionEvent arg0) {
            if(id > 0) {
                String sql = "update customers set f_name=?, l_name=?, phone=?, address=? where customer_id=?";

                try {
                    if(firstNameTF.getText().equals("") || lastNameTF.getText().equals("") ||
                            phoneTF.getText().equals("")|| addressTF.getText().equals("")){
                        JOptionPane.showMessageDialog(frame, "Проверете данните!");
                        return;
                    }

                    state = conn.prepareStatement(sql);
                    state.setString(1, firstNameTF.getText());
                    state.setString(2, lastNameTF.getText());
                    state.setString(3, phoneTF.getText());
                    state.setString(4, addressTF.getText());
                    state.setInt(5,id);
                    state.execute();
                    id = -1;
                    refreshCustomersTable();
                    refreshCustomersCombo();
                    clearForms();

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                }
            }
        }
    }

    class ProductsEditAction implements ActionListener {
        public void actionPerformed (ActionEvent arg0) {
            if(id > 0) {
                String sql = "update products set brand=?, model=?, price=?, storage=?, color=? where product_id=?";

                try {
                    if(Objects.requireNonNull(brandsCombo.getSelectedItem()).toString().equals("") || productModelTF.getText().equals("") ||
                            productColorTF.getText().equals("")){
                        JOptionPane.showMessageDialog(frame, "Проверете данните!");
                        return;
                    }

                    state = conn.prepareStatement(sql);
                    state.setString(1, Objects.requireNonNull(brandsCombo.getSelectedItem()).toString());
                    state.setString(2, productModelTF.getText());
                    state.setFloat(3, Float.parseFloat(productPriceTF.getText()));
                    state.setInt(4, Integer.parseInt(productStorageTF.getText()));
                    state.setString(5, productColorTF.getText());
                    state.setInt(6,id);
                    state.execute();
                    id = -1;
                    refreshProductsTable();
                    refreshProductsCombo();
                    clearForms();

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                }
            }
        }
    }

    class PurchaseEditAction implements ActionListener {
        public void actionPerformed (ActionEvent arg0) {
            if(id > 0) {
                String sql = "update purchases set customer_name=?, product_name=?, quantity=?, purchase_date=? where purchase_id=?";

                try {
                    if(Objects.requireNonNull(customerCombo.getSelectedItem()).toString().equals("") ||
                            Objects.requireNonNull(productCombo.getSelectedItem()).toString().equals("")){
                        JOptionPane.showMessageDialog(frame, "Проверете данните!");
                        return;
                    }

                    state = conn.prepareStatement(sql);
                    state.setString(1, Objects.requireNonNull(customerCombo.getSelectedItem()).toString());
                    state.setString(2, Objects.requireNonNull(productCombo.getSelectedItem()).toString());
                    state.setInt(3, Integer.parseInt(quantityTF.getText()));
                    state.setString(4, (purchaseDateTF.getText()));
                    state.setInt(5, id);
                    state.execute();
                    id = -1;
                    refreshPurchasesTable();
                    checkCustomerPurchaseTable();
                    checkProductPurchaseTable();
                    clearForms();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                }
            }
        }
    }

    class CustomersDeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String selectSql = "SELECT f_name, l_name FROM customers WHERE customer_id=?";
            String deleteSql = "DELETE FROM customers WHERE customer_id=?";
            customerNameS = "";

            try {
                state = conn.prepareStatement(selectSql);
                state.setInt(1, id);
                ResultSet rs = state.executeQuery();

                if (rs.next()) {
                    String firstName = rs.getString("f_name");
                    String lastName = rs.getString("l_name");
                    customerNameS = firstName + " " + lastName;
                }

                boolean hasPurchaseRecordCustomer = false;
                for (String s : purchaseCustomerNamesList) {
                    if (customerNameS.equals(s)) {
                        JOptionPane.showMessageDialog(frame, "Първо изтрийте поръчката/ите с този клиент!");
                        hasPurchaseRecordCustomer = true;
                        break;
                    }
                }

                if (!hasPurchaseRecordCustomer) {
                    try {
                        state = conn.prepareStatement(deleteSql);
                        state.setInt(1, id);
                        state.execute();
                        id = -1;
                        refreshCustomersTable();
                        refreshCustomersCombo();
                        clearForms();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ProductsDeleteAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String selectSql = "SELECT brand, model FROM products WHERE product_id=?";
            String deleteSql="delete from products where product_id=?";
            productNameS = "";

            try {
                state = conn.prepareStatement(selectSql);
                state.setInt(1, id);
                ResultSet rs = state.executeQuery();

                if (rs.next()) {
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    productNameS = brand + " " + model;
                }

                boolean hasPurchaseRecordItem = false;
                for (String s : purchaseCustomerProductsList) {
                    if (productNameS.equals(s)) {
                        JOptionPane.showMessageDialog(frame, "Първо изтрийте поръчката/ите с този продукт!");
                        hasPurchaseRecordItem = true;
                        break;
                    }
                }

                if (!hasPurchaseRecordItem) {
                    try {
                        state = conn.prepareStatement(deleteSql);
                        state.setInt(1, id);
                        state.execute();
                        id = -1;
                        refreshProductsTable();
                        refreshProductsCombo();
                        clearForms();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    class PurchasesDeleteAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "delete from purchases where purchase_id=?";

            try {
                state = conn.prepareStatement(sql);
                state.setInt(1, id);
                state.execute();
                id = -1;
                refreshPurchasesTable();
                checkCustomerPurchaseTable();
                checkProductPurchaseTable();
                clearForms();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    class CustomersSearchActionPhone implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select * from customers where phone=?";

            try {
                state = conn.prepareStatement(sql);
                state.setInt(1, Integer.parseInt(phoneTF.getText()));
                result = state.executeQuery();
                customersTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class CustomersSearchActionAddress implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select * from customers where lower(address)=lower(?)";

            try {
                if(addressTF.getText().equals("")){
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                    return;
                }

                state = conn.prepareStatement(sql);
                state.setString(1, addressTF.getText());
                result = state.executeQuery();
                customersTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class ProductsSearchActionBrand implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select * from products where brand=?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, Objects.requireNonNull(brandsCombo.getSelectedItem()).toString());
                result = state.executeQuery();
                productsTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class ProductsSearchActionModel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select * from products where lower(model)=lower(?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, productModelTF.getText());
                result = state.executeQuery();
                productsTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class PurchasesSearchActionCustomer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select pu.purchase_id, pu.customer_name, c.address, pu.product_name, " +
                    "pu.quantity, pu.purchase_date from purchases pu join customers c on c.f_name || ' ' || c.l_name = pu.customer_name" +
                    " join products pr on pr.brand || ' ' || pr.model = pu.product_name where pu.customer_name = ?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, Objects.requireNonNull(customerCombo.getSelectedItem()).toString());
                result = state.executeQuery();
                purchaseTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class PurchasesSearchActionProduct implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select pu.purchase_id, pu.customer_name, c.address, pu.product_name, " +
                    "pu.quantity, pu.purchase_date from purchases pu join customers c on c.f_name || ' ' || c.l_name = pu.customer_name" +
                    " join products pr on pr.brand || ' ' || pr.model = pu.product_name where pu.product_name = ?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, Objects.requireNonNull(productCombo.getSelectedItem()).toString());
                result = state.executeQuery();
                purchaseTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class PurchasesSearchActionDate implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select pu.purchase_id, pu.customer_name, c.address, pu.product_name, " +
                    "pu.quantity, pu.purchase_date from purchases pu join customers c on c.f_name || ' ' || c.l_name = pu.customer_name" +
                    " join products pr on pr.brand || ' ' || pr.model = pu.product_name where pu.purchase_date = ?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, purchaseDateTF.getText());
                result = state.executeQuery();
                purchaseTable.setModel(new MyModel(result));
                clearForms();
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class InquirySearchAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "select pu.purchase_id, pu.customer_name, c.address, pr.brand, pr.model, pr.color, pu.quantity " +
                    "from purchases pu join customers c on c.f_name || ' ' || c.l_name = pu.customer_name " +
                    "join products pr on pr.brand || ' ' || pr.model = pu.product_name " +
                    "where lower(c.address)=lower(?) and lower(pr.model)=lower(?)";
            try {
                if(inquiryAddressTF.getText().equals("") || inquiryModelTF.getText().equals("")){
                    JOptionPane.showMessageDialog(frame, "Проверете данните!");
                    return;
                }

                state = conn.prepareStatement(sql);
                state.setString(1, inquiryAddressTF.getText());
                state.setString(2, inquiryModelTF.getText());
                result = state.executeQuery();
                inquiryTable.setModel(new MyModel(result));
                clearForms();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Проверете данните!");
            }
        }
    }

    class CustomersRefreshAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshCustomersTable();
            clearForms();
        }
    }

    class ProductsRefreshAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshProductsTable();
            clearForms();
        }
    }

    class PurchasesRefreshAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshPurchasesTable();
            clearForms();
        }
    }

    class InquiryRefreshAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            inquiryTable.setModel(new DefaultTableModel());
            clearForms();
        }
    }

}