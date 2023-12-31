package com.stitches.service.serviceImpl;

import com.stitches.service.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SftpFileTransfer {

//        implements CommandLineRunner {

    @Autowired
    private FileTransferService fileTransferService;

    private Logger logger = LoggerFactory.getLogger(SftpFileTransfer.class);

//    @Override
//    public void run(String... args) throws Exception {
//        logger.info("Start download file");
//        boolean isDownloaded = fileTransferService.downloadFile("/home/simplesolution/readme.txt", "/readme.txt");
//        logger.info("Download result: " + String.valueOf(isDownloaded));
//
//        logger.info("Start upload file");
//        boolean isUploaded = fileTransferService.uploadFile("/home/simplesolution/readme.txt", "/readme2.txt");
//        logger.info("Upload result: " + String.valueOf(isUploaded));
//    }

}