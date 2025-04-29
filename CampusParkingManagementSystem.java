import java.util.*;

abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}


class Supervisor extends Person {
    private int yearsOfExperience;

    public Supervisor(String name, int years) {
        super(name);
        this.yearsOfExperience = years;
    }

    public int getYearsOfExperience() {
         return yearsOfExperience; 
        }

    public void setYearsOfExperience(int years) {
         this.yearsOfExperience = years;
         }
}

class Owner extends Person {
    public Owner(String name) {
        super(name);
    }
}

class PermitHolder extends Person {
    private static int counter = 1;
    private final String permitId;

    public PermitHolder(String name) {
        super(name);
        this.permitId = "PID" + (counter++);
    }

    public String getPermitId() {
        return permitId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PermitHolder) {
            return this.permitId.equals(((PermitHolder) obj).getPermitId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return permitId.hashCode();
    }
}


class Vehicle implements Cloneable {
    private String licensePlate;
    private String type;
    private Owner owner;

    public Vehicle(String licensePlate, String type, Owner owner) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.owner = owner;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getType() { return type; }

    public Owner getOwner() { return owner; }

    public void setOwner(Owner owner) { this.owner = owner; }

 
    public Vehicle shallowClone() {
        try {
            return (Vehicle) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

   
    public Vehicle deepClone() {
        return new Vehicle(this.licensePlate, this.type, new Owner(this.owner.getName()));
    }

    @Override
    public String toString() {
        return licensePlate + " (" + type + "), Owner: " + owner.getName();
    }
}


class ParkingZone {
    private static int zoneCounter = 1;
    private final String zoneId;
    private Vehicle[] vehicles = new Vehicle[5];

    public ParkingZone() {
        this.zoneId = "A" + zoneCounter++;
    }

    public String getZoneId() { return zoneId; }

    public boolean addVehicle(Vehicle v) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle != null && vehicle.getLicensePlate().equals(v.getLicensePlate())) {
                System.out.println("Vehicle with same license already exists.");
                return false;
            }
        }
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i] == null) {
                vehicles[i] = v;
                return true;
            }
        }
        System.out.println("Zone full.");
        return false;
    }

    public void displayVehicles() {
        System.out.println("Zone " + zoneId + ":");
        for (Vehicle v : vehicles) {
            if (v != null) System.out.println("  - " + v);
        }
    }
}


class ParkingSystem {
    private static ParkingSystem instance;
    private String campusName;
    private Supervisor supervisor;
    private List<ParkingZone> zones = new ArrayList<>();
    private List<PermitHolder> permitHolders = new ArrayList<>();

    // Private constructor
    private ParkingSystem(String campusName, Supervisor supervisor) {
        this.campusName = campusName;
        this.supervisor = supervisor;
    }

   
    public static ParkingSystem getInstance(String campusName, Supervisor supervisor) {
        if (instance == null) {
            instance = new ParkingSystem(campusName, supervisor);
        }
        return instance;
    }

    public void addZone(ParkingZone zone) {
        zones.add(zone);
    }

    public void addPermitHolder(PermitHolder ph) {
        permitHolders.add(ph);
    }

    public void displaySystemInfo() {
        System.out.println("Campus: " + campusName);
        System.out.println("Supervisor: " + supervisor.getName() + " (" + supervisor.getYearsOfExperience() + " years experience)");
        System.out.println("Parking Zones:");
        for (ParkingZone zone : zones) {
            zone.displayVehicles();
        }
        System.out.println("Permit Holders:");
        for (PermitHolder ph : permitHolders) {
            System.out.println("  - " + ph.getName() + " (ID: " + ph.getPermitId() + ")");
        }
    }
}
public class CampusParkingManagementSystem {
    public static void main(String[] args) {
      
        Supervisor supervisor = new Supervisor("Dr. Alice", 15);
        ParkingSystem system = ParkingSystem.getInstance("Greenfield Campus", supervisor);

        
        ParkingZone zone1 = new ParkingZone();
        ParkingZone zone2 = new ParkingZone();
        system.addZone(zone1);
        system.addZone(zone2);

        
        PermitHolder ph1 = new PermitHolder("John Doe");
        PermitHolder ph2 = new PermitHolder("Jane Smith");
        system.addPermitHolder(ph1);
        system.addPermitHolder(ph2);
        Owner owner1 = new Owner("Mark");
        Owner owner2 = new Owner("Lisa");

        Vehicle v1 = new Vehicle("ABC123", "Car", owner1);
        Vehicle v2 = new Vehicle("XYZ789", "Bike", owner2);

        
        zone1.addVehicle(v1);
        zone1.addVehicle(v2);
        Vehicle shallowCopy = v1.shallowClone();
        Vehicle deepCopy = v1.deepClone();
        owner1.setName("Changed Mark");

        System.out.println("\n--- Clone Demonstration ---");
        System.out.println("Original: " + v1);
        System.out.println("Shallow Copy: " + shallowCopy);
        System.out.println("Deep Copy: " + deepCopy);
        System.out.println("\n--- System Info ---");
        system.displaySystemInfo();
    }
}
