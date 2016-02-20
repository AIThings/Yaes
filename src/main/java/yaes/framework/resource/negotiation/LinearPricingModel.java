package yaes.framework.resource.negotiation;

import java.util.HashMap;
import java.util.Map;

import yaes.framework.resource.Resource;
import yaes.framework.resource.ResourceAllocation;

public class LinearPricingModel implements IPricingModel {
    private final Map<String, Double> prices = new HashMap<>();

    public LinearPricingModel() {
    }

    public void addUnitPrice(String name, double value) {
        prices.put(name, value);
    }

    @Override
    public int getPrice(ResourceAllocation ra) {
        double price = 0;
        for (final Resource r : ra.getResourceSet().getResources()) {
            final Double unitPrice = prices.get(r.getId());
            if (unitPrice != null) {
                price = price + unitPrice * r.getQuantity() * ra.getDuration();
            }
        }
        return (int) price;
    }
}
