package org.iesvdm.service;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.iesvdm.dao.ComercialDAO;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAO comercialDAO;

    public void create(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public List<Comercial> getAll() {
        return comercialDAO.getAll();
    }

    public List<ComercialDTO> getAllDTO() {
        return comercialDAO.getAllDTO();
    }

    public Optional<Comercial> find(int id) {
        return comercialDAO.find(id);
    }

    public Optional<ComercialDTO> findDTO(int id) {
        return comercialDAO.findDTO(id);
    }

    public void update(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void delete(int id) {
        comercialDAO.delete(id);
    }
}
