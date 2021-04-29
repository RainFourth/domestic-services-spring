package com.rrain.springdomesticservicesapp.services;

import com.rrain.springdomesticservicesapp.model.Role;
import com.rrain.springdomesticservicesapp.model.User;
import com.rrain.springdomesticservicesapp.repo.RoleRepository;
import com.rrain.springdomesticservicesapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    //@Autowired private DelegatingPasswordEncoder encoder;
    private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        System.out.println(user);

        if (user == null) throw new UsernameNotFoundException(String.format("User with login \"%s\" not found", login));

        return toUserDetails(user);
    }

    private UserDetails toUserDetails(User user){
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getRole).toArray(String[]::new))
                .build();

        return userDetails;
    }

    public void createNewUser(User newUser) throws UnacceptableLoginException, LoginAlreadyExistsException, UnacceptableNameException, UnacceptablePasswordException {
        //if (newUser == null) throw new UnacceptableLoginException("Login must be at least 1 chars length");
        if (newUser.getLogin() == null || newUser.getLogin().isEmpty()) throw new UnacceptableLoginException("Login is required");
        if (userRepo.existsByLogin(newUser.getLogin())) throw new LoginAlreadyExistsException();
        if (newUser.getPassword() == null || newUser.getPassword().length() < 5) throw new UnacceptablePasswordException("Password must be at least 5 chars length");
        if (newUser.getName() == null) throw new UnacceptableNameException("Name is required");


        newUser = new User(
                newUser.getLogin(),
                "{bcrypt}"+ bCryptEncoder.encode(newUser.getPassword()),
                Set.of(roleRepo.findByRole(Role.USER)),
                newUser.getName(),
                true);

        try {
            newUser = userRepo.save(newUser);
        } catch (DuplicateKeyException e) {
            throw new LoginAlreadyExistsException();
        }

        //return newUser;
    }

}
