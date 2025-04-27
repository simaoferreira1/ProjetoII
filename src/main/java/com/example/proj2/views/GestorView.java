package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Cliente;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Projeto;
import com.example.proj2.services.ClienteService;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GestorView {
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public GestorView(Stage stage, Gestordeprojeto gestor) {
        this.stage = stage;
        this.gestor = gestor;
    }

    public void show() {
        BorderPane layout = new BorderPane();

        // Criar e adicionar o menu lateral
        VBox menu = criarMenuLateral();
        layout.setLeft(menu);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Gestor");
        stage.show();
    }

    private VBox criarMenuLateral() {
        VBox menu = new VBox(20);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        VBox conteudoMenu = new VBox(20);
        conteudoMenu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor: " + (gestor != null ? gestor.getNome() : "Desconhecido"));
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 160px; " +
                "-fx-pref-height: 60px; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-border-color: #cccccc; " +
                "-fx-border-width: 1px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2); " +
                "-fx-alignment: center;";

        String estiloHover = "-fx-background-color: #e0e0e0; " +
                "-fx-scale-x: 1.02; " +
                "-fx-scale-y: 1.02;";

        Button btnRegistrarCliente = criarBotao("👥 Registrar\nCliente", estiloBtn, estiloHover);
        Button btnRegistrarProjeto = criarBotao("📋 Registrar\nProjeto", estiloBtn, estiloHover);
        Button btnSolicitacoes = criarBotao("📋 Solicitações\nde Projeto", estiloBtn, estiloHover);
        Button btnProjetosCurso = criarBotao("📂 Projetos\nem Curso", estiloBtn, estiloHover);
        Button btnProjetosPrePlaneamento = criarBotao("📝 Projetos em\nPré-Planeamento", estiloBtn, estiloHover);
        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage, gestor).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage, gestor).show());
        btnProjetosPrePlaneamento.setOnAction(e -> new ProjetosPrePlaneamentoView(stage, gestor).show());
        btnLogout.setOnAction(e -> stage.close());

        // Ação do botão "Registrar Cliente"
        btnRegistrarCliente.setOnAction(event -> {
            Dialog<Cliente> dialog = new Dialog<>();
            dialog.setTitle("Registrar Cliente");
            dialog.setHeaderText("Preencha os dados do novo cliente");

            ButtonType btnSalvar = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnSalvar, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome");
            TextField enderecoField = new TextField();
            enderecoField.setPromptText("Endereço");
            TextField emailField = new TextField();
            emailField.setPromptText("Email");
            TextField telefoneField = new TextField();
            telefoneField.setPromptText("Telefone (somente números)");
            TextField passwordField = new TextField();
            passwordField.setPromptText("Senha");

            grid.add(new Label("Nome:"), 0, 0);
            grid.add(nomeField, 1, 0);
            grid.add(new Label("Endereço:"), 0, 1);
            grid.add(enderecoField, 1, 1);
            grid.add(new Label("Email:"), 0, 2);
            grid.add(emailField, 1, 2);
            grid.add(new Label("Telefone:"), 0, 3);
            grid.add(telefoneField, 1, 3);
            grid.add(new Label("Senha:"), 0, 4);
            grid.add(passwordField, 1, 4);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnSalvar) {
                    try {
                        Cliente cliente = new Cliente();
                        cliente.setNome(nomeField.getText());
                        cliente.setEndereco(enderecoField.getText());
                        cliente.setEmail(emailField.getText());
                        cliente.setTelefone(new BigDecimal(telefoneField.getText()));
                        cliente.setPassword(passwordField.getText());

                        ClienteService clienteService = SpringContext.getBean(ClienteService.class);
                        List<Cliente> clientesExistentes = clienteService.listarClientes();
                        Long novoId = 1L;
                        if (!clientesExistentes.isEmpty()) {
                            Long maiorId = clientesExistentes.stream()
                                    .map(Cliente::getId)
                                    .filter(id -> id != null)
                                    .max(Long::compareTo)
                                    .orElse(0L);
                            novoId = maiorId + 1;
                        }
                        cliente.setId(novoId);

                        return cliente;
                    } catch (NumberFormatException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText("O telefone deve conter apenas números.");
                        error.showAndWait();
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(cliente -> {
                if (cliente != null) {
                    try {
                        ClienteService clienteService = SpringContext.getBean(ClienteService.class);
                        clienteService.salvarCliente(cliente);

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Sucesso");
                        success.setHeaderText(null);
                        success.setContentText("Cliente registrado com sucesso!");
                        success.showAndWait();
                    } catch (Exception e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText("Erro ao registrar cliente: " + e.getMessage());
                        error.showAndWait();
                    }
                }
            });
        });

        // Ação do botão "Registrar Projeto"
        btnRegistrarProjeto.setOnAction(event -> {
            ClienteService clienteService = SpringContext.getBean(ClienteService.class);
            List<Cliente> clientes = clienteService.listarClientes();

            if (clientes.isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erro");
                error.setHeaderText(null);
                error.setContentText("Nenhum cliente registrado. Registre um cliente antes de criar um projeto.");
                error.showAndWait();
                return;
            }

            Dialog<Projeto> dialog = new Dialog<>();
            dialog.setTitle("Registrar Projeto");
            dialog.setHeaderText("Preencha os dados do novo projeto");

            ButtonType btnSalvar = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnSalvar, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome do Projeto");
            TextField descricaoField = new TextField();
            descricaoField.setPromptText("Descrição");
            TextField localizacaoField = new TextField();
            localizacaoField.setPromptText("Localização");
            DatePicker dataInicioField = new DatePicker();
            dataInicioField.setPromptText("Data de Início");
            DatePicker dataFimPrevistaField = new DatePicker();
            dataFimPrevistaField.setPromptText("Data de Fim Prevista");

            ComboBox<Cliente> clienteComboBox = new ComboBox<>();
            clienteComboBox.getItems().addAll(clientes);
            clienteComboBox.setPromptText("Selecione o Cliente");
            clienteComboBox.setCellFactory(param -> new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome());
                    }
                }
            });
            clienteComboBox.setButtonCell(new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Selecione o Cliente");
                    } else {
                        setText(item.getNome());
                    }
                }
            });

            grid.add(new Label("Nome:"), 0, 0);
            grid.add(nomeField, 1, 0);
            grid.add(new Label("Descrição:"), 0, 1);
            grid.add(descricaoField, 1, 1);
            grid.add(new Label("Localização:"), 0, 2);
            grid.add(localizacaoField, 1, 2);
            grid.add(new Label("Data de Início:"), 0, 3);
            grid.add(dataInicioField, 1, 3);
            grid.add(new Label("Data de Fim Prevista:"), 0, 4);
            grid.add(dataFimPrevistaField, 1, 4);
            grid.add(new Label("Cliente:"), 0, 5);
            grid.add(clienteComboBox, 1, 5);

            dialog.getDialogPane().setContent(grid);

            Button salvarButton = (Button) dialog.getDialogPane().lookupButton(btnSalvar);
            salvarButton.setDisable(true);

            nomeField.textProperty().addListener((obs, oldValue, newValue) -> {
                salvarButton.setDisable(
                        newValue.trim().isEmpty() ||
                                descricaoField.getText().trim().isEmpty() ||
                                localizacaoField.getText().trim().isEmpty() ||
                                dataInicioField.getValue() == null ||
                                dataFimPrevistaField.getValue() == null ||
                                clienteComboBox.getValue() == null
                );
            });
            descricaoField.textProperty().addListener((obs, oldValue, newValue) -> {
                salvarButton.setDisable(
                        newValue.trim().isEmpty() ||
                                nomeField.getText().trim().isEmpty() ||
                                localizacaoField.getText().trim().isEmpty() ||
                                dataInicioField.getValue() == null ||
                                dataFimPrevistaField.getValue() == null ||
                                clienteComboBox.getValue() == null
                );
            });
            localizacaoField.textProperty().addListener((obs, oldValue, newValue) -> {
                salvarButton.setDisable(
                        newValue.trim().isEmpty() ||
                                nomeField.getText().trim().isEmpty() ||
                                descricaoField.getText().trim().isEmpty() ||
                                dataInicioField.getValue() == null ||
                                dataFimPrevistaField.getValue() == null ||
                                clienteComboBox.getValue() == null
                );
            });
            dataInicioField.valueProperty().addListener((obs, oldValue, newValue) -> {
                salvarButton.setDisable(
                        newValue == null ||
                                nomeField.getText().trim().isEmpty() ||
                                descricaoField.getText().trim().isEmpty() ||
                                localizacaoField.getText().trim().isEmpty() ||
                                dataFimPrevistaField.getValue() == null ||
                                clienteComboBox.getValue() == null
                );
            });
            dataFimPrevistaField.valueProperty().addListener((obs, oldValue, newValue) -> {
                salvarButton.setDisable(
                        newValue == null ||
                                nomeField.getText().trim().isEmpty() ||
                                descricaoField.getText().trim().isEmpty() ||
                                localizacaoField.getText().trim().isEmpty() ||
                                dataInicioField.getValue() == null ||
                                clienteComboBox.getValue() == null
                );
            });
            clienteComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
                salvarButton.setDisable(
                        newValue == null ||
                                nomeField.getText().trim().isEmpty() ||
                                descricaoField.getText().trim().isEmpty() ||
                                localizacaoField.getText().trim().isEmpty() ||
                                dataInicioField.getValue() == null ||
                                dataFimPrevistaField.getValue() == null
                );
            });

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnSalvar) {
                    Projeto projeto = new Projeto();
                    projeto.setNome(nomeField.getText());
                    projeto.setDescricao(descricaoField.getText());
                    projeto.setLocalizacao(localizacaoField.getText());
                    projeto.setDatainicio(dataInicioField.getValue());
                    projeto.setDatafimprevista(dataFimPrevistaField.getValue());
                    projeto.setIdcliente(clienteComboBox.getValue());
                    projeto.setEstado("pendente de orçamento");

                    ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
                    List<Projeto> projetosExistentes = projetoService.listarProjetos();
                    BigDecimal novoId = BigDecimal.ONE;
                    if (!projetosExistentes.isEmpty()) {
                        BigDecimal maiorId = projetosExistentes.stream()
                                .map(Projeto::getId)
                                .filter(id -> id != null)
                                .max(BigDecimal::compareTo)
                                .orElse(BigDecimal.ZERO);
                        novoId = maiorId.add(BigDecimal.ONE);
                    }
                    projeto.setId(novoId);

                    return projeto;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(projeto -> {
                if (projeto != null) {
                    try {
                        ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
                        projetoService.salvarProjeto(projeto);

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Sucesso");
                        success.setHeaderText(null);
                        success.setContentText("Projeto registrado com sucesso!");
                        success.showAndWait();
                    } catch (Exception e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText("Erro ao registrar projeto: " + e.getMessage());
                        error.showAndWait();
                    }
                }
            });
        });

        conteudoMenu.getChildren().addAll(nome, btnRegistrarCliente, btnRegistrarProjeto, btnSolicitacoes, btnProjetosCurso, btnProjetosPrePlaneamento);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);

        return menu;
    }

    private Button criarBotao(String texto, String estiloBase, String estiloHover) {
        Button button = new Button(texto);
        button.setWrapText(true);
        button.setStyle(estiloBase);
        button.setOnMouseEntered(e -> button.setStyle(estiloBase + estiloHover));
        button.setOnMouseExited(e -> button.setStyle(estiloBase));
        return button;
    }
}