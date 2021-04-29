package com.rrain.springdomesticservicesapp.controllers;

import com.rrain.springdomesticservicesapp.model.User;
import com.rrain.springdomesticservicesapp.repo.UserRepository;
import com.rrain.springdomesticservicesapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
//@CrossOrigin(origins=”http://localhost:4200″)
//@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepository userRepo;
    @Autowired private AuthenticationManager authManager;
    @Autowired private MongoUserDetailsService userDetailsService;


    private record LoginData(String login, String password) {}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData loginData){
        System.out.println("begin login");

        Authentication authentication = null;
        try {
            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.login, loginData.password/*, userDetails.getAuthorities()*/));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("*", "Login (email) or password incorrect"));
        }

        System.out.println("end login: isAuthenticated(): "+authentication.isAuthenticated());
        System.out.println(loginData);

        return ResponseEntity.ok().build();
    }

    // TODO: 02.04.2021 посылать браузеру команду удалить куки сессии
    @RequestMapping(path = "/logout", method = {RequestMethod.GET, RequestMethod.DELETE})
    @ResponseStatus(HttpStatus.OK)
    public void logout(){
        System.out.println("logout");
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private record UserData(String id, String login, String name){}
    @GetMapping("/me")
    public ResponseEntity<?> me(Principal principal){
        if (principal==null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User user = userRepo.findByLogin(principal.getName());
        return ResponseEntity.ok(new UserData(user.getId(), user.getLogin(), user.getName()));
    }

    private record SignupData(String login, String password, String name){}
    @PutMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupData data){
        try {
            userDetailsService.createNewUser(new User(data.login(), data.password(), data.name()));
        } catch (UnacceptableLoginException e) {
            return ResponseEntity.badRequest().body(Map.of("login", e.getMessage()));
        } catch (LoginAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(Map.of("login", "Login already taken"));
        } catch (UnacceptableNameException e) {
            return ResponseEntity.badRequest().body(Map.of("name", e.getMessage()));
        } catch (UnacceptablePasswordException e) {
            return ResponseEntity.badRequest().body(Map.of("password", e.getMessage()));
        }

        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(data.login(), data.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }






    @GetMapping("/profile")
    // @ResponseStatus(value=HttpStatus.OK)
    public String profile(Principal principal){
        User user = userRepo.findByLogin(principal.getName());
        return "profile: " + user;
    }





    @GetMapping("/admin")
    public String admin(Principal principal){
        User user = userRepo.findByLogin(principal.getName());
        return "you are admin: " + user;
    }






/*
    // Получение и изменение свойства объекта сессии.
    // As we can see in this example,
    // we're incrementing counter on every hit to the endpoint and storing its value in a session attribute named count.
    @GetMapping("/session")
    public ResponseEntity<Integer> count(HttpSession session) {

        Integer counter = (Integer) session.getAttribute("count");

        if (counter == null) {
            counter = 1;
        } else {
            counter++;
        }

        session.setAttribute("count", counter);

        return ResponseEntity.ok(counter);
    }
*/




}
