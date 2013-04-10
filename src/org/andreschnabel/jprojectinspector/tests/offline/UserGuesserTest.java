package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.evaluation.projects.UserProject;
import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjects;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.evaluation.survey.UserGuesser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserGuesserTest {

	@Test
	public void testGuessUserWithProjects() {
		List<UserProject> usrProjsLst = new ArrayList<UserProject>();
		usrProjsLst.add(new UserProject("Hans", "a"));
		usrProjsLst.add(new UserProject("Hans", "b"));
		usrProjsLst.add(new UserProject("Peter", "b"));
		UserProjects projs = new UserProjects(usrProjsLst);
		ResponseProjects rp = new ResponseProjects("a", "b", "c", "d");
		Assert.assertEquals("Hans", UserGuesser.guessUserWithProjects(rp, projs.usrProjs));
	}

}
