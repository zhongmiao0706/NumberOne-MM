/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.oa.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.numberone.master.common.persistence.Page;
import com.numberone.master.common.service.CrudService;
import com.numberone.master.common.utils.StringUtils;
import com.numberone.master.modules.act.service.ActTaskService;
import com.numberone.master.modules.act.utils.ActUtils;
import com.numberone.master.modules.oa.entity.TestAudit;
import com.numberone.master.modules.oa.dao.TestAuditDao;

/**
 * 审批Service
 * @author numberone
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class TestAuditService extends CrudService<TestAuditDao, TestAudit> {

	@Autowired
	private ActTaskService actTaskService;
	
	public TestAudit getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	public Page<TestAudit> findPage(Page<TestAudit> page, TestAudit testAudit) {
		testAudit.setPage(page);
		page.setList(dao.findList(testAudit));
		return page;
	}
	
	/**
	 * 审核新增或编辑
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void save(TestAudit testAudit) {
		
		// 申请发起
		if (StringUtils.isBlank(testAudit.getId())){
			testAudit.preInsert();
			dao.insert(testAudit);
			
			// 启动流程
			actTaskService.startProcess(ActUtils.PD_TEST_AUDIT[0], ActUtils.PD_TEST_AUDIT[1], testAudit.getId(), testAudit.getContent());
			
		}
		
		// 重新编辑申请		
		else{
			testAudit.preUpdate();
			dao.update(testAudit);

			testAudit.getAct().setComment(("yes".equals(testAudit.getAct().getFlag())?"[重申] ":"[销毁] ")+testAudit.getAct().getComment());
			
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(testAudit.getAct().getFlag())? "1" : "0");
			actTaskService.complete(testAudit.getAct().getTaskId(), testAudit.getAct().getProcInsId(), testAudit.getAct().getComment(), testAudit.getContent(), vars);
		}
	}

	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void auditSave(TestAudit testAudit) {
		
		// 设置意见
		testAudit.getAct().setComment(("yes".equals(testAudit.getAct().getFlag())?"[同意] ":"[驳回] ")+testAudit.getAct().getComment());
		
		testAudit.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = testAudit.getAct().getTaskDefKey();

		// 审核环节
		if ("audit".equals(taskDefKey)){
			
		}
		else if ("audit2".equals(taskDefKey)){
			testAudit.setHrText(testAudit.getAct().getComment());
			dao.updateHrText(testAudit);
		}
		else if ("audit3".equals(taskDefKey)){
			testAudit.setLeadText(testAudit.getAct().getComment());
			dao.updateLeadText(testAudit);
		}
		else if ("audit4".equals(taskDefKey)){
			testAudit.setMainLeadText(testAudit.getAct().getComment());
			dao.updateMainLeadText(testAudit);
		}
		else if ("apply_end".equals(taskDefKey)){
			
		}
		
		// 未知环节，直接返回
		else{
			return;
		}
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(testAudit.getAct().getFlag())? "1" : "0");
		actTaskService.complete(testAudit.getAct().getTaskId(), testAudit.getAct().getProcInsId(), testAudit.getAct().getComment(), vars);

//		vars.put("var_test", "yes_no_test2");
//		actTaskService.getProcessEngine().getTaskService().addComment(testAudit.getAct().getTaskId(), testAudit.getAct().getProcInsId(), testAudit.getAct().getComment());
//		actTaskService.jumpTask(testAudit.getAct().getProcInsId(), testAudit.getAct().getTaskId(), "audit2", vars);
	}
	
}
