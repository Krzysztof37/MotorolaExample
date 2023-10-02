package motorola.example.app;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
@Transactional
public class DeviceController {

    private final DeviceRepository deviceRepository;

    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @GetMapping("/getdevices")
    public String getDevices() throws FileNotFoundException {
        DeviceService deviceService = new DeviceService();

        List<Device> listOfDevices = deviceRepository.findAll();
        Gson gson = new Gson();


        return gson.toJson(listOfDevices);
    }



    @GetMapping("/getDevicesByEnabled")
    public String getDevicesByEnabled(@RequestParam(name = "enabled")String enabled){
        if(enabled.equals("true") || enabled.equals("false")) {
            List<Device> devices = deviceRepository.findAllByEnabled(enabled);
            Gson gson = new Gson();
            return gson.toJson(devices);
        }
        return "Use 'false' or 'true'";
    }

    @GetMapping("/getDevicesByIp")
    public String getDevicesByIp(@RequestParam(name = "ip")String ip){
        Device device = deviceRepository.findByIpAddress(ip);
        return device.toString();
    }


    @GetMapping("/savedevices")
    public String saveDevices() throws IOException {
        DeviceService deviceService = new DeviceService();
        List<Device> listOfDevices = deviceService.getInfoFromFile();
        for(Device device : listOfDevices){
            deviceRepository.save(device);

        }
        return "Devices saved";
    }



    @GetMapping("/deleteByHostname")
    public String deleteByHostname(@RequestParam(name = "hostname") String hostname){
        Device device = deviceRepository.findByHostname(hostname);
        if(device == null){
            return "Device with hostname "+hostname+" does not exist";
        }
        deviceRepository.deleteByHostname(hostname);


        return "Device deleted hostname: "+hostname;
    }

    @PutMapping("/updateEnabled")
    public String updateEnabled(@RequestParam(name = "hostname")String hostname, @RequestParam(name = "enabled") String enabled){
        Optional<Device> device = Optional.ofNullable(deviceRepository.findByHostname(hostname));
        if(device.toString().equals("Optional.empty")){
            return "Device does not exist";
        }

        if(enabled.equals("true") || enabled.equals("false")){
            device.ifPresent(e -> e.enabled(enabled));
            device.ifPresent(e -> deviceRepository.save(device.get()));
            return "Device "+hostname+" updated";
        }

        return "Use 'false' or 'true'";
    }
}
