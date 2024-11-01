package ru.mipt.bit.platformer.data;

public class GameSettings {

    private static boolean SHOW_HEALTH_BAR = false;

    public static boolean showHealthBar() {
        return SHOW_HEALTH_BAR;
    }

    public static void toggleHealthBar() {
        SHOW_HEALTH_BAR = !SHOW_HEALTH_BAR;
    }
}
