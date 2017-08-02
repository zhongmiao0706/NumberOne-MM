/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberone.master.common.service.CrudService;
import com.numberone.master.modules.test.entity.Test;
import com.numberone.master.modules.test.dao.TestDao;

/**
 * 测试Service
 * @author numberone
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
