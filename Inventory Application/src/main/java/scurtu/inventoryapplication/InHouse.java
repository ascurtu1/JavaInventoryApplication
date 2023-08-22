package scurtu.inventoryapplication;

/** This is the InHouse class. */
public class InHouse extends Part {

    private int machineId;

    //Constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return  machineId returns the machine ID
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * @param machineId sets the machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
