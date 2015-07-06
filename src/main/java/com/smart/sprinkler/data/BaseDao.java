package com.smart.sprinkler.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.sqlite.JDBC;

public class BaseDao {

	public static	Connection  getConnection()
	{
		Connection connection = null;

		try {
			DriverManager.registerDriver(new JDBC());
			connection = DriverManager
					.getConnection("jdbc:sqlite:Hackathon.db");

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return connection;

	}
	
	public String getDuration(String cropType)
	{
		Connection connection=null;
		PreparedStatement statement=null;
		String baseline=null;
			//Based on the crop type read the duration value from table
			
			try {
				connection=getConnection();
				statement=connection.prepareStatement("select WateringDuration from CropBaseline where CropType=?");
				statement.setString(1, cropType);
				ResultSet resultset=statement.executeQuery();
				
				while(resultset.next())
				{
					baseline=resultset.getString("WateringDuration");
				}
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}finally
			{
				try{
					statement.close();
					connection.close();
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
				
			}
			return baseline;
	}
	
	public Date getLastRun(String cropType)
	{
		Connection connection=null;
		PreparedStatement statement=null;
		String baseline=null;
		Date date =  null;
			//Based on the crop type read the duration value from table
			
			try {
				connection=getConnection();
				statement=connection.prepareStatement("select max(Run_Time) AS lastRun from Events where Crop_Type=?");
				statement.setString(1, cropType);
				ResultSet resultset=statement.executeQuery();
				
				while(resultset.next())
				{
					
					date = resultset.getDate("lastRun");
					
				}
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}finally
			{
				try{
					statement.close();
					connection.close();
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
				
			}
			return date;
	}
	
	public void insertEvent(String cropType){
		Connection connection=null;
		PreparedStatement statement=null;
			
			try {
				connection=getConnection();
				statement=connection.prepareStatement("Insert into Events(Crop_Type,Run_Time) values(?, datetime('now'))");
				statement.setString(1, cropType);
				statement.executeUpdate();
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}finally
			{
				try{
					statement.close();
					connection.close();
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
				
			}
	}
	
	public void updateBaseLine(int correctedBL, String cropType){
		Connection connection=null;
		PreparedStatement statement=null;
			
			try {
				connection=getConnection();
				statement=connection.prepareStatement("update CropBaseline set WateringDuration=? where CropType=?");
				statement.setInt(1, correctedBL);
				statement.setString(2, cropType);
				statement.executeUpdate();
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}finally
			{
				try{
					statement.close();
					connection.close();
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
				
			}
	}

}