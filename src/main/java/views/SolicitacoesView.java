package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.services.SolicitacaoprojetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class SolicitacoesView {

    private final Stage stage;

    public SolicitacoesView(Stage stage) {
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

        VBox conteudoMenu = new VBox(20);
        conteudoMenu.setAlignment(Pos.TOP_CENTER);

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

        for (Button btn : List.of(btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera)) {
            btn.setStyle(estiloBtn);
        }

        conteudoMenu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera);

        Button btnLogout = new Button("\u21A9 Sair");
        btnLogout.setStyle(estiloBtn);
        btnLogout.setOnAction(e -> stage.close());

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage).show());
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoView(stage).show());
        btnProjetosEspera.setOnAction(e -> new ProjetosEmEsperaView(stage).show());

        layout.setLeft(menu);

        // === CONTEUDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Projetos Solicitados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
        List<Solicitacaoprojeto> solicitacoes = service.listarSolicitacoes();

        VBox lista = new VBox(10);
        for (Solicitacaoprojeto sp : solicitacoes) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: white;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label("Projeto ID: " + sp.getId());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label("Local: " + sp.getLocalreuniao());

            info.getChildren().addAll(nomeProjeto, descricao);

            Button btnAbrir = new Button("Abrir");
            btnAbrir.setOnAction(e -> new DetalhesSolicitacaoView(stage, sp).show());

            Button btnEliminar = new Button("\uD83D\uDDD1 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red;");
            btnEliminar.setOnAction(e -> eliminarProjeto(sp));

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
        stage.setTitle("Solicita\u00e7\u00f5es de Projeto");
        stage.show();
    }

    private void eliminarProjeto(Solicitacaoprojeto sp) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirma\u00e7\u00e3o");
        confirmacao.setHeaderText("Tem certeza que deseja eliminar esta solicita\u00e7\u00e3o?");
        confirmacao.setContentText("Esta a\u00e7\u00e3o \u00e9 irrevers\u00edvel.");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
                service.eliminarSolicitacao(sp.getId());

                Stage sucessoStage = new Stage();
                VBox sucessoLayout = new VBox(20);
                sucessoLayout.setAlignment(Pos.CENTER);
                sucessoLayout.setStyle("-fx-background-color: white; -fx-padding: 20px;");

                ImageView sucessoImg = new ImageView(new Image(getClass().getResource("/images/sucesso.png").toExternalForm()));
                sucessoImg.setFitHeight(100);
                sucessoImg.setPreserveRatio(true);

                Label sucessoLabel = new Label("Solicita\u00e7\u00e3o eliminada com sucesso!");
                sucessoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                sucessoLayout.getChildren().addAll(sucessoImg, sucessoLabel);

                Scene sucessoScene = new Scene(sucessoLayout, 300, 200);
                sucessoStage.setScene(sucessoScene);
                sucessoStage.setTitle("Sucesso");
                sucessoStage.show();

                show(); // Atualiza a tela
            }
        });
    }
}