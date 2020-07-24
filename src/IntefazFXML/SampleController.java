package IntefazFXML;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import EpistemicChecker.EpistemicParser;
import EpistemicChecker.FormulaManager;
import EpistemicChecker.ReadTXT;
import EpistemicChecker.Run;
import EpistemicChecker.Traductor;
import EpistemicChecker.XMLManager;
import Graphs.CreateGraph;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.fol.syntax.Negation;


public class SampleController {
	 String errorOutput = null;
	 SwingNode swingNode = new SwingNode();
	
	
	@FXML
	private Label errorLabel;
	@FXML
	private Text modelText;
	@FXML
	private ImageView adImg;
	@FXML 
	private Button selectFile;
	@FXML
	private Label pathLabel;
	@FXML
	private BorderPane root;
	@FXML
	private TextField formulaField;
	@FXML
	private TextArea returnArea;
	@FXML 
	private TabPane tabPane;
	
	@FXML
	private ImageView run;
	@FXML
	private ImageView clear;
	@FXML
	private ImageView info;
	@FXML
	private ImageView reload;
	@FXML
	private ImageView file;
	@FXML
	private ImageView logo;
	
	
	private void loadImages() throws IOException {
		BufferedImage masB = ImageIO.read(ClassLoader.getSystemResource( "mas.png" ) );
		Image masImg = SwingFXUtils.toFXImage(masB, null);
		BufferedImage reloadB = ImageIO.read(ClassLoader.getSystemResource( "reload.png" ) );
		Image reloadImg = SwingFXUtils.toFXImage(reloadB, null);
		BufferedImage helpB = ImageIO.read(ClassLoader.getSystemResource( "help.png" ) );
		Image helpImg = SwingFXUtils.toFXImage(helpB, null);
		BufferedImage runB = ImageIO.read(ClassLoader.getSystemResource( "triangulo.png" ) );
		Image runImg = SwingFXUtils.toFXImage(runB, null);
		BufferedImage clearB = ImageIO.read(ClassLoader.getSystemResource( "basura.png" ) );
		Image clearImg = SwingFXUtils.toFXImage(clearB, null);
		BufferedImage logoB = ImageIO.read(ClassLoader.getSystemResource( "logo.png" ) );
		Image logoImg = SwingFXUtils.toFXImage(logoB, null);
		BufferedImage adB = ImageIO.read(ClassLoader.getSystemResource( "ad.png" ) );
		Image adImage = SwingFXUtils.toFXImage(adB, null);
		
		run.setImage(runImg);
		reload.setImage(reloadImg);
		info.setImage(helpImg);
		file.setImage(masImg);
		clear.setImage(clearImg);
		logo.setImage(logoImg);
		adImg.setImage(adImage);
		
	}
	
	private FileChooser chooser = new FileChooser();
	
	public void loadMsg(String msg) throws IOException {
		errorLabel.setText(msg);
		BufferedImage img = ImageIO.read(ClassLoader.getSystemResource( "valid.png" ) );
		Image image = SwingFXUtils.toFXImage(img, null);
		adImg.setImage(image);	  
	}
	
