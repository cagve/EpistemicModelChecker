package org.interfazfx;

import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.fol.syntax.Negation;
import org.epistemic.*;
import org.graph.CreateGraph;

/**
 *  Clase controller de la interfaz javafxml
 * @version 3.01
 * @author Carlos Aguilera
 */
public class Controller {

    @FXML
    private Label errorLabel;
    @FXML
    private Text modelText;
    @FXML
    private ImageView adImg;
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

    final FileChooser chooser = new FileChooser();
    /**
     * Método encargado de cargar las imágenes almacenadas en la carpeta resources
     * @throws IOException si  no encuentra la imagen correspondiente
     */
    private void loadImages() throws IOException {
        BufferedImage masB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/mas.png" ) );
        Image masImg = SwingFXUtils.toFXImage(masB, null);
        BufferedImage reloadB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/reload.png" ) );
        Image reloadImg = SwingFXUtils.toFXImage(reloadB, null);
        BufferedImage helpB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/help.png" ) );
        Image helpImg = SwingFXUtils.toFXImage(helpB, null);
        BufferedImage runB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/triangulo.png" ) );
        Image runImg = SwingFXUtils.toFXImage(runB, null);
        BufferedImage clearB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/basura.png" ) );
        Image clearImg = SwingFXUtils.toFXImage(clearB, null);
        BufferedImage logoB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/logo.png" ) );
        Image logoImg = SwingFXUtils.toFXImage(logoB, null);
        BufferedImage adB = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/ad.png" ) );
        Image adImage = SwingFXUtils.toFXImage(adB, null);
        BufferedImage valid= ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/valid.png"));
        Image validImage = SwingFXUtils.toFXImage(adB, null);

        run.setImage(runImg);
        reload.setImage(reloadImg);
        info.setImage(helpImg);
        file.setImage(masImg);
        clear.setImage(clearImg);
        logo.setImage(logoImg);
        adImg.setImage(adImage);

    }

    /**
     *  Carga mensajes sobre procesos ejecutados correctamente. Genera un diálogo .
     * @param msg mensaje que muestra el diálogo
     * @throws IOException si  no encuentra la image
     */
    public void loadMsg(String msg) throws IOException {
        BufferedImage img = ImageIO.read(ClassLoader.getSystemResource( "org/interfazfx/img/valid.png" ) );
        Image image = SwingFXUtils.toFXImage(img, null);
        adImg.setImage(image);
        errorLabel.setText(msg);
    }

