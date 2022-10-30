package cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.repositories;

import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.entity.Role;
import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}