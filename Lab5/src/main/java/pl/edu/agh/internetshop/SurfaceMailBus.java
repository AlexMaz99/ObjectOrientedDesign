package pl.edu.agh.internetshop;


import pl.edu.agh.internetshop.Shipment.ShipmentMethod;

public interface SurfaceMailBus extends ShipmentMethod {
    boolean isSent(Object shipment);
}
