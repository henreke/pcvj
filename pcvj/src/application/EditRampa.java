package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import processo.Tanque;
import processo.Tanque.RampaAquecimento;

public class EditRampa {


	private MainApp mainApp;

	private ObservableList<RampaAquecimento> rampaData = FXCollections.observableArrayList();

	private Tanque tanque1;

	@FXML
	private TableView<RampaAquecimento> rampaTable;

	@FXML
    private TableColumn<RampaAquecimento, String> temperaturaColumn;
    @FXML
    private TableColumn<RampaAquecimento, String> tempoColumn;

    @FXML
    private TableColumn<RampaAquecimento, String> executadaColumn;


    @FXML
    private void initialize() {

    	tanque1 = new Tanque();

    	temperaturaColumn.setCellValueFactory(cellData -> cellData.getValue().temperaturaProperty().asString());
    	tempoColumn.setCellValueFactory(cellData -> cellData.getValue().tempoProperty().asString());
    	executadaColumn.setCellValueFactory(cellData -> cellData.getValue().finishedProperty().asString());

    	rampaTable.setItems(rampaData);
    	setTableEditable();
    }

    @FXML
    private void insertRow(){

    	int ind = tanque1.addRampaAquecimento(10,30);
    	rampaData.add(tanque1.getRampa(ind));
    }
    public EditRampa(){}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Adiciona os dados da observable list na tabela
        ///personTable.setItems(mainApp.getPersonData());
    }

    private void setTableEditable() {

        rampaTable.setEditable(true);

        // allows the individual cells to be selected

        rampaTable.getSelectionModel().cellSelectionEnabledProperty().set(true);

        // when character or numbers pressed it will start edit in editable

        // fields

        rampaTable.setOnKeyPressed(event -> {

            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {

                editFocusedCell();

            } else if (event.getCode() == KeyCode.RIGHT ||

                event.getCode() == KeyCode.TAB) {

                rampaTable.getSelectionModel().selectNext();

                event.consume();

            } else if (event.getCode() == KeyCode.LEFT) {

                // work around due to

                // TableView.getSelectionModel().selectPrevious() due to a bug

                // stopping it from working on

                // the first column in the last row of the table

                selectPrevious();

                event.consume();

            }

        });

    }

    @SuppressWarnings("unchecked")

    private void editFocusedCell() {

        final TablePosition < RampaAquecimento, ? > focusedCell = rampaTable

            .focusModelProperty().get().focusedCellProperty().get();

        rampaTable.edit(focusedCell.getRow(), focusedCell.getTableColumn());

    }

    @SuppressWarnings("unchecked")

    private void selectPrevious() {

        if (rampaTable.getSelectionModel().isCellSelectionEnabled()) {

            // in cell selection mode, we have to wrap around, going from

            // right-to-left, and then wrapping to the end of the previous line

            TablePosition < RampaAquecimento, ? > pos = rampaTable.getFocusModel()

                .getFocusedCell();

            if (pos.getColumn() - 1 >= 0) {

                // go to previous row

                rampaTable.getSelectionModel().select(pos.getRow(),getTableColumn(pos.getTableColumn(), -1));

            } else if (pos.getRow() < rampaTable.getItems().size()) {

                // wrap to end of previous row

                rampaTable.getSelectionModel().select(pos.getRow() - 1,

                    rampaTable.getVisibleLeafColumn(

                        rampaTable.getVisibleLeafColumns().size() - 1));

            }

        } else {

            int focusIndex = rampaTable.getFocusModel().getFocusedIndex();

            if (focusIndex == -1) {

                rampaTable.getSelectionModel().select(rampaTable.getItems().size() - 1);

            } else if (focusIndex > 0) {

                rampaTable.getSelectionModel().select(focusIndex - 1);

            }

        }

    }

    private TableColumn < RampaAquecimento, ? > getTableColumn(

            final TableColumn < RampaAquecimento, ? > column, int offset) {

            int columnIndex = rampaTable.getVisibleLeafIndex(column);

            int newColumnIndex = columnIndex + offset;

            return rampaTable.getVisibleLeafColumn(newColumnIndex);

        }

}
