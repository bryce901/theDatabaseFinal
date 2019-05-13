import com.github.javafaker.Faker;

import java.sql.*;
import java.util.Scanner;

public class updateClass extends MainFile {
    public static Integer selection=null;
    public static String DB_URL= MainFile.DB_URL;
    public static String USER= MainFile.USER;
    public static String PASS= MainFile.PASS;


 public static void main (String[] args){
     Scanner in=new Scanner(System.in);
     System.out.println("What would you like to update");
     System.out.println("|********************************|");
     System.out.println("|         (1) A playlist         |");
     System.out.println("|  (2) Amount of time listened   |");
     System.out.println("**********************************");
     selection=in.nextInt();
     switch (selection){
         case 1:changePlaylist();
         break;
         case 2:changeTimeListened();
     }
 }

 public static void changePlaylist(){
     Faker faker=new Faker();
     Statement stmt=null;
     Connection conn=null;

     Scanner in=new Scanner(System.in);
     Scanner in1=new Scanner(System.in);
     System.out.println("Which song would you like to change playlist?");
     String songName=in.next();
     System.out.println("What playlist do you want to move it to?");
     String playlistName=in1.next();
     String updateStatement="UPDATE allSavedSongs SET playlist=\""+playlistName+"\" WHERE song=\""+songName+"\" AND userID=\""+ MainFile.currentUserID+"\";";
     try {
         conn= DriverManager.getConnection(DB_URL,USER,PASS);
         PreparedStatement preparedStmt=conn.prepareStatement(updateStatement);
         preparedStmt.execute();
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
    public static void changeTimeListened(){
        Faker faker=new Faker();
        Statement stmt=null;
        Connection conn=null;

        Scanner in=new Scanner(System.in);
        Scanner in1=new Scanner(System.in);
        System.out.println("Which song would you like to change playlist?");
        String songName=in.next();
        System.out.println("How much time would you like to change it to? (in seconds)");
        Integer time=in1.nextInt();
        String updateStatement="UPDATE allSavedSongs SET timeListened=\""+time+"\" WHERE song=\""+songName+"\" AND userID=\""+ MainFile.currentUserID+"\";";
        try {
            conn= DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement preparedStmt=conn.prepareStatement(updateStatement);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
