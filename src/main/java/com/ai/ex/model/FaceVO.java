package com.ai.ex.model;

public class FaceVO {
	private String gender;
	private double genderConf;
	private String age;
	private double ageConf;
	private String emotion;
	private double emotionConf;
	private String pose;
	private double poseConf;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getGenderConf() {
		return genderConf;
	}
	public void setGenderConf(double genderConf) {
		this.genderConf = genderConf;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public double getAgeConf() {
		return ageConf;
	}
	public void setAgeConf(double ageConf) {
		this.ageConf = ageConf;
	}
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	public double getEmotionConf() {
		return emotionConf;
	}
	public void setEmotionConf(double emotionConf) {
		this.emotionConf = emotionConf;
	}
	public String getPose() {
		return pose;
	}
	public void setPose(String pose) {
		this.pose = pose;
	}
	public double getPoseConf() {
		return poseConf;
	}
	public void setPoseConf(double poseConf) {
		this.poseConf = poseConf;
	}
	
	
}
