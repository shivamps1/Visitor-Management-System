package com.example.demo.service;


import com.example.demo.domain.Address;
import com.example.demo.domain.Flat;
import com.example.demo.domain.User;
import com.example.demo.model.UserDTO;
import com.example.demo.model.UserStatus;
import com.example.demo.repos.AddressRepository;
import com.example.demo.repos.FlatRepository;
import com.example.demo.repos.UserRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FlatRepository flatRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map((user) -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void markInactive(final Long id){
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        userDTO.setFlat(user.getFlat() == null ? null : user.getFlat().getId());
        userDTO.setAddress(user.getAddress() == null ? null : user.getAddress().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setPassword(passwordEncoder.encode("1234"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());
        final Flat flat = userDTO.getFlat() == null ? null : flatRepository.findById(userDTO.getFlat())
                .orElseThrow(() -> new NotFoundException("flat not found"));
        user.setFlat(flat);
        final Address address = userDTO.getAddress() == null ? null : addressRepository.findById(userDTO.getAddress())
                .orElseThrow(() -> new NotFoundException("address not found"));
        user.setAddress(address);
        return user;
    }

    public void createInBulk(final List<UserDTO> userDTOList){
        List<User> userList = new ArrayList<>();
        for(UserDTO userDTO : userDTOList){
            final User user = new User();
            mapToEntity(userDTO, user);
            userList.add(user);
        }
        userRepository.saveAll(userList);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
