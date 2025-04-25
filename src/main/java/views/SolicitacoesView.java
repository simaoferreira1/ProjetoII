package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.services.SolicitacaoprojetoService;
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

public class SolicitacoesView {

    private final Stage stage;

    public SolicitacoesView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // ===== MENU LATERAL =====
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 13px; " +
                "-fx-pref-width: 180px; -fx-pref-height: 60px; -fx-wrap-text: true;";

        Button btnSolicitacoes = new Button("📋 Solicitações de Projeto");
        Button btnProjetosCurso = new Button("🗂 Listar projetos em curso");
        Button btnProjetosOrcamento = new Button("💰 Listar projetos para orçamento");
        Button btnProjetosEspera = new Button("🕒 Listar projetos em espera");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : List.of(btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout)) {
            btn.setStyle(estiloBtn);
        }

        menu.getChildren().addAll(btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);

        // ===== TOPO: Título à esquerda, logo à direita =====
        HBox topo = new HBox();
        topo.setPadding(new Insets(20, 20, 10, 20));
        topo.setAlignment(Pos.CENTER_LEFT);
        topo.setSpacing(10);

        Label titulo = new Label("Projetos solicitados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox.setHgrow(titulo, Priority.ALWAYS);

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(70);
        logo.setPreserveRatio(true);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        topo.getChildren().addAll(titulo, espaco, logo);

        // ===== LISTA DE PROJETOS =====
        VBox lista = new VBox(10);
        lista.setPadding(new Insets(0, 20, 20, 20));

        SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
        List<Solicitacaoprojeto> solicitacoes = service.listarSolicitacoes();

        for (Solicitacaoprojeto sp : solicitacoes) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(15));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: #fff; -fx-background-radius: 5px;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label("Projeto " + sp.getId());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label(sp.getLocalreuniao());

            info.getChildren().addAll(nomeProjeto, descricao);

            Button btnAbrir = new Button("Abrir");
            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");

            VBox botoes = new VBox(10, btnAbrir, btnEliminar);
            botoes.setAlignment(Pos.CENTER_RIGHT);

            Region espacador = new Region();
            HBox.setHgrow(espacador, Priority.ALWAYS);

            card.getChildren().addAll(info, espacador, botoes);
            lista.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:transparent;");

        VBox centro = new VBox(topo, scroll);
        centro.setStyle("-fx-background-color: #f9f9f9;");

        // === Final ===
        root.setLeft(menu);
        root.setCenter(centro);

        Scene scene = new Scene(root, 920, 635);
        stage.setScene(scene);
        stage.setTitle("Solicitações de Projeto");
        stage.show();
    }


}

