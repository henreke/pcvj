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


	private MainApp mainApp;

	private ObservableList<RampaAquecimento> rampaData = FXCollections.observableArrayList();

	private Tanque tanque1;

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

    	tanque1 = new Tanque();

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

    	int ind = tanque1.addRampaAquecimento(10,30);
    	rampaData.add(tanque1.getRampa(ind));
    }

    @FXML
    private void deleteRow(){
    	int ind = rampaTable.getSelectionModel().getSelectedIndex();
    	if (ind > -1)
    		rampaData.remove(ind);
    }
    public EditRampa(){}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Adiciona os dados da observable list na tabela
        ///personTable.setItems(mainApp.getPersonData());
    }

//    private void setTableEditable() {
//
//        rampaTable.setEditable(true);
//
//        // allows the individual cells to be selected
//
//        rampaTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
//
//        // when character or numbers pressed it will start edit in editable
//
//        // fields
//
//        rampaTable.setOnKeyPressed(event -> {
//
//            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
//
//                editFocusedCell();
//
//            } else if (event.getCode() == KeyCode.RIGHT ||
//
//                event.getCode() == KeyCode.TAB) {
//
//                rampaTable.getSelectionModel().selectNext();
//
//                event.consume();
//
//            } else if (event.getCode() == KeyCode.LEFT) {
//
//                // work around due to
//
//                // TableView.getSelectionModel().selectPrevious() due to a bug
//
//                // stopping it from working on
//
//                // the first column in the last row of the table
//
//                selectPrevious();
//
//                event.consume();
//
//            }
//
//        });
//
//    }
//
//    @SuppressWarnings("unchecked")
//
//    private void editFocusedCell() {
//
//        final TablePosition < RampaAquecimento, ? > focusedCell = rampaTable
//
//            .focusModelProperty().get().focusedCellProperty().get();
//
//        rampaTable.edit(focusedCell.getRow(), focusedCell.getTableColumn());
//
//    }
//
//    @SuppressWarnings("unchecked")
//
//    private void selectPrevious() {
//
//        if (rampaTable.getSelectionModel().isCellSelectionEnabled()) {
//
//            // in cell selection mode, we have to wrap around, going from
//
//            // right-to-left, and then wrapping to the end of the previous line
//
//            TablePosition < RampaAquecimento, ? > pos = rampaTable.getFocusModel()
//
//                .getFocusedCell();
//
//            if (pos.getColumn() - 1 >= 0) {
//
//                // go to previous row
//
//                rampaTable.getSelectionModel().select(pos.getRow(),getTableColumn(pos.getTableColumn(), -1));
//
//            } else if (pos.getRow() < rampaTable.getItems().size()) {
//
//                // wrap to end of previous row
//
//                rampaTable.getSelectionModel().select(pos.getRow() - 1,
//
//                    rampaTable.getVisibleLeafColumn(
//
//                        rampaTable.getVisibleLeafColumns().size() - 1));
//
//            }
//
//        } else {
//
//            int focusIndex = rampaTable.getFocusModel().getFocusedIndex();
//
//            if (focusIndex == -1) {
//
//                rampaTable.getSelectionModel().select(rampaTable.getItems().size() - 1);
//
//            } else if (focusIndex > 0) {
//
//                rampaTable.getSelectionModel().select(focusIndex - 1);
//
//            }
//
//        }
//
//    }
//
//    private TableColumn < RampaAquecimento, ? > getTableColumn(
//
//            final TableColumn < RampaAquecimento, ? > column, int offset) {
//
//            int columnIndex = rampaTable.getVisibleLeafIndex(column);
//
//            int newColumnIndex = columnIndex + offset;
//
//            return rampaTable.getVisibleLeafColumn(newColumnIndex);
//
//        }
//
//
//    class EditingCell extends TableCell<RampaAquecimento, String> {
//
//        private TextField textField;
//
//        public EditingCell() {
//        }
//
//        @Override
//        public void startEdit() {
//            super.startEdit();
//
//            if (textField == null) {
//                createTextField();
//            }
//
//            setGraphic(textField);
//            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//            textField.selectAll();
//        }
//
//        @Override
//        public void cancelEdit() {
//            super.cancelEdit();
//
//            setText(String.valueOf(getItem()));
//            setContentDisplay(ContentDisplay.TEXT_ONLY);
//        }
//
//        @Override
//        public void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//
//            if (empty) {
//                setText(null);
//                setGraphic(textField);
//            } else {
//                if (isEditing()) {
//                    if (textField != null) {
//                        textField.setText(getString());
//                    }
//                    setGraphic(textField);
//                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//                } else {
//                    setText(getString());
//                    setContentDisplay(ContentDisplay.TEXT_ONLY);
//                }
//            }
//        }
//
//        private void createTextField() {
//            textField = new TextField(getString());
//            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//            textField.setOnKeyPressed(t -> {
//                if (t.getCode() == KeyCode.ENTER) {
//                    commitEdit(textField.getText());
//                } else if (t.getCode() == KeyCode.ESCAPE) {
//                    cancelEdit();
//                }
//            });
//        }
//
//        private String getString() {
//            return getItem() == null ? "" : getItem();
//        }
//    }

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
