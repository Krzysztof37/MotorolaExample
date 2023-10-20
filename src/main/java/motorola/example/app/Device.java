package motorola.example.app;


import jakarta.persistence.Column;
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

    @Id
    private String hostname;
    @Column(unique = true)
    private String ipAddress;
    private List<String> cfgFiles;
    private String enabled;
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

    public String getHostname() {
        return hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public List<String> getCfgFiles() {
        return cfgFiles;
    }

    public String getEnabled() {
        return enabled;
    }

    public List<String> getLoadConditions() {
        return loadConditions;
    }

    public String getModel() {
        return model;
    }

    public String getNetworkingFunction() {
        return networkingFunction;
    }

    public String getPath() {
        return path;
    }

    public String getPingBackAllowed() {
        return pingBackAllowed;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public String getSshStatus() {
        return sshStatus;
    }

    public String getSshSupported() {
        return sshSupported;
    }

    public String getTelnetStatus() {
        return telnetStatus;
    }

    public String getTelnetSupported() {
        return telnetSupported;
    }

    public String getTftpAddress() {
        return tftpAddress;
    }
}
