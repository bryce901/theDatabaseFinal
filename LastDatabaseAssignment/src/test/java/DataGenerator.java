import com.github.javafaker.Faker;
import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;
//import com.mysql.jdbc.Driver;
import java.util.Scanner;
import java.util.Random;



public class DataGenerator {


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
        System.out.println("|                  (6) View your stats                  |");
        System.out.println("*********************************************************");
        Scanner in = new Scanner(System.in);
        int selection;
        selection = in.nextInt();
        switch (selection) {
            //show names
            case 1:
                try {
                    showNames();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
             //show your songs
            case 2:
                showUserSongs();
                break;
            //current song
            case 3:
                currentlyListeningTo();
                //populateAllSavedSongs();
                break;
            case 4:
                // TODO: 5/8/2019
                //fix hour minutes seconds
                totalTimeEachSong();
                break;
            case 5:
                populateAllSavedSongs();
                // TODO: 5/8/2019
                break;
            case 6:
                // TODO: 5/9/2019
                break;
        }
        //theFileName=input.nextLine();
        //System.out.println("How many tuples do you want");


    }

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
                int seconds=timeListened%60;
                int minutes=timeListened/60;
                int hours=minutes%60;
                hours=minutes/60;
                System.out.println(song+"  *****  "+hours+" hours, "+minutes+" minutes, "+seconds+" seconds");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
            /*


            //Creating Main Table
            String TableDropMain="DROP TABLE IF EXISTS MainInfoTable;";
            String CreateMain="CREATE TABLE MainInfoTable (ID VARCHAR (25) PRIMARY KEY, FirstName VARCHAR(25),\n" +
                    "                      LastName VARCHAR(25),CompanyName varchar (45),StreetAddress varchar(45),FavoriteBeer varchar (45));";
            stmt.executeUpdate(TableDropMain);
            stmt.executeUpdate(CreateMain);

            //Creating Simple Table
            String simpleTableDrop="DROP TABLE IF EXISTS simpleTable;";
            String CreateSimple="CREATE TABLE simpleTable (ID VARCHAR (20) PRIMARY KEY, FirstName VARCHAR(25),\n" +
                    "                      LastName VARCHAR(25));";
            stmt.executeUpdate(simpleTableDrop);
            stmt.executeUpdate(CreateSimple);

            //Creating Address table
            String addressTableDrop="DROP TABLE IF EXISTS addressTable;";
            String createAddressTable="CREATE TABLE addressTable (StreetAdress varchar (30), CityName VARCHAR(50),\n" +
                    "                      Country VARCHAR(50));";
            stmt.executeUpdate(addressTableDrop);
            stmt.executeUpdate(createAddressTable);

            //Creating Company Table
            String companyTableDrop="DROP TABLE IF EXISTS companyTable;";
            String createCompanyTable="CREATE TABLE companyTable (CompanyName varchar (30),\n" +
                    "                      Industry VARCHAR(50));";
            stmt.executeUpdate(companyTableDrop);
            stmt.executeUpdate(createCompanyTable);

            //Creating Beer Table
            String beerTableDrop="DROP TABLE IF EXISTS beerTable;";
            String createBeerTable="CREATE TABLE beerTable (BeerName varchar (50), Hop VARCHAR(50),\n" +
                    "                      Style VARCHAR(100));";
            stmt.executeUpdate(beerTableDrop);
            stmt.executeUpdate(createBeerTable);


            numTuplesInt=Integer.parseInt(numOfTuples);*/



/*
        while (mainTableNumber<numTuplesInt)
        {
            //now printing all tuples to the csv
            firstName=faker.name().firstName();
            LastName=faker.name().lastName();
            jobTitle=faker.job().title();
            companyName=faker.company().name();
            streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
            cityName=faker.address().cityName();
            country=faker.address().country();
            beerHop=faker.beer().hop();
            beerStyle=faker.beer().style();
            faveBeerName=faker.beer().name();
            industryName=faker.company().industry();

            //ID is set to zero up top and is now counting and printing to the csv
            ID++;
            String sID=ID.toString();
            try{
                MainfileWriter=new FileWriter(theFileName,true);
                MainfileWriter.append(sID+",");
                MainfileWriter.append(firstName+","+LastName+","+jobTitle+","+companyName+","+streetAddress+","+cityName+","+country+","+beerHop+","+beerStyle+","+faveBeerName+","+industryName+"\n");
                MainfileWriter.close();

            }
            catch (Exception e)
            {
            }

            mainTableNumber++;
        }

        //writing to simpleTable
        try {
            BufferedReader br=new BufferedReader(new FileReader(theFileName));
            String line="";
            String csvSplitBy=",";
            while((line=br.readLine())!=null){
                String [] data=line.split(csvSplitBy);
                String studentIDS =data[0];
                String thefirstName=data[1];
                String lastName=data[2];
                String insertIntoSimple="INSERT into simpleTable (ID, FirstName,LastName) values (?,?,?);";
                PreparedStatement preparedStmt=conn.prepareStatement(insertIntoSimple);
                preparedStmt.setString(1,studentIDS);
                preparedStmt.setString(2,thefirstName);
                preparedStmt.setString(3,lastName);
                preparedStmt.execute();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //writing mainTable
        try {
            BufferedReader br=new BufferedReader(new FileReader(theFileName));
            String line="";
            String csvSplitBy=",";
            while((line=br.readLine())!=null){
                String [] data=line.split(csvSplitBy);
                String studentIDS =data[0];
                String thefirstName=data[1];
                String lastName=data[2];
                String CompanyName=data[4];
                String StreetAddress=data[5];
                String favBeer=data[10];

                String insertIntoMain="INSERT into MainInfoTable (ID, FirstName,LastName,CompanyName,StreetAddress,FavoriteBeer) values (?,?,?,?,?,?);";
                PreparedStatement preparedStmt=conn.prepareStatement(insertIntoMain);
                preparedStmt.setString(1,studentIDS);
                preparedStmt.setString(2,thefirstName);
                preparedStmt.setString(3,lastName);
                preparedStmt.setString(4,CompanyName);
                preparedStmt.setString(5,StreetAddress);
                preparedStmt.setString(6,favBeer);
                preparedStmt.execute();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //writing to address table
        try {
            BufferedReader br=new BufferedReader(new FileReader(theFileName));
            String line="";
            String csvSplitBy=",";
            while((line=br.readLine())!=null){
                String [] data=line.split(csvSplitBy);
                String TheStreetAddress=data[5];
                String TheCityName=data[6];
                String TheCountry=data[7];
                String insertIntoAddressTable="INSERT into addressTable (StreetAdress,CityName,Country) values (?,?,?);";
                PreparedStatement preparedStmt=conn.prepareStatement(insertIntoAddressTable);
                preparedStmt.setString(1,TheStreetAddress);
                preparedStmt.setString(2,TheCityName);
                preparedStmt.setString(3,TheCountry);

                preparedStmt.execute();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //writing to beer table
        try {
            BufferedReader br=new BufferedReader(new FileReader(theFileName));
            String line="";
            String csvSplitBy=",";
            while((line=br.readLine())!=null){
                String [] data=line.split(csvSplitBy);
                String TheBeerName=data[10];
                String TheHopName=data[9];
                String TheStyleName=data[8];
                String insertIntoAddressTable="INSERT into beerTable (BeerName,Hop,Style) values (?,?,?);";
                PreparedStatement preparedStmt=conn.prepareStatement(insertIntoAddressTable);
                preparedStmt.setString(1,TheBeerName);
                preparedStmt.setString(2,TheHopName);
                preparedStmt.setString(3,TheStyleName);
                preparedStmt.execute();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //inserting into company table
        try {
            BufferedReader br=new BufferedReader(new FileReader(theFileName));
            String line="";
            String csvSplitBy=",";
            while((line=br.readLine())!=null){
                String [] data=line.split(csvSplitBy);
                String TheCompanyNames=data[4];
                String TheIndustryNames=data[11];
                String insertIntoAddressTable="INSERT into companyTable (CompanyName,Industry) values (?,?);";
                PreparedStatement preparedStmt=conn.prepareStatement(insertIntoAddressTable);
                preparedStmt.setString(1,TheCompanyNames);
                preparedStmt.setString(2,TheIndustryNames);
                preparedStmt.execute();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } */





}

