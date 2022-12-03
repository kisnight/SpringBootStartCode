package cn.mldn.microboot.fileparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileParserManager {
    @Autowired
    private Factory factory;

    public FileParser getFileParser(int type) {
        return factory.getReportIns(String.valueOf(type));
    }
}
