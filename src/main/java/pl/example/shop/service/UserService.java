package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import pl.example.shop.domain.User;
import pl.example.shop.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User creteUser(User user){
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
}

//Jave, Intelillij, Maven, git, postman, doker i dokercompose

    //User inbound = ... User existing = userRepository.findByFirstname(inbound.getFirstname());
// if(existing != null) inbound.setId(existing.getId());
// userRepository.save(inbound);



    //public void updateLaserDataByHumanId(String replacement, String humanId)
// { List<LaserData> laserDataByHumanId = laserDataRepository.findByHumanId(humanId);
// laserDataByHumanId.stream() .map(en -> en.setHumanId(replacement))
// .collect(Collectors.toList()) .forEach(en -> laserDataRepository.save(en)); }