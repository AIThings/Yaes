package yaes.framework.resource.negotiation;

import yaes.framework.resource.ResourceAllocation;

public interface IPricingModel {
    int getPrice(ResourceAllocation ra);
}
