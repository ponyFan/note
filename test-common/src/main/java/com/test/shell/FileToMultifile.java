package com.test.shell;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * runtime方式
 * @author zelei.fan
 * @date 2019/9/18 16:53
 */
@RestController("/test")
public class FileToMultifile {
    public static void main(String[] args) throws IOException {
        File file = File.createTempFile("temp", Long.toString(System.nanoTime()));
        FileItem test = createFileItem(new File("C:\\Users\\fanzelei\\Downloads\\access.log"), "test");
        MultipartFile multipartFile = new CommonsMultipartFile(test);
    }

    public static FileItem createFileItem(File file, String fieldName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[5120];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 5120)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
