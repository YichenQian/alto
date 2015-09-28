/**
 * @author chenguohai:chenguohai67@outlook.com, chenguohai@huawei.com
 *
 */

package org.altoclient.AltoClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public final class AlpsLogger
{
	private  String subFolderName;  
	private  Logger logger;  
	
	AlpsLogger(String className,Level level,String subFolderName){
		logger = Logger.getLogger(className);
		this.subFolderName=subFolderName;
		setLogingProperties(logger,level);
	}      
	/** 
     *get file name
     * @return file path and name
     */  
    private  String getLogName() {  
        StringBuffer logPath = new StringBuffer();  
        logPath.append(System.getProperty("user.home"));  
        logPath.append("\\"+subFolderName);  
        File file = new File(logPath.toString());  
        if (!file.exists())  
           file.mkdir();  
          
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        logPath.append("\\"+sdf.format(new Date())+".log");  
          
        return logPath.toString();  
    } 
    
  /** 
   * configure logger path and level 
   * @param logger 
   * @param level  
   * @throws SecurityException 
   * @throws IOException 
   */  
  public  void setLogingProperties(Logger logger,Level level) {  
      FileHandler fh;  
      try {  
          fh = new FileHandler(getLogName(),true);  
          logger.addHandler(fh);  
          logger.setLevel(level);  
          fh.setFormatter(new SimpleFormatter());  
          
          //logger.addHandler(new ConsoleHandler());//output to console  
      } catch (SecurityException e) {  
         logger.log(Level.SEVERE, "security error", e);  
      } catch (IOException e) {  
          logger.log(Level.SEVERE,"file operation failure", e);  
     }  
    }

    public void log(Level level, String msg) {
	  logger.log(level,msg);
  }
	      
  public static void main(String [] args) {  
	  AlpsLogger autoLogger=new  AlpsLogger("altoclient",Level.INFO,"alps");
     try {   
          autoLogger.log(Level.INFO, "ddddd");  
          autoLogger.log(Level.INFO, "eeeeee");  
          autoLogger.log(Level.INFO, "ffffff");  
          autoLogger.log(Level.INFO, "gggggg");  
          autoLogger.log(Level.INFO, "hhhhhh");  
      }catch (SecurityException e) {   
          e.printStackTrace();  
      }  catch(Exception e) 
      {        	 
          e.printStackTrace();  
      } 
   }

}