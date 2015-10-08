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
    
    
    public static Pair meIShitur(String tipi){
        Connection c = null;
        Statement stmt = null;
        String  emri = "";
        int sasia = 0;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs;
            rs = stmt.executeQuery("SELECT SUM(ProduktiShitur.Sasia) as Sassia, Produktet."+tipi+"  as value " +
            "FROM ProduktiShitur  " +
            "INNER JOIN Produktet " +
            "ON ProduktiShitur.ID_Prod = Produktet.ID_Prod " +
            "GROUP BY value ORDER BY Sassia DESC;");
        while ( rs.next() ) {
            emri = rs.getString("value");
            sasia = rs.getInt("Sassia");
            break;
        }
        rs.close();
        stmt.close();
        c.close();
        } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
        }
        return new Pair(emri, sasia);
    }
            
            
    public static void addProducts(javax.swing.table.DefaultTableModel model, String kat ){
        Connection c = null;
        Statement stmt = null;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs;
        if(kat.equals("")){
             rs = stmt.executeQuery( "SELECT * FROM Produktet;" );
        }else{
             rs = stmt.executeQuery( "SELECT * FROM Produktet WHERE Kategoria = '"+kat+"';" );
        }
        while ( rs.next() ) {
            //int id = rs.getInt("id");
            String  emri = rs.getString("Emri");
            String  cmimi = rs.getString("Cmimi");
            //int age  = rs.getInt("age");
            //String  address = rs.getString("address");
            //float salary = rs.getFloat("salary");
            System.out.println( "NAME = " + emri );
            Object [] row = {emri, cmimi};
            System.out.println("Kategoria: "+kat);
            model.addRow(row);
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
    public static void main(String[] args) {
        //System.out.println(checkLogin("admin","admin"));
    }

    static void addProductsToDb(String kategoria, String emri, String cmimi, javax.swing.JLabel jLabelhandleMsg) {
        Connection c = null;
        Statement stmt = null;
        int goterror = 0;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        String sql = "INSERT INTO Produktet (Emri, Cmimi, Sasia, Kategoria) " +
                    "VALUES ('"+emri+"', '"+cmimi+"', '0', '"+kategoria+"' );"; 
        stmt.executeUpdate(sql);
        stmt.close();
        c.commit();
        c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.err.println( e.getMessage() );
            if(e.getMessage().equals("UNIQUE constraint failed: Produktet.Emri")){
                System.out.println("Wtfdude?");
                jLabelhandleMsg.setText("Nje produkt me ate emer ekziston.");
                goterror = 1;
            }
            else{
                System.out.println("Wtfdude?2");
                System.exit(0);
            }
        }
        if(goterror == 0){jLabelhandleMsg.setText("Produkti u shtua");}
        System.out.println("Records created successfully.");
        //return "?";
    }

    static int shtoFature(){
        Connection c = null;
        Statement stmt = null;
        int row = -1;
        int goterror = 0;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        String sql = "INSERT INTO Fatura (ID_User) " +
                    "VALUES ('1');"; 
        stmt.executeUpdate(sql);
        row = stmt.getGeneratedKeys().getInt(1);
        stmt.close();
        c.commit();
        c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.err.println( e.getMessage() );
                System.exit(0);
        }
        System.out.println("Records created successfully.");
        return row;
    }
    
    public static String getKategori(int id){
        Connection c = null;
        String  kat = "";
        Statement stmt = null;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery( "SELECT Kategoria FROM Produktet WHERE ID_Prod = '"+id+"';" );
        while ( rs.next() ) {
            //int id = rs.getInt("id");
            kat = rs.getString("Kategoria");
            //int age  = rs.getInt("age");
            //String  address = rs.getString("address");
            //float salary = rs.getFloat("salary");
        }
        rs.close();
        stmt.close();
        c.close();
        } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
        }
        System.out.println("Operation done successfully");
        return fixKat(kat);
    }
    public static String fixKat(String kat){
        if(kat.equals("TeFtohta")){
            kat = "Te Ftohta";
        }
        else if(kat.equals("TeNxehta")){
            kat = "Te Nxehta";
        }
        return kat;
    }

    static void shtoShitje(int faturaID, int emriID, String sasia) {
        String kat = getKategori(emriID);
        fixKat(kat);
        Connection c = null;
        Statement stmt = null;
        int row = -1;
        int goterror = 0;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        String sql = "INSERT INTO ProduktiShitur (ID_Prod, ID_Fatura, Sasia) " +
                    "VALUES ('"+emriID+"', '"+faturaID+"', '"+sasia+"' );"; 
        stmt.executeUpdate(sql);
        //works
        stmt.close();
        c.commit();
        c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.err.println( e.getMessage() );
                System.exit(0);
        }
        System.out.println("Records created successfully.");
    }

    static int getTotalin() {
        Connection c = null;
        int totali = 0;
        Statement stmt = null;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery( "SELECT Totali FROM Shitjet_Fatura ;" );
        while ( rs.next() ) {
            int tot = rs.getInt("Totali");
            totali += tot;
        }
        rs.close();
        stmt.close();
        c.close();
        } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
        }
        System.out.println(totali);
        return totali;

    }

    static int getProductID(String emri) {
        Connection c = null;
        int id = 0;
        Statement stmt = null;
        try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:src\\com\\datBar\\Storage.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");

        stmt = c.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery( "SELECT ID_Prod FROM Produktet WHERE Emri = '"+emri+"' ;" );
        while ( rs.next() ) {
            id = rs.getInt("ID_Prod");
        }
        rs.close();
        stmt.close();
        c.close();
        } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return id;
    }
}
final class Pair {
    private final String first;
    private final int second;

    public Pair(String first, int second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
