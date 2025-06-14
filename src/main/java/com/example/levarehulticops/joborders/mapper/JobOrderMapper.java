package com.example.levarehulticops.joborders.mapper;

import com.example.levarehulticops.users.dto.UserDto;
import com.example.levarehulticops.users.entity.User;
import com.example.levarehulticops.users.mapper.UserMapper;
import com.example.levarehulticops.items.dto.ItemReadDto;
import com.example.levarehulticops.items.mapper.ItemMapper;
import com.example.levarehulticops.joborders.dto.JobOrderCreateRequest;
import com.example.levarehulticops.joborders.dto.JobOrderReadDto;
import com.example.levarehulticops.joborders.dto.JobOrderStatusChangeDto;
import com.example.levarehulticops.joborders.dto.JobOrderUpdateRequest;
import com.example.levarehulticops.joborders.entity.JobOrder;
import org.springframework.stereotype.Component;

/**
 * Manual mapper for JobOrder ↔ DTOs.
 */
@Component
public class JobOrderMapper {

    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public JobOrderMapper(ItemMapper itemMapper,
                          UserMapper userMapper) {
        this.itemMapper = itemMapper;
        this.userMapper = userMapper;
    }

    /**
     * Entity → Read DTO
     */
    public JobOrderReadDto toReadDto(JobOrder jo) {
        ItemReadDto itemDto = itemMapper.toReadDto(jo.getItem());
        UserDto respDto = userMapper.toDto(jo.getResponsibleUser());

        return new JobOrderReadDto(
                jo.getId(),
                jo.getWorkOrder().getId(),
                itemDto,
                jo.getStatus(),
                respDto,
                jo.getComments(),
                jo.getVersion()
        );
    }

    /**
     * Create DTO → new Entity
     */
    public JobOrder toEntity(JobOrderCreateRequest dto) {
        JobOrder jo = new JobOrder();
        // relationships (workOrder, item, responsibleEmployee) set in service
        jo.setComments(dto.comments());
        // initial status set in service or defaults to CREATED
        return jo;
    }

    /**
     * Update DTO → existing Entity
     */
    public void updateEntityFromDto(JobOrderUpdateRequest dto, JobOrder jo) {
        // allowed: responsibleEmployee and comments (others in service)
        if (dto.responsibleEmployeeId() != null) {
            User e = new User();
            e.setId(dto.responsibleEmployeeId());
            jo.setResponsibleUser(e);
        }
        if (dto.comments() != null) {
            jo.setComments(dto.comments());
        }
    }

    /**
     * Status-change DTO → existing Entity
     */
    public void updateStatusFromDto(JobOrderStatusChangeDto dto, JobOrder jo) {
        jo.setStatus(dto.status());
        // version check and save handled in service
    }
}