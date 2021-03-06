package quizWebsite;
// listens for BOTH context AND session. 
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Application Lifecycle Listener implementation class listener
 *
 */
@WebListener
public class listener implements HttpSessionListener, ServletContextListener {

    /**
     * Default constructor. 
     */
    public listener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     * setup global data for all users
     */
    public void contextInitialized(ServletContextEvent event) {
    	System.out.println("context initialized");
        // set up a connection to the mysql database. 
    	// deposit in context attribute Connection. 
   	 ServletContext context=event.getServletContext();

    	try{
        	Class.forName("com.mysql.jdbc.Driver");
        	java.sql.Connection con = DriverManager.getConnection(
        		"jdbc:mysql://" +  myDBinfo.MYSQL_DATABASE_SERVER, 
        		myDBinfo.MYSQL_USERNAME, myDBinfo.MYSQL_PASSWORD);
        	java.sql.Statement stmt = con.createStatement();
        	stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
             context.setAttribute(Constants.context_Connection, con);
             System.out.println("testing");
            
    	}catch (Exception e){
        	e.printStackTrace();
        }
       // increment this each time you add a new question, so that it can be unique for each question.
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     * setup local data for single user 
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    	try {
			((Connection) event.getServletContext().getAttribute(Constants.context_Connection)).close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
}
