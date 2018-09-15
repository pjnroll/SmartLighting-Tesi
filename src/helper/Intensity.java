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

    /*INTENSITY_0(0),
    INTENSITY_1(9),
    INTENSITY_2(15),
    INTENSITY_3(24),
    INTENSITY_4(38),
    INTENSITY_5(62),
    INTENSITY_6(100);

    private int intensity;

    public int getIntensity() {
        return intensity;
    }

    public static int[] getIntensitiesInt() {
        Intensity[] intensities = values();
        int[] toRet = new int[7];
        for (int i = 0; i < 7; i++) {
            toRet[i] = intensities[i].intensity;
        }

        return toRet;
    }
    Intensity(int intensity) {
        this.intensity = intensity;
    }*/
}
