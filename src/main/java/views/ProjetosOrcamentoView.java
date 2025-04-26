package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.services.OrcamentoprojetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class ProjetosOrcamentoView {

    private final Stage stage;

    public ProjetosOrcamentoView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: white;");

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; " +
                "-fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        Button btnSolicitacoes = new Button("📋 Solicitações\nde Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos\nem curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos\npara orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos\nem espera");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : List.of(btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout)) {
            btn.setStyle(estiloBtn);
        }

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage).show());
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoView(stage).show());
        btnProjetosEspera.setOnAction(e -> new ProjetosEmEsperaView(stage).show());
        btnLogout.setOnAction(e -> stage.close());

        menu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);

        layout.setLeft(menu);

        // === CONTEÚDO PRINCIPAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        // Topo com título e logo
        HBox topo = new HBox();
        topo.setPadding(new Insets(0, 20, 0, 0));
        topo.setAlignment(Pos.TOP_RIGHT);
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);
        topo.getChildren().add(logo);

        Label titulo = new Label("Projetos para orçamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox tituloBox = new VBox(titulo, topo);
        tituloBox.setAlignment(Pos.TOP_LEFT);
        tituloBox.setPadding(new Insets(0, 20, 0, 20));

        conteudo.getChildren().add(tituloBox);

        // Lista de projetos
        VBox lista = new VBox(10);
        lista.setPadding(new Insets(10));
        lista.setMaxWidth(Double.MAX_VALUE);

        OrcamentoprojetoService service = SpringContext.getBean(OrcamentoprojetoService.class);
        List<Orcamentoprojeto> orcamentos = service.listarOrcamentoprojetos();

        for (Orcamentoprojeto orc : orcamentos) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: white;");
            card.setAlignment(Pos.CENTER_LEFT);
            card.setMaxWidth(Double.MAX_VALUE);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label("Projeto " + orc.getProjeto().getNome());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label("Valor: " + orc.getValortotal() + " €");
            info.getChildren().addAll(nomeProjeto, descricao);

            Button btnAbrir = new Button("Abrir");
            btnAbrir.setOnAction(e -> new DetalhesProjetoOrcamentoView(orc).show());

            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");
            btnEliminar.setOnAction(e -> {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Tem a certeza que deseja eliminar?");
                Optional<ButtonType> result = confirmacao.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    service.removerOrcamentoprojeto(orc.getId());
                    mostrarAlerta("Sucesso", "Projeto eliminado com sucesso.", Alert.AlertType.INFORMATION);
                    show(); // recarrega para atualizar a lista
                }
            });

            HBox botoes = new HBox(10, btnAbrir, btnEliminar);
            botoes.setAlignment(Pos.CENTER_RIGHT);

            Region espacador = new Region();
            HBox.setHgrow(espacador, Priority.ALWAYS);

            card.getChildren().addAll(info, espacador, botoes);
            lista.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        conteudo.getChildren().add(scroll);

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 920, 635);
        stage.setScene(scene);
        stage.setTitle("Projetos para Orçamento");
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
