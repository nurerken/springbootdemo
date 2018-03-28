package kz.guestbook.components.User.service;

import kz.guestbook.components.User.model.User;
import kz.guestbook.components.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getUserBySearchString(String searchString){
        if(searchString == null)
            searchString = "";
        return userRepository.findByEmailOrFirstNameOrLastName(searchString);
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public User getUserByEmail(String email){
        return userRepository.findActiveByEmail(email);
    }

    public void saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Integer activateUser(Long id){
        return userRepository.setActive(id, true);
    }

    public Integer deleteUser(Long id){
        return userRepository.setActive(id, false);
    }
}
