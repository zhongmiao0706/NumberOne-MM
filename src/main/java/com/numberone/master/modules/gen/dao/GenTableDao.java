/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.gen.dao;

import com.numberone.master.common.persistence.CrudDao;
import com.numberone.master.common.persistence.annotation.MyBatisDao;
import com.numberone.master.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @author numberone
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}
