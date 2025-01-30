package org.iesvdm.controlador;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/comerciales")
    public String getComercial(Model model) {
        model.addAttribute("listaComerciales", comercialService.getAll());
        return "comerciales";
    }

    @GetMapping("/comerciales/crear")
    public String crearComercial(@ModelAttribute("comercial") Comercial comercial) {
        return "crearComercial";
    }

    @PostMapping("/comerciales/crear")
    public String crearComercialPost(@ModelAttribute("comercial")Comercial comercial) {
        comercialService.create(comercial);
        return "redirect:/comerciales";
    }

    @GetMapping("/comerciales/editar/{id}")
    public String editarComercial(@PathVariable("id") int id, Model model) {
        if (comercialService.find(id).isEmpty()) {
            return "redirect:/comerciales";
        }
        model.addAttribute("comercial", comercialService.find(id).get());
        return "editarComercial";
    }

    @PostMapping("/comerciales/editar")
    public String editarComercialPost(@ModelAttribute("comercial")Comercial comercial) {
        comercialService.update(comercial);
        return "redirect:/comerciales";
    }

    @PostMapping("/comerciales/borrar/{id}")
    public String borrarComercial(@PathVariable("id") int id) {
        comercialService.delete(id);
        return "redirect:/comerciales";
    }

    @GetMapping("/comerciales/{id}")
    public String detalleComercial(@PathVariable("id") int id, Model model) {
        if (comercialService.findDTO(id).isEmpty()) {
            return "redirect:/comerciales";
        }
        model.addAttribute("comercial", comercialService.findDTO(id).get());

        return "detallesComercial";
    }
}