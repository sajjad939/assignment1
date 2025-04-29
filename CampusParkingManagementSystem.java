
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

    public int getYearsOfExperience() { return yearsOfExperience; }

    public void setYearsOfExperience(int years) { this.yearsOfExperience = years; }
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

    public String getLicensePlate() { 
        return licensePlate; 
    }

    public String getType() { 
        return type;
    }

    public Owner getOwner() {
        return owner; 
    }

    public void setOwner(Owner owner) {
        this.owner = owner; 
    }
    public Vehicle shallowClone() {
    return new Vehicle(this.licensePlate, this.type, this.owner); 
}

    }
    public Vehicle deepClone() {
    String newLicense = this.licensePlate;
    String newType = this.type;
    String ownerName = this.owner.getName();
    Owner newOwner = new Owner(ownerName);
    return new Vehicle(newLicense, newType, newOwner);
}


    @Override
    public String toString() {
        return licensePlate + " (" + type + "), Owner: " + owner.getName();
    }
}
class ParkingZone {
    private static int counter = 1;
    private final String zoneId;
    private Vehicle[] vehicles = new Vehicle[5];
    private int vehicleCount = 0;

    public ParkingZone() {
        this.zoneId = "A" + counter++;
    }

    public String getZoneId() { return zoneId; }

    public boolean addVehicle(Vehicle v) {
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].getLicensePlate().equals(v.getLicensePlate())) {
                System.out.println("Vehicle with same license already exists.");
                return false;
            }
        }
        if (vehicleCount < vehicles.length) {
            vehicles[vehicleCount++] = v;
            return true;
        } else {
            System.out.println("Zone full.");
            return false;
        }
    }

    public void displayVehicles() {
        System.out.println("Zone " + zoneId + ":");
        for (int i = 0; i < vehicleCount; i++) {
            System.out.println("  - " + vehicles[i]);
        }
    }
}
class ParkingSystem {
    private static ParkingSystem instance;
    private String campusName;
    private Supervisor supervisor;
    private ParkingZone[] zones = new ParkingZone[5];
    private PermitHolder[] permitHolders = new PermitHolder[10];
    private int zoneCount = 0;
    private int permitCount = 0;

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
        if (zoneCount < zones.length) {
            zones[zoneCount++] = zone;
        }
    }

    public void addPermitHolder(PermitHolder ph) {
        if (permitCount < permitHolders.length) {
            permitHolders[permitCount++] = ph;
        }
    }

    public void displaySystemInfo() {
        System.out.println("Campus: " + campusName);
        System.out.println("Supervisor: " + supervisor.getName() + "" + supervisor.getYearsOfExperience() + " years experience");
        System.out.println("Parking Zones:");
        for (int i = 0; i < zoneCount; i++) {
            zones[i].displayVehicles();
        }
        System.out.println("Permit Holders:");
        for (int i = 0; i < permitCount; i++) {
            System.out.println("  - " + permitHolders[i].getName() + " ID: " + permitHolders[i].getPermitId() + "");
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