    /**
     * Carga mensaje de alerta, generalmente cuando se requiere alguna acción por parte del usuario. Genera un diálogo.
     * @param msg1 mensaje de cabezera que muestra el diálogo
     * @param msg2 mensaje que muestra el diálogo
     */
    public void loadAlertError(String msg2, String msg1) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(msg2);
        alert.setContentText(msg1);
        alert.showAndWait();
    }

    /**
     * Carga un mensaje de error. Devuelve un diálogo
     * @param msg mensaje que muestra el diálogo
     * @throws IOException si no encuentra la imagen
     */
    public void loadError(String msg) throws IOException {
        adImg.setVisible(true);
        errorLabel.setText(msg);
        BufferedImage img = ImageIO.read( ClassLoader.getSystemResource( "resources/icons/ad.png" ) );
        Image image = SwingFXUtils.toFXImage(img, null);
        adImg.setImage(image);
    }

    /**
     * Método encargado de  manejar los errores.
     * @deprecated no está optimizado
     */
    public void error() {
        modelText.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if(!newValue.isEmpty()) {
                adImg.setVisible(false);
                errorLabel.setText("");
            }
        });
    }

    /**
     *  Se utiliza para seleccionar el archivo txt que corresponde con el modelo a introducir. Para ello genera , en un diálogo, un explorador de archivos.
     */
    public void selectFile() throws IOException {
        System.out.println("Inicializando selector de modelos");
        chooser.setTitle("Open an epistemic model");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(root.getScene().getWindow());
        String strPath = file.getPath();
        pathLabel.setText(strPath);
        modelText.setText(file.getName());
        modelText.setVisible(true);
        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        Tab tab = new Tab();
        tab.setContent(swingNode);
        tab.setText("Model");
        tabPane.getTabs().add(tab);
        this.loadMsg("Model loaded");
    }

    /**
     * Se encarga de recargar el modelo previamente introducido.
     */
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

    /**
     * Genera un diálogo con algunas referencias de ayuda para el manejo de la interfaz.
     */
    public void help() {
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

    /**
     * Ejecuta el verificador epistémico.
     */
    public void run() throws ParserConfigurationException, TransformerException, IOException {
        String form = formulaField.getText();
        String filePath = pathLabel.getText();
        if(form.isEmpty()) {
            this.loadAlertError("Not found formula", "Please write a formula");
        }else if(filePath.isEmpty()){
            this.loadAlertError("Not found model", "Please insert a model");
        }else {
            this.cleanGraph();
            String cad;
            Reasoner reasoner = new Reasoner();
            System.out.println(filePath);
            cad =reasoner.runReasoner(form, filePath);
            cad=cad+"\n -----------------------------------------------------";
           cad=cad+this.createMultiGraph();

       //     } catch (ParserException e) {
       //         // TODO Auto-generated catch block
       //         this.loadAlertError("Not found formula", "Please insert a valid formula");
       //         e.printStackTrace();
       //     } catch (IOException e) {
       //         // TODO Auto-generated catch block
       //         e.printStackTrace();
       //     } catch (ParserConfigurationException e) {
       //         // TODO Auto-generated catch block
       //         e.printStackTrace();
       //     } catch (TransformerException e) {
       //         // TODO Auto-generated catch block
       //         e.printStackTrace();
       //     }
            returnArea.setText(cad);
        }
    }

    /**
     * Genera los paneles correspondientes para cada uno de los pasos que ha realizado el verificador epistémico
     */
    public String createMultiGraph() throws IOException {

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
        ArrayList<RelationalFormula> newList;
        ArrayList<String> finalList = new ArrayList<>();
        int numGraph=1;
        String currentFormula;

        for(int i=0;i<=sub.getMaxLg(list);i++) {
            newList=sub.getFormulaLg(i, list);
            System.out.println("Lg("+i+")");
            finalList.clear();
            if(!newList.isEmpty()) {
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

    /**
     * Se encarga de limpiar las áreas de texto de las interfaz; salida de texto y entrada de fórmula.
     */
    public void clean() throws IOException {
        System.out.println("Limpiando");
        this.cleanGraph();
        //this.reload();
        returnArea.clear();
        formulaField.clear();
        this.loadMsg("Cleared");

    }

    /**
     * Limpia los paneles de los grafos generados
     */
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

    /**
     * Se encarga de crear el panel Swing necesario para mostrar el grafo principal. Pues la libreria utilizada (GraphStream) devuelve un panel de este tipo.
     *  @param swingNode grafo creado a partir de la librería GraphStream
     *  @return void
     */
    private void createSwingContent(final SwingNode swingNode) {
       SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               String strPath=pathLabel.getText();
               ReadTxt read = new ReadTxt(strPath);
               EpistemicModel model = null;
               try {
                   model = new EpistemicModel(read.buildWorldSet(),read.buildRelationSet(read.buildWorldSet()));
                   CreateGraph graph = new CreateGraph();
                   System.out.println(strPath);
                   swingNode.setContent(graph.createGraph(model));
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (TransformerException e) {
                   e.printStackTrace();
               } catch (ParserConfigurationException e) {
                   e.printStackTrace();
               }
           }
       });
    }

    /**
     * Se encarga de crear el panel Swing necesario para mostrar los grafos. Pues la libreria utilizada (GraphStream) devuelve un panel de este tipo.
     */
    private void createSwingContentAtoms(final SwingNode swingNode, ArrayList<RelationalFormula> newList) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String strPath=pathLabel.getText();
                CreateGraph graph = new CreateGraph();
                ReadTxt txt = new ReadTxt(strPath);
                System.out.println(strPath);
                try {
                    EpistemicModel model = new EpistemicModel(txt.buildWorldSet(),txt.buildRelationSet(txt.buildWorldSet()));
                    swingNode.setContent(graph.createGraphAtoms(model, newList));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Se encarga de iniciar la interfaz
     *  @throws IOException
     */
    @FXML
    private void initialize() throws IOException {
        this.error();
        this.loadImages();
    }

}


