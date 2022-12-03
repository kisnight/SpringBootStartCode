package cn.mldn.microboot.dao.aps;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileParseParam {
    private Long id;
    private FileUpload fileUpload;
    private String bVersion;
    private int ciType;
}
