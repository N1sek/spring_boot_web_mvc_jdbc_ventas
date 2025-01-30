package org.iesvdm.controlador;


import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    public ComercialController(ComercialService comercialService){
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales")
    public String listar(Model model){
        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaClientes", listaComerciales);

        return "comerciales";
    }

    @GetMapping("/comerciales/crear")
    public String crear(@ModelAttribute("comercial") Comercial comercial){
        return "comerciales";
    }

    @PostMapping("/comerciales/crear")
    public String guardar(@ModelAttribute("comercial") Comercial comercial){
        comercialService.crear(comercial);
        return "redirect:/comerciales";
    }
}
