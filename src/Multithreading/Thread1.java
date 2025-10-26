package Multithreading;

public class Thread1 implements Runnable{
	private SharedResource fSharedResource;
	private String taskName;
	public Thread1(SharedResource sharedResource, String taskName){
		fSharedResource = sharedResource;
		this.taskName = taskName;
	}
	@Override
	public void run() {
		fSharedResource.readValue(taskName);
	}
}
