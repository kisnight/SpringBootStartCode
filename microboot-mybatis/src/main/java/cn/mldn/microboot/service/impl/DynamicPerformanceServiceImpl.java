package cn.mldn.microboot.service.impl;

import cn.mldn.microboot.fileparser.FileParserManager;
import cn.mldn.microboot.service.DynamicPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicPerformanceServiceImpl implements DynamicPerformanceService {

    @Autowired
    private FileParserManager fileParserManager;

    @Override
    public FileParserManager getFileParserManager() {
        return fileParserManager;
    }
}