	public void loadAlertError(String msg2, String msg1) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setHeaderText(msg2);
		alert.setContentText(msg1);
		alert.showAndWait();
	}
	
	public void loadError(String msg) throws IOException {
		adImg.setVisible(true);
		errorLabel.setText(msg);
		BufferedImage img = ImageIO.read( ClassLoader.getSystemResource( "resources/icons/ad.png" ) );
		Image image = SwingFXUtils.toFXImage(img, null);
		adImg.setImage(image);
	}
	
	public void error() {
			modelText.textProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println(newValue); 
		    if(newValue.isEmpty()==false) {
		    	adImg.setVisible(false);
		    	errorLabel.setText("");
		    }
		    
		});
	}
	
	public void selectFile() throws IOException {
		System.out.println("Inicializando selector de modelos");
		String strPath="";
		chooser.setTitle("Open an epistemic model");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		chooser.getExtensionFilters().add(extFilter);
		File file = chooser.showOpenDialog(root.getScene().getWindow());
		strPath = file.getPath();
		pathLabel.setText(strPath);
		modelText.setText(file.getName());		
		modelText.setVisible(true);
		final SwingNode swingNode = new SwingNode();
	    createSwingContent(swingNode);
	    Tab tab = new Tab();
	    tab.setContent(swingNode);
	    tab.setText("Model");
	    tabPane.getTabs().add(tab);
	    this.loadMsg("Model loaded");;
	}
	
	public void reload() throws IOException {
		if(pathLabel.getText().isEmpty()) {
			this.loadAlertError("Not found Model", "Please, select a model file to load");
			this.selectFile();
		}else {
		System.out.println("Recargando modelo");
		String strPath = pathLabel.getText();
		final SwingNode swingNode = new SwingNode();
	    createSwingContent(swingNode);
	    Tab tab = new Tab();
	    tabPane.getTabs().clear();
	    tab.setContent(swingNode);
	    tab.setText("Model");
	    tabPane.getTabs().add(tab);
		if(strPath.isEmpty()) {
			this.loadError("No model to reload, insert a model file");
		}
		this.loadMsg("Reloaded");
		}
	}
	
	public void help() throws FileNotFoundException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		Label lb = new Label("Atoms: p, q, r, ..., x, y, z\n\n"
				+ "Agents: a, b, c, d\n\n"
				+ "Conectives: \n"
				+ "Conjunction - \\land\n"
				+ "Disjunction - \\lor\n"
				+ "Implication - \\to\n"
				+ "Equivalence - \\leftrightarrow\n "
				+ "Negation* - \\lnot(formula)\n"
				+ "Knowledge* - Ka(formula)\n"
				+ "PosKnowledge* -Mb(formula)\n"
				+ "*IMPORTANT, you need to use the parenthesis\n\n"
				+ "If you need some help, go to the link below:"
				+ "http://unarduinoenfilosofia.xyz \n"
				);
		
		
		lb.setWrapText(true);
		alert.getDialogPane().setContent(lb);
		alert.showAndWait();
		
	}
		
	public void run() throws ParserException, IOException{
		String form = formulaField.getText();
		String filePath = pathLabel.getText();
		if(form.isEmpty()) {
			this.loadAlertError("Not found formula", "Please write a formula");
		}else if(filePath.isEmpty()){
			this.loadAlertError("Not found model", "Please insert a model");
		}else {
		this.cleanGraph();
		Run run = new Run();
		String cad = null;
		try {
			cad = run.runModelChecker(form, filePath);
			cad=cad+"\n -----------------------------------------------------";
			cad=cad+this.createMultiGraph();

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			this.loadAlertError("Not found formula", "Please insert a valid formula");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnArea.setText(cad);
		}
	}
		
		
	public String createMultiGraph() throws ParserException, IOException {
		
			String form = formulaField.getText();
			Traductor t = new Traductor();
			String cad="";
			
		 	EpistemicParser epistemicParser= new EpistemicParser();    	
	        FolSignature sig = new FolSignature();
			sig.add(new Predicate("p",0));
			sig.add(new Predicate("q",0));
			sig.add(new Predicate("r",0));
			sig.add(new Predicate("s",0));
			sig.add(new Predicate("t",0));
			sig.add(new Predicate("u",0));
			sig.add(new Predicate("v",0));
			sig.add(new Predicate("w",0));
			sig.add(new Predicate("x",0));
			sig.add(new Predicate("y",0));
			sig.add(new Predicate("z",0));
			epistemicParser.setSignature(sig);
			form=t.convertEMC(form);
		    RelationalFormula formula = epistemicParser.parseFormula(form);
		    Negation neg = new Negation(formula);
	        String newCad;
	        
	        FormulaManager sub=new FormulaManager();
			ArrayList<RelationalFormula> list = sub.getSubFormula(formula);
			ArrayList<RelationalFormula> newList = new ArrayList<RelationalFormula>();
			ArrayList<String> finalList = new ArrayList<String>();
			int numGraph=1;
			String nombre="";
			String currentFormula;
						
			for(int i=0;i<=sub.getMaxLg(list);i++) {
				newList=sub.getFormulaLg(i, list);
				System.out.println("Lg("+i+")");
				finalList.clear();
				if(newList.isEmpty()==false) {
					for(int j=0;j<newList.size();j++) {
						if(newList.get(j).getClass()==neg.getClass()) {
							currentFormula = t.convertNormal(newList.get(j).getFormula().toString());
							finalList.add("��("+currentFormula+")");
						}else {	
							currentFormula = t.convertNormal(newList.get(j).toString());
							finalList.add(currentFormula);
						}
					}
					System.out.println(finalList);
					final SwingNode swingNode = new SwingNode();
				    createSwingContentAtoms(swingNode,newList);
					Tab tab = new Tab("Graph " + String.valueOf(numGraph));
					tab.setContent(swingNode);
					tabPane.getTabs().add(tab);
					newCad="\n Graph "+String.valueOf(numGraph)+": Lg("+i+") subformula=" +finalList;
					cad=cad+newCad;
					numGraph++;
				}
			}
			return cad;
	}
			
	public void clean() throws IOException {
		System.out.println("Limpiando");
		this.cleanGraph();
		//this.reload();
		returnArea.clear();
		formulaField.clear();
		this.loadMsg("Cleared");
	
	}
	
	public void cleanGraph() {
		int i=1;
		tabPane.getTabs().clear();
		try {
			this.reload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	String strPath=pathLabel.getText();
            	CreateGraph graph = new CreateGraph();
            	System.out.println(strPath);
            	try {
            		swingNode.setContent(graph.createGraph(strPath));
				} catch (ParserConfigurationException | TransformerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });	
    }
	
    private void createSwingContentAtoms(final SwingNode swingNode, ArrayList<RelationalFormula> newList ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	String strPath=pathLabel.getText();
            	CreateGraph graph = new CreateGraph();
            	System.out.println(strPath);
            	try {
            		swingNode.setContent(graph.createGraphAtoms(strPath, newList));
				} catch (ParserConfigurationException | TransformerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
		
	@FXML
	private void initialize() throws IOException {
		this.error();
		this.loadImages();
	}
	
}	
