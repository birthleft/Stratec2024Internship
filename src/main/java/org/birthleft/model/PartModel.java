package org.birthleft.model;

import java.util.Map;

public class PartModel {
    private Integer id;
    private String type;
    private Integer items;
    private Map<MachineModel, Integer> operations;

    public PartModel(Integer id, String type, Integer items, Map<MachineModel, Integer> operations) {
        this.id = id;
        this.type = type;
        this.items = items;
        this.operations = operations;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Map<MachineModel, Integer> getOperations() {
        return operations;
    }

    public void addOperation(MachineModel machine, Integer time) {
        operations.put(machine, time);
    }

    public void removeOperation(MachineModel machine) {
        operations.remove(machine);
    }


    @Override
    public String toString() {
        return "Part type: " + type + ", Number of items: " + items + " items" + ", Operations: " + operations.toString();
    }

}
