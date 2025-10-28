package DesignPatterns.SingletonDesignPatter;

public class DatabaseConnectionLazy {
	private static DatabaseConnectionLazy instance;
	private DatabaseConnectionLazy(){
		System.out.println("Database Connection Established");
	}

	public  static DatabaseConnectionLazy getInstance(){
		if(instance == null){
			instance = new DatabaseConnectionLazy();
		}
		return instance;
	}

	public void query(String sql) {
		System.out.println("Executing query: " + sql);
	}
}
