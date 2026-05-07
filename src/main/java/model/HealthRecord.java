package model;

import java.sql.Timestamp;

public class HealthRecord {
    private int healthRecordId;
    private String username; //user's username
    private String bloodPressure;
    private float weight;
    private float temperature;
    private String note;

    private Timestamp createdAt;
    private Timestamp editedAt;

    public HealthRecord(int healthRecordId, String username , String bloodPressure, float weight , float temperature , String note , Timestamp createdAt , Timestamp editedAt){

        this.healthRecordId = healthRecordId;
        this.username = username;
        this.bloodPressure = bloodPressure;
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

    public String getBloodPressure(){
        return this.bloodPressure;
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

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
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

}
