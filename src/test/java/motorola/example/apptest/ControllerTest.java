package motorola.example.apptest;



import jakarta.persistence.EntityManager;
import motorola.example.app.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTest {


    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepositoryTest deviceRepositoryTest;



    //    @Test
//    public void controllerTestSaveDevices() throws Exception {
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/saveDevices",
//                ));
//
//    }
    @Test
    public void controllerTestGetDevices() throws Exception {
        this.mockMvc.perform(get("/getDevices")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void controllerTestGetDevicesByState() throws Exception{
        this.mockMvc.perform(get("/getDevicesByState").param("enabled", "false")).
                andExpect(status().isOk());
        this.mockMvc.perform(get("/getDevicesByState").param("enabled","true"))
                .andExpect(status().isOk());
    }

    @Test
    public void controllerTestGetDeviceByIp() throws Exception{
        this.mockMvc.perform(get("/getDeviceByIp").param("ip","test"))
                .andExpect(status().isOk());
    }

    @Test
    public void controllerTestSaveDevices() throws Exception{
        this.mockMvc.perform(post("/saveDevices").param("path","C:\\TNCT_LAB.zip"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(60)))
                .andExpect(jsonPath("$.[1].ipAddress", matchesPattern("[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}")));
    }

    @Test
    public void controllerTestGetDeviceByHostname() throws Exception {
        this.mockMvc.perform(get("/getDeviceByHostname").param("hostname","test"))
                .andExpect(status().isOk());
    }

    @Test
    public void controllerTestDeleteByHostname() throws Exception {
        this.mockMvc.perform(post("/saveDevices").param("path", "C:\\TNCT_LAB.zip")).andExpect(status().isOk());

        this.mockMvc.perform(get("/deleteByHostname").param("hostname", "z003lans01")).andExpect(status().isOk())
                .andExpect(content().string("Device deleted, hostname: z003lans01"));


    }

    @Test
    public void controllerTestUpdateDeviceState() throws Exception{
        this.mockMvc.perform(post("/saveDevices").param("path", "C:\\TNCT_LAB.zip"))
                .andExpect(status().isOk());
        List<Device> listOfDevices = deviceRepositoryTest.findAll();
        //Device device = deviceRepositoryTest.findByHostname("z003lans01");
        Device device = listOfDevices.get(0);
        String deviceStateBeforeChange = device.getEnabled();
        String paramForTest = "";
        if(deviceStateBeforeChange.equals("true")){
            paramForTest = "false";
        }else{
            paramForTest = "true";
        }

    this.mockMvc.perform(put("/updateDeviceState").param("hostname",device.getHostname())
            .param("enabled",paramForTest)).andExpect(status().isOk());
        Device device2 = deviceRepositoryTest.findByHostname(device.getHostname());
        String deviceStateAfterChange = device2.getEnabled();
        if(deviceStateBeforeChange.equals(deviceStateAfterChange)){
            throw new Exception();
        }


    }

}
