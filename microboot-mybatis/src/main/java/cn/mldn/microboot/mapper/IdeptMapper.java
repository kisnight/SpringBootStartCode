package cn.mldn.microboot.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.mldn.microboot.dao.Dept;

@Mapper
public interface IdeptMapper {
	public List<Dept> findAll();
	public boolean doCreate(Dept vo) ;
}
