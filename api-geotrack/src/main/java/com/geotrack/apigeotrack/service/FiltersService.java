package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.filterdevices.DataDevicesDTO;
import com.geotrack.apigeotrack.dto.filterdevices.RequestDevice;
import com.geotrack.apigeotrack.dto.filterdevices.ResponseDevices;
import com.geotrack.apigeotrack.dto.filterusers.DataUsersDTO;
import com.geotrack.apigeotrack.dto.filterusers.RequestUser;
import com.geotrack.apigeotrack.dto.filterusers.ResponseUsers;
import com.geotrack.apigeotrack.entities.Devices;
import com.geotrack.apigeotrack.entities.User;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FiltersService  {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DevicesRepository devicesRepository;


    @Cacheable(value = "lists", key = "#request")
    public ResponseUsers listUsers(RequestUser request) throws NoSuchElementException{
        // Here it says which page I want and the number of items per page
        PageRequest page = PageRequest.of(request.page(), request.qtdPage());

        // Here I consult the users in the DB and bring them in the form of a list
        Optional<Page<User>> users = userRepository.listUser(page);

        if(users.get().isEmpty()){
            throw new NoSuchElementException("Nenhum usuário encontrado");
        }

        Page<DataUsersDTO> usersPage = users.get().map(DataUsersDTO::new);

        //Get the current page number
        int pageAtual = usersPage.getNumber();

        //Get the total number of pages
        int totalPages = usersPage.getTotalPages();

        // Get the list of users
        List<DataUsersDTO> usersResponse = usersPage.getContent();

        return new ResponseUsers(usersResponse,pageAtual,totalPages);

    }
}
