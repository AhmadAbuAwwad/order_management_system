package com.project.ordermanagementsystems.service;

import com.project.ordermanagementsystems.controller.dto.UserDTO;
import com.project.ordermanagementsystems.controller.request.AuthRequest;
import com.project.ordermanagementsystems.controller.request.RegisterRequest;
import com.project.ordermanagementsystems.exception.ResourceNotFoundException;
import com.project.ordermanagementsystems.exception.UserException;
import com.project.ordermanagementsystems.model.ERole;
import com.project.ordermanagementsystems.model.Role;
import com.project.ordermanagementsystems.model.User;
import com.project.ordermanagementsystems.repository.RoleRepository;
import com.project.ordermanagementsystems.repository.UserRepository;
import com.project.ordermanagementsystems.security.jwt.JwtUtils;
import com.project.ordermanagementsystems.security.services.UserDetailsImpl;
import lombok.SneakyThrows;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RoleRepository roleRepository;


    /**
     * @param user
     * @return
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * @return
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    /**
     * @param id
     * @param updatedUser
     * @return
     */
    public User updateUser(Long id, UserDTO updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserException("User not found for id: " + id);
        }
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setEmail(updatedUser.getLastname());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        return userRepository.save(user);
    }

    /**
     * @param id
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public String login(AuthRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails.getEmail());
        return jwt;
    }

    @SneakyThrows
    public void signup(RegisterRequest registerRequest) {
        validateUserInfo(registerRequest);

        // Create new user's account
        User user = addNewUser(registerRequest, ERole.ROLE_USER);
        userRepository.saveAndFlush(user);
    }

    @SneakyThrows
    public void addAdmin(RegisterRequest registerRequest) {
        validateUserInfo(registerRequest);

        User user = addNewUser(registerRequest, ERole.ROLE_ADMIN);
        userRepository.saveAndFlush(user);
    }

    private User addNewUser(RegisterRequest registerRequest, ERole roleType) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setDateOfBirth(registerRequest.getDateOfBirth());

        Set<Role> roles = new HashSet<>();

        Optional<Role> role = roleRepository.findByRoleName(roleType);
        if (!role.isPresent()) {
            throw new ResourceNotFoundException("No Role Found with name " + roleType.name());
        }

        roles.add(role.get());
        user.setRoles(roles);
        return user;
    }

    private void validateUserInfo(RegisterRequest registerRequest) throws AuthenticationException {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AuthenticationException("Username is taken");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AuthenticationException("Email is taken");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
