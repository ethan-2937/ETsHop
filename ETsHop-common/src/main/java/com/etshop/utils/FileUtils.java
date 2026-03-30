package com.etshop.utils;

import com.etshop.entity.config.AppConfig;
import com.etshop.entity.constants.Constants;
import com.etshop.entity.enums.DateTimePatternEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class FileUtils {

    @Resource
    private AppConfig appConfig;

    public String uploadImage(MultipartFile file,Boolean createThumbnail) throws IOException {
        String folderName = DateUtil.format(new Date(), DateTimePatternEnum.YYYY_MM.getPattern()+"/");
        String folderPath = appConfig.getProjectFolder() + Constants.FILE_FOLDER + folderName;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = StringTools.getRandomString(Constants.LENGTH_30);
        String suffix = StringTools.getFileSuffix(file.getOriginalFilename());
        String resultFileName = fileName+suffix;
        file.transferTo(new File(folderPath + resultFileName));
        if (createThumbnail!=null && createThumbnail) {
            String thumbnail = fileName + Constants.IMAGE_THUMBNAIL_SUFFIX+suffix;
            createImageThumbnail(folderPath+resultFileName,folderPath+thumbnail);
        }
        return folderName + resultFileName;
    }

    private void createImageThumbnail(String sourcePath,String targetPath) throws IOException {
        final String CMD_CREATE_THUMBNAIL = "ffmpeg -i \"%s\" -vf scale=200:-1 \"%s\" ";
        String cmd = String.format(CMD_CREATE_THUMBNAIL,sourcePath,targetPath);
        ProcessUtils.executeCommand(cmd,true);
    }
}
