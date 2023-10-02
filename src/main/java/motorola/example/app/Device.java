package motorola.example.app;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Data
@Entity
public class Device {

//    @Id
//    @GeneratedValue
//    private long id;
    private List<String> cfgFiles;
    private String enabled;

    @Id
    private String hostname;
    private String ipAddress;
    private List<String> loadConditions;
    private String model;
    private String networkingFunction;
    private String path;
    private String pingBackAllowed;
    private String pingStatus;
    private String processingStatus;
    private String sshStatus;
    private String sshSupported;
    private String telnetStatus;
    private String telnetSupported;
    private String tftpAddress;

    @Override
    public String toString() {
        return "Device{" +
                "cfgFiles='" + cfgFiles + '\'' +
                ", enabled='" + enabled + '\'' +
                ", hostname='" + hostname + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", loadConditions='" + loadConditions + '\'' +
                ", model='" + model + '\'' +
                ", networkingFunction='" + networkingFunction + '\'' +
                ", path='" + path + '\'' +
                ", pingBackAllowed='" + pingBackAllowed + '\'' +
                ", pingStatus='" + pingStatus + '\'' +
                ", processingStatus='" + processingStatus + '\'' +
                ", sshStatus='" + sshStatus + '\'' +
                ", sshSupported='" + sshSupported + '\'' +
                ", telnetStatus='" + telnetStatus + '\'' +
                ", telnetSupported='" + telnetSupported + '\'' +
                ", tftpAddress='" + tftpAddress + '\'' +
                '}';
    }
}
