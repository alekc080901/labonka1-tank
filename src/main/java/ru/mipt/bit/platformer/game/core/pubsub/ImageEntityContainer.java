package ru.mipt.bit.platformer.game.core.pubsub;

import ru.mipt.bit.platformer.game.core.Coordinates;

public class ImageEntityContainer implements EntityContainer {
    private final Coordinates coordinates;
    private final int zIndex;
    private final String entityImagePath;

    public ImageEntityContainer(Coordinates coordinates, String entityImagePath, int zIndex) {
        this.coordinates = coordinates;
        this.zIndex = zIndex;
        this.entityImagePath = entityImagePath;
    }

    public int getzIndex() {
        return zIndex;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getImagePath() {
        return entityImagePath;
    }
}
