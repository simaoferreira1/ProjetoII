package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Projeto;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ProjetosEmEsperaView {

    private final Stage stage;

    public ProjetosEmEsperaView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;");

        // === MENU LATERAL COM STACKPANE PARA OCUPAR ALTURA ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 13px;" +
                "-fx-pref-width: 180px; -fx-pref-height: 60px; -fx-wrap-text: true;";

        Button btnSolicitacoes = new Button("📋 Solicitações de Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos em curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos para orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos em espera");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : List.of(btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout)) {
            btn.setStyle(estiloBtn);
            btn.setAlignment(Pos.CENTER);
        }

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage).show());
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoView(stage).show());
        btnProjetosEspera.setOnAction(e -> new ProjetosEmEsperaView(stage).show());
        btnLogout.setOnAction(e -> stage.close());

        menu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);

        StackPane menuContainer = new StackPane(menu);
        root.setLeft(menuContainer);

        // === TOPO COM LOGO ===
        HBox topo = new HBox();
        topo.setAlignment(Pos.TOP_RIGHT);
        topo.setPadding(new Insets(10));

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);
        topo.getChildren().add(logo);

        root.setTop(topo);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        Label titulo = new Label("Projetos em espera");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        VBox lista = new VBox(10);
        ProjetoService service = SpringContext.getBean(ProjetoService.class);
        atualizarListaProjetos(lista, service);

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        content.getChildren().add(scroll);

        root.setCenter(content);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos em Espera");
        stage.show();
    }

    private void atualizarListaProjetos(VBox lista, ProjetoService service) {
        lista.getChildren().clear();

        List<Projeto> projetos = service.listarProjetos()
                .stream()
                .filter(p -> "em espera".equalsIgnoreCase(p.getEstado()))
                .toList();

        for (Projeto projeto : projetos) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: #fff;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label(projeto.getNome());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label(projeto.getDescricao());
            info.getChildren().addAll(nomeProjeto, descricao);

            Button btnAbrir = new Button("Abrir");
            btnAbrir.setOnAction(e -> new DetalhesProjetosEmEsperaView(projeto).show());

            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");
            btnEliminar.setOnAction(e -> {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Tem a certeza que deseja eliminar?");
                confirmacao.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            service.removerProjeto(projeto.getId());
                            mostrarAlerta("Sucesso", "Projeto eliminado com sucesso.", Alert.AlertType.INFORMATION);
                            atualizarListaProjetos(lista, service);
                        } catch (Exception ex) {
                            mostrarAlerta("Erro", "Erro ao eliminar: " + ex.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
                });
            });

            HBox botoes = new HBox(10, btnAbrir, btnEliminar);
            botoes.setAlignment(Pos.CENTER_RIGHT);

            Region espacador = new Region();
            HBox.setHgrow(espacador, Priority.ALWAYS);

            card.getChildren().addAll(info, espacador, botoes);
            lista.getChildren().add(card);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
