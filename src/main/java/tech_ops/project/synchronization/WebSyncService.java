package tech_ops.project.synchronization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tech_ops.project.dto.EquipmentDto;
import tech_ops.project.dto.RequestResponseDto;
import tech_ops.project.dto.UserDto;
import tech_ops.project.entity.Equipment;

import java.util.List;

@Service
public class WebSyncService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSyncService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendUserSync(String action, List<UserDto> users) {
        messagingTemplate.convertAndSend("/topic/users", new SyncMessage<>(action, users));
    }

    public void sendRequestSync(String action, List<RequestResponseDto> requests) {
        messagingTemplate.convertAndSend("/topic/requests", new SyncMessage<>(action, requests));
    }

    public void sendEquipmentSync(String action, List<EquipmentDto> equipment) {
        messagingTemplate.convertAndSend("/topic/equipment-updates", new SyncMessage<>(action, equipment));
    }
}
