package yaes.framework.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ResourceSet {
    private final Collection<Resource>      resources;
    private final HashMap<String, Resource> resourceSet = new HashMap<>();

    public ResourceSet() {
        this.resources = new ArrayList<>();
        for (final Resource r : resources) {
            resourceSet.put(r.getId(), r);
        }
    }

    public void addResource(String id, int quantity) {
        final Resource r = new Resource(id, quantity);
        resourceSet.put(r.getId(), r);
        resources.add(r);
    }

    public int getResourceQuantity(String name) {
        final Resource r = resourceSet.get(name);
        if (r == null) {
            return 0;
        } else {
            return r.getQuantity();
        }
    }

    /**
     * @return Returns the resources.
     */
    public Collection<Resource> getResources() {
        return resources;
    }

    public String toSimpleString() {
        final StringBuffer buffer = new StringBuffer("ResourceSet: ");
        for (final Resource resource : resources) {
            buffer.append(resource.getId() + "=" + resource.getQuantity() + " ");
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        return toSimpleString();
    }
}
