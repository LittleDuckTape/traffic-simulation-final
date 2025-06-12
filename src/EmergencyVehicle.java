import java.util.List;

/**
 * Subclass of Vehicle
 * Always has priority (only matters in priority queue)
 * Used to simulate ambulances, fire trucks, etc
 */
public class EmergencyVehicle extends Vehicle {
    public EmergencyVehicle(String id, String direction, List<String> path) {
        super(id, direction, 1, path); // Priority 1 for emergencies
    }
}
