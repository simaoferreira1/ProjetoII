package com.example.proj2.views;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FinanceiroView {

    private final Stage stage;
    private final Membrodepartamentofinanceiro financeiro;

    public FinanceiroView(Stage stage, Membrodepartamentofinanceiro financeiro) {
        this.stage = stage;
        this.financeiro = financeiro;
    }

    public void show() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: white;");

        // ==== MENU LATERAL PADRONIZADO ====
        VBox menu = new VBox(20);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        // Exibir o nome e ID do financeiro
        Label nome = new Label("👤 Financeiro: " + (financeiro != null && financeiro.getNome() != null ? financeiro.getNome() : "Desconhecido"));
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nome.setWrapText(true);

        Label idFinanceiro = new Label("🆔 ID: " + (financeiro != null && financeiro.getId() != null ? financeiro.getId() : "N/A"));
        idFinanceiro.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        // Estilos dos botões
        String estiloBtn = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 180px; " +
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

        // Botões
        Button btnPedidosOrcamento = criarBotao("💰 Pedidos de\nOrçamento", estiloBtn, estiloHover);
        Button btnProjetosCurso = criarBotao("🗂 Projetos\nem curso", estiloBtn, estiloHover);
        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);

        // Navegação dos botões
        btnPedidosOrcamento.setOnAction(e -> new PedidosOrcamentoView(stage, financeiro).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoFinanceiroView(stage, financeiro).show());
        btnLogout.setOnAction(e -> stage.close());

        VBox conteudoMenu = new VBox(20, nome, idFinanceiro, btnPedidosOrcamento, btnProjetosCurso);
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
        stage.setTitle("Painel do Financeiro");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.show();
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