/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.sys.dao;

import java.util.List;

import com.numberone.master.common.persistence.CrudDao;
import com.numberone.master.common.persistence.annotation.MyBatisDao;
import com.numberone.master.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author numberone
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
