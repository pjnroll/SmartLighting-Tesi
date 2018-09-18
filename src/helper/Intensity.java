package helper;

public enum Intensity {
    INTENSITY_20(20), INTENSITY_100(100);

    private int intensity;

    Intensity(int intensity) {
        this.intensity = intensity;
    }

    public int getIntensity() {
        return intensity;
    }
}
