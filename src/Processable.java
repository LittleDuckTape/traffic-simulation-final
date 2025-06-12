import java.util.List;

/**
 * Interface that ensures any object can be processed
 * (Useful for polymorphic behavior if you want to expand types later)
 */
public interface Processable {
    String getId();
    String getDirection();
    int getPriority();
    java.util.List<String> getPath();
}
