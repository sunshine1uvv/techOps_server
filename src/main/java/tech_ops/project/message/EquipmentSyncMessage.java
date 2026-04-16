package tech_ops.project.message;

import tech_ops.project.entity.Equipment;

import java.util.List;

public class EquipmentSyncMessage {
    private String action;
    private List<Equipment> equipmentList;

    public EquipmentSyncMessage() {}

    public EquipmentSyncMessage(String action, List<Equipment> equipmentList) {
        this.action = action;
        this.equipmentList = equipmentList;
    }

    public String getAction() {
        return action;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
