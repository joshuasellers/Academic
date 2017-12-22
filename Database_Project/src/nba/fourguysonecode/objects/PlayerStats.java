package nba.fourguysonecode.objects;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by joshuasellers on 4/3/17.
 */
public class PlayerStats {
    private final SimpleIntegerProperty player_id;
    private final SimpleIntegerProperty games_played;
    private final SimpleFloatProperty tot_mins;
    private final SimpleFloatProperty tot_pts;
    private final SimpleFloatProperty fg_att;
    private final SimpleFloatProperty fg_made;
    private final SimpleFloatProperty three_att;
    private final SimpleFloatProperty three_made;
    private final SimpleFloatProperty free_att;
    private final SimpleFloatProperty free_made;
    private final SimpleFloatProperty off_rebound;
    private final SimpleFloatProperty def_rebound;
    private final SimpleFloatProperty assists;
    private final SimpleFloatProperty steals;
    private final SimpleFloatProperty blocks;
    private final SimpleFloatProperty turnovers;

    public PlayerStats(int player_id, int games_played, float tot_mins, float tot_pts, int fg_att, int fg_made,
            int three_att, int three_made, int free_att, int free_made, int off_rebound, int def_rebound,
            int assists, int steals, int blocks, int turnovers){
        this.player_id = new SimpleIntegerProperty(player_id);
        this.games_played = new SimpleIntegerProperty(games_played);
        this.tot_mins = new SimpleFloatProperty(tot_mins);
        this.tot_pts = new SimpleFloatProperty(tot_pts);
        this.fg_att = new SimpleFloatProperty(fg_att);
        this.fg_made = new SimpleFloatProperty(fg_made);
        this.three_att = new SimpleFloatProperty(three_att);
        this.three_made = new SimpleFloatProperty(three_made);
        this.free_att = new SimpleFloatProperty(free_att);
        this.free_made = new SimpleFloatProperty(free_made);
        this.off_rebound = new SimpleFloatProperty(off_rebound);
        this.def_rebound = new SimpleFloatProperty(def_rebound);
        this.assists = new SimpleFloatProperty(assists);
        this.steals = new SimpleFloatProperty(steals);
        this.blocks = new SimpleFloatProperty(blocks);
        this.turnovers = new SimpleFloatProperty(turnovers);
    }

    public PlayerStats(String[] data){
        this.player_id = new SimpleIntegerProperty(Integer.parseInt(data[0]));
        this.games_played = new SimpleIntegerProperty(Integer.parseInt(data[1]));
        this.tot_mins = new SimpleFloatProperty(Float.parseFloat(data[2]));
        this.tot_pts = new SimpleFloatProperty(Float.parseFloat(data[3]));
        this.fg_att = new SimpleFloatProperty(Float.parseFloat(data[4]));
        this.fg_made = new SimpleFloatProperty(Float.parseFloat(data[5]));
        this.three_att = new SimpleFloatProperty(Float.parseFloat(data[6]));
        this.three_made = new SimpleFloatProperty(Float.parseFloat(data[7]));
        this.free_att = new SimpleFloatProperty(Float.parseFloat(data[8]));
        this.free_made = new SimpleFloatProperty(Float.parseFloat(data[9]));
        this.off_rebound = new SimpleFloatProperty(Float.parseFloat(data[10]));
        this.def_rebound = new SimpleFloatProperty(Float.parseFloat(data[11]));
        this.assists = new SimpleFloatProperty(Float.parseFloat(data[12]));
        this.steals = new SimpleFloatProperty(Float.parseFloat(data[13]));
        this.blocks = new SimpleFloatProperty(Float.parseFloat(data[14]));
        this.turnovers = new SimpleFloatProperty(Float.parseFloat(data[15]));
    }

    public PlayerStats(ResultSet result) throws SQLException
    {
        // Initialize fields using the result set.
        this.player_id = new SimpleIntegerProperty(result.getInt(1));
        this.games_played = new SimpleIntegerProperty(result.getInt(2));
        this.tot_mins = new SimpleFloatProperty(result.getFloat(3));
        this.tot_pts = new SimpleFloatProperty(result.getFloat(4));
        this.fg_att = new SimpleFloatProperty(result.getFloat(5));
        this.fg_made = new SimpleFloatProperty(result.getFloat(6));
        this.three_att = new SimpleFloatProperty(result.getFloat(7));
        this.three_made = new SimpleFloatProperty(result.getFloat(8));
        this.free_att = new SimpleFloatProperty(result.getFloat(9));
        this.free_made = new SimpleFloatProperty(result.getFloat(10));
        this.off_rebound = new SimpleFloatProperty(result.getFloat(11));
        this.def_rebound = new SimpleFloatProperty(result.getFloat(12));
        this.assists = new SimpleFloatProperty(result.getFloat(13));
        this.steals = new SimpleFloatProperty(result.getFloat(14));
        this.blocks = new SimpleFloatProperty(result.getFloat(15));
        this.turnovers = new SimpleFloatProperty(result.getFloat(16));
    }

    public int getPlayer_id() {return player_id.get();}
    public int getGames_played() {return games_played.get();}
    public float getTot_mins() {return tot_mins.get();}
    public float getTot_pts() {return tot_pts.get();}
    public float getFg_att() {return fg_att.get();}
    public float getFg_made() {return fg_made.get();}
    public float getThree_att() {return three_att.get();}
    public float getThree_made() {return three_made.get();}
    public float getFree_att() {return free_att.get();}
    public float getFree_made() {return free_made.get();}
    public float getOff_rebound() {return off_rebound.get();}
    public float getDef_rebound() {return def_rebound.get();}
    public float getAssists() {return assists.get();}
    public float getSteals() {return steals.get();}
    public float getBlocks() {return blocks.get();}
    public float getTurnovers() {return  turnovers.get();}
}
