package tech_ops.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "equipment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Equipment parent;

    @NotNull(message = "Необходимо указать тип оборудования")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="type_id", nullable = false)
    private EquipmentType type;

    @Size(max = 255, message = "Название не должно превышать 255 символов")
    @Column(name = "name")
    private String name;

    @Pattern(
            regexp = "^ИТ\\d{5}$",
            message = "Неверный формат номера. Ожидается 'ИТ' и 5 цифр (например, ИТ00123)"
    )
    @Column(name = "inventory_number", unique = true, length = 7)
    private String inventoryNumber;

    @Size(max = 30, message = "Серийный номер не может превышать 30 символов")
    @Column(name = "serial_number", length = 30)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User employee;

    @NotBlank(message = "Местоположение обязательно для заполнения")
    @Size(max = 255, message = "Местоположение не должно превышать 255 символов")
    @Column(name = "location",nullable = false)
    private String location;

    @NotNull(message = "Категория обязательна")
    @Min(value = 1, message = "Минимальная категория — 1")
    @Max(value = 5, message = "Максимальная категория — 5")
    @Column(name = "category", nullable = false)
    private Integer category;

    public Long getId() {
        return id;
    }

    public Equipment getParent() {
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

    public User getEmployee() {
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

    public void setParent(Equipment parent) {
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

    public void setEmployee(User employee) {
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
}
