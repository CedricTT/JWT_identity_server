package com.example.JWT.model.mapper;

import com.example.JWT.model.dto.UserDTO;
import com.example.JWT.model.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<User, UserDTO> {

    @Override
    public User convertToEntity(UserDTO dto) {
        User user = new User();

        if (dto != null)
            BeanUtils.copyProperties(dto, user);

        return user;
    }

    @Override
    public UserDTO convertToDto(User entity) {
        UserDTO userDTO = new UserDTO();

        if(entity != null)
            BeanUtils.copyProperties(entity, userDTO);

        return userDTO;
    }
}
