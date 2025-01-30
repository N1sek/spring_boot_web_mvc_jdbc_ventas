package org.iesvdm.service;


import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {
    private ComercialDAO comercialDAO;

    public ComercialService(ComercialDAO comercialDAO){
        this.comercialDAO = comercialDAO;
    }

    public List<Comercial> listAll(){
        return comercialDAO.getAll();
    }

    public void crear(Comercial comercial){
        comercialDAO.create(comercial);
    }

    public Optional<Comercial> find(int id) {
        return comercialDAO.find(id);
    }

    public void update(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void delete(int id) {
        comercialDAO.delete(id);
    }
}
