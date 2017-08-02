/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.test.dao;

import com.numberone.master.common.persistence.TreeDao;
import com.numberone.master.common.persistence.annotation.MyBatisDao;
import com.numberone.master.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author numberone
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}