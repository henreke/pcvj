package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;
import processo.Tanque;
import processo.Tanque.RampaAquecimento;

public class EditRampa {


	private TesteComandos processoApp;

	private ObservableList<RampaAquecimento> rampaData = FXCollections.observableArrayList();


	@FXML
	private TableView<RampaAquecimento> rampaTable;

	@FXML
    private TableColumn temperaturaColumn;
    @FXML
    private TableColumn tempoColumn;

    @FXML
    private TableColumn<RampaAquecimento, String> executadaColumn;




    @SuppressWarnings("unchecked")
	@FXML
    private void initialize() {


    	//rampaData.addAll(processoApp.tanque1.getArrayRampaAquecimento());
    	temperaturaColumn.setCellValueFactory(cellData -> ((CellDataFeatures<RampaAquecimento, String>) cellData).getValue().temperaturaProperty().asObject());
    	tempoColumn.setCellValueFactory(cellData -> ((CellDataFeatures<RampaAquecimento, String>) cellData).getValue().tempoProperty().asObject());
    	executadaColumn.setCellValueFactory(cellData -> cellData.getValue().finishedProperty().asString());



    	 rampaTable.setEditable(true);
         Callback<TableColumn, TableCell> cellFactory =
                 new Callback<TableColumn, TableCell>() {
                     public TableCell call(TableColumn p) {
                         return new EditingCell();
                     }
                 };


               //--- Add for Editable Cell of Value field, in Double



                 temperaturaColumn.setCellFactory(cellFactory);
                 temperaturaColumn.setOnEditCommit(
                         new EventHandler<TableColumn.CellEditEvent<RampaAquecimento, Float>>() {
                             @Override public void handle(TableColumn.CellEditEvent<RampaAquecimento, Float> t) {
                                 ((RampaAquecimento)t.getTableView().getItems().get(
                                         t.getTablePosition().getRow())).setTemperatura(t.getNewValue());
                             }
                         });
                 //---


                 Callback<TableColumn, TableCell> cellFactory2 =
                         new Callback<TableColumn, TableCell>() {
                             public TableCell call(TableColumn p) {
                                 return new EditingCell2();
                             }
                         };
                         tempoColumn.setCellFactory(cellFactory2);
                         tempoColumn.setOnEditCommit(
                                 new EventHandler<TableColumn.CellEditEvent<RampaAquecimento, Integer>>() {
                                     @Override public void handle(TableColumn.CellEditEvent<RampaAquecimento, Integer> t) {
                                         ((RampaAquecimento)t.getTableView().getItems().get(
                                                 t.getTablePosition().getRow())).setTempo(t.getNewValue());
                                     }
                                 });
                         //---


           rampaTable.setItems(rampaData);
    }

    @FXML
    private void insertRow(){

    	int ind = processoApp.HLT.addRampaAquecimento(10,30);
    	rampaData.add(processoApp.HLT.getRampa(ind));
    }

    @FXML
    private void deleteRow(){
    	int ind = rampaTable.getSelectionModel().getSelectedIndex();
    	if (ind > -1){
    		rampaData.remove(ind);
    		processoApp.HLT.removeRampaAquecimento(ind);
    	}
    }
    public EditRampa(){}

    public void setMainApp(TesteComandos processoApp) {
        this.processoApp = processoApp;
        rampaData.addAll(processoApp.HLT.getArrayRampaAquecimento());
        // Adiciona os dados da observable list na tabela
        ///personTable.setItems(mainApp.getPersonData());
    }



    class EditingCell extends TableCell<RampaAquecimento, Float> {

        private TextField textField;

        public EditingCell() {}

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Float item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }



        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit((float) Double.parseDouble(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }


    class EditingCell2 extends TableCell<RampaAquecimento, Integer> {

        private TextField textField;

        public EditingCell2() {}

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }



        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Integer.parseInt(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
