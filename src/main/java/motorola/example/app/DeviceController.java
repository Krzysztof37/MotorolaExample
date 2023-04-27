package motorola.example.app;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
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

    @GetMapping("/getdevicesbyid")
    public String getDevicesById(@RequestParam(name = "id") Long id){
        Optional<Device> device = deviceRepository.findById(id);
        return device.toString();
    }

    @GetMapping("/getdevicesbyenabled")
    public String getDevicesByEnabled(@RequestParam(name = "enabled")String enabled){
        List<Device> devices = deviceRepository.findAllByEnabled(enabled);
        Gson gson = new Gson();
        return gson.toJson(devices);
    }

    @GetMapping("/getdevicesbyip")
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

    @GetMapping("/deletebyid")
    public String deleteById(@RequestParam(name = "id")Long id){
        deviceRepository.deleteById(id);
        return "Device deleted id: "+id;
    }

    @GetMapping("/deletebyhostname")
    public String deleteByHostname(@RequestParam(name = "hostname") String hostname){
        deviceRepository.deleteByHostname(hostname);

        return "Device deleted hostname: "+hostname;
    }

    @GetMapping("/updateenabled")
    public String updateEnabled(@RequestParam(name = "id")Long id, @RequestParam(name = "enabled") String enabled){
        Optional<Device> device = deviceRepository.findById(id);
        device.ifPresent(e -> e.enabled(enabled));
        deviceRepository.save(device.get());
        return "Device updated";
    }
}
