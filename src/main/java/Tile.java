/**
 * Tile represents a province or region on the game map.
 * In grand strategy games, tiles can represent anything from small provinces
 * to vast territories depending on their strategic importance.
 *
 * @author CS366 Course Staff
 */
public class Tile {
    private final int id;
    private final String name;
    private final TerrainType terrain;

    /**
     * Enum representing different terrain types with their movement costs.
     */
    public enum TerrainType {
        PLAINS(1.0, "Plains"),           // Easy movement
        FOREST(1.5, "Forest"),            // Moderate difficulty
        HILLS(2.0, "Hills"),              // Increased difficulty
        MOUNTAINS(3.0, "Mountains"),      // Very difficult
        DESERT(2.5, "Desert"),            // Difficult terrain
        SWAMP(2.0, "Swamp"),              // Difficult terrain
        TUNDRA(1.8, "Tundra"),            // Cold, difficult
        WATER(1.0, "Water"),              // For naval units
        URBAN(0.8, "Urban");              // Roads and infrastructure

        private final double movementCost;
        private final String displayName;

        TerrainType(double movementCost, String displayName) {
            this.movementCost = movementCost;
            this.displayName = displayName;
        }

        public double getMovementCost() {
            return movementCost;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Creates a new tile.
     *
     * @param id Unique identifier for the tile
     * @param name Name of the province/region
     * @param terrain Terrain type affecting movement
     */
    public Tile(int id, String name, TerrainType terrain) {
        this.id = id;
        this.name = name;
        this.terrain = terrain;
    }

    /**
     * Creates a new tile with default plains terrain.
     *
     * @param id Unique identifier for the tile
     * @param name Name of the province/region
     */
    public Tile(int id, String name) {
        this(id, name, TerrainType.PLAINS);
    }

    /**
     * Gets the tile ID.
     *
     * @return Tile ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the tile name.
     *
     * @return Tile name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the terrain type.
     *
     * @return Terrain type
     */
    public TerrainType getTerrain() {
        return terrain;
    }

    /**
     * Gets the base movement cost for this tile.
     *
     * @return Movement cost multiplier
     */
    public double getMovementCost() {
        return terrain.getMovementCost();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tile tile = (Tile) obj;
        return id == tile.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Tile[%d: %s (%s)]", id, name, terrain.getDisplayName());
    }
}