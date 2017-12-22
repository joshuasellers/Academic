package nba.fourguysonecode.tables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nba.fourguysonecode.objects.PlayerStats;

/**
 * Class to make and manipulate the playerstats table
 * @author joshuasellers
 * Created on 4/3/17.
 */
public class PlayerStatsTable extends DatabaseTable
{

    public static final String TableName = "playerstats";

    /**
     * Reads a cvs file for data and adds them to the playerstats table
     *
     * Does not create the table. It must already be created
     *
     * @param conn: database connection to work with
     * @param fileName
     * @throws SQLException
     */

    public static void populatePlayerStatsTableFromCSV(Connection conn,
                                                  String fileName)
            throws SQLException{
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<PlayerStats> playerstats = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Skip the first line which is just the format specifier for the CSV file.
            String line = br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                playerstats.add(new PlayerStats(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Creates the SQL query to do a bulk add of all playerstats
         * that were read in. This is more efficient then adding one
         * at a time
         */
        String sql = createPlayerStatsInsertSQL(playerstats);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    /**
     * Create the playerstats table with the given attributes
     *
     * @param conn: the database connection to work with
     */
    public static void createPlayerStatsTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS playerstats("
                    + "PLAYER_ID INT PRIMARY KEY auto_increment" +
                    ","
                    + "GAMES_PLAYED INT,"
                    + "TOT_MINS FLOAT,"
                    + "TOT_PTS FLOAT,"
                    + "FG_ATT FLOAT,"
                    + "FG_MADE FLOAT,"
                    + "THREE_ATT FLOAT,"
                    + "THREE_MADE FLOAT,"
                    + "FREE_ATT FLOAT,"
                    + "FREE_MADE FLOAT,"
                    + "OFF_REBOUND FLOAT,"
                    + "DEF_REBOUND FLOAT,"
                    + "ASSISTS FLOAT,"
                    + "STEALS FLOAT,"
                    + "BLOCKS FLOAT,"
                    + "TURNOVERS FLOAT,"
                    + ");" ;

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a single playerstats to the database
     *
     * @param conn
     * @param player_id
     * @param games_played
     * @param tot_mins
     * @param fg_att
     * @param fg_made
     * @param three_att
     * @param three_made
     * @param free_att
     * @param free_made
     * @param off_rebound
     * @param def_rebound
     * @param assists
     * @param steals
     * @param blocks
     * @param turnovers
     */
    public static void addPlayerStats(Connection conn,
                                 int player_id,
                                 int games_played,
                                 float tot_mins,
                                 float tot_pts,
                                 float fg_att,
                                 float fg_made,
                                 float three_att,
                                 float three_made,
                                 float free_att,
                                 float free_made,
                                 float off_rebound,
                                 float def_rebound,
                                 float assists,
                                 float steals,
                                 float blocks,
                                 float turnovers){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO playerstats "
                        + "VALUES(%d, %d, %f, %f, %f, %f, " +
                        "%f, %f, %f, %f, %f, %f, %f, %f, %f, %f);",
                player_id, games_played, tot_mins, tot_pts, fg_att, fg_made,
                three_att, three_made, free_att, free_made, off_rebound,
                def_rebound, assists, steals, blocks, turnovers);
        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This creates an sql statement to do a bulk add of playerstats
     *
     * @param playerstats: list of PlayerStats objects to add
     *
     * @return
     */
    public static String createPlayerStatsInsertSQL(ArrayList<PlayerStats> playerstats){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO playerstats (PLAYER_ID, GAMES_PLAYED, TOT_MINS," +
                "TOT_PTS, FG_ATT, FG_MADE, THREE_ATT, THREE_MADE, FREE_ATT, FREE_MADE, OFF_REBOUND," +
                "DEF_REBOUND, ASSISTS, STEALS, BLOCKS, TURNOVERS) VALUES");

        /**
         * For each playerstats append a tuple
         *
         * If it is not the last playerstats add a comma to seperate
         *
         * If it is the last playerstats add a semi-colon to end the statement
         */
        for(int i = 0; i < playerstats.size(); i++){
            PlayerStats ps = playerstats.get(i);
            sb.append(String.format("(%d, %d, %f, %f, %f, %f, %f, " +
                            "%f, %f, %f, %f, %f, %f, %f, %f, %f)",
                    ps.getPlayer_id(), ps.getGames_played(), ps.getTot_mins(), ps.getTot_pts(), ps.getFg_att(),
                    ps.getFg_made(), ps.getThree_att(), ps.getThree_made(), ps.getFree_att(), ps.getFree_made(),
                    ps.getOff_rebound(), ps.getDef_rebound(), ps.getAssists(), ps.getSteals(), ps.getBlocks(),
                    ps.getTurnovers()));
            if( i != playerstats.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    /**
     * Makes a query to the playerstats table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static List<PlayerStats> queryPlayerStatsTable(Connection conn,
                                                          ArrayList<String> columns,
                                                          ArrayList<String> whereClauses)
    {
        // Query the database for all matching results.
        ResultSet results = ConferenceTable.queryTable(conn, PlayerStatsTable.TableName, columns, whereClauses);

        // Create a list to hold all of the PlayerStats objects.
        ArrayList<PlayerStats> playerStats = new ArrayList<>();

        try
        {
            // Loop through all of the results and create a PlayerStats object for each one.
            while (results.next())
            {
                // Create a new PlayerStats object using the result data.
                playerStats.add(new PlayerStats(results));
            }
        }
        catch (SQLException e)
        {
            // An error occurred while processing the results, print the stack trace.
            e.printStackTrace();
        }

        // Return the player stats list.
        return playerStats;
    }

    /**
     * Queries and print the table
     * @param conn
     */
    public static void printPlayerStatsTable(Connection conn){
        String query = "SELECT * FROM playerstats;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("Player %d: %d %f %f %f %f %f %f %f %f %f %f %f %f %f %f\n",
                        result.getInt(1),
                        result.getInt(2),
                        result.getFloat(3),
                        result.getFloat(4),
                        result.getFloat(5),
                        result.getFloat(6),
                        result.getFloat(7),
                        result.getFloat(8),
                        result.getFloat(9),
                        result.getFloat(10),
                        result.getFloat(11),
                        result.getFloat(12),
                        result.getFloat(13),
                        result.getFloat(14),
                        result.getFloat(15),
                        result.getFloat(16));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}