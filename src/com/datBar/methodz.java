/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class methodz {
    //mos harro me hek static
    public static boolean checkLogin(String user, String pw){
        Connection c = null;
        Statement stmt = null;
        String lolz = "";
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT Username, Password FROM Users WHERE Username = '"+user+"';" );
        while ( rs.next() ) {
         String  name = rs.getString("Username");
         String  hash = rs.getString("Password");
         lolz = hash;
        }
        rs.close();
        stmt.close();
        c.close();
        } catch ( Exception e ) {
        JOptionPane.showMessageDialog(null, "Problem me DataBase." /*+e.getMessage()*/, "Error", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        }
        //System.out.println("Operation done successfully");

        if(BCrypt.checkpw(pw, lolz)){
            return true;
        }
        else{
            return false;
        }
    }
    public static void main(String[] args) {
        //System.out.println(checkLogin("admin","admin"));
    }
}
