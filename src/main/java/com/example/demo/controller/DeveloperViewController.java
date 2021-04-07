package com.example.demo.controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
@Join(path = "/developer-view/{id}", to = "/developer-view.jsf")
@Component(value = "developerView")
@ELBeanName(value = "developerView")
public class DeveloperViewController {

	@Autowired
	DeveloperRepo repo;

	private Developer developer = new Developer();
	private String newSkill = "";

	@Deferred
	@RequestAction
	@IgnorePostback
	public void getDeveloperDetails() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String id = request.getParameter("id");
		developer = repo.findById(Integer.parseInt(id)).orElse(new Developer());
	}

	public String addNewSkill() {
		if (developer.getSkills() != null) {
			developer.setSkills(developer.getSkills().concat(", ").concat(newSkill));
		} else {
			developer.setSkills(newSkill);
		}
		repo.save(developer);
		newSkill = "";
		return "/developers-list.xhtml?faces-redirect=true";
	}

	public Developer getDeveloper() {
		return developer;

	}

	/**
	 * @return the newSkill
	 */
	public String getNewSkill() {
		return newSkill;
	}

	/**
	 * @param newSkill the newSkill to set
	 */
	public void setNewSkill(String newSkill) {
		this.newSkill = newSkill;
	}
}
