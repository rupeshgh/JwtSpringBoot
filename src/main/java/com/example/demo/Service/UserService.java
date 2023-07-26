package com.example.demo.Service;


import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;


public void addUser(User user){

    userRepository.save(user);


}
}
