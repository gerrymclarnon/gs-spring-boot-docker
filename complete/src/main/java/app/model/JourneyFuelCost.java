package app.model;

public class JourneyFuelCost {
    private Double costInPence;
    private Double dutyInPence;
    private Double todayDifferenceInPence;

    public Double getCostInPence() {
        return costInPence;
    }

    public void setCostInPence(Double costInPence) {
        this.costInPence = costInPence;
    }

    public Double getDutyInPence() {
        return dutyInPence;
    }

    public void setDutyInPence(Double dutyInPence) {
        this.dutyInPence = dutyInPence;
    }

    public Double getTodayDifferenceInPence() {
        return todayDifferenceInPence;
    }

    public void setTodayDifferenceInPence(Double todayDifferenceInPence) {
        this.todayDifferenceInPence = todayDifferenceInPence;
    }
}
