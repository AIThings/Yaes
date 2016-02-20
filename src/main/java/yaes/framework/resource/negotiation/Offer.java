package yaes.framework.resource.negotiation;

import yaes.framework.resource.ResourceAllocation;

public class Offer {
    public enum States {
        ALLOCATED, OFFER
    };

    private final ResourceAllocation allocationDescriptor;
    private final int                price;
    private String                   provider;
    private String                   reservationId;
    private States                   state;

    public Offer(ResourceAllocation allocationDescriptor, int price,
            String reservationId) {
        this.allocationDescriptor = allocationDescriptor;
        this.price = price;
        this.reservationId = reservationId;
    }

    public ResourceAllocation getAllocationDescriptor() {
        return allocationDescriptor;
    }

    public int getPrice() {
        return price;
    }

    public String getProvider() {
        return allocationDescriptor.getProvider();
    }

    public String getReservationId() {
        return reservationId;
    }

    public States getState() {
        return state;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public String toString() {
        final String tmp = "Offer " + state + " provider: " + provider
                + " resource req: " + allocationDescriptor.toString()
                + " price: " + price;
        return tmp;
    }
}
