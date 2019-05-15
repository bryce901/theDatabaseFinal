import java.io.*;
import java.sql.*;

public class exportClass {
    public static String DB_URL=MainFile.DB_URL;
    public static String user=MainFile.USER;
    public static String pass=MainFile.PASS;
    static final String JBDC_DRIVER="com.mysql.jbdc.Driver";

    public static void main(String[]args){
        String filename = "D:/exportedSpotifyData.csv";
        File dbFile=new File(filename);
        dbFile.getParentFile().mkdirs();
        try {
            dbFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create file");
        }


        try{
            FileWriter fw=new FileWriter(dbFile);
            Connection conn = (Connection) DriverManager.getConnection(DB_URL,user,pass);
            Integer currentID=MainFile.currentUserID;
            String query  = "select * from allSavedSongs where userID ="+currentID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("UserID,");
            fw.append("Name,");
            fw.append("song,");
            fw.append("Genre,");
            fw.append("Artist,");
            fw.append("Length In Seconds,");
            fw.append("Time Listened in Seconds,");
            fw.append("Playlist\n");
            while(rs.next())
            {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append(rs.getString(8));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
