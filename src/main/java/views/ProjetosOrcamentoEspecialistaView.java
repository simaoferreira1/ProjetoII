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

public class ProjetosOrcamentoEspecialistaView {

    private final Stage stage;

    public ProjetosOrcamentoEspecialistaView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: white;");

        // === MENU LATERAL ===
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

// Layout interno do menu (para separar os itens de cima e o botão sair em baixo)
        VBox conteudoMenu = new VBox(20);
        conteudoMenu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Especialista");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-pref-width: 160px; " +
                "-fx-pref-height: 60px; " +
                "-fx-alignment: center;";

        Button btnProjetosCurso = new Button("🗂 Projetos\nem curso");
        btnProjetosCurso.setStyle(estiloBtn);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage).show());

        Button btnProjetosOrcamento = new Button("💰 Projetos\npara orçamento");
        btnProjetosOrcamento.setStyle(estiloBtn);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage).show());

        conteudoMenu.getChildren().addAll(nome, btnProjetosCurso, btnProjetosOrcamento);

// Botão Log Out
        Button btnLogout = new Button("↩ Sair");
        btnLogout.setStyle(estiloBtn);
        btnLogout.setOnAction(e -> stage.close());

// Espaço para empurrar o botão para baixo
        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);
        layout.setLeft(menu);


        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

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
            Label valor = new Label("Valor: " + orc.getValortotal() + " €");

            info.getChildren().addAll(nomeProjeto, valor);

            Button btnAbrir = new Button("Abrir");
            btnAbrir.setOnAction(e -> {
                new DetalhesProjetoOrcamentoEspecialistaView(orc).show();
            });

            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red;");
            btnEliminar.setOnAction(e -> {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação");
                confirmacao.setHeaderText("Tem certeza que deseja eliminar este projeto?");
                confirmacao.setContentText("Esta ação é irreversível.");

                confirmacao.showAndWait().ifPresent(resposta -> {
                    if (resposta == ButtonType.OK) {
                        OrcamentoprojetoService serviceInterno = SpringContext.getBean(OrcamentoprojetoService.class);
                        serviceInterno.removerOrcamentoprojeto(orc.getId());

                        // Mostrar imagem de sucesso
                        Stage sucessoStage = new Stage();
                        VBox sucessoLayout = new VBox(20);
                        sucessoLayout.setAlignment(Pos.CENTER);
                        sucessoLayout.setStyle("-fx-background-color: white; -fx-padding: 20px;");

                        ImageView sucessoImg = new ImageView(new Image(getClass().getResource("/images/sucesso.png").toExternalForm()));
                        sucessoImg.setFitHeight(100);
                        sucessoImg.setPreserveRatio(true);

                        Label sucessoLabel = new Label("Projeto eliminado com sucesso!");
                        sucessoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                        sucessoLayout.getChildren().addAll(sucessoImg, sucessoLabel);

                        Scene sucessoScene = new Scene(sucessoLayout, 300, 200);
                        sucessoStage.setScene(sucessoScene);
                        sucessoStage.setTitle("Sucesso");
                        sucessoStage.show();

                        show(); // Atualiza a lista
                    }
                });
            });

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
