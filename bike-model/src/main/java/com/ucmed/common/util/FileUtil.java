package com.ucmed.common.util;

import com.ucmed.common.filter.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by ucmed on 2017/3/18.
 */
public class FileUtil {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
            "/yyyy/MM/dd/");
    private static final Logger LOG = LoggerFactory.getLogger(FileFilter.class);

    public static String saveFile(String upRoot, String fileParents,
                                  CommonsMultipartFile file, boolean isRename) {
        if(file == null)
            return null;
        String path = null;
        String name = file.getOriginalFilename();
        try {
            path = saveFile(
                    upRoot,
                    fileParents,
                    file.getBytes(),
                    (isRename) ? null : name.substring(0,
                            name.lastIndexOf(".")),
                    name.substring(name.lastIndexOf(".") + 1));
        } catch(IOException e) {
            LOG.error("file store failed", e);
        }
        return path;
    }

    public static String saveFile(String upRoot, String fileParents,
                                  byte[] file, String fileName, String suffix)
            throws IOException {
        if((file == null) && (file.length < 1))
            return null;
        StringBuilder sb = new StringBuilder();
        sb.append((fileParents == null) ? "" : fileParents);
        sb.append(FORMAT.format(new Date()));
        File dir = new File(upRoot + sb.toString());
        if(!(dir.exists()))
            dir.mkdirs();
        sb.append((fileName == null) ? Long.valueOf(System
                .currentTimeMillis()) : fileName);
        sb.append("." + suffix);
        File f = new File(upRoot + sb.toString());
        FileCopyUtils.copy(file, f);
        return sb.toString();
    }
}
