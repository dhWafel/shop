package pl.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.example.shop.domain.User;
import pl.example.shop.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getListUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user){
        return userService.creteUser(user);
    }

    @GetMapping("/{id}")        //wstawia zmienna do linku
    public User findUser(@PathVariable Long id) throws Exception {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @GetMapping
    public Page<User> userPage(Pageable pageable){
        return userService.userPage(pageable);
    }

    @PutMapping
    public User update(@RequestBody User user) throws Exception {
        return userService.update(user);
    }
}
