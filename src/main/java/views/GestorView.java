package com.example.proj2.views;

import com.example.proj2.models.Gestordeprojeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GestorView {
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public GestorView(Stage stage, Gestordeprojeto gestor) {
        this.stage = stage;
        this.gestor = gestor;
    }

    public void show() {
        BorderPane layout = new BorderPane();

        // ==== MENU LATERAL PADRONIZADO ====
        VBox menu = new VBox(20);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("\uD83D\uDC64 Gestor");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-pref-width: 160px; " +
                "-fx-pref-height: 60px; " +
                "-fx-alignment: center;";

        Button btnSolicitacoes = new Button("\uD83D\uDCCB Solicita\u00e7\u00f5es\n de Projeto");
        Button btnProjetosCurso = new Button("\uD83D\uDCC2 Projetos\nem curso");
        Button btnProjetosOrcamento = new Button("\uD83D\uDCB0 Projetos\npara or\u00e7amento");
        Button btnProjetosEspera = new Button("\uD83D\uDD52 Projetos\nem espera");

        for (Button btn : new Button[]{btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera}) {
            btn.setStyle(estiloBtn);
        }

        Button btnLogout = new Button("\u21A9 Sair");
        btnLogout.setStyle(estiloBtn);

        // Navegação dos botões
        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage).show());
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoView(stage).show());
        btnProjetosEspera.setOnAction(e -> new ProjetosEmEsperaView(stage).show());
        btnLogout.setOnAction(e -> stage.close());

        VBox conteudoMenu = new VBox(20, nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera);
        conteudoMenu.setAlignment(Pos.TOP_CENTER);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);

        layout.setLeft(menu);

        // ==== LOGO CENTRAL ====
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10));

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(400);
        logo.setPreserveRatio(true);
        logo.setOpacity(0.3);

        center.getChildren().add(logo);

        layout.setCenter(center);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Painel do Gestor");
        stage.show();
    }
}
