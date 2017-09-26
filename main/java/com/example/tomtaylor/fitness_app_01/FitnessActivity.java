package com.example.tomtaylor.fitness_app_01;

/**
 * Created by tom.taylor on 25/09/2017.
 */

public class FitnessActivity {

    private long activityId;
    private String name;
    private String amountOfTime;
    private String day;

    public FitnessActivity(long activityId, String name, String amountOfTime, String day ){
        this.activityId = activityId;
        this.name = name;
        this.amountOfTime = amountOfTime;
        this.day = day;
    }

    public FitnessActivity (){}


    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmountOfTime() {
        return amountOfTime;
    }

    public void setAmountOfTime(String amountOfTime) {
        this.amountOfTime = amountOfTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", amountOfTime='" + amountOfTime + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

}
