package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

/**
 * Ventana para listar usuarios usando JavaFX
 */
@Component
public class VentanaListarUsuarios {

	private Stage stage;
	private TableView<Usuario> tableUsuarios;
	private ControlListarUsuarios control;
	private boolean initialized = false;

	/**
	 * Constructor without UI initialization
	 */
	public VentanaListarUsuarios() {
		// Don't initialize JavaFX components in constructor
	}
	
	/**
	 * Initialize UI components on the JavaFX application thread
	 */
	private void initializeUI() {
		if (initialized) {
			return;
		}
		
		// Create UI only if we're on JavaFX thread
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(this::initializeUI);
			return;
		}
		
		stage = new Stage();
		stage.setTitle("Lista de Usuarios");
		
		// Create UI components
		Label lblTitulo = new Label("Usuarios Registrados");
		
		// Create table
		tableUsuarios = new TableView<>();
		
		// Configure columns
		TableColumn<Usuario, Long> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
		
		TableColumn<Usuario, String> nombreColumn = new TableColumn<>("Nombre");
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		TableColumn<Usuario, String> apellidoColumn = new TableColumn<>("Apellido");
		apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
		
		TableColumn<Usuario, Integer> edadColumn = new TableColumn<>("Edad");
		edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
		
		// Add Actions column with delete button
		TableColumn<Usuario, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(col -> new TableCell<>() {
			private final Button deleteButton = new Button("Eliminar");
			
			{
				deleteButton.setOnAction(e -> {
					Usuario usuario = getTableView().getItems().get(getIndex());
					
					// Mostrar diálogo de confirmación
					Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
					confirmAlert.setTitle("Confirmar eliminación");
					confirmAlert.setHeaderText("¿Está seguro que desea eliminar este usuario?");
					confirmAlert.setContentText("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
					confirmAlert.initOwner(stage);
					
					confirmAlert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							try {
								control.eliminaUsuario(usuario);
								
								// Mostrar mensaje de éxito
								Alert successAlert = new Alert(AlertType.INFORMATION);
								successAlert.setTitle("Éxito");
								successAlert.setHeaderText("Usuario eliminado");
								successAlert.setContentText("El usuario ha sido eliminado correctamente.");
								successAlert.initOwner(stage);
								successAlert.showAndWait();
								
								// Actualizar la tabla después de eliminar
								Platform.runLater(() -> {
									List<Usuario> usuarios = control.getUsuarios();
									tableUsuarios.setItems(FXCollections.observableArrayList(usuarios));
								});
								
							} catch (Exception ex) {
								Alert errorAlert = new Alert(AlertType.ERROR);
								errorAlert.setTitle("Error");
								errorAlert.setHeaderText("Error al eliminar usuario");
								errorAlert.setContentText("No se pudo eliminar el usuario. " + ex.getMessage());
								errorAlert.initOwner(stage);
								errorAlert.showAndWait();
							}
						}
					});
				});
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(deleteButton);
				}
			}
		});
		
		tableUsuarios.getColumns().addAll(idColumn, nombreColumn, apellidoColumn, edadColumn, actionsColumn);
		
		Button btnCerrar = new Button("Cerrar");
		btnCerrar.setOnAction(e -> stage.close());
		
		// Layout
		VBox vboxTop = new VBox(10);
		vboxTop.setPadding(new Insets(10));
		vboxTop.getChildren().add(lblTitulo);
		
		VBox vboxBottom = new VBox(10);
		vboxBottom.setPadding(new Insets(10));
		vboxBottom.getChildren().add(btnCerrar);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(vboxTop);
		borderPane.setCenter(tableUsuarios);
		borderPane.setBottom(vboxBottom);
		
		Scene scene = new Scene(borderPane, 450, 400);
		stage.setScene(scene);
		
		initialized = true;
	}
	
	/**
	 * Muestra la ventana y carga los usuarios
	 * 
	 * @param control El controlador asociado
	 * @param usuarios La lista de usuarios a mostrar
	 */
	public void muestra(ControlListarUsuarios control, List<Usuario> usuarios) {
		this.control = control;
		
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> this.muestra(control, usuarios));
			return;
		}
		
		initializeUI();
		
		ObservableList<Usuario> data = FXCollections.observableArrayList(usuarios);
		tableUsuarios.setItems(data);
		
		stage.show();
	}
}
