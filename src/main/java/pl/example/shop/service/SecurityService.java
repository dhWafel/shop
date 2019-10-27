package pl.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.example.shop.repository.UserRepository;

@Service
public class SecurityService {
    @Autowired
    private  UserRepository userRepository;

    public Boolean hasAccessToUser(Long id){
        String getEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(getEmail==null) {
            return false;
        }
        System.out.println(id);
        return userRepository.findByEmail(getEmail).map(u -> u.getId().equals(id)).orElse(false);
    }
}
