package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class PedidoDAOImpl implements PedidoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    ClienteDAOImpl clienteDAOImpl;

    @Override
    public List<Pedido> findByComercialID(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM pedido WHERE id_comercial = ?",
                (rs, rowNum) -> new Pedido(rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial")), id
        );
    }

    public List<Integer> findClienteIDByComercialID(int id) {
        return jdbcTemplate.query(
                "SELECT id_cliente FROM pedido WHERE id_comercial = ?",
                (rs, rowNum) -> rs.getInt("id_cliente"), id
        );
    }

    public List<Double> getTotalFromClientID(Long id) {
        return jdbcTemplate.query(
                "SELECT total FROM pedido WHERE id_cliente = ?",
                (rs, rowNum) -> rs.getDouble("total"), id
        );
    }
}
