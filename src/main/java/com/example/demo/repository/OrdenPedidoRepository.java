    package com.example.demo.repository;


    import com.example.demo.models.entity.OrdenPedido;
    import jakarta.transaction.Transactional;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface OrdenPedidoRepository extends JpaRepository<OrdenPedido, Long> {

        @Query("SELECT MAX(o.numeroPedido) FROM OrdenPedido o")
        Long findMaxNumeroPedido();

        @Query("SELECT op FROM OrdenPedido op WHERE op.cliente.id = :clienteId")
        List<OrdenPedido> findByClienteId(@Param("clienteId") Long clienteId);

        @Modifying
        @Transactional
        @Query("DELETE FROM OrdenPedido o WHERE o.cliente.id = :clienteId")
        void deleteByClienteId(@Param("clienteId") Long clienteId);
    }