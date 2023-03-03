package motorola.example.utils;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import motorola.example.model.Device;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DeviceService {

//    public List<Device> getInfoFromFile(){
//        List<Device> listOfDevices = new ArrayList<>();
//        List<String> listValues = new ArrayList<>();
//        try(FileReader reader = new FileReader("systemstructure.txt")){
//            Scanner scanner = new Scanner(reader);
//            int counter = 0;
//            while(scanner.hasNextLine()){
//
//                String deviceInfoLine = scanner.nextLine();
//                deviceInfoLine = deviceInfoLine.replace(",","");
//                String arrayString[] = deviceInfoLine.split(":");
//                System.out.println(Arrays.toString(arrayString));
//
//                if(arrayString.length > 1){
//                    listValues.add(arrayString[1]);
//                    counter++;
//                }
//
//                if(counter == 16){
//                    Device device1 = new Device();
//                    device1.cfgFiles(listValues.get(0));
//                    device1.enabled(listValues.get(1));
//                    device1.hostname(listValues.get(2));
//                    device1.ipAddress(listValues.get(3));
//                    device1.loadConditions(listValues.get(4));
//                    device1.model(listValues.get(5));
//                    device1.networkingFunction(listValues.get(6));
//                    device1.path(listValues.get(7));
//                    device1.pingBackAllowed(listValues.get(8));
//                    device1.pingStatus(listValues.get(9));
//                    device1.processingStatus(listValues.get(10));
//                    device1.sshStatus(listValues.get(11));
//                    device1.sshSupported(listValues.get(12));
//                    device1.telnetStatus(listValues.get(13));
//                    device1.telnetSupported(listValues.get(14));
//                    device1.tftpAddress(listValues.get(15));
//
//                    listOfDevices.add(device1);
//                    counter = 0;
//
////                    System.out.println("to jest config service");
////                    System.out.println(listValues);
//                    listValues.clear();
//                }
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return listOfDevices;
//    }


    public List<Device> getInfoFromFile() throws FileNotFoundException {

       FileReader fileReader = new FileReader("systemstructure.txt");
       JsonReader reader = new JsonReader(fileReader);
       Device device[] = new Gson().fromJson(reader,Device[].class);

       return List.of(device);
    }

}
