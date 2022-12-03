package cn.mldn.microboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileUploadMapper {
	boolean updateFileUploadStatus(@Param("fileUploadId") Long fileUploadId,
								   @Param("status") boolean status,
								   @Param("isParsedSuccess") boolean isParsedSuccess,
								   @Param("reason") String reason);
}
