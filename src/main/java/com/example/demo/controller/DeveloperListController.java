package com.example.demo.controller;

import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.demo.dao.DeveloperRepo;
import com.example.demo.model.Developer;

@Scope(value = "session")
@Join(path = "/developers", to = "/developers-list.jsf")
@Component(value = "developerList")
@ELBeanName(value = "developerList")
public class DeveloperListController {

	@Autowired
	DeveloperRepo repo;

	private Developer developer = new Developer();

	private List<Developer> developers;

	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {

		developers = repo.findAll();
	}

	public List<Developer> getDevelopers() {
		return developers;
	}

	public String save() {
		repo.save(developer);
		developer = new Developer();
		return "/developers-list.xhtml?faces-redirect=true";
	}

	public Developer getDeveloper() {
		return developer;

	}
}
