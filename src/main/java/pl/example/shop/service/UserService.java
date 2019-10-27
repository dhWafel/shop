package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.User;
import pl.example.shop.repository.RoleRepository;
import pl.example.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User creteUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()->new Exception("User is not found with id" + id));
    }


    public Page<User> userPage(@PageableDefault Pageable pageable) {
        return userRepository.findAll(pageable);
    }



    public User update(User user) throws Exception {
        return userRepository.findById(user.getId()).map(u -> {
            if(!u.getName().equals(user.getName())){
                u.setName(user.getName());
            }
            if(!u.getSurname().equals(user.getSurname())){
                u.setSurname(user.getSurname());
            }
            return userRepository.save(u);

        }).orElseThrow(()->new Exception("Brak danych"));
    }



  /*  public User update(User user) throws Exception {
        return userRepository.findById(user.getId()).map(u -> {
            if(!u.getName().equals(user.getName())){
                u.setName(user.getName());
            }
            if(!u.getSurname().equals(user.getSurname())){
                u.setSurname(user.getSurname());
            }
            return userRepository.save(u);

            }).orElseThrow(()->new Exception("Brak danych"));
    } */


}

//Jave, Intelillij, Maven, git, postman, doker i dokercompose

