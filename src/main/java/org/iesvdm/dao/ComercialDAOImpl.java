package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.mapper.ComercialMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

    //JdbcTemplate se inyecta por el constructor de la clase automáticamente
    //
    private JdbcTemplate jdbcTemplate;
    private ComercialMapper comercialMapper;
    PedidoDAOImpl pedidoDAOImpl;
    ClienteDAOImpl clienteDAOImpl;

    @Override
    public void create(Comercial cliente) {
        int rows = jdbcTemplate.update("INSERT INTO comercial (nombre, apellido1, apellido2, comisión) VALUES (?, ?, ?, ?)",
                cliente.getNombre(), cliente.getApellido1(), cliente.getApellido2(), cliente.getComision());

        log.info("Filas afectadas: {}", rows);
    }

    @Override
    public List<Comercial> getAll() {

        List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getFloat("comisión"))

        );

        log.info("Devueltos {} registros.", listComercial.size());

        return listComercial;
    }

    @Override
    public List<ComercialDTO> getAllDTO() {

        return getAll().stream().map(comercial -> {
            List<Pedido> pedidos = pedidoDAOImpl.findByComercialID(comercial.getId());
            List<Cliente> clientes = pedidoDAOImpl.findClienteIDByComercialID(comercial.getId()).stream().map(id -> {
                if (clienteDAOImpl.find(id).isPresent()) {
                    return clienteDAOImpl.find(id).get();
                } else {
                    return null;
                }
            }).distinct().sorted((o1, o2) -> {
                return Double.compare(pedidoDAOImpl.getTotalFromClientID(o1.getId()).stream().mapToDouble(Double::doubleValue).sum(), pedidoDAOImpl.getTotalFromClientID(o2.getId()).stream().mapToDouble(Double::doubleValue).sum());
            }).toList();
            return comercialMapper.toDTO(
                    comercial,
                    pedidos,
                    clientes,
                    pedidos.stream().mapToDouble(Pedido::getTotal).sum(),
                    pedidos.stream().mapToDouble(Pedido::getTotal).average().orElse(0.0),
                    pedidos.stream().mapToDouble(Pedido::getTotal).min().orElse(0.0),
                    pedidos.stream().mapToDouble(Pedido::getTotal).max().orElse(0.0)
            );
        }).toList();
    }

    @Override
    public Optional<Comercial> find(int id) {
        Comercial comercial = jdbcTemplate
                .queryForObject("SELECT * FROM comercial WHERE id = ?"
                        , (rs, rowNum) -> new Comercial(rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido1"),
                                rs.getString("apellido2"),
                                rs.getFloat("comisión"))
                        , id
                );

        if (comercial != null) {
            return Optional.of(comercial);
        } else {
            log.info("Comercial no encontrado.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<ComercialDTO> findDTO(int id) {
        if (find(id).isPresent()) {
            return find(id).map(comercial -> {
                List<Pedido> pedidos = pedidoDAOImpl.findByComercialID(comercial.getId());
                List<Cliente> clientes = pedidoDAOImpl.findClienteIDByComercialID(comercial.getId()).stream().map(idcom -> {
                    return clienteDAOImpl.find(idcom).orElse(null);
                }).distinct().sorted((o1, o2) -> {
                    return Double.compare(pedidoDAOImpl.getTotalFromClientID(o1.getId()).stream().mapToDouble(Double::doubleValue).sum(), pedidoDAOImpl.getTotalFromClientID(o2.getId()).stream().mapToDouble(Double::doubleValue).sum());
                }).toList();
                return comercialMapper.toDTO(
                        comercial,
                        pedidos,
                        clientes,
                        pedidos.stream().mapToDouble(Pedido::getTotal).sum(),
                        Math.floor(pedidos.stream().mapToDouble(Pedido::getTotal).average().orElse(0.0) *100) / 100,
                        pedidos.stream().mapToDouble(Pedido::getTotal).min().orElse(0.0),
                        pedidos.stream().mapToDouble(Pedido::getTotal).max().orElse(0.0)
                );
            });
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void update(Comercial cliente) {
        jdbcTemplate.update("UPDATE comercial SET nombre = ?, apellido1 = ?, apellido2 = ?, comisión = ? WHERE id = ?", cliente.getNombre(), cliente.getApellido1(), cliente.getApellido2(), cliente.getComision(), cliente.getId());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);
    }

}
