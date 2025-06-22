package com.example.service;

import com.example.exception.UserException;
import com.example.model.Ride;
import com.example.model.User;

import java.util.List;

public interface UserService {

//    public User createUser(User user) throws UserException;

    public User getReqUserProfile(String token) throws UserException;

    public User findUserById(Integer id) throws UserException;

//    public User findUserByEmail(String email) throws UserException;

    public List<Ride> completedRides(Integer userId) throws UserException;

}
