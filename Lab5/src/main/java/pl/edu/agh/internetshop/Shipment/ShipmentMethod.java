package pl.edu.agh.internetshop.Shipment;


import pl.edu.agh.internetshop.Address;

public interface ShipmentMethod {
    boolean send(Object shipment, Address sender, Address recipient);
}
