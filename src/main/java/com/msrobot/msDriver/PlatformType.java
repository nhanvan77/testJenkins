package com.msrobot.msDriver;

enum PlatformType {
	ANDROID("android"),
	IOS("iOS");
	private String platformName;
	PlatformType(String platformName){
		this.platformName = platformName;
	}
	public String getPlatformName(){
		return platformName;
	}
}
