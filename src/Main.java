
import java.sql.SQLException;

import com.HelpManager.uche.MainFrame;
public class Main {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		
		
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                
	            	
	            	new MainFrame("Help Manager");
	            }
	        }); 
		
	}
}
