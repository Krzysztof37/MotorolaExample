package motorola.example.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

     Device findByHostname(String hostname);
     List<Device> findAllByEnabled(String enabled);

     Device findByIpAddress(String ip);
     void deleteByHostname(String hostname);




}
