package com.migration;
/**
 * @author Rambrabu Jaganathan
 *
 * Oracle to Mysql Migration.
 */

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.*;
import java.text.*;

import com.mysql.jdbc.PreparedStatement;


public class OracleToMysqlMigration {
	
	static String ORACLE_URL;
	static String ORACLE_USER;
	static String ORACLE_PASSWD;
	static String MYSQL_URL;
	static String MYSQL_USER;
	static String MYSQL_PASSWD;
	static String NOMIG_TABLE[];
	static String DB;
	static List<String> errorStatements = new ArrayList<String>();
	
	protected static String defaultLogFile = "log.txt";
    
    public static void write(String s) throws IOException {
    	write(defaultLogFile, s);
    }
    
    public static void write(String f, String s) throws IOException {
    	TimeZone tz = TimeZone.getTimeZone("IST"); 
        Date now = new Date();
        DateFormat df = new SimpleDateFormat ("dd.mm.yyyy hh:mm:ss ");
        df.setTimeZone(tz);
        String currentTime = df.format(now);
    	
    	FileWriter aWriter = new FileWriter(f, true);
    	aWriter.write(currentTime + " " + s+"\r\n"); 
        aWriter.flush();
        aWriter.close();
    }
	
	public static void main(String[] args) throws Exception {
		
		File file = new File(defaultLogFile); 
		if(file.exists()){
			file.delete();
		}
		
		Connection mysql_conn= null;
		Connection oracle_conn= null;
		
		/*Reading configuration file*/
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			ORACLE_URL = prop.getProperty("oracle_url");
			ORACLE_USER = prop.getProperty("oracle_user");
			ORACLE_PASSWD = prop.getProperty("oracle_password");
			MYSQL_URL = prop.getProperty("mysql_url");
			MYSQL_USER = prop.getProperty("mysql_user");
			MYSQL_PASSWD = prop.getProperty("mysql_password");
			NOMIG_TABLE = prop.getProperty("nomig_table").toUpperCase().split(",");
			DB = prop.getProperty("database_name");
		}catch(Exception e){
			write("Error occurs while reading config.properties file");
			System.out.println("Error occurs while reading config.properties file "+e.getMessage());
			return;
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					write("Error occurs while reading config.properties file "+e.getMessage());
					System.out.println("Error occurs while reading config.properties file "+e.getMessage());
					return;
				}
			}
		}
    	/*Oracle database connection*/
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		oracle_conn= DriverManager.getConnection(ORACLE_URL.trim(),ORACLE_USER.trim(),ORACLE_PASSWD.trim()); 
    		if (oracle_conn != null) {
    			write("Oracle database connected sucessfully...");
    			System.out.println("Oracle database connected sucessfully...");
    		} else {
    			write("Oracle Connection failed");
    			System.out.println("Oracle Connection failed");
    		}
    	}catch(SQLException e){
    		write("Oracle Connection failed");
    		System.out.println("Oracle Connection failed ");
    		return;
    	}catch(ClassNotFoundException e) {
    		write("Please provide Oracle JDBC Driver?");
			System.out.println("Please provide Oracle JDBC Driver?");
			return;
		}
    	
    	/*Mysql database connection*/
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		mysql_conn= DriverManager.getConnection(MYSQL_URL.trim(),MYSQL_USER.trim(),MYSQL_PASSWD.trim()); 
    		if (mysql_conn != null) {
    			write("Mysql database connected sucessfully...");
    			System.out.println("Mysql database connected sucessfully...");
    		} else {
    			write("Mysql Connection failed");
    			System.out.println("Mysql Connection failed");
    		}
    	}catch(SQLException e){
    		write("Mysql Connection failed");
    		System.out.println("Mysql Connection failed ");
    		return;
    	}catch(ClassNotFoundException e) {
    		write("Please provide Mysql JDBC Driver?");
			System.out.println("Please provide Mysql JDBC Driver?");
			return;
		}
    	
    	try{
    		createTableMigration(oracle_conn,mysql_conn);
    		createJunkCharFunction(oracle_conn);
    		moveOracleDataToMysql(oracle_conn,mysql_conn);
    		moveOracleBlobClobToMysql(oracle_conn,mysql_conn);
    		dropJunkCharFunction(oracle_conn);
    		dropRowIdInMysql(oracle_conn,mysql_conn);
    		addConstraints(oracle_conn,mysql_conn);
    		
    		if(errorStatements.size() !=0 ){
    			for(String tmp : errorStatements){
    				write("Errors : "+tmp);
    				System.out.println("Errors : "+tmp);
    			}
    		}
    		write("Oracle To Mysql Migration Completed....");
    		System.out.println("Oracle To Mysql Migration Completed....");
    	}catch(Exception e){
    		write("Error occurs please restart the application "+e.getMessage());
    		System.out.println("Error occurs please restart the application "+e.getMessage());
    		return;
    	}finally {
            if (oracle_conn != null) oracle_conn.close();
            if (mysql_conn != null) mysql_conn.close();
        }
	}
	
	public static void createTableMigration (Connection oracle_conn, Connection mysql_conn){
		try {
            Statement stmt=oracle_conn.createStatement();  
            ResultSet rs=stmt.executeQuery("SELECT table_name FROM user_tables order by table_name");  
            ArrayList<String> tableNames = new ArrayList<String>();
            ArrayList<String> genCreateQuery = new ArrayList<String>();
            while(rs.next()){  
            	if(rs.getString(1)!=null && !Arrays.asList(NOMIG_TABLE).contains(rs.getString(1).toUpperCase())){
	            	tableNames.add(rs.getString(1));
            		}
            	}  
            rs.close();
            stmt.close();
            for(String tn : tableNames){
            	if(tn!=null && !Arrays.asList(NOMIG_TABLE).contains(tn.toUpperCase())){
            	String sql = "SELECT A.COLUMN_NAME, DATA_TYPE, COLUMN_ID, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, DATA_DEFAULT, B.COMMENTS  FROM USER_TAB_COLS A,USER_COL_COMMENTS B WHERE A.TABLE_NAME = '"+tn+"' AND COLUMN_ID IS NOT NULL AND A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME =  B.COLUMN_NAME ORDER BY COLUMN_ID";
            	Statement cstmt = oracle_conn.createStatement();
            	ResultSet crs = cstmt.executeQuery(sql);
            	StringBuilder sb = new StringBuilder();
	            sb.append("CREATE TABLE `"+DB+"`.`"+tn+"` (");
            	while(crs.next()){ 
            			sb.append("`"+crs.getString(1)+"` "+(convertDatatype(crs.getString(2),crs.getInt(4),crs.getInt(5),crs.getInt(6)).trim())+" "+(getDefaultValue(crs.getString(2),crs.getString(7)).trim())+(crs.getString(8)!=null?" COMMENT '"+crs.getString(8)+"',":","));
            		 }
            	sb.append("`ROWID` VARCHAR(100) ) ENGINE=InnoDB" );
            	genCreateQuery.add(sb.toString());
            	cstmt.close();
            	crs.close();
            	}
            }
            int count =1;
            for(String gcq : genCreateQuery){
            		write("Creating table "+count+" \n "+gcq);
            		System.out.println("Creating table "+count+" \n "+gcq);	
            		Statement exeCreateStmt = mysql_conn.createStatement();
	            	exeCreateStmt.execute(gcq);
	            	count++;
            }
        }catch(Exception e){
        	System.out.println("error occurs while creating the table "+e.getMessage());
        	return;
        }
	} 
	
	public static void createJunkCharFunction (Connection oracle_conn) throws IOException{
		try {
			Statement stmt = oracle_conn.createStatement();
			String function = "create or replace FUNCTION JUNK_CHAR (STRING_DATA VARCHAR2)  "
				    +" RETURN VARCHAR2  "
				    +" IS"
				    +" nonewline VARCHAR(4000);"
				    +" clean VARCHAR(4000);"
				    +" BEGIN"
				    +" nonewline:=REPLACE(REPLACE(STRING_DATA, '\n', ''), '\r', '');"
				    +" clean:=TRIM(REPLACE(REPLACE(nonewline,'\','\\'),'''','\'''));"
				    +" IF LENGTH(clean) = 2"
				    +" THEN"
				    +" clean:=TRIM(REPLACE(clean,'\''',''));"
				    +" END IF;"
				    +" RETURN  clean;"
				    +" END;";
				    stmt.executeUpdate(function);
           
        }catch(Exception e){
        	write("error occurs while creating junk character function "+e.getMessage());
        	System.out.println("error occurs while creating junk character function "+e.getMessage());
        	return;
        }
	} 
	
	public static void moveOracleDataToMysql (Connection oracle_conn, Connection mysql_conn) throws IOException{
		try {
            Statement stmt=oracle_conn.createStatement();  
            ResultSet rs=stmt.executeQuery("SELECT table_name FROM user_tables order by table_name");  
            ArrayList<String> tableNames = new ArrayList<String>();
            ArrayList<String> genInsertQuery = new ArrayList<String>();
            while(rs.next()){  
            	if(rs.getString(1)!=null && !Arrays.asList(NOMIG_TABLE).contains(rs.getString(1).toUpperCase())){
	            	tableNames.add(rs.getString(1));
            		}
            	}  
            rs.close();
            stmt.close();
            for(String tn : tableNames){
            	if(tn!=null && !Arrays.asList(NOMIG_TABLE).contains(tn.toUpperCase())){
            	//calling createInsertScript method, it will return query to generate insert statements.
            	//String sql = "SELECT CREATE_INSERT_STATEMENT('"+tn+"') FROM DUAL";
            	String sql = createInsertScript(oracle_conn,tn.toUpperCase());
            	Statement genQuerStmt = oracle_conn.createStatement();
            	ResultSet genQuerSet = genQuerStmt.executeQuery(sql);
            	while(genQuerSet.next()){ 
            			genInsertQuery.add(genQuerSet.getClob(1).getSubString(1, (int)genQuerSet.getClob(1).length()));
            		}
            		genQuerStmt.close();
            		genQuerSet.close();
            	}
            }
            
            int count = 1;
            for(String giq : genInsertQuery){
            	long startTime = System.currentTimeMillis();
	            	Statement insQuerStmt = oracle_conn.createStatement();
	            	ResultSet insQuerSet = insQuerStmt.executeQuery(giq);
	            	Statement mysql_stmt = mysql_conn.createStatement();
	            	mysql_conn.setAutoCommit(false);
	            	final int batchSize = 1000;
	            	int batchcount = 0;
	            	while(insQuerSet.next()){ 
	            			mysql_stmt.addBatch(insQuerSet.getString(1));
	            			if(++batchcount % batchSize == 0) {
	            				mysql_stmt.executeBatch();
	            		    }
	            		}
	            	write("Inserting table "+count);
	            	System.out.println("Inserting table "+count);
	            	mysql_stmt.executeBatch();
	            	mysql_conn.commit();
	            	mysql_stmt.clearBatch();
	            	insQuerSet.close();
	            	insQuerStmt.close();
	            	mysql_stmt.close();
	            	long endTime = System.currentTimeMillis();
	            	write("It took " + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) + " Seconds to move the records for the table "+count);
	            	System.out.println("It took " + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) + " Seconds to move the records for the table "+count);
	            	System.gc();
	            	count++;
            }
           
        }catch(Exception e){
        	write("error occurs while migrating the data "+e.getMessage());
        	System.out.println("error occurs while migrating the data "+e.getMessage());
        	return;
        }
	} 
	
	public static String createInsertScript (Connection oracle_conn, String tableName) throws IOException{
		StringBuffer query = new StringBuffer();
		try {
			StringBuffer select = new StringBuffer();
			StringBuffer values = new StringBuffer();
			String columns=null;
			Statement stmt=oracle_conn.createStatement(); 
			ResultSet rs=stmt.executeQuery("SELECT COLUMN_NAME, DATA_TYPE, COLUMN_ID FROM USER_TAB_COLS WHERE TABLE_NAME = '"+tableName+"' AND COLUMN_ID IS NOT NULL ORDER BY COLUMN_ID");
			while(rs.next()){  
	            	System.out.println(rs.getString(1)+", "+rs.getString(2)+", "+rs.getInt(3));
	            	if(rs.getString(2)!= null && !rs.getString(2).toUpperCase().equals("BLOB") && !rs.getString(2).toUpperCase().equals("CLOB")){
	            		select.append((rs.getInt(3)==1?" ":",")+"`"+rs.getString(1)+"`");
	            		
	            		if(rs.getString(2).toUpperCase().contains("CHAR")){
	            			columns = "''''||JUNK_CHAR("+rs.getString(1)+")"+"||''''"; 
	            		}else if(rs.getString(2).toUpperCase().equals("DATE") || rs.getString(2).toUpperCase().equals("TIMESTAMP")){
	            			columns="TO_CHAR("+ rs.getString(1) +",'YYYYMMDDHHMISS')";
	            		}else{
	            			columns=rs.getString(1);
	            		}
	            		
	            		if(!rs.getString(2).toUpperCase().equals("LONG")){
	            			values.append(","+"'||DECODE("+rs.getString(1)+",NULL,'NULL',"+columns+")||'"); 
	            		}else{
	            			values.append(","+columns);
	            		}
	            	}
	        } 
			rs.close();
	        stmt.close();
			query.append("SELECT 'INSERT INTO "+tableName+" (`ROWID`,"+select+") VALUES ('''||ROWID||'''"+values+")' FROM "+tableName+" ORDER BY ROWID");
	        System.out.println(query.toString());
			
        }catch(Exception e){
        	write("error occurs while creating the insert script "+e.getMessage());
        	System.out.println("error occurs while creating the insert script "+e.getMessage());
        }
		return query.toString();
	} 
	
	public static void moveOracleBlobClobToMysql (Connection oracle_conn, Connection mysql_conn) throws IOException{
		try{
			Statement getTable_stmt=oracle_conn.createStatement(); 
		    ResultSet getTable_rs=getTable_stmt.executeQuery("SELECT a.TABLE_NAME, b.COLUMN_NAME, b.DATA_TYPE FROM USER_TABLES a, USER_TAB_COLS b WHERE a.TABLE_NAME = b.TABLE_NAME AND b.COLUMN_ID IS NOT NULL AND (TRIM(b.DATA_TYPE) = TRIM('BLOB') OR TRIM(b.DATA_TYPE) = TRIM('CLOB')) ORDER BY a.TABLE_NAME");  
		    mysql_conn.setAutoCommit(false);
		    while(getTable_rs.next()){ 
		    	String tableName = getTable_rs.getString(1);
			    String columnName = getTable_rs.getString(2);
			    String datatype = getTable_rs.getString(3);
			    String blob_clob_update = "update "+tableName+" set "+columnName+"=? where rowId=?";
			    Statement colstmt=oracle_conn.createStatement();  
			    ResultSet colrs=colstmt.executeQuery("SELECT ROWID RID, "+columnName+" FROM " +tableName+" ORDER BY ROWNUM");
			    PreparedStatement blob_clob_update_stmt = (PreparedStatement) mysql_conn.prepareStatement(blob_clob_update);
			    while(colrs.next()){ 
			    	String rowId = colrs.getString(1);
				    if(datatype!= null & datatype.equalsIgnoreCase("BLOB")){
				    	Blob blob = colrs.getBlob(2);
					    if(blob!=null){
					    	blob_clob_update_stmt.setBlob(1, blob);
					    	blob_clob_update_stmt.setString(2, rowId);
					    	blob_clob_update_stmt.addBatch();
					        }
				    }else if(datatype!= null & datatype.equalsIgnoreCase("CLOB")){
				    	Clob clob = colrs.getClob(2);
				        if(clob!=null){
				        	blob_clob_update_stmt.setClob(1, clob);
				        	blob_clob_update_stmt.setString(2, rowId);
				        	blob_clob_update_stmt.addBatch();
				        }
				   }
			   }
			   if(blob_clob_update_stmt!=null){
				   blob_clob_update_stmt.executeBatch();
			       mysql_conn.commit();
			       blob_clob_update_stmt.clearBatch();
			       write(columnName+" of "+tableName+" updated sucessfully ");
			       System.out.println(columnName+" of "+tableName+" updated sucessfully ");
			   }
		    }  
	        }catch(Exception e){
	        	write("Error Occurs while migrating blob and clob "+e.getMessage());
	        	System.out.println("Error Occurs while migrating blob and clob "+e.getMessage());
	        	return;
	        }
		} 
	
	public static void dropJunkCharFunction (Connection oracle_conn) throws IOException{
		try {
			Statement stmt = oracle_conn.createStatement();
			String function = "DROP FUNCTION JUNK_CHAR";
			stmt.executeUpdate(function);
           
        }catch(Exception e){
        	write("error occurs while droping junk character function "+e.getMessage());
        	System.out.println("error occurs while droping junk character function "+e.getMessage());
        	return;
        }
	} 
	
	public static void dropRowIdInMysql (Connection oracle_conn, Connection mysql_conn) throws IOException{
		try{
			Statement stmt=mysql_conn.createStatement();  
        	String all_table_sql = "SELECT table_name FROM information_schema.tables WHERE table_type = 'base table' AND table_schema='"+ORACLE_USER+"' order by table_name;";
        	ResultSet rs=stmt.executeQuery(all_table_sql);  
            ArrayList<String> tableNames = new ArrayList<String>();
            while(rs.next()){  
            	tableNames.add(rs.getString(1));
            }
            for(String tn : tableNames){
            	if(tn!=null && !Arrays.asList(NOMIG_TABLE).contains(tn.toUpperCase())){
            		String sql = "ALTER TABLE "+tn+" DROP COLUMN rowId";
            		System.out.println(sql);
            		write(sql);
            		Statement genQuerStmt = mysql_conn.createStatement();
            		genQuerStmt.execute(sql);
            	}
            }
        }catch(Exception e){
        	write("Error Occurs while drop row id in oracle database "+e.getMessage());
        	System.out.println("Error Occurs while drop row id in oracle database "+e.getMessage());
        	return;
        }
	}
	
	public static void addConstraints (Connection oracle_conn, Connection mysql_conn){
		try {
            Statement consstmt=oracle_conn.createStatement();  
            String conssql = "SELECT a.table_name table_name,a.constraint_name constraint_name, b.constraint_type constraint_type, listagg('`'||a.column_name||'`',',') within group( order by a.column_name ) column_name"
    				+" FROM USER_CONS_COLUMNS a, USER_CONSTRAINTS b"
    				+" WHERE a.constraint_name = b.constraint_name and b.constraint_type in ('P','U')"
    				+" GROUP BY a.table_name,a.constraint_name,b.constraint_type"
    				+" ORDER BY b.constraint_type,a.table_name,a.constraint_name";
            ResultSet crs=consstmt.executeQuery(conssql);  
           
        	while(crs.next()){
        		if(crs.getString(1)!=null && !Arrays.asList(NOMIG_TABLE).contains(crs.getString(1).toUpperCase())){
	        		String constraints = "";
	        		switch(crs.getString(3)){
	        			case "P":
	        				constraints = "ALTER TABLE `"+DB+"`.`"+crs.getString(1)+"` ADD CONSTRAINT "+crs.getString(2)+" PRIMARY KEY ("+crs.getString(4)+")";
	        				break;
	        			case "U":
	        				constraints = "ALTER TABLE `"+DB+"`.`"+crs.getString(1)+"` ADD CONSTRAINT "+crs.getString(2)+" UNIQUE ("+crs.getString(4)+")";
	        				break;
	        		}
	        		try{
	        		Statement stmt=mysql_conn.createStatement();
	        		write(constraints);
	        		System.out.println(constraints);
	        		stmt.execute(constraints);
	        		stmt.close();
	        		}catch(Exception e){
            			errorStatements.add(constraints);
            			System.out.println("Error occured while adding foriegn key");
            		}
        		}
        	} 
            crs.close();
            
            String frnsql = "SELECT CONS.CONSTRAINT_NAME, CONS.TABLE_NAME, COLS.COLUMN_NAME, CONS.R_CONSTRAINT_NAME, CONS_R.TABLE_NAME R_TABLE_NAME, COLS_R.COLUMN_NAME R_COLUMN_NAME"
            		+" FROM USER_CONSTRAINTS CONS"
            		+" LEFT JOIN USER_CONS_COLUMNS COLS ON COLS.CONSTRAINT_NAME = CONS.CONSTRAINT_NAME"
            		+" LEFT JOIN USER_CONSTRAINTS CONS_R ON CONS_R.CONSTRAINT_NAME = CONS.R_CONSTRAINT_NAME"
            		+" LEFT JOIN USER_CONS_COLUMNS COLS_R ON COLS_R.CONSTRAINT_NAME = CONS.R_CONSTRAINT_NAME"
            		+" WHERE CONS.CONSTRAINT_TYPE = 'R'"
            		+" ORDER BY CONS.TABLE_NAME, COLS.COLUMN_NAME";
            ResultSet frs=consstmt.executeQuery(frnsql);  
            while(frs.next()){
            	if(frs.getString(1)!=null && !Arrays.asList(NOMIG_TABLE).contains(frs.getString(1).toUpperCase())){
            	String foreignkey = "ALTER TABLE `"+DB+"`.`"+frs.getString(2)+"` ADD CONSTRAINT "+frs.getString(1)+" FOREIGN KEY ("+frs.getString(3)+") REFERENCES `"+DB+"`.`"+frs.getString(5)+"` ("+frs.getString(6)+")";
            	try{
            		Statement stmt=mysql_conn.createStatement();
            		write(foreignkey);
            		System.out.println(foreignkey);
            		stmt.execute(foreignkey);
            		stmt.close();
            	}catch(Exception e){
            			errorStatements.add(foreignkey);
            			System.out.println("Error occured while adding foriegn key");
            		}
            	}
            }
            frs.close();
            
            String nnsql = "SELECT a.table_name, a.column_name, c.data_type, c.data_precision, c. data_scale, c.data_length"
            		+" FROM USER_CONS_COLUMNS a, USER_CONSTRAINTS b, USER_TAB_COLS c"
            		+" WHERE a.constraint_name = b.constraint_name and b.constraint_type in ('C')"
            		+" and a.table_name = c.table_name and a.column_name = c.column_name"
            		+" ORDER BY b.constraint_type,a.table_name,a.constraint_name";
            ResultSet nnrs=consstmt.executeQuery(nnsql);  
            while(nnrs.next()){
            	if(nnrs.getString(1)!=null && !Arrays.asList(NOMIG_TABLE).contains(nnrs.getString(1).toUpperCase())){
	            	String notnull = "ALTER TABLE `"+DB+"`.`"+nnrs.getString(1)+"` MODIFY `"+nnrs.getString(2)+"` "+convertDatatype(nnrs.getString(3),nnrs.getInt(6),nnrs.getInt(4),nnrs.getInt(5))+" NOT NULL";
	            	try{
	            		Statement stmt=mysql_conn.createStatement();
	            		write(notnull);
	            		System.out.println(notnull);
	            		stmt.execute(notnull);
	            		stmt.close();
	            	}catch(Exception e){
	            		errorStatements.add(notnull);
            			System.out.println("Error occured while adding foriegn key");
	            	}
            	}
            }
            nnrs.close();
            consstmt.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println("Error occured while adding constraints "+e.getMessage());
	    	return;
	    }
	}
	
	public static String convertDatatype(String datatype,Integer dataLength, Integer dataPrecision, Integer dataScale){
		String mysqlDatatype = "";
		
		switch(datatype){
			case "NUMBER":
				if(dataScale == null && dataPrecision == null){
					mysqlDatatype = "DOUBLE";
				}else if(dataScale!=null && dataScale!=0)
					mysqlDatatype = "DECIMAL("+dataPrecision+","+dataScale+")";
				else{
					switch(dataPrecision){
						case 1: case 2:
							mysqlDatatype = "TINYINT("+dataPrecision+")";
							break;
						case 3: case 4:
							mysqlDatatype = "SMALLINT("+dataPrecision+")";
							break;
						case 5: case 6: case 7:
							mysqlDatatype = "MEDIUMINT("+dataPrecision+")";
							break;
						case 8: case 9:
							mysqlDatatype = "INT("+dataPrecision+")";
							break;
						case 10: case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19:
							mysqlDatatype = "BIGINT("+dataPrecision+")";
							break;
						default:
							mysqlDatatype = "DECIMAL("+38+","+dataScale+")";
							break;
					} 
				}
				break;
			case "VARCHAR2":
				mysqlDatatype = "VARCHAR("+(dataLength!=null?dataLength:"4000")+")";
				break;
			case "BLOB":
				mysqlDatatype = "LONGBLOB";
				break;
			case "CLOB":
				mysqlDatatype = "LONGTEXT";
				break;
			case "DATE":
				mysqlDatatype = "DATETIME";
				break;
			case "CHAR":
				mysqlDatatype = "CHAR("+dataLength+")";
				break;
			case "FLOAT":
				mysqlDatatype = "DOUBLE";
				break;
			default:
				mysqlDatatype = datatype;
				break;
		}
		if(datatype.toLowerCase().contains("timestamp"))
			mysqlDatatype = "TIMESTAMP";
		return mysqlDatatype;
	}
	
	public static String getDefaultValue(String datatype,String defaultValue){
		String defval = "";
		
		if(defaultValue != null && defaultValue.trim().equalsIgnoreCase("sysdate")){
			defval = " DEFAULT NOW()";
		}else if(defaultValue!=null){
			defval = " DEFAULT "+defaultValue;
		}
		
		return defval;
	}
		
}
