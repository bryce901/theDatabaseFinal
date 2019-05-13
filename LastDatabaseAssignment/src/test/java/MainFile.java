import com.github.javafaker.Faker;
import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;
import java.util.Scanner;



public class MainFile {


    // CSV file header
    private static final Object[] FILE_HEADER = { "Empoyee Name","Empoyee Code", "In Time", "Out Time", "Duration", "Is Working Day" };
    static final String JBDC_DRIVER="com.mysql.jbdc.Driver";
    static final String DB_URL="jdbc:mysql://35.192.17.149:3306/MasterDB";
    static final String USER="ryanbryce";
    static final String PASS="poop";
    public static Integer userID = null;
    public static String song = "";
    public static String genre = "";
    public static String artist = "";
    public static String name="";
    public static Integer length = null;
    public static Integer timeListened = null;
    public static String playlistName = "";
    public static Integer IDNumber;
    public static Boolean enteredRightInput=null;
    public static Integer currentUserID=null;

    public static void main (String[] args) {
        Faker faker = new Faker();
        Connection conn = null;
        Statement stmt = null;
        Scanner input = new Scanner(System.in);

        String currentlyListeningTo = "";
        String followers[] = new String[50];
        String following[] = new String[50];
        String recentPlayedArtists[] = new String[50];
        String publicPlaylists[] = new String[50];
        FileWriter MainfileWriter = null;
        enteredRightInput=false;



        try {
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database successfully\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("         Who are you?     ");
        System.out.println("*******************************");
        System.out.println("|      (1) Eugena Robel       |");
        System.out.println("|     (2) Coleen Upton MD     |");
        System.out.println("|   (3) Shirleen Shanahan IV  |");
        System.out.println("*******************************");
        while (enteredRightInput.equals(false)){
            try{
                Scanner myOBJ=new Scanner(System.in);
                currentUserID=myOBJ.nextInt();
                enteredRightInput=true;
            }
            catch (Exception InputMismatch)
            {
                System.out.println("Enter an Integer");

            }
        }

        System.out.println("       Type in the number for the option you want");
        System.out.println("*********************************************************");
        System.out.println("|            (1) Show all names in database             |");
        System.out.println("|              (2) View all of your songs               |");
        System.out.println("|           (3) View currently listening song           |");
        System.out.println("|  (4) View the total time you've listened to each song |");
        System.out.println("|                    (5) Account Info                   |");
        System.out.println("*********************************************************");
        Scanner in = new Scanner(System.in);
        int selection;
        selection = in.nextInt();
        switch (selection) {
            //show names
            case 1:
                try {
                    optionsClass.showNames();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
             //show your songs
            case 2:
                optionsClass.showUserSongs();
                //populateuserStats();
                //populatePublicPlaylists();
                //populateRecentArtists();
                break;
            //current song
            case 3:
                optionsClass.currentlyListeningTo();
                //populateAllSavedSongs();
                break;
            case 4:
                optionsClass.totalTimeEachSong();
                break;
            case 5:
                displayOptions();
                break;

        }

    }
    public static void displayOptions(){


        System.out.println("|**********************************|");
        System.out.println("| (1) How many followers  you have |");
        System.out.println("|  (2) How many people you follow  |");
        System.out.println("|   (3) Recently played artists    |");
        System.out.println("|    (4) Your public playlists     |");
        System.out.println("| (5) See top songs from everyone  |");
        System.out.println("|  (6) Add a song to your library  |");
        System.out.println("|  (7) Remove a song from library  |");
        System.out.println("|        (8) Update a song         |");
        System.out.println("|**********************************|");
        Scanner in = new Scanner(System.in);
        int selection;
        selection = in.nextInt();
        switch (selection){
            case 1: accountInfoDisplay.howManyFollowers();
                //populateRecentArtists();
                 break;

            case 2:
                accountInfoDisplay.howManyYouFollow(); break;

            case 3:
                accountInfoDisplay.recentArtists(); break;

            case 4:
                accountInfoDisplay.publicPlaylists(); break;

            case 5:
                accountInfoDisplay.displayTopSongsFromEveryone(); break;

            case 6:addAndRemoveClass.addSong(); break;

            case 7:addAndRemoveClass.removeSong(); break;

            case 8:updateClass.main(null);

        }

    }



    //***all of the methods below were used to populate the database***
    public static void populateuserStats(){
        Statement stmt=null;
        Connection con=null;
        String insertTable="INSERT into allUserStats (song, totalUserTimeListened,genre,artist) values (?,?,?,?);";
        Faker faker = new Faker();
        String songName="";
        Integer totalTimeListened=null;
        String theGenre="";
        String theArtist="";
        Integer amount=0;
        while (amount<20){
            songName=faker.space().meteorite();
            totalTimeListened=(int)(25000*Math.random());
            theGenre=faker.music().genre();
            theArtist=faker.rockBand().name();
            try {
                con=DriverManager.getConnection(DB_URL,USER,PASS);
                PreparedStatement preparedStmt=con.prepareStatement(insertTable);
                preparedStmt.setString(1,songName);
                preparedStmt.setInt(2,totalTimeListened);
                preparedStmt.setString(3,theGenre);
                preparedStmt.setString(4,theArtist);
                preparedStmt.execute();
                System.out.println("Worked"+amount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            amount++;
        }
    }
    public static void populateAllSavedSongs()
    {
        Statement stmt=null;
        Connection con=null;
        String insertTable="INSERT into allSavedSongs (userID, name,song,genre,artist,length,timeListened,playlist) values (?,?,?,?,?,?,?,?);";
        Faker faker = new Faker();
        Integer amount=0;
        name=faker.name().fullName();
        while(amount<45)
        {
            //System.out.println("worked");
            userID=1;
            genre=faker.music().genre();
            length=(int)(300*Math.random());
            timeListened=(int)(10000*Math.random());
            playlistName=faker.hipster().word();
            artist=faker.rockBand().name();
            song=faker.space().meteorite();
            try {
                con=DriverManager.getConnection(DB_URL,USER,PASS);
                stmt=con.createStatement();
                PreparedStatement preparedStmt=con.prepareStatement(insertTable);
                preparedStmt.setInt(1,3);
                preparedStmt.setString(2,name);
                preparedStmt.setString(3,song);
                preparedStmt.setString(4,genre);
                preparedStmt.setString(5,artist);
                preparedStmt.setInt(6,length);
                preparedStmt.setInt(7,timeListened);
                preparedStmt.setString(8,playlistName);
                preparedStmt.execute();
                System.out.println("worked "+amount);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            amount++;
        }
    }
    public static void populateRecentArtists()
    {
        String recentartist="";
        Statement stmt=null;
        Connection con=null;
        String insertTable="INSERT into recentArtists (userID, recentPlayedArtist) values (?,?);";
        Faker faker = new Faker();
        Integer amount=0;

        while(amount<30)
        {
            recentartist=faker.rockBand().name();
            try {
                con=DriverManager.getConnection(DB_URL,USER,PASS);
                stmt=con.createStatement();
                PreparedStatement preparedStmt=con.prepareStatement(insertTable);
                preparedStmt.setInt(1,3);
                preparedStmt.setString(2,recentartist);
                preparedStmt.execute();
                System.out.println("worked "+amount);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            amount++;
        }
    }
    public static void populatePublicPlaylists(){
        String recentPlaylist="";
        Statement stmt=null;
        Connection con=null;
        String insertTable="INSERT into publicPlaylists (userID, publicPlaylist) values (?,?);";
        Faker faker = new Faker();
        Integer amount=0;

        while(amount<6)
        {
            recentPlaylist=faker.hipster().word();
            try {
                con=DriverManager.getConnection(DB_URL,USER,PASS);
                stmt=con.createStatement();
                PreparedStatement preparedStmt=con.prepareStatement(insertTable);
                preparedStmt.setInt(1,3);
                preparedStmt.setString(2,recentPlaylist);
                preparedStmt.execute();
                System.out.println("worked "+amount);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            amount++;
        }
    }

}

