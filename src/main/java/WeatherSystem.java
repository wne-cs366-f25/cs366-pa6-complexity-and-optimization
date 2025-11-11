import java.util.*;

/**
 * WeatherSystem represents dynamic weather conditions that affect movement speed.
 * This demonstrates why precomputed paths fail when game state changes dynamically.
 *
 * In real games, weather might change seasonally or randomly, making it impossible
 * to precompute all possible paths. This is a key limitation for modding - adding
 * weather to a game that uses precomputed paths would require major refactoring.
 *
 * @author CS366 Course Staff
 */
public class WeatherSystem {
    private Map<Integer, Weather> tileWeather;
    private Weather globalWeather;

    /**
     * Enum representing different weather conditions and their movement penalties.
     */
    public enum Weather {
        CLEAR(1.0, "Clear"),           // No penalty
        RAIN(1.5, "Rain"),              // 50% slower movement
        SNOW(2.0, "Snow"),              // 100% slower movement
        STORM(2.5, "Storm"),            // 150% slower movement
        FOG(1.3, "Fog"),                // 30% slower movement
        MUDDY(1.8, "Muddy Season");     // 80% slower movement (spring thaw)

        private final double multiplier;
        private final String displayName;

        Weather(double multiplier, String displayName) {
            this.multiplier = multiplier;
            this.displayName = displayName;
        }

        public double getMultiplier() {
            return multiplier;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Creates a weather system with clear weather everywhere.
     */
    public WeatherSystem() {
        this.tileWeather = new HashMap<>();
        this.globalWeather = Weather.CLEAR;
    }

    /**
     * Sets global weather affecting all tiles.
     *
     * @param weather Weather condition to apply globally
     */
    public void setGlobalWeather(Weather weather) {
        this.globalWeather = weather;
        this.tileWeather.clear(); // Clear local weather when setting global
    }

    /**
     * Sets weather for a specific tile (overrides global weather).
     *
     * @param tileId ID of the tile
     * @param weather Weather condition for this tile
     */
    public void setTileWeather(int tileId, Weather weather) {
        tileWeather.put(tileId, weather);
    }

    /**
     * Gets the weather multiplier for a specific tile.
     *
     * @param tileId ID of the tile
     * @return Movement speed multiplier due to weather
     */
    public double getWeatherMultiplier(int tileId) {
        // Check for local weather first, then use global
        Weather weather = tileWeather.getOrDefault(tileId, globalWeather);
        return weather.getMultiplier();
    }

    /**
     * Gets the weather condition for a specific tile.
     *
     * @param tileId ID of the tile
     * @return Weather condition
     */
    public Weather getWeather(int tileId) {
        return tileWeather.getOrDefault(tileId, globalWeather);
    }

    /**
     * Applies weather multiplier to an edge weight.
     * This simulates units moving slower in bad weather.
     *
     * @param baseWeight Original edge weight (terrain cost)
     * @param sourceTile Source tile ID
     * @param targetTile Target tile ID
     * @return Modified weight accounting for weather
     */
    public double applyWeatherToEdge(double baseWeight, int sourceTile, int targetTile) {
        // Average the weather effects of both tiles
        double sourceMultiplier = getWeatherMultiplier(sourceTile);
        double targetMultiplier = getWeatherMultiplier(targetTile);
        double averageMultiplier = (sourceMultiplier + targetMultiplier) / 2.0;

        return baseWeight * averageMultiplier;
    }

    /**
     * Simulates a weather front moving across the map.
     * This demonstrates dynamic weather that would invalidate precomputed paths.
     *
     * @param affectedTiles List of tile IDs affected by the weather front
     * @param weather Weather condition of the front
     */
    public void applyWeatherFront(List<Integer> affectedTiles, Weather weather) {
        for (int tileId : affectedTiles) {
            setTileWeather(tileId, weather);
        }
    }

    /**
     * Clears all weather (sets everything to clear).
     */
    public void clearAllWeather() {
        this.tileWeather.clear();
        this.globalWeather = Weather.CLEAR;
    }

    /**
     * Creates a random weather pattern for testing.
     *
     * @param map GameMap to apply weather to
     * @param severityFactor 0.0 (all clear) to 1.0 (maximum severity)
     */
    public void generateRandomWeather(GameMap map, double severityFactor) {
        Random rand = new Random();
        Weather[] weatherTypes = Weather.values();

        for (int nodeId : map.getNodeIds()) {
            if (rand.nextDouble() < severityFactor) {
                // Apply random weather with bias toward less severe
                double roll = rand.nextDouble();
                Weather weather;
                if (roll < 0.4) {
                    weather = Weather.CLEAR;
                } else if (roll < 0.6) {
                    weather = Weather.RAIN;
                } else if (roll < 0.75) {
                    weather = Weather.FOG;
                } else if (roll < 0.85) {
                    weather = Weather.MUDDY;
                } else if (roll < 0.95) {
                    weather = Weather.SNOW;
                } else {
                    weather = Weather.STORM;
                }
                setTileWeather(nodeId, weather);
            }
        }
    }

    /**
     * Gets a summary of current weather conditions.
     *
     * @return String describing weather state
     */
    public String getWeatherSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weather System:\n");
        sb.append("  Global: ").append(globalWeather.getDisplayName()).append("\n");

        if (!tileWeather.isEmpty()) {
            sb.append("  Local weather:\n");
            for (Map.Entry<Integer, Weather> entry : tileWeather.entrySet()) {
                sb.append("    Tile ").append(entry.getKey())
                  .append(": ").append(entry.getValue().getDisplayName())
                  .append(" (").append(entry.getValue().getMultiplier()).append("x)\n");
            }
        }

        return sb.toString();
    }

    /**
     * Demonstrates why weather breaks precomputation.
     * Returns true if weather has changed from clear.
     *
     * @return true if any non-clear weather exists
     */
    public boolean hasActiveWeather() {
        if (globalWeather != Weather.CLEAR) {
            return true;
        }

        for (Weather weather : tileWeather.values()) {
            if (weather != Weather.CLEAR) {
                return true;
            }
        }

        return false;
    }
}