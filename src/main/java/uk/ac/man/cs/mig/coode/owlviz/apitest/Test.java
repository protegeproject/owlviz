package uk.ac.man.cs.mig.coode.owlviz.apitest;


/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: May 4, 2005<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class Test {

	public Test() {
//		OWLModel model = ProtegeOWL.createJenaOWLModel();
//		OWLNamedClass clsA = model.createOWLNamedClass("ClassA");
//		OWLNamedClass clsB = model.createOWLNamedClass("ClassB");
//		clsB.addSuperclass(clsA);
//		final DefaultController controller = new DefaultController(new OWLKBAssertedGraphModel(model));
//		controller.setNodeRenderer(new OWLClsNodeRenderer(controller,
//		                                                  controller.getVisualisedObjectManager(),
//		                                                  new OWLClsNodeLabelRenderer(),
//		                                                  model));
//		controller.setEdgeRenderer(new OWLClsEdgeRenderer(controller));
//		controller.getVisualisedObjectManager().showObject(model.getOWLNamedClass("ClassA"));
//		controller.getVisualisedObjectManager().showObject(model.getOWLNamedClass("ClassB"));
//		OWLSomeValuesFrom sVF = model.createOWLSomeValuesFrom(model.createOWLObjectProperty("propP"),
//		                                                      clsB);
//		clsA.addSuperclass(sVF);
//		controller.getVisualisedObjectManager().showObject(sVF);
//
//		controller.getGraphView().setPreferredSize(new Dimension(400, 400));
//		JPanel panel = new JPanel() {
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				controller.getGraphView().draw((Graphics2D)g, false, false, true, true);
//			}
//		};
//
//		controller.getGraphView().revalidateGraph();
//
//
//		JFrame frm = new JFrame();
//		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frm.getContentPane().setLayout(new BorderLayout());
//		frm.getContentPane().add(panel);
//		frm.show();
//		String path = "/Users/matthewhorridge/desktop/testpng.png";
//		PNGExportFormat exportFormat = new PNGExportFormat();
//		try {
//			FileOutputStream fos = new FileOutputStream(path);
//			exportFormat.export(controller, fos);
//			try {
//				fos.close();
//			}
//			catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}

	}

	public static void main(String [] args) {
		Test t = new Test();
	}
}

