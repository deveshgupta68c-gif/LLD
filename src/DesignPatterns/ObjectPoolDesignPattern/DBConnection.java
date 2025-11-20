package DesignPatterns.ObjectPoolDesignPattern;

public class DBConnection {
	private String id;
	private String connectionUrl;
	private String username;
	private String password;
	private String dBname;

	public DBConnection(String id,String connectionUrl, String username, String password, String dBname) {
		this.id = id;
		this.connectionUrl = connectionUrl;
		this.username = username;
		this.password = password;
		this.dBname = dBname;
	}

	public void showConnectionDetails(){
		System.out.println("Connection Details : \nConnection URL : " + connectionUrl + "\nUsername : " + username + "\nDatabase Name : " + dBname + "\nUUID : " + id + "\n");
	}

	@Override
	public String toString() {
		return "DBConnection [id=" + id + ", connectionUrl=" + connectionUrl + ", username=" + username + ", password="
				+ password + ", dBname=" + dBname + "]";
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DBConnection)) {
			return false;
		}
		DBConnection dbConnection = (DBConnection) obj;
		return this.id.equals(dbConnection.id);
	}
}
