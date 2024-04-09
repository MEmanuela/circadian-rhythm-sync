package com.example.circadianrhythmsync.services;

import com.example.circadianrhythmsync.dtos.UserDTO;
import com.example.circadianrhythmsync.entities.Role;
import com.example.circadianrhythmsync.entities.User;
import com.example.circadianrhythmsync.exceptions.ResourceNotFoundException;
import com.example.circadianrhythmsync.mappers.UserMapper;
import com.example.circadianrhythmsync.repositories.RoleRepository;
import com.example.circadianrhythmsync.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = UserMapper.INSTANCE.toListOfUserDTOs(users);
        return userDTOList;
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException(messageSource.getMessage("exception.resourceNotFound.noSuchUser", null, Locale.ENGLISH));
        }
        return UserMapper.INSTANCE.toUserDTO(user.get());
    }

    public String generateDefaultUsername(String name) {
        int cnt = userRepository.countByUsername(name);
        if (cnt > 0) {
            return name.toLowerCase().replace(" ", "_").concat(String.valueOf(cnt));
        }
        return name.toLowerCase().replace(" ", "_");
    }

    public String generateDefaultPassword(String n) {
        String pcn = "123456789";
        String name = n.substring(0, 1).toUpperCase() +
                n.substring(1).toLowerCase().replace(" ", "");
        return name.substring(name.length() -3, name.length())
                + pcn.substring(pcn.length()/2-1, pcn.length()/2 + 2)
                + name.substring(0, 3) + "#";
    }

    @Transactional
    public void createUser(Long roleId, UserDTO userDTO) {
        Optional<Role> byId = roleRepository.findById(roleId);
        if (!byId.isPresent()) {
            throw new ResourceNotFoundException(messageSource.getMessage("exception.resourceNotFound.noSuchRole", null, Locale.ENGLISH));
        }
        Role role = byId.get();
        // tie Role to User
        String username = generateDefaultUsername(userDTO.getUsername());
        String password = generateDefaultPassword(userDTO.getUsername());

        User user = UserMapper.INSTANCE.fromUserDTO(userDTO);
        user.setRole(role);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void updateUserPassword(Long userId, String password) {
        User user = userRepository.findById(userId).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByRole(Long roleId) {
        return userRepository.findByRole_RoleId(roleId);
    }
}
