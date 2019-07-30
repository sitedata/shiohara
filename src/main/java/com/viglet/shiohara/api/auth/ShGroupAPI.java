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

package com.viglet.shiohara.api.auth;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.viglet.shiohara.api.ShJsonView;
import com.viglet.shiohara.persistence.model.auth.ShGroup;
import com.viglet.shiohara.persistence.model.auth.ShUser;
import com.viglet.shiohara.persistence.repository.auth.ShGroupRepository;
import com.viglet.shiohara.persistence.repository.auth.ShUserRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v2/group")
@Api(tags = "Group", description = "Group API")
public class ShGroupAPI {

	@Autowired
	private ShGroupRepository shGroupRepository;
	@Autowired
	private ShUserRepository shUserRepository;

	@GetMapping
	@JsonView({ ShJsonView.ShJsonViewObject.class })
	public List<ShGroup> shGroupList() {
		return shGroupRepository.findAll();
	}

	@GetMapping("/{id}")
	@JsonView({ ShJsonView.ShJsonViewObject.class })
	public ShGroup shGroupEdit(@PathVariable String id) {
		ShGroup shGroup = shGroupRepository.findById(id).get();
		List<ShGroup> shGroups = new ArrayList<>();
		shGroups.add(shGroup);
		shGroup.setShUsers(shUserRepository.findByShGroupsIn(shGroups));
		return shGroup;
	}

	@PutMapping("/{id}")
	@JsonView({ ShJsonView.ShJsonViewObject.class })
	public ShGroup shGroupUpdate(@PathVariable String id, @RequestBody ShGroup shGroup) {
		shGroupRepository.save(shGroup);
		ShGroup shGroupRepos = shGroupRepository.findById(shGroup.getId()).get();
		List<ShGroup> shGroups = new ArrayList<>();
		shGroups.add(shGroup);
		Set<ShUser> shUsers = shUserRepository.findByShGroupsIn(shGroups);
		for (ShUser shUser : shUsers) {
			shUser.getShGroups().remove(shGroupRepos);
			shUserRepository.saveAndFlush(shUser);
		}
		for (ShUser shUser : shGroup.getShUsers()) {
			ShUser shUserRepos = shUserRepository.findByUsername(shUser.getUsername());
			shUserRepos.getShGroups().add(shGroup);
			shUserRepository.saveAndFlush(shUserRepos);
		}

		return shGroup;
	}

	@Transactional
	@DeleteMapping("/{id}")
	public boolean shGroupDelete(@PathVariable String id) {
		shGroupRepository.delete(id);
		return true;
	}

	@PostMapping
	@JsonView({ ShJsonView.ShJsonViewObject.class })
	public ShGroup shGroupAdd(@RequestBody ShGroup shGroup) {

		shGroupRepository.save(shGroup);

		return shGroup;
	}

	@GetMapping("/model")
	@JsonView({ ShJsonView.ShJsonViewObject.class })
	public ShGroup shGroupStructure() {
		return new ShGroup();

	}

}
