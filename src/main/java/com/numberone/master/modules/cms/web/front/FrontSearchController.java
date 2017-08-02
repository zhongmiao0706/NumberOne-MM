/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.modules.cms.web.front;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.numberone.master.common.persistence.Page;
import com.numberone.master.common.utils.StringUtils;
import com.numberone.master.common.web.BaseController;
import com.numberone.master.modules.cms.entity.Article;
import com.numberone.master.modules.cms.entity.Guestbook;
import com.numberone.master.modules.cms.entity.Site;
import com.numberone.master.modules.cms.service.ArticleService;
import com.numberone.master.modules.cms.service.GuestbookService;
import com.numberone.master.modules.cms.utils.CmsUtils;
import com.numberone.master.modules.sys.utils.UserUtils;

/**
 * 网站搜索Controller
 * @author numberone
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/search")
public class FrontSearchController extends BaseController{
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GuestbookService guestbookService;
	
	/**
	 * 全站搜索
	 */
	@RequestMapping(value = "")
	public String search(String t, @RequestParam(required=false) String q, @RequestParam(required=false) String qand, @RequestParam(required=false) String qnot, 
			@RequestParam(required=false) String a, @RequestParam(required=false) String cid, @RequestParam(required=false) String bd,
			@RequestParam(required=false) String ed, HttpServletRequest request, HttpServletResponse response, Model model) {
		long start = System.currentTimeMillis();
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
		
		// 重建索引（需要超级管理员权限）
		if ("cmd:reindex".equals(q)){
			if (UserUtils.getUser().isAdmin()){
				// 文章模型
				if (StringUtils.isBlank(t) || "article".equals(t)){
					articleService.createIndex();
				}
				// 留言模型
				else if ("guestbook".equals(t)){
					guestbookService.createIndex();
				}
				model.addAttribute("message", "重建索引成功，共耗时 " + (System.currentTimeMillis() - start) + "毫秒。");
			}else{
				model.addAttribute("message", "你没有执行权限。");
			}
		}
		// 执行检索
		else{
			String qStr = StringUtils.replace(StringUtils.replace(q, "，", " "), ", ", " ");
			// 如果是高级搜索
			if ("1".equals(a)){
				if (StringUtils.isNotBlank(qand)){
					qStr += " +" + StringUtils.replace(StringUtils.replace(StringUtils.replace(qand, "，", " "), ", ", " "), " ", " +"); 
				}
				if (StringUtils.isNotBlank(qnot)){
					qStr += " -" + StringUtils.replace(StringUtils.replace(StringUtils.replace(qnot, "，", " "), ", ", " "), " ", " -"); 
				}
			}
			// 文章检索
			if (StringUtils.isBlank(t) || "article".equals(t)){
				Page<Article> page = articleService.search(new Page<Article>(request, response), qStr, cid, bd, ed);
				page.setMessage("匹配结果，共耗时 " + (System.currentTimeMillis() - start) + "毫秒。");
				model.addAttribute("page", page);
			}
			// 留言检索
			else if ("guestbook".equals(t)){
				Page<Guestbook> page = guestbookService.search(new Page<Guestbook>(request, response), qStr, bd, ed);
				page.setMessage("匹配结果，共耗时 " + (System.currentTimeMillis() - start) + "毫秒。");
				model.addAttribute("page", page);
			}
			
		}
		model.addAttribute("t", t);// 搜索类型
		model.addAttribute("q", q);// 搜索关键字
		model.addAttribute("qand", qand);// 包含以下全部的关键词
		model.addAttribute("qnot", qnot);// 不包含以下关键词
		model.addAttribute("cid", cid);// 搜索类型
		return "modules/cms/front/themes/"+site.getTheme()+"/frontSearch";
	}
	
}
