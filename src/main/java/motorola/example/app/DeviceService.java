package motorola.example.app;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import motorola.example.app.Device;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


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


public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    File destFile = new File(destinationDir, zipEntry.getName());

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
}

    public void unZip() throws IOException {

        String pathToZipFile = "TNCT_LAB.zip";
        File destDir = new File("unZipContent");
        ZipInputStream zis = new ZipInputStream(new FileInputStream(pathToZipFile));
        ZipEntry zipEntry = new ZipEntry(zis.getNextEntry());
        byte[] buffer = new byte[1024];

        while (zipEntry != null) {
            File newFile = newFile(destDir,zipEntry);

            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
    }

    public List<Device> getInfoFromFile() throws IOException {
       unZip();

       FileReader fileReader = new FileReader("unZipContent/systemstructure.txt");
       JsonReader reader = new JsonReader(fileReader);
       Device device[] = new Gson().fromJson(reader,Device[].class);
       reader.close();
       FileUtils.deleteDirectory(new File("unZipContent"));
       return List.of(device);
    }

}
