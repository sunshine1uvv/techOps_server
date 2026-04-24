package tech_ops.project.dto;

import jakarta.validation.constraints.*;
import tech_ops.project.entity.Equipment;
import tech_ops.project.entity.EquipmentType;

public class EquipmentDto {
    private Long id;

    private EquipmentDto parent;

    @NotNull(message = "Необходимо указать тип оборудования")
    private EquipmentType type;

    @Size(max = 255, message = "Название не должно превышать 255 символов")
    private String name;

    @Pattern(
            regexp = "^ИТ\\d{5}$",
            message = "Неверный формат номера. Ожидается 'ИТ' и 5 цифр (например, ИТ00123)"
    )
    private String inventoryNumber;

    @Size(max = 30, message = "Серийный номер не может превышать 30 символов")
    private String serialNumber;

    private UserDto employee;

    @NotBlank(message = "Местоположение обязательно для заполнения")
    @Size(max = 255, message = "Местоположение не должно превышать 255 символов")
    private String location;

    @NotNull(message = "Категория обязательна")
    @Min(value = 1, message = "Минимальная категория — 1")
    @Max(value = 5, message = "Максимальная категория — 5")
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
