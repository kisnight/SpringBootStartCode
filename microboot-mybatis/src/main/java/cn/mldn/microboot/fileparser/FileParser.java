package cn.mldn.microboot.fileparser;

import cn.mldn.microboot.Helper.VersionHelper;
import cn.mldn.microboot.dao.aps.FileParseParam;
import cn.mldn.microboot.dao.aps.FileUpload;
import cn.mldn.microboot.mapper.FileUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.List;

public abstract class FileParser <T> {
    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private VersionHelper versionHelper;

    public boolean parsrAndSave(FileParseParam fileParseParam) {
        if(fileParseParam == null) {
            return false;
        }

        FileUpload fileUpload = fileParseParam.getFileUpload();
        if(fileUpload == null) {
            return false;
        }

        if(fileUpload.getUseId() == 1) {
            return true;
        }

        String bVerson = fileParseParam.getBVersion();
        if(StringUtils.isEmpty(bVerson)) {
            return false;
        }

        if(fileUpload.getId() == null) {
            return false;
        }

        /*
            做b版本的解析，方便后期查询
         */
        versionHelper.checkBVersion(fileUpload.getId());

        /*
            统一做一次基本信息的入库操作,对dynamicperformance表进行入库操作并返回sampleId;
         */
        Long sampleId = dynamicGetSampleIdAndRefreshUploadTime(fileParseParam);
        
        List<T> result = doParse(fileParseParam);
        if(CollectionUtils.isEmpty(result)) {
            fileUploadMapper.updateFileUploadStatus(fileParseParam.getFileUpload().getId(), true, false, "");
            return false;
        }

        boolean isSuccess = doSave(fileParseParam, result);
        fileUploadMapper.updateFileUploadStatus(fileParseParam.getFileUpload().getId(), true, isSuccess, "");
        // 如果是个人调测的话就进行welink通知
        if(fileParseParam.getCiType() == 2) {
            setPersonalNextBuildStateAndSendNotice(bVerson, isSuccess, fileUpload.getFileType());
        }

        /*
            新增或者更新Redis缓存
         */
        if(isSuccess) {
            setRedisCache(result, fileParseParam, bVerson);
        }
        return isSuccess;
    }

    public abstract List<T> doParse(FileParseParam fileParseParam);
    public abstract boolean doSave(FileParseParam fileParseParam, List<T> result);

    private boolean setPersonalNextBuildStateAndSendNotice(String bversion, boolean isSuccess, int fileType) {
        return true;
    }

    private boolean setRedisCache(List<T> result, FileParseParam fileParseParam, String bVerson) {
        return true;
    }

    private Long dynamicGetSampleIdAndRefreshUploadTime(FileParseParam fileParseParam) {
        return 2345L;
    }
}
