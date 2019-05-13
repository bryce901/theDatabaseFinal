import com.github.javafaker.Faker;

import java.sql.*;
import java.util.Scanner;

public class addAndRemoveClass extends MainFile{
    public static String DB_URL= MainFile.DB_URL;
    public static String USER= MainFile.USER;
    public static String PASS= MainFile.PASS;
    public static void main (String[] args){


    }
    public static void removeSong(){
        Faker faker=new Faker();
        Statement stmt=null;
        Connection conn=null;

        Scanner in=new Scanner(System.in);
        System.out.println("Which song would you like to remove?");
        String songName=in.next();
        String removeSongStmt="DELETE FROM allSavedSongs WHERE song=\""+songName+"\" AND userID=\""+MainFile.currentUserID+"\";";
        try {
            conn= DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement preparedStmt=conn.prepareStatement(removeSongStmt);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void addSong(){
        Faker faker=new Faker();
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String insertIntoAllSavedSongs="INSERT INTO allSavedSongs(userID,name,song,genre,artist,length,timeListened,playlist) values(?,?,?,?,?,?,?,?);";
        Scanner in=new Scanner(System.in);
        Scanner in2=new Scanner(System.in);
        Scanner in3=new Scanner(System.in);
        String songName;
        String artistName;
        String genre;
        String name=null;
        int theLength=(int)(300*Math.random());
        int theTimeListened=(int)(10000*Math.random());
        //couldn't think on how to name playlist so used hipster name
        String playlistName=faker.hipster().word();
        System.out.println("What song would you like to add?");
        songName=in.next();
        System.out.println("What's the Artist's name?");
        artistName=in2.next();
        System.out.println("What's the genre?");
        genre=in3.next();
        if(MainFile.currentUserID==1){
            name="Eugena Robel";
        }
        if(MainFile.currentUserID==2){
            name="Coleen Upton MD";
        }
        if(MainFile.currentUserID==3){
            name="Shirleen Shanahan IV";
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            //used prepare statement
            PreparedStatement preparedStmt=conn.prepareStatement(insertIntoAllSavedSongs);
            preparedStmt.setInt(1,currentUserID);
            preparedStmt.setString(2,name);
            preparedStmt.setString(3,songName);
            preparedStmt.setString(4,genre);
            preparedStmt.setString(5,artistName);
            preparedStmt.setInt(6,theLength);
            preparedStmt.setInt(7,theTimeListened);
            preparedStmt.setString(8,playlistName);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
