/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.numberone.master.common.service.CrudService;
import com.numberone.master.modules.cms.dao.ArticleDataDao;
import com.numberone.master.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author numberone
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
