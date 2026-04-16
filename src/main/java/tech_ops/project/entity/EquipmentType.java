package tech_ops.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "equipment_type")
@JsonIgnoreProperties({"parent", "hibernateLazyInitializer", "handler"})
public class EquipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EquipmentType parent;

    @NotBlank(message = "Название типа оборудования не может быть пустым")
    @Size(max = 255, message = "Название слишком длинное (макс. 255 символов)")
    @Column(nullable = false, length = 255)
    private String name;

    @NotNull(message = "Уровень вложенности обязателен")
    @Min(value = 1, message = "Минимальный уровень — 1")
    @Max(value = 6, message = "Максимальный уровень — 6")
    @Column(nullable = false)
    private Integer level;

    @NotBlank(message = "Короткий код обязателен")
    @Size(min = 1, max = 4, message = "Код должен содержать от 1 до 4 символов")
    @Column(nullable = false, length = 4)
    private String code;

    @NotBlank(message = "Полный код обязателен")
    @Size(max = 50, message = "Полный код не должен превышать 50 символов")
    @Column(name = "full_code", nullable = false, unique = true, length = 50)
    private String fullCode;

    public Long getId() {
        return id;
    }

    public EquipmentType getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Integer getLevel() {
        return level;
    }

    public String getCode() {
        return code;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParent(EquipmentType parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    //    @Override
//    public String toString() {
//        return "EquipmentType{" +
//                "id=" + id +
//                ", parent=" + parent +
//                ", name='" + name + '\'' +
//                ", level=" + level +
//                ", code='" + code + '\'' +
//                ", fullCode='" + fullCode + '\'' +
//                '}';
//    }
}