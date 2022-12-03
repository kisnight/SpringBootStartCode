package cn.mldn.microboot.uploadasync;

import cn.mldn.microboot.dao.aps.CiInfo;
import cn.mldn.microboot.dao.aps.FileParseParam;
import cn.mldn.microboot.dao.aps.FileUpload;
import cn.mldn.microboot.fileparser.FileParser;
import cn.mldn.microboot.fileparser.FileParserManager;
import cn.mldn.microboot.service.DynamicPerformanceService;
import org.springframework.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FileUploadParseTask implements Callable<Map<String, Object>> {
    private DynamicPerformanceService dynamicPerformanceService;
    private List<CiInfo> ciInfoList;


    public FileUploadParseTask(DynamicPerformanceService dynamicPerformanceService, List<CiInfo> ciInfoList) {
        this.dynamicPerformanceService = dynamicPerformanceService;
        this.ciInfoList = ciInfoList;
    }

    @Override
    public Map<String, Object> call() {
        Map<String, Object> ret = new HashMap<>();
        parseFileUploadAsync();
        return ret;
    }

    private boolean parseFileUploadAsync() {
        if(CollectionUtils.isEmpty(ciInfoList)) {
            return false;
        }
        for(CiInfo ciInfo : ciInfoList) {
            if(ciInfo == null) {
                continue;
            }
            if(ciInfo.getUploadId() == null) {
                continue;
            }

            boolean isSuccess = importFileUpload(dynamicPerformanceService.getFileParserManager(), ciInfo.getFileParseParam());
            /*
                如果是个人调测需要回写sampleId
             */
            if(ciInfo.getCiType() == 2) {
                System.out.println("回写sampleId到mongdb中的调测记录中");
            }
            // 如果是个人调测，需要通知用户解析是否成功
            if(ciInfo.getFileType() == 2) {
                sendWelinkNotice(ciInfo.getBVersion(), ciInfo);
            }
            return isSuccess;
        }
        return true;
    }

    private boolean importFileUpload(FileParserManager fileParserManager, FileParseParam fileParseParam) {
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

//        List<String> zipFiles =

        FileParser fileParser = fileParserManager.getFileParser(fileUpload.getFileType());
        if(fileParser == null) {
            return false;
        }
        return fileParser.parsrAndSave(fileParseParam);

    }

    private boolean sendWelinkNotice(String bVersion, CiInfo ciInfo) {
        return true;
    }

}
