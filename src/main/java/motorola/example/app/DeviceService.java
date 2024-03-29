package motorola.example.app;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import motorola.example.app.Device;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class DeviceService {


private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    File destFile = new File(destinationDir, zipEntry.getName());

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
}

    private void unZip(MultipartFile file) throws IOException {
        try {

            File destDir = new File("unZipContent");
            //Buffered Stream jest potrzebny aby w łatwy sposób odczytać zawartość multipart file
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(file.getInputStream()));
            ZipEntry zipEntry = new ZipEntry(zis.getNextEntry());
            byte[] buffer = new byte[1024];

            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);

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
        }catch (IOException e){
            System.out.println("file not found");
        }
    }
    public List<Device> getInfoFromFile(MultipartFile file) throws IOException {
       unZip(file);
        try {
            FileReader fileReader = new FileReader("unZipContent/systemstructure.txt");
            JsonReader reader = new JsonReader(fileReader);
            Device device[] = new Gson().fromJson(reader, Device[].class);
            reader.close();
            FileUtils.deleteDirectory(new File("unZipContent"));
            return List.of(device);
        }catch (IOException e){
            e.printStackTrace();
        }

        return List.of();
    }

}
