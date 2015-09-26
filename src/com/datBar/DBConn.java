/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datBar;

import java.sql.*;
/**
 *
 * @author Administrator
 */
public class DBConn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection c = null;
        Statement stmt = null;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM Users;" );
      while ( rs.next() ) {
         int id = rs.getInt("ID");
         String  name = rs.getString("Username");
         String  pw = rs.getString("Password");
         System.out.println( "ID = " + id );
         System.out.println( "NAME = " + name );
         System.out.println( "PW = " + pw );
         System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
        
        
    }
    
    private void conn(){
        
    }
    
    
}
