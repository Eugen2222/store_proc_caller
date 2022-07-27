package store_proc_caller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class SPCaller {

	public SPCaller() {
		// TODO Auto-generated constructor stub
		
	}
	   static{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        System.setProperty("log4jFileName", dateFormat.format(new Date()));
	    }
	final static Logger logger =  LogManager. getLogger(SPCaller.class);
	public static void main(String[] args) {
		
	
		logger.info("===========================Start===============================");
		String[] storeProcNames=args;
		String argsname="";
		int l=storeProcNames.length;
		for (int i =0;i<l;i++) {
			argsname+='"'+storeProcNames[i]+'"';
			if(i<l-1) {
				argsname+=", ";
			}
			
		}
		logger.info("Store procedure names:"+argsname);
    	//Setup the logger
//        PropertyConfigurator.configure("config.properties"); 
        //Setup the config info
        Properties config = null;
        logger.info("Try to get config file");
      
        config = new ReadConfigProperties("config.properties").get();
        if (config == null) {
            logger.error("Failed to open config file.");
            System.exit(0);

        } else {
            logger.info("ok");
        }
        sssss
		
		
		
		// TODO Auto-generated method stub
		 PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
	        try {
				pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
		        pds.setURL(config.getProperty("DB_URL"));
		        pds.setUser(config.getProperty("DB_USER"));
		        pds.setPassword(config.getProperty("DB_PASSWORD"));
		        pds.setInitialPoolSize(5);
		     
			} catch (SQLException e) {
				// TODO Auto-generated catch block
	            logger.error("Cannot read the DB connect info.");    
	            logger.error(e.getMessage(),e);    
	
			}

	          // ³]©w CallableStatement 
	        	for(String n : storeProcNames) {
	    	        CallableStatement cs; 
	    	        try (Connection connection = (Connection) pds.getConnection()) {
	    	        logger.info("Trying to call store procedure: "+'"'+n+'"'+".");
	  	          cs = connection.prepareCall("{call "+n+"}"); 
		          // °õ¦æ CallableStatement 
		          cs.execute();
		          logger.info("Completed.");
	    	        } catch (SQLException e) { 
		                logger.error(e.getMessage(),e);  
	    	        }
	        	}
	        	logger.info("All tasks have been completed.");

	}

}
