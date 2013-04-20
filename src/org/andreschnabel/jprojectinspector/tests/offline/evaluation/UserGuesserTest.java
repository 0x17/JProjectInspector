package org.andreschnabel.jprojectinspector.tests.offline.evaluation;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.UserGuesser;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserGuesserTest {

	@Test
	public void testGuessUserWithProjects() {
		List<Project> usrProjsLst = new ArrayList<Project>();

		usrProjsLst.add(new Project("Hans", "a"));
		usrProjsLst.add(new Project("Hans", "b"));
		usrProjsLst.add(new Project("Hans", "d"));

		usrProjsLst.add(new Project("Peter", "b"));
		usrProjsLst.add(new Project("Peter", "c"));

		ResponseProjects rp = new ResponseProjects("a", "b", "c", "d");

		Assert.assertEquals("Hans", UserGuesser.guessUserWithProjects(rp, usrProjsLst));
	}

}
