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

package com.viglet.shiohara.component;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viglet.shiohara.persistence.model.post.ShPostAttr;

import com.viglet.shiohara.persistence.repository.post.ShPostAttrRepository;
import com.viglet.shiohara.utils.ShPostUtils;

@Component
public class ShGetRelationComponent {

	@Autowired
	private ShPostAttrRepository shPostAttrRepository;
	@Autowired
	private ShPostUtils shPostUtils;
	
	//@Cacheable(value = "component", key = "{ #root.methodName, #postAttrId}")
	public List<Map<String, ShPostAttr>> findByPostAttrId(String postAttrId) {

		ShPostAttr shPostAttr = shPostAttrRepository.findById(postAttrId).get();
		return shPostUtils.relationToMap(shPostAttr);		
	}
}
