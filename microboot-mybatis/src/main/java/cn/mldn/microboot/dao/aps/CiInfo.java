package cn.mldn.microboot.dao.aps;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CiInfo {
    private Long id;
    private int ciType;
    private String bVersion;
    private int fileType;
    private Long uploadId;
    private FileParseParam fileParseParam;

}
