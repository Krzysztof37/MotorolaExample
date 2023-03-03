package motorola.example.utils;

import motorola.example.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

     List<Device> findByHostname(String hostname);
     List<Device> findAllByEnabled(String enabled);

     Device findByIpAddress(String ip);

     Device deleteByHostname(String hostname);



}
