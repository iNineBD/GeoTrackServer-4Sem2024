package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.filterdevices.DataDevices;
import com.geotrack.apigeotrack.dto.filterdevices.RequestDevice;
import com.geotrack.apigeotrack.dto.filterusers.DataUsers;
import com.geotrack.apigeotrack.dto.filterusers.RequestUser;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FiltersService  {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DispositivoRepository dispositivoRepository;


    public Page<DataUsers> listUsers(RequestUser request) throws Exception{
        // Here it says which page I want and the number of items per page
        PageRequest page = PageRequest.of(request.page(),5);

        // Here I consult the users in the DB and bring them in the form of a list
        Optional<Page<Usuario>> users = usuarioRepository.listUser(page);

        if(users.get().isEmpty()){
            throw new RuntimeException("Nenhum usuário encontrado");
        }else{
            // In my return I modify the user objects to only show their ID and name
            return users.get().map(DataUsers::new);
        }
    }

    public Page<DataDevices> listDevices(RequestDevice request) throws Exception {
        // Here it says which page I want and the number of items per page
        PageRequest page = PageRequest.of(request.page(),5);

        // Here I consult the devices in the DB and bring them in the form of a list
        Optional<Page<Dispositivo>> devices = dispositivoRepository.listDevices(request.idUser(),page);

        if(devices.get().isEmpty()){
            throw new Exception("Nenhum dispositivo encontrado para o usuário");
        }else{
            // In my return I modify the devices objects to only show their ID and name
            return devices.get().map(DataDevices::new);
        }
    }
}
