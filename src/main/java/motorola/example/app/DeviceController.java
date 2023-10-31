package motorola.example.app;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @GetMapping("/getDevices")
    public List<Device> getDevices() throws FileNotFoundException {
        return deviceRepository.findAll();
    }



    @GetMapping("/getDevicesByState")
    public List<Device> getDevicesByState(@RequestParam(name = "enabled")String enabled){
        if(enabled.equals("true") || enabled.equals("false")) {
           List<Device> devices = deviceRepository.findAllByEnabled(enabled);
           return devices;
        }
        return List.of();
    }

    @GetMapping("/getDeviceByIp")
    public Optional<Device> getDevicesByIp(@RequestParam(name = "ip")String ip)  {
        Optional<Device> device = Optional.ofNullable(deviceRepository.findByIpAddress(ip));
        //.orElseThrow();
        return device;
    }

// nie dziala z zalaczaniem pliku, walidator swagera nie przepuszcza, mozna wrocic do starego rozwiazania czyli przekazanie sciezki do pliku
    @PostMapping(value = "/saveDevices",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Device> saveDevices(@RequestParam() MultipartFile file) throws IOException {
        DeviceService deviceService = new DeviceService();
        List<Device> listOfDevices = deviceService.getInfoFromFile(file);
        for(Device device : listOfDevices){
            deviceRepository.save(device);
        }

        return listOfDevices;
    }



    @GetMapping("/deleteByHostname")
    public String deleteByHostname(@RequestParam(name = "hostname") String hostname){
        Device device = deviceRepository.findByHostname(hostname);
        if(device == null){
            return "Device with hostname "+hostname+" does not exist";
        }
        deviceRepository.deleteByHostname(hostname);


        return "Device deleted, hostname: "+hostname;
    }

    @PutMapping("/updateDeviceState")
    public Optional<Device> updateDeviceState(@RequestParam(name = "hostname")String hostname, @RequestParam(name = "enabled") String enabled){
        Optional<Device> device = Optional.ofNullable(deviceRepository.findByHostname(hostname));

        if(enabled.equals("true") || enabled.equals("false")){
            device.ifPresent(e -> e.enabled(enabled));
            device.ifPresent(e -> deviceRepository.save(device.get()));
            return device;
        }

        return device;
    }

    @GetMapping("/getDeviceByHostname")
    public Optional<Device> getDeviceByHostname(String hostname){
        Optional<Device> device = Optional.ofNullable(deviceRepository.findByHostname(hostname));
        return device;
    }


}
