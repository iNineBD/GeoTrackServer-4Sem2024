package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.filterdevices.DataUsers;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.exceptions.GlobalExceptionHandler;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiltersService  {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    DispositivoRepository dispositivoRepository;


    public List<DataUsers> listUsers(){
        Optional<List<Usuario>> users = usuarioRepository.listUser();
        if(users.isPresent()){

            List<DataUsers> dataUsers = users.get().stream().
                    map(DataUsers::new).toList();
            return dataUsers;
        }else{
            throw new RuntimeException("Nenhum usuário encontrado");
        }
    }
}
