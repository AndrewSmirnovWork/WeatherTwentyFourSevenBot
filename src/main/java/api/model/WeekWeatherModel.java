package api.model;

public class WeekWeatherModel {
    private String timeZone;
    private double mornTemp;
    private double MaxTemp;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public double getMornTemp() {
        return mornTemp;
    }

    public void setMornTemp(double mornTemp) {
        this.mornTemp = mornTemp;
    }

    public double getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        MaxTemp = maxTemp;
    }
}
