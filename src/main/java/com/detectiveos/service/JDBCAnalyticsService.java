package com.detectiveos.service;

import com.detectiveos.config.AppConfig;
import java.sql.*;
import java.util.*;

/** Direct JDBC reporting layer used for assessment demonstration. */
public class JDBCAnalyticsService {
    private Connection connect() throws SQLException {return DriverManager.getConnection(AppConfig.get("db.url"),AppConfig.get("db.username"),AppConfig.get("db.password"));}
    public String caseSummary(){
        StringBuilder out=new StringBuilder("CASE STATUS ANALYTICS\n--------------------\n");
        String sql="SELECT status, COUNT(*) total FROM cases GROUP BY status ORDER BY status";
        try(Connection con=connect();PreparedStatement ps=con.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next())out.append(String.format(Locale.ROOT,"%-10s : %d%n",rs.getString("status"),rs.getInt("total")));}catch(SQLException e){return out.append("JDBC unavailable: ").append(e.getMessage()).toString();}
        return out.toString();
    }
    public List<String> topSuspects(){
        List<String> result=new ArrayList<>();
        String sql="SELECT name, suspicionScore FROM persons WHERE person_type='SUSPECT' ORDER BY suspicionScore DESC LIMIT 5";
        try(Connection con=connect();PreparedStatement ps=con.prepareStatement(sql);ResultSet rs=ps.executeQuery()){while(rs.next())result.add(String.format(Locale.ROOT,"%s  →  %.1f/100",rs.getString(1),rs.getDouble(2)));}catch(SQLException e){result.add("JDBC unavailable: "+e.getMessage());}
        return result;
    }
}
