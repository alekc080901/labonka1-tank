package ru.mipt.bit.platformer.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@Component
public class GameSettings {

    @Value("${game.settings.show-health}")
    private boolean showHealthBar = false;

    public boolean showHealthBar() {
        return showHealthBar;
    }

    public void toggleHealthBar() {
        showHealthBar = !showHealthBar;
    }
}
