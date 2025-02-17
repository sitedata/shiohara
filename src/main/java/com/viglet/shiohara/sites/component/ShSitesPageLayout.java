/*
 * Copyright (C) 2016-2019 Alexandre Oliveira <alexandre.oliveira@viglet.com> 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.viglet.shiohara.sites.component;

import java.util.Map;

public class ShSitesPageLayout {
	private String id;
	private Map<String, Object> shContent;
	private String javascriptCode;
	private String HTML;
	private String pageCacheKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getJavascriptCode() {
		return javascriptCode;
	}
	public void setJavascriptCode(String javascriptCode) {
		this.javascriptCode = javascriptCode;
	}
	public String getHTML() {
		return HTML;
	}
	public void setHTML(String hTML) {
		HTML = hTML;
	}
	public String getPageCacheKey() {
		return pageCacheKey;
	}
	public void setPageCacheKey(String pageCacheKey) {
		this.pageCacheKey = pageCacheKey;
	}
	
	public Map<String, Object> getShContent() {
		return shContent;
	}
	public void setShContent(Map<String, Object> shContent) {
		this.shContent = shContent;
	}

	
}
