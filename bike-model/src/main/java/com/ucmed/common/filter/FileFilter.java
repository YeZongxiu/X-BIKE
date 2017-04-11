package com.ucmed.common.filter;

import com.ucmed.common.exception.ExecFilterException;
import com.ucmed.common.util.FileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ucmed on 2017/3/18.
 */
public abstract class FileFilter extends AbsNamedApiExecFilter {
    private String apiNames[] = new String[0];

    private List<String> apiParams = null;
    private int maxLength = 1024 * 1024 * 8;
    private String parentPath = "filter";
    private static final Logger LOG = LoggerFactory.getLogger(FileFilter.class);

    public void setApiParams(List<String> apiParams) {
        this.apiParams = apiParams;
        if (apiParams != null) {
            apiNames = apiParams.toArray(apiNames);
        }
    }

    public List<String> getApiParams() {
        return apiParams;
    }

    @Override
    public void filter(FilterChain filterChain, JSONObject jsonReq,
                       JSONObject jsonRes, HttpServletRequest request,
                       HttpServletResponse response) throws ExecFilterException, Exception {
        JSONObject obj = jsonReq.optJSONObject(getParamsName());
        JSONArray fails = new JSONArray();
        JSONArray successes = new JSONArray();
        JSONArray savedFiles = new JSONArray();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
            CommonsMultipartFile f = (CommonsMultipartFile) r
                    .getFile("attachment_1");
            if (f != null) {
                try {
                    if (validateFile(f.getOriginalFilename(),
                            f.getBytes().length)) {
                        String p = saveFile(f);
                        LOG.info("path:" + p);
                        if (StringUtils.hasText(p)) {
                            savedFiles.add(p);
                            successes.add("attachment_1");
                        }
                    }
                } catch(Exception e) {
                    LOG.error("", e);
                }
            }
            fails.add("attachment_1s");
            JSONObject fileUploaded = new JSONObject();
            fileUploaded.put("fail_count", fails.size());
            fileUploaded.put("fail_files", fails);
            fileUploaded.put("success_count", successes.size());
            fileUploaded.put("success_files", successes);
            fileUploaded.put("file_paths", savedFiles);
            obj.put("file_uploaded", fileUploaded);
        }
        super.filter(filterChain, jsonReq, jsonRes, request, response);
    }

    protected String saveFile(CommonsMultipartFile f) {
        int dot = f.getOriginalFilename().lastIndexOf(".");
        String filetype = null;
        if(dot != -1) {
            filetype = f.getOriginalFilename().substring(dot + 1).toLowerCase();
        }else {
            filetype = f.getOriginalFilename();
        }
        LOG.info("fileType:" + filetype);
        LOG.info("fileSize:" + f.getSize());
        if(filetype.equals("jpg") || filetype.equals("gif") || filetype.equals("jpeg") || filetype.equals("png") || filetype.equals("bmp")) {
            return FileUtil.saveFile(getImageRoot(), parentPath, f, true);
        }
        return null;
    }

    protected boolean validateFile(String originalFilename, int length) {
        return length <= maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    /**
     * 获取图像存储根目录
     *
     */
    public abstract String getImageRoot();

    /**
     * 获取api参数obj名
     *
     */
    public abstract String getParamsName();

    @Override
    public String[] getApiNames() {
        return apiNames;
    }
}