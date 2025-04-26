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

import java.math.BigDecimal;
import java.util.List;

public class SolicitacoesView {

    private final Stage stage;

    public SolicitacoesView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;"); // fundo branco

        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 13px; -fx-pref-width: 180px; -fx-pref-height: 60px;";

        Button btnSolicitacoes = new Button("📋 Solicitações de Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos em curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos para orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos em espera");
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

        VBox lista = new VBox(10);
        lista.setPadding(new Insets(0, 20, 20, 20));

        SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);

        atualizarListaProjetos(lista, service);

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:transparent;");

        VBox centro = new VBox(topo, scroll);
        centro.setStyle("-fx-background-color: white;");

        root.setLeft(menu);
        root.setCenter(centro);

        Scene scene = new Scene(root, 920, 635);
        stage.setScene(scene);
        stage.setTitle("Solicitações de Projeto");
        stage.show();
    }

    private void atualizarListaProjetos(VBox lista, SolicitacaoprojetoService service) {
        lista.getChildren().clear();

        List<Solicitacaoprojeto> solicitacoes = service.listarSolicitacoes();
        for (Solicitacaoprojeto sp : solicitacoes) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(15));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-background-color: #fff; -fx-background-radius: 5px;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label("Projeto " + sp.getId());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label("Local: " + sp.getLocalreuniao());

            info.getChildren().addAll(nomeProjeto, descricao);

            Button btnAbrir = new Button("Abrir");
            btnAbrir.setOnAction(e -> new DetalhesSolicitacaoView(stage, sp).show());


            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red; -fx-background-color: transparent;");

            btnEliminar.setOnAction(e -> {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmar eliminação");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Tem a certeza que deseja eliminar?");
                confirmacao.showAndWait().ifPresent(resposta -> {
                    if (resposta == ButtonType.OK) {
                        try {
                            service.eliminarSolicitacao(sp.getId());
                            mostrarAlerta("Sucesso", "Solicitação eliminada com sucesso.", Alert.AlertType.INFORMATION);
                            atualizarListaProjetos(lista, service);
                        } catch (Exception ex) {
                            mostrarAlerta("Erro", "Erro ao eliminar o projeto: " + ex.getMessage(), Alert.AlertType.ERROR);
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
