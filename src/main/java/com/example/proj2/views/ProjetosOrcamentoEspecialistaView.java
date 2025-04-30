package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.services.OrcamentoprojetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ProjetosOrcamentoEspecialistaView {

    private final Stage stage;
    private final Especialista especialista;

    public ProjetosOrcamentoEspecialistaView(Stage stage) {
        this.stage = stage;
        this.especialista = null; // Especialista não está sendo passado no construtor atual
    }

    public ProjetosOrcamentoEspecialistaView(Stage stage, Especialista especialista) {
        this.stage = stage;
        this.especialista = especialista;
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

        Label nome = new Label("👤 Especialista: " + (especialista != null && especialista.getNome() != null ? especialista.getNome() : "N/A"));
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label idEspecialista = new Label("🆔 ID: " + (especialista != null && especialista.getId() != null ? especialista.getId() : "N/A"));
        idEspecialista.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        String estiloBtn = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 160px; " +
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

        Button btnProjetosCurso = criarBotao("🗂 Projetos\nem curso", estiloBtn, estiloHover);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage, especialista).show());

        Button btnProjetosOrcamento = criarBotao("💰 Projetos\nem pré-planeamento", estiloBtn, estiloHover);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage, especialista).show());

        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);
        btnLogout.setOnAction(e -> stage.close());

        conteudoMenu.getChildren().addAll(nome, idEspecialista, btnProjetosCurso, btnProjetosOrcamento);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);
        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Projetos em pré-Planeamento)");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        OrcamentoprojetoService service = SpringContext.getBean(OrcamentoprojetoService.class);
        List<Orcamentoprojeto> orcamentos = service.listarOrcamentoprojetos();

        // Filtrar orçamentos para projetos em pré-planeamento
        List<Orcamentoprojeto> orcamentosFiltrados = orcamentos.stream()
                .filter(orc -> orc != null && orc.getProjeto() != null && orc.getProjeto().getEstado() != null &&
                        "em pré-planeamento".equalsIgnoreCase(orc.getProjeto().getEstado().trim()))
                .toList();

        if (orcamentosFiltrados.isEmpty()) {
            Label semProjetos = new Label("Nenhum projeto em pré-planeamento encontrado.");
            semProjetos.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            conteudo.getChildren().add(semProjetos);
        } else {
            VBox lista = new VBox(10);
            for (Orcamentoprojeto orc : orcamentosFiltrados) {
                HBox card = new HBox(15);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 2);");
                card.setAlignment(Pos.CENTER_LEFT);

                VBox info = new VBox(5);
                Label idOrcamento = new Label("🆔 ID Orçamento: " + (orc.getId() != null ? orc.getId() : "N/A"));
                Label idProjeto = new Label("🆔 ID Projeto: " + (orc.getProjeto() != null && orc.getProjeto().getId() != null ? orc.getProjeto().getId() : "N/A"));
                Label nomeProjeto = new Label("📌 Projeto: " + (orc.getProjeto() != null && orc.getProjeto().getNome() != null ? orc.getProjeto().getNome() : "N/A"));
                nomeProjeto.setStyle("-fx-font-weight: bold;");
                Label valor = new Label("💰 Valor: " + (orc.getValortotal() != null ? orc.getValortotal() + " €" : "N/A"));

                info.getChildren().addAll(idOrcamento, idProjeto, nomeProjeto, valor);

                Button btnAbrir = criarBotaoAcao("Abrir", false);
                btnAbrir.setOnAction(e -> new DetalhesProjetoOrcamentoEspecialistaView(orc).show());

                Button btnEliminar = criarBotaoAcao("🗑 Eliminar", true);
                btnEliminar.setOnAction(e -> {
                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacao.setTitle("Confirmação");
                    confirmacao.setHeaderText("Tem certeza que deseja eliminar este orçamento?");
                    confirmacao.setContentText("Esta ação é irreversível.");

                    confirmacao.showAndWait().ifPresent(resposta -> {
                        if (resposta == ButtonType.OK) {
                            OrcamentoprojetoService serviceInterno = SpringContext.getBean(OrcamentoprojetoService.class);
                            serviceInterno.removerOrcamentoprojeto(orc.getId());

                            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                            sucesso.setTitle("Sucesso");
                            sucesso.setHeaderText(null);
                            sucesso.setContentText("Orçamento eliminado com sucesso!");
                            sucesso.showAndWait();

                            show(); // Atualizar a view
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
        }

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos em Pré-Planeamento");
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

    private Button criarBotaoAcao(String texto, boolean vermelho) {
        String estilo = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: " + (vermelho ? "red" : "#333333") + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 6px 12px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: " + (vermelho ? "red" : "#cccccc") + "; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;";
        Button button = new Button(texto);
        button.setStyle(estilo);

        button.setOnMouseEntered(e -> button.setStyle(estilo +
                "-fx-background-color: " + (vermelho ? "#ffcccc" : "#e0e0e0") + "; " +
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle(estilo));
        return button;
    }
}