package ObjectPoolDesignPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBConnectionManager {
	private static DBConnectionManager staticManagerPool;
	private List<DBConnection> availableConnections;
	private List<DBConnection> inUseConnections;
	private Integer CURRENT_POOL_SIZE;
	private Integer MAX_POOL_SIZE;

	private DBConnectionManager(Integer maxPoolSize){
		this.MAX_POOL_SIZE = maxPoolSize;
		this.CURRENT_POOL_SIZE = 0;
		this.availableConnections = new ArrayList<>();
		this.inUseConnections = new ArrayList<>();
	}

	public static DBConnectionManager getInstance(){
		if(staticManagerPool == null){
			synchronized (DBConnectionManager.class) {
				if(staticManagerPool == null){
					staticManagerPool = new DBConnectionManager(4);
				}
			}
		}
		return staticManagerPool;
	}

	public synchronized DBConnection getConnection(){
		DBConnection dbConnection = null;
		if(availableConnections.isEmpty()){
			if(CURRENT_POOL_SIZE < MAX_POOL_SIZE){
				CURRENT_POOL_SIZE += 1;
				DBConnection dbConnection1 = new DBConnection(UUID.randomUUID().toString(),"localhost:3132", "admin", "password", "SampleDB");
				availableConnections.add(dbConnection1);
			} else {
				throw new RuntimeException("DB Connection limit reached please try again in some time!");
			}
		}
		dbConnection = availableConnections.get(0);
		availableConnections.remove((dbConnection));
		inUseConnections.add(dbConnection);
		return dbConnection;
	}

	public synchronized void returnConnectionToPool(DBConnection dbConnection){
		inUseConnections.remove(dbConnection);
		availableConnections.add(dbConnection);
		System.out.println("Successfully returned the connection to pool");
	}


}
