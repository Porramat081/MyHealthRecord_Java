package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class HealthRecord {
    private int healthRecordId;
    private String username; //user's username
    private int upperBP;
    private int lowerBP;
    private float weight;
    private float temperature;
    private String note;

    private Timestamp createdAt;
    private Timestamp editedAt;

    public HealthRecord(int healthRecordId ,String username , int upperBP , int lowerBP , float weight , float temperature ,Timestamp createdAt){
        this.healthRecordId = healthRecordId;
        this.username = username;
        this.upperBP = upperBP;
        this.lowerBP = lowerBP;
        this.weight = weight;
        this.temperature = temperature;

        this.createdAt = createdAt;
        this.editedAt = createdAt;
    }
    public HealthRecord(int healthRecordId, String username , int upperBP , int lowerBP , float weight , float temperature , String note , Timestamp createdAt , Timestamp editedAt){
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow  = LocalTime.now();

        this.healthRecordId = healthRecordId;
        this.username = username;
        this.upperBP = upperBP;
        this.lowerBP = lowerBP;
        this.weight = weight;
        this.temperature = temperature;

        this.createdAt = createdAt;
        this.editedAt = editedAt;

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

    public int getHealthRecordId() {
        return healthRecordId;
    }

    public void setHealthRecordId(int healthRecordId) {
        this.healthRecordId = healthRecordId;
    }

    public void setWeight(float weight) {
        this.weight = weight;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Timestamp editedAt) {
        this.editedAt = editedAt;
    }

    public float getWeight() {
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

    public String getBP(){
        return this.upperBP+" / "+this.lowerBP;
    }
}
