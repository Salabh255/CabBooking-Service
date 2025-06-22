package com.example.service;

import com.example.config.JwtUtil;
import com.example.exception.UserException;
import com.example.model.Ride;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User getReqUserProfile(String token) throws UserException {
        String email = jwtUtil.getEmailFromJwt(token);
        User user = userRepository.findByEmail(email);

        if(user!=null){
            return user;
        }
        throw new UserException("Invalid token....");
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> opt = userRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not found with id"+id);
    }

    @Override
    public List<Ride> completedRides(Integer userId) throws UserException {
        List<Ride> completeRides=userRepository.getCompletedRides(userId);
        return completeRides;
    }
}
