package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.filterdevices.DataDevicesDTO;
import com.geotrack.apigeotrack.dto.filterdevices.RequestDevice;
import com.geotrack.apigeotrack.dto.filterdevices.ResponseDevices;
import com.geotrack.apigeotrack.dto.filterusers.DataUsersDTO;
import com.geotrack.apigeotrack.dto.filterusers.RequestUser;
import com.geotrack.apigeotrack.dto.filterusers.ResponseUsers;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FiltersService  {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DispositivoRepository dispositivoRepository;


    public ResponseUsers listUsers(RequestUser request) throws NoSuchElementException{
        // Here it says which page I want and the number of items per page
        PageRequest page = PageRequest.of(request.page(),5);

        // Here I consult the users in the DB and bring them in the form of a list
        Optional<Page<Usuario>> users = usuarioRepository.listUser(page);

        if(users.get().isEmpty()){
            throw new NoSuchElementException("Nenhum usuário encontrado");
        }
        // In my return I modify the user objects to only show their ID and name
        Page<DataUsersDTO> usersPage = users.get().map(DataUsersDTO::new);

        //Get the current page number
        int pageAtual = usersPage.getNumber();

        //Get the total number of pages
        int totalPages = usersPage.getTotalPages();

        // Get the list of users
        List<DataUsersDTO> usersResponse = usersPage.getContent();

        return new ResponseUsers(usersResponse,pageAtual,totalPages);

    }

    public ResponseDevices listDevices(RequestDevice request) throws NoSuchElementException {
        // Here it says which page I want and the number of items per page
        PageRequest page = PageRequest.of(request.page(),5);

        // Here I consult the devices in the DB and bring them in the form of a list
        Optional<Page<Dispositivo>> devices = dispositivoRepository.listDevices(request.idUser(),page);

        if(devices.get().isEmpty()){
            throw new NoSuchElementException("Nenhum dispositivo encontrado para o usuário");
        }
        // In my return I modify the devices objects to only show their ID and name
        Page<DataDevicesDTO> devicesPage = devices.get().map(DataDevicesDTO::new);

        //Get the current page number
        int pageAtual = devicesPage.getNumber();

        //Get the total number of pages
        int totalPages = devicesPage.getTotalPages();

        // Get the list of devices
        List<DataDevicesDTO> devicesResponse = devicesPage.getContent();

        return new ResponseDevices(devicesResponse,pageAtual,totalPages);
    }
}
