package motorola.example.apptest;

import motorola.example.app.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepositoryTest extends JpaRepository<Device,Long> {

    Device findByHostname(String hostname);


}