/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.sys.dao;

import com.numberone.master.common.persistence.TreeDao;
import com.numberone.master.common.persistence.annotation.MyBatisDao;
import com.numberone.master.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author numberone
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
