import java.sql.*;

public class accountInfoDisplay extends MainFile{
    public static String DB_URL= MainFile.DB_URL;
    public static String USER= MainFile.USER;
    public static String PASS= MainFile.PASS;

    public static void howManyYouFollow(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectLength="SELECT following FROM userInfo WHERE userID="+MainFile.currentUserID+";";
        try {
            conn= DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectLength);
            while (rs.next()){
                Object followering=rs.getObject("following");
                String followeingStr=followering.toString();
                System.out.println("You are following "+followering+" people");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void recentArtists(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectLength="SELECT recentPlayedArtist FROM recentArtists WHERE userID="+MainFile.currentUserID+" LIMIT 10;";
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectLength);
            while (rs.next()){
                Object artist=rs.getObject("recentPlayedArtist");
                String artistStr=artist.toString();
                System.out.println(artistStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void publicPlaylists(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectLength="SELECT publicPlaylist FROM publicPlaylists WHERE userID="+currentUserID;
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectLength);
            while (rs.next()){
                Object playlist=rs.getObject("publicPlaylist");
                String playlistString=playlist.toString();
                System.out.println(playlistString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void displayTopSongsFromEveryone(){
        System.out.println("Top  5 Songs ordered by total user time listened: ");
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectLength="SELECT song FROM allUserStats ORDER BY totalUserTimeListened DESC LIMIT 5";
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectLength);
            while (rs.next()){
                Object playlist=rs.getObject("song");
                String playlistString=playlist.toString();
                System.out.println(playlistString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void howManyFollowers(){
        Statement stmt=null;
        Connection conn=null;
        ResultSet rs=null;
        String selectLength="SELECT followers FROM userInfo WHERE userID="+currentUserID+";";
        try {
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            stmt=conn.createStatement();
            rs=stmt.executeQuery(selectLength);
            while (rs.next()){
                Object followers=rs.getObject("followers");
                String followersStr=followers.toString();
                System.out.println("You have: "+followersStr+" followers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
