package cl.juan.stockmarketapiservice.infrastructure.configuration;

import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.entity.Role;
import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.entity.RoleType;
import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.repositories.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(ApplicationArguments args) {
        roleRepository.save(new Role(1, RoleType.ROLE_ADMIN));
    }
}