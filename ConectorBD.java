import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBD {
	private static Connection connect = null;
	
	public synchronized static Connection createConnection() throws SQLException{
        String bd_name = "web_crawler";
		String url = "jdbc:mysql://localhost:3306/"+bd_name+"?useTimezone=true&serverTimezone=UTC"; //Nome da base de dados
        String user = "root"; //nome do usuário do MySQL
        String password = ""; //senha do MySQL
                       
        Connection conexao = null;
        conexao = DriverManager.getConnection(url, user, password);
         
        return conexao;
    }

	public static Connection getConnection() {
		return connect;
	}
	
}
