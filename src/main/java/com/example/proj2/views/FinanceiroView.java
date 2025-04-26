package com.example.proj2.views;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FinanceiroView {

    private final Stage stage;
    private final Membrodepartamentofinanceiro financeiro;

    public FinanceiroView(Stage stage, Membrodepartamentofinanceiro financeiro) {
        this.stage = stage;
        this.financeiro = financeiro;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // ==== MENU LATERAL ====
        VBox menu = new VBox(15); // Espaçamento de 15 entre os elementos, como na GestorView
        menu.setStyle("-fx-background-color: #f1c40f;"); // Mesma cor amarela da GestorView
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        // Título "Financeiro" com ícone de usuário
        Label nome = new Label("👤 Financeiro");
        nome.setStyle("-fx-font-size: 16px;"); // Mesma fonte da GestorView

        // Estilo comum para botões
        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        // Botões com ícones Unicode e texto em várias linhas
        Button btnPedidosOrcamento = new Button("💰 Pedidos de \nOrçamento");
        Button btnProjetosCurso = new Button("🗂 Projetos \nem Curso");
        Button btnLogout = new Button("↩ Log Out");

        // Aplica o mesmo estilo a todos os botões
        for (Button btn : new Button[]{btnPedidosOrcamento, btnProjetosCurso, btnLogout}) {
            btn.setStyle(estiloBtn);
        }

        // Ação do botão "Log Out" (fecha a aplicação por agora)
        btnLogout.setOnAction(event -> stage.close());

        // Adicionar elementos à sidebar
        menu.getChildren().addAll(nome, btnPedidosOrcamento, btnProjetosCurso, btnLogout);

        // ==== CONTEÚDO CENTRAL ====
        VBox center = new VBox(10);
        center.setAlignment(Pos.CENTER);

        // Logo (usando a mesma imagem da GestorView)
        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        imageView.setFitHeight(400); // Imagem maior, como na GestorView
        imageView.setPreserveRatio(true);
        imageView.setOpacity(0.3); // Opacidade reduzida, como na GestorView

        center.getChildren().add(imageView);

        // Adiciona ao layout principal
        root.setLeft(menu);
        root.setCenter(center);

        // Configurar a cena e o palco
        Scene scene = new Scene(root, 900, 600); // Mesmo tamanho da GestorView
        stage.setScene(scene);
        stage.setTitle("Painel do Financeiro");
        stage.show();
    }
}