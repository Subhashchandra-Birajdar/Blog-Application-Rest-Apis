package com.Blog_App_Apis.service.serviceImpl;

import com.Blog_App_Apis.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException{
        //File name
        String name = file.getOriginalFilename(); //abc.png

        //random name generate file
        String randomID = UUID.randomUUID().toString();
        String filename1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        //Fullpath
        String filePath = path + File.separator + filename1;

        //create folder if it not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return filename1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        //db logic to return in the InputStream
        return is;
    }
}
