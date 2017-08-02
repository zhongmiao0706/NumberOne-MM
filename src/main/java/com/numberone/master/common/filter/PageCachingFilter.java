/**
 * Copyright &copy; 2012-2016 <a href="https://shop450532966.taobao.com/">NumberOne</a> All rights reserved.
 */
package com.numberone.master.common.filter;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import com.numberone.master.common.utils.SpringContextHolder;

/**
 * 页面高速缓存过滤器
 * @author numberone
 * @version 2013-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	@Override
	protected CacheManager getCacheManager() {
		this.cacheName = "pageCachingFilter";
		return cacheManager;
	}
	
}
