package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class HealthRecord {
    private String username; //user's username
    private int upperBP;
    private int lowerBP;
    private int weight;
    private float temperature;
    private String note;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private LocalDate editedDate;
    private LocalTime editedTime;

    public HealthRecord(String username , int upperBP , int lowerBP , int weight , float temperature){
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow  = LocalTime.now();

        this.username = username;
        this.upperBP = upperBP;
        this.lowerBP = lowerBP;
        this.weight = weight;
        this.temperature = temperature;
        this.createdDate = dateNow;
        this.createdTime = timeNow;
        this.editedDate = dateNow;
        this.editedTime = timeNow;

        this.note = "";
    }
    public HealthRecord(String username , int upperBP , int lowerBP , int weight , float temperature , String note){
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow  = LocalTime.now();

        this.username = username;
        this.upperBP = upperBP;
        this.lowerBP = lowerBP;
        this.weight = weight;
        this.temperature = temperature;
        this.createdDate = dateNow;
        this.createdTime = timeNow;
        this.editedDate = dateNow;
        this.editedTime = timeNow;

        this.note = note;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUpperBP() {
        return upperBP;
    }

    public void setUpperBP(int upperBP) {
        this.upperBP = upperBP;
    }

    public int getLowerBP() {
        return lowerBP;
    }

    public void setLowerBP(int lowerBP) {
        this.lowerBP = lowerBP;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(LocalDate editedDate) {
        this.editedDate = editedDate;
    }

    public LocalTime getEditedTime() {
        return editedTime;
    }

    public void setEditedTime(LocalTime editedTime) {
        this.editedTime = editedTime;
    }

    public String getBP(){
        return this.upperBP+" / "+this.lowerBP;
    }
}
