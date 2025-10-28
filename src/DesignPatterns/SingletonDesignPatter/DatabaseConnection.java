package DesignPatterns.SingletonDesignPatter;

/* Thread Safe implementation */
public class DatabaseConnection {
	private  static volatile DatabaseConnection instance;
	private DatabaseConnection(){
		System.out.println("Database Connection Established");
	}

	public static DatabaseConnection getInstance(){
		if(instance == null){
			synchronized (DatabaseConnection.class){
				if(instance == null) {
					instance = new DatabaseConnection();
				}
			}
		}
		return instance;
	}
	public void query(String sql) {
		System.out.println("Executing query: " + sql);
	}
}
