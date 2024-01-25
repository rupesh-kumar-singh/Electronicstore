package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.exceptions.BadapiRequest;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MyFileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(MyFileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("filename :{}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filenamewithextension = filename + extention;
        String fullpathwithfilename = path+ File.separator+filenamewithextension;
        if (extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".jpg")|| extention.equalsIgnoreCase(".jpeg") ){


            // sav ethe file

            File folder = new File(path);
            if (!folder.exists()){
                // create the folder
                folder.mkdirs();
            }

           Files.copy(file.getInputStream(), Paths.get(fullpathwithfilename));
return filenamewithextension;
        }else {
            throw new BadapiRequest("file with this extension" +extention+"not allowed");

        }

    }

    @Override
    public InputStream getresources(String path, String name) throws FileNotFoundException {
        String fullpath = path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;

    }
}
