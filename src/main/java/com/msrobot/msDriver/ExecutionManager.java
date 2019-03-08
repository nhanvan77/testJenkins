package com.msrobot.msDriver;

public class ExecutionManager {
	private static final ThreadLocal<MsWebDriver> drivers = new ThreadLocal<MsWebDriver>();
	
	public static void setSession(MsWebDriver driver) {
		drivers.set(driver);
	}
	
	public static void removeSession() {
		drivers.remove();
	}
	
	public static MsWebDriver getSession(){
		if (drivers.get() == null) {
		  throw new IllegalStateException("Driver should have not been null.");
		}
		return drivers.get();
	}
}
