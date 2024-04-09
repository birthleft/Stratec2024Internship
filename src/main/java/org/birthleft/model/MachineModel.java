package org.birthleft.model;

public class MachineModel {
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer cooldownTime;

    public MachineModel(Integer id, String name, Integer capacity, Integer cooldownTime) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.cooldownTime = cooldownTime;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(Integer cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    @Override
    public String toString() {
        return "Machine name: " + name + ", Machine capacity: " + capacity + " parts at a time" + ", Cooldown time: " + cooldownTime + " seconds after each part.";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MachineModel machineModel = (MachineModel) obj;
        return id.equals(machineModel.id);
    }
}
