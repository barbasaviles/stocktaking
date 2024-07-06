/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.buysModel;
import model.products;
import views.vista;

/**
 *
 * @author Juan
 */
public class operationController implements ActionListener {

    vista view;
    ArrayList<buysModel> buys;
    ArrayList<products> productsBuy;

    public operationController() {

        buys = new ArrayList<buysModel>();
        productsBuy = new ArrayList<products>();
        view = new vista();
        view.getBtnRegisterBuy().addActionListener(this);
        view.getBtnRegisterProduct().addActionListener(this);
    }

    public void addBuy() {
        System.err.println("Craando Compra");
        String nameProvider = view.getNameProvider().getText();
        String address = view.getAddress().getText();
        String phone = view.getPhone().getText();
        String seller = view.getSeller().getText();

        buysModel objProduct = new buysModel();

        objProduct.setCompany(nameProvider);
        objProduct.setAddress(address);
        objProduct.setPhone(phone);
        objProduct.setSeller(seller);

        ArrayList<products> productsBuyCurrent = new ArrayList<products>();

        productsBuyCurrent.addAll(productsBuy);

        objProduct.setProducts(productsBuyCurrent);
        buys.add(objProduct);
        productsBuy.clear();

        DefaultTableModel model = (DefaultTableModel) view.getTableProducts().getModel();
        model.setRowCount(0);
        view.getTableProducts().setModel(model);

        view.getTotalProducts().setText("TOTAL 0");

        
        loadTableStocks();
    }

    public void addProduct() {
        products objProducts = new products();

        String product = view.getProduct().getSelectedItem().toString();
        Integer amount = Integer.parseInt(view.getAmount().getText());
        Double price = Double.parseDouble(view.getPrice().getText() + "");
        String payment = view.getPayment().getSelectedItem().toString();

        objProducts.setProduct(product);
        objProducts.setAmount(amount);
        objProducts.setPrice(price);
        objProducts.setPayment(payment);

        productsBuy.add(objProducts);

        loadTableBuy();
    }

    private void loadTableBuy() {

        DefaultTableModel model = (DefaultTableModel) view.getTableProducts().getModel();

        model.setRowCount(0);

        int numColum = view.getTableProducts().getColumnCount();

        Double sumPrice = 0.0;

        for (int i = 0; i < productsBuy.size(); i++) {

            Integer amount = productsBuy.get(i).getAmount();
            Double price = productsBuy.get(i).getPrice();

            Object[] rowData = new Object[numColum];
            rowData[0] = productsBuy.get(i).getProduct();
            rowData[1] = amount;
            rowData[2] = price;
            rowData[3] = Math.round(amount * price);

            sumPrice += price;

            model.addRow(rowData);
        }

        view.getTableProducts().setModel(model);
        view.getTotalProducts().setText("TOTAL: "+sumPrice.toString());

    }

    private void loadTableStocks() {
        DefaultTableModel model = (DefaultTableModel) view.getTableStocks().getModel();
        model.setRowCount(0);

        int numColum = view.getTableStocks().getColumnCount();

        Double sumStock = 0.0;

        for (int i = 0; i < buys.size(); i++) {

            Object[] rowData = new Object[numColum];

            String provider = buys.get(i).getCompany();

            ArrayList<products> products = buys.get(i).getProducts();

            for (int j = 0; j < products.size(); j++) {

                Double price = products.get(j).getPrice();

                rowData[0] = provider;
                rowData[1] = products.get(j).getProduct();
                rowData[2] = products.get(j).getAmount();
                rowData[3] = price;
                rowData[4] = products.get(j).getPayment();

                sumStock += price;

                model.addRow(rowData);
            }
        }

        view.getTableStocks().setModel(model);
        view.getTotalStock().setText("TOTAL: "+sumStock.toString());
    }

    private void imprimirConsola() {
        System.err.println("Iniciando Impresion de arreglo");

        System.err.println("-------------------------------");
        for (int i = 0; i < buys.size(); i++) {
            System.out.print(i + 1 + " ~ " + buys.get(i).getCompany() + " ");
            System.out.println(buys.get(i).getPhone());

            ArrayList<products> products1 = buys.get(i).getProducts();

            for (int j = 0; j < products1.size(); j++) {
                System.out.print(j + 1 + " # " + products1.get(j).getProduct() + " ");
                System.out.print(products1.get(j).getAmount() + " ");
                System.out.print(products1.get(j).getPrice() + " ");
                System.out.println(products1.get(j).getPayment() + " ");
            }

        }

    }

    public void Run() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.getBtnRegisterProduct()) {
            addProduct();
        }

        if (e.getSource() == view.getBtnRegisterBuy()) {
            addBuy();
        }
    }

}
