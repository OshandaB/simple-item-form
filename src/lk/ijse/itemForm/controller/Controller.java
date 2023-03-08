package lk.ijse.itemForm.controller;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    private static final String URL = "jdbc:mysql://localhost:3306/foodcity ? useSSL =false";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtItem;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;


    @FXML
    void btnSave(ActionEvent event) throws SQLException {
        String item = txtItem.getText();
        String desc = txtDesc.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        try(Connection connection=DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO item(ItemCode,Description,UnitPrice,QtyOnHand)"+"VALUES(?,?,?,?)";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, item);
            pstm.setString(2,desc);
            pstm.setString(3, String.valueOf(price));
            pstm.setString(4, String.valueOf(qty));

            int affectedRows = pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "ITEM Added!").show();
            }

           txtItem.setText(" ");
            txtDesc.setText(" ");
            txtPrice.setText(" ");
            txtQty.setText(" ");

        }
    }

    @FXML
    void btnUpdate(ActionEvent event) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, props)){
            String sql = "UPDATE item set ItemCode = ?, Description = ?, UnitPrice = ?, QtyOnHand = ? WHERE ItemCode = ? ";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,txtItem.getText());
            pstm.setString(2,txtDesc.getText());
            pstm.setString(3,txtPrice.getText());
            pstm.setString(4,txtQty.getText());
            pstm.setString(5,onActionId);

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully!").show();
            }
            txtItem.setText(" ");
            txtDesc.setText(" ");
            txtPrice.setText(" ");
            txtQty.setText(" ");
        }



    }

    @FXML
    void btnDelete(ActionEvent event) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, props);){
            String sql = "DELETE FROM item WHERE ItemCode = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,txtItem.getText());
            int affectedRows  = pstm.executeUpdate();

            if(affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "DELETE Successfully!").show();
            }
            txtItem.setText(" ");
            txtDesc.setText(" ");
            txtPrice.setText(" ");
            txtQty.setText(" ");
        }

    }

    @FXML
    void initialize() {
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'itemForm.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'itemForm.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'itemForm.fxml'.";
        assert txtDesc != null : "fx:id=\"txtDesc\" was not injected: check your FXML file 'itemForm.fxml'.";
        assert txtItem != null : "fx:id=\"txtItem\" was not injected: check your FXML file 'itemForm.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'itemForm.fxml'.";
        assert txtQty != null : "fx:id=\"txtQty\" was not injected: check your FXML file 'itemForm.fxml'.";

    }
    String onActionId;
    public void schItem(ActionEvent event) throws SQLException {
        onActionId=txtItem.getText();
        try(Connection connection = DriverManager.getConnection(URL, props);){
            String sql = "SELECT * FROM item WHERE ItemCode = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,txtItem.getText());
            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                String desc = resultSet.getString(2);
                double price = Double.parseDouble(resultSet.getString(3));
                int qty = Integer.parseInt(resultSet.getString(4));

                txtDesc.setText(desc);
                txtPrice.setText(String.valueOf(price));
                txtQty.setText(String.valueOf(qty));
            }
        }

    }

}
