package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.services.OrcamentoprojetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ProjetosOrcamentoView {

    private final Stage stage;

    public ProjetosOrcamentoView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane layout = new BorderPane();

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(0, 20, 20, 20)); // Top 0 para remover o espaço
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        Button btnSolicitacoes = new Button("📋 Solicitações\nde Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos\nem curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos\npara orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos\nem espera");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : new Button[]{btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout}) {
            btn.setStyle(estiloBtn);
        }

        menu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);
        layout.setLeft(menu);

        // === LOGO DIREITA ===
        VBox logoBox = new VBox();
        logoBox.setAlignment(Pos.TOP_RIGHT);
        logoBox.setPadding(new Insets(10, 10, 0, 0)); // Posição no canto superior direito

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);
        logoBox.getChildren().add(logo);

        layout.setRight(logoBox);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20, 20, 20, 20));

        Label titulo = new Label("Projetos para orçamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        OrcamentoprojetoService service = SpringContext.getBean(OrcamentoprojetoService.class);
        List<Orcamentoprojeto> orcamentos = service.listarOrcamentoprojetos();

        VBox lista = new VBox(10);
        for (Orcamentoprojeto orc : orcamentos) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: white;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label("Projeto " + orc.getProjeto().getNome());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label("Valor: " + orc.getValortotal() + " €");
            info.getChildren().addAll(nomeProjeto, descricao);

            Button btnAbrir = new Button("Abrir");
            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red;");

            HBox botoes = new HBox(10, btnAbrir, btnEliminar);
            botoes.setAlignment(Pos.CENTER_RIGHT);

            Region espaco = new Region();
            HBox.setHgrow(espaco, Priority.ALWAYS);

            card.getChildren().addAll(info, espaco, botoes);
            lista.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        conteudo.getChildren().add(scroll);
        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos para Orçamento");
        stage.show();
    }
}
