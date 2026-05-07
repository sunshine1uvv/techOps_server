package tech_ops.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operating_hours_log")
public class OperatingHoursLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "hours_added", nullable = false)
    private Integer hoursAdded;

    @Column(name = "log_date")
    private LocalDateTime logDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public User getUser() {
        return user;
    }

    public Integer getHoursAdded() {
        return hoursAdded;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHoursAdded(Integer hoursAdded) {
        this.hoursAdded = hoursAdded;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    @Override
    public String toString() {
        return "OperatingHoursLog{" +
                "id=" + id +
                ", equipment=" + equipment +
                ", user=" + user +
                ", hoursAdded=" + hoursAdded +
                ", logDate=" + logDate +
                '}';
    }
}