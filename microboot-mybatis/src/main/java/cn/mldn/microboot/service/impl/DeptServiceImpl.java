package cn.mldn.microboot.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.microboot.mapper.IdeptMapper;
import cn.mldn.microboot.service.IDeptService;
import cn.mldn.microboot.dao.Dept;

@Service
public class DeptServiceImpl implements IDeptService {
	@Resource
	private IdeptMapper deptDAO;
	@Override
	public List<Dept> list() {
		return this.deptDAO.findAll();
	}
	@Override
	public boolean add(Dept vo) {
		return this.deptDAO.doCreate(vo);
	}
}
