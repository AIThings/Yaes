package yaes.framework.resource;

public class Resource {
    public static final String RESOURCETYPE_MEMORY    = "memory";
    public static final String RESOURCETYPE_PROCESSOR = "processor";
    private final String       id;
    private final int          quantity;

    /**
     * @param id
     * @param quantity
     */
    public Resource(String id, int quantity) {
        super();
        this.id = id;
        this.quantity = quantity;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Returns the quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "RESOURCE: " + id + " = " + quantity;
    }
}
