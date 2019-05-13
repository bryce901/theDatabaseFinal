import java.sql.*;

public class optionsClass extends MainFile {

    public static void showNames() throws SQLException {
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectTable="SELECT DISTINCT name FROM allSavedSongs;";
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            rs=stmt.executeQuery(selectTable);
            while (rs.next()){
                Object obj=rs.getObject("name");
                System.out.println(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        stmt.execute(selectTable);
    }
    public static void showUserSongs(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectSongs="SELECT DISTINCT song FROM allSavedSongs WHERE userID="+currentUserID;
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectSongs);
            while (rs.next()){
                Object obj=rs.getObject("song");
                System.out.println(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void currentlyListeningTo(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectCurrentSong="SELECT currentlyListeningTo FROM listeningTo WHERE userID="+currentUserID;
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectCurrentSong);
            while (rs.next()){
                Object obj=rs.getObject("currentlyListeningTo");
                System.out.println(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void totalTimeEachSong(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectLength="SELECT song,timeListened FROM allSavedSongs WHERE userID="+currentUserID;
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectLength);
            while (rs.next()){
                Object song=rs.getObject("song");
                Integer timeListened=rs.getInt("timeListened");
                int hours=timeListened/3600;
                int remainder=timeListened-hours*3600;
                int minutes=remainder/60;
                remainder=remainder-minutes*60;
                int seconds=remainder;
                System.out.println(song+"  *****  "+hours+" hours, "+minutes+" minutes, "+seconds+" seconds");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
