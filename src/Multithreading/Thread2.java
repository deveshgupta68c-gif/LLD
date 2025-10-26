package Multithreading;

public class Thread2 implements Runnable{
	private SharedResource fSharedResource;
	private String taskName;
	public Thread2(SharedResource sharedResource, String taskName){
		fSharedResource = sharedResource;
		this.taskName = taskName;
	}
	@Override
	public void run() {
		fSharedResource.readValue(taskName);
	}
}
