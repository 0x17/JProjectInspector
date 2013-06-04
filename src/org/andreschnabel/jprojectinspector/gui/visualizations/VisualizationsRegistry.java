package org.andreschnabel.jprojectinspector.gui.visualizations;

import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.ITransform;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Registrierungsort für Visualisierungen.
 */
public class VisualizationsRegistry {

	public final static Map<String, IVisualization> visualizations;

	static {
		List<IVisualization> visLst = new LinkedList<IVisualization>();
		visLst.add(new BarChart());
		visLst.add(new ScatterPlot());
		visLst.add(new BoxAndWhisker());
		List<String> names = Func.map(new ITransform<IVisualization, String>() {
			@Override
			public String invoke(IVisualization vis) {
				return vis.getName();
			}
		},visLst);
		visualizations = Func.zipMap(names, visLst);
	}

}
