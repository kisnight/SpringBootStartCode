package cn.mldn.microboot.fileparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Factory {
    @Autowired
    private final Map<String, FileParser> fileParserIns = new ConcurrentHashMap<>();

    public FileParser getReportIns(String type) {
        FileParser fileParserInstance = fileParserIns.get(type);
        if(fileParserInstance == null) {
            throw new RuntimeException("未定义fileParserInstance ");
        }
        return fileParserInstance;
    }
}
