//package lk.ijse.vehiclesevice.util;
//
//
//import lk.ijse.userservice.dto.UserDTO;
//import lk.ijse.userservice.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//
//
//@Component
//@RequiredArgsConstructor
//public class DataTypeConvertor {
//    private final ModelMapper modelMapper;
//
//    public UserDTO getUserDTO(User user) {
//        return modelMapper.map(user, UserDTO.class);
//    }
//
//    public User getUser(UserDTO userDTO) {
//        return modelMapper.map(userDTO,User.class);
//    }
//}
