package com.smart.sprinkler.weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import com.smart.sprinkler.data.BaseDao;



public class SmartMachine {

	
	public static void main(String[] args) {
		

	}
	
	
	public void engineActivate(int sensorValue,String chanceOfRain,String cropType)
	{
		Connection connection=null;
		PreparedStatement statement=null;
		PreparedStatement statement1=null;
		String baseline=null;
		java.sql.Timestamp timeStamp1 =  null;
		int updatedBaseline=0;
		if(sensorValue==0)
		{
			//Based on the crop type read the duration value from table
			
			try {
				connection=BaseDao.getConnection();
				statement=connection.prepareStatement("select WateringDuration from CropBaseline where CropType=?");
				statement.setString(0, cropType);
				ResultSet resultset=statement.executeQuery();
				java.sql.Timestamp timeStamp =  null;				
				
				while(resultset.next())
				{
				
					
					baseline=resultset.getString(0);
					
				}
				
				updatedBaseline=Integer.parseInt(baseline)-((Integer.parseInt(baseline)*Integer.parseInt(chanceOfRain))/100);
				
				//Pull the event details from the table
				
				statement1=connection.prepareStatement("select max(TimeStamp) from Event where CropType=? ");
				statement1.setString(0, cropType);
				ResultSet resultset2=statement.executeQuery();
				while(resultset2.next())
				{
					 timeStamp = resultset2.getTimestamp(0);
				}
				Date date = new Date();
				java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());
				long diff = currentTime.getTime()-timeStamp.getTime();
				long diffHours = diff/((60*60*1000)%24);
				if(diffHours>6)
				{
					updatedBaseline =Integer.parseInt(baseline)+((10*Integer.parseInt(baseline))/100);
				}
				
				
				
			} catch (SQLException e) 
			{
				
				e.printStackTrace();
			}finally
			{
				try{
					
					connection.close();
					statement.close();
					statement1.close();
				}catch(Exception exp)
				{
					exp.printStackTrace();
				}
				
			}
		}
		else if(sensorValue==1)
		{
			try{
				
				statement1=connection.prepareStatement("select max(TimeStamp) from Event where CropType=? ");
				statement1.setString(0, cropType);
				ResultSet resultset2=statement.executeQuery();
				while(resultset2.next())
				{
					timeStamp1 = resultset2.getTimestamp(0);
				}
				Date date = new Date();
				java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());
				long diff = currentTime.getTime()-timeStamp1.getTime();
				long diffDay = diff/(24 * 60 * 60 * 1000);
				if(diffDay>1)
				{
					// send notification to farmer
				}
			}catch(Exception exp){
				exp.printStackTrace();
			}finally{
				try{
					connection.close();
					statement1.close();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
			
			
		}
		
	}
	
	
	
	

}
