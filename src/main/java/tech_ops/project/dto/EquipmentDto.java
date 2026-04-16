package tech_ops.project.dto;

import tech_ops.project.entity.Equipment;
import tech_ops.project.entity.EquipmentType;

public class EquipmentDto {
    private Long id;

    private EquipmentDto parent;

    private EquipmentType type;

    private String name;

    private String inventoryNumber;

    private String serialNumber;

    private UserDto employee;

    private String location;

    private Integer category;

    public Long getId() {
        return id;
    }

    public EquipmentDto getParent() {
        return parent;
    }

    public EquipmentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public UserDto getEmployee() {
        return employee;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParent(EquipmentDto parent) {
        this.parent = parent;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setEmployee(UserDto employee) {
        this.employee = employee;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", parent=" + parent +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", employee='" + employee + '\'' +
                ", location='" + location + '\'' +
                ", category=" + category +
                '}';
    }

    public static EquipmentDto fromEquipment(Equipment equipment) {
        if (equipment == null) return null;
        EquipmentDto dto = new EquipmentDto();
        dto.setId(equipment.getId());
        if (equipment.getParent() != null) {
            dto.setParent(EquipmentDto.fromEquipment(equipment.getParent()));
        }
        dto.setType(equipment.getType());
        dto.setName(equipment.getName());
        dto.setInventoryNumber(equipment.getInventoryNumber());
        dto.setSerialNumber(equipment.getSerialNumber());
        dto.setEmployee(UserDto.fromUser(equipment.getEmployee()));
        dto.setLocation(equipment.getLocation());
        dto.setCategory(equipment.getCategory());
        return dto;
    }
}
