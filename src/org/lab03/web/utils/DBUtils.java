package org.lab03.web.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.lab03.web.beans.Product;
import org.lab03.web.beans.UserAccount;

public class DBUtils {
	 
    public static UserAccount findUser(Connection conn, //
            String userName, String password) throws SQLException {
/*    	
        String sql = "select user_Name, password, gender FROM user_account " //
                + " where user_name = ? and password= ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
*/		
    	String sql = "SELECT user_name, password, gender from user_account where user_name = \'" //
    			+ userName + "\'and password = sha1(\'" + password + "\');";
    	Statement stm = conn.createStatement();
    	ResultSet rs = stm.executeQuery(sql);
    	
        if (rs.next()) {
            String gender = rs.getString("Gender");
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            user.setGender(gender);
            return user;
        }
        return null;
    }
 
    public static UserAccount findUser(Connection conn, String userName) throws SQLException {
 /*
        String sql = "SELECT user_name, password, gender FROM user_account a "//
                + " where a.user_name = ? ";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
 
        ResultSet rs = pstm.executeQuery();
*/
    	String sql = "SELECT user_name, password, gender from user_account where user_name=\'" //
    			+ userName + "\';";
    	Statement stm = conn.createStatement();
    	ResultSet rs = stm.executeQuery(sql);
    	
    	if (rs.next()) {
            String password = rs.getString("Password");
            String gender = rs.getString("Gender");
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            user.setGender(gender);
            return user;
        }
        return null;
    }
 
    public static List<Product> queryProduct(Connection conn) throws SQLException {
        String sql = "SELECT code, name, price FROM product";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        ResultSet rs = pstm.executeQuery();
        List<Product> list = new ArrayList<Product>();
        while (rs.next()) {
            String code = rs.getString("Code");
            String name = rs.getString("Name");
            float price = rs.getFloat("Price");
            Product product = new Product();
            product.setCode(code);
            product.setName(name);
            product.setPrice(price);
            list.add(product);
        }
        return list;
    }
 
    public static Product findProduct(Connection conn, String code) throws SQLException {
/*
    	String sql = "select a.code, a.name, a.price from product a where a.code=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, code);
 
        ResultSet rs = pstm.executeQuery();
*/
    	String sql = "SELECT code, name, price FROM product where code=\'" + code + "\';";
    	Statement stm = conn.createStatement();
    	ResultSet rs = stm.executeQuery(sql);
    	
        while (rs.next()) {
            String name = rs.getString("Name");
            float price = rs.getFloat("Price");
            Product product = new Product(code, name, price);
            return product;
        }
        return null;
    }
    
 
    public static void updateProduct(Connection conn, Product product) throws SQLException {
/* 
    	String sql = "Update Product set Name =?, Price=? where Code=? ";
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, product.getName());
        pstm.setFloat(2, product.getPrice());
        pstm.setString(3, product.getCode());
        pstm.executeUpdate();
*/
    	String sql = "UPDATE product set name = \"" + product.getName() + //
    			"\", price = " + product.getPrice() + "where code= \'"+ product.getCode() + "\';";
    	Statement stm = conn.createStatement();
    	stm.executeUpdate(sql);
    }
 
    public static void insertProduct(Connection conn, Product product) throws SQLException {
/*        
    	String sql = "Insert into Product(Code, Name,Price) values (?,?,?)"; 
        PreparedStatement pstm = conn.prepareStatement(sql);
         
        pstm.setString(1, product.getCode());
        pstm.setString(2, product.getName());
        pstm.setFloat(3, product.getPrice()); 
        pstm.executeUpdate();
*/
    	String sql = "INSERT INTO product(code, name, price) values (\'" //
    			+ product.getCode() + "\',\'" //
    			+ product.getName() + "\'," //
    			+ product.getPrice() + ");";
    	Statement stm = conn.createStatement();
    	stm.executeUpdate(sql);
    }
 
    public static void deleteProduct(Connection conn, String code) throws SQLException {
/*
    	String sql = "Delete From Product where Code= ?"; 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, code); 
        pstm.executeUpdate();
*/
    	String sql = "DELETE FROM product where code= \'" + code + "\';";
    	Statement stm = conn.createStatement();
    	stm.executeUpdate(sql);
    }
 
}