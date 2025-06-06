package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.models.Projeto;
import com.example.proj2.services.SolicitacaoprojetoService;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class SolicitacoesView {

    private final Stage stage;
    private final Gestordeprojeto gestor;

    public SolicitacoesView(Stage stage, Gestordeprojeto gestor) {
        this.stage = stage;
        this.gestor = gestor;
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

        Label nome = new Label("👤 Gestor: " + (gestor != null ? gestor.getNome() : "Desconhecido"));
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

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
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 0, 2); " +
                "-fx-alignment: center;";

        String estiloHover = "-fx-background-color: #e0e0e0; " +
                "-fx-scale-x: 1.02; " +
                "-fx-scale-y: 1.02;";

        Button btnSolicitacoes = criarBotao("📋 Solicitações\nde Projeto", estiloBtn, estiloHover);
        Button btnProjetosCurso = criarBotao("📂 Projetos\nem Curso", estiloBtn, estiloHover);
        Button btnProjetosPrePlaneamento = criarBotao("📝 Projetos em\nPré-Planeamento", estiloBtn, estiloHover);
        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);

        conteudoMenu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosPrePlaneamento);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage, gestor).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage, gestor).show());
        btnProjetosPrePlaneamento.setOnAction(e -> new ProjetosPrePlaneamentoView(stage, gestor).show());
        btnLogout.setOnAction(e -> stage.close());

        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Projetos Solicitados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
        List<Solicitacaoprojeto> solicitacoes = service.listarSolicitacoes();

        if (solicitacoes == null || solicitacoes.isEmpty()) {
            Label semSolicitacoes = new Label("Nenhuma solicitação de projeto encontrada.");
            semSolicitacoes.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            conteudo.getChildren().add(semSolicitacoes);
        } else {
            VBox lista = new VBox(10);
            for (Solicitacaoprojeto sp : solicitacoes) {
                HBox card = new HBox(15);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                card.setAlignment(Pos.CENTER_LEFT);

                VBox info = new VBox(5);
                String nomeCliente = sp.getCliente() != null && sp.getCliente().getNome() != null ? sp.getCliente().getNome() : "N/A";
                String emailCliente = sp.getCliente() != null && sp.getCliente().getEmail() != null ? sp.getCliente().getEmail() : "N/A";

                Label clienteLabel = new Label("Cliente: " + nomeCliente);
                clienteLabel.setStyle("-fx-font-weight: bold;");
                Label emailLabel = new Label("Email: " + emailCliente);

                info.getChildren().addAll(clienteLabel, emailLabel);

                Button btnAbrir = criarBotaoAcao("Abrir", false);
                btnAbrir.setOnAction(e -> new DetalhesSolicitacaoView(stage, sp).show());

                Button btnAceitar = criarBotaoAcao("✔ Aceitar", false);
                btnAceitar.setStyle("-fx-background-color: #ffffff; " +
                        "-fx-text-fill: green; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 6px 12px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-border-color: green; " +
                        "-fx-border-width: 1px; " +
                        "-fx-cursor: hand;");
                btnAceitar.setOnMouseEntered(e -> btnAceitar.setStyle(
                        "-fx-background-color: #ccffcc; " +
                                "-fx-text-fill: green; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 6px 12px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-border-color: green; " +
                                "-fx-border-width: 1px; " +
                                "-fx-cursor: hand; " +
                                "-fx-scale-x: 1.05; " +
                                "-fx-scale-y: 1.05;"));
                btnAceitar.setOnMouseExited(e -> btnAceitar.setStyle(
                        "-fx-background-color: #ffffff; " +
                                "-fx-text-fill: green; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 6px 12px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-border-color: green; " +
                                "-fx-border-width: 1px; " +
                                "-fx-cursor: hand;"));
                btnAceitar.setOnAction(e -> aceitarSolicitacao(sp));

                Button btnRecusar = criarBotaoAcao(" Recusar", true);
                btnRecusar.setOnAction(e -> eliminarProjeto(sp));

                HBox botoes = new HBox(10, btnAbrir, btnAceitar, btnRecusar);
                botoes.setAlignment(Pos.CENTER_RIGHT);

                Region espaco = new Region();
                HBox.setHgrow(espaco, Priority.ALWAYS);

                card.getChildren().addAll(info, espaco, botoes);
                lista.getChildren().add(card);
            }
            conteudo.getChildren().add(lista);
        }

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Solicitações de Projeto");
        stage.show();
    }

    private void aceitarSolicitacao(Solicitacaoprojeto sp) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Tem certeza que deseja aceitar esta solicitação?");
        confirmacao.setContentText("O projeto será movido para o estado 'em pré-planeamento'.");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                Projeto projeto = new Projeto();
                projeto.setNome(sp.getNome() != null ? sp.getNome() : "Projeto ID " + sp.getId());
                projeto.setDescricao(sp.getDescricao() != null ? sp.getDescricao() : "Descrição não fornecida");
                projeto.setLocalizacao(sp.getLocalreuniao() != null ? sp.getLocalreuniao() : "Não especificado");

                // ✅ Data início e fim prevista corrigidas
                projeto.setDatainicio(sp.getDatasolicitacao() != null ? sp.getDatasolicitacao() : java.time.LocalDate.now());
                projeto.setDatafimprevista(projeto.getDatainicio().plusMonths(6)); // ou outro período

                projeto.setIdcliente(sp.getCliente());
                projeto.setGestordeprojeto(gestor);
                projeto.setEstado("em pré-planeamento");

                ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
                projetoService.salvarProjeto(projeto);

                SolicitacaoprojetoService solicitacaoService = SpringContext.getBean(SolicitacaoprojetoService.class);
                solicitacaoService.eliminarSolicitacao(sp.getId());

                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("Sucesso");
                sucesso.setHeaderText(null);
                sucesso.setContentText("Solicitação aceite com sucesso! O projeto foi movido para 'em pré-planeamento'.");
                sucesso.showAndWait();

                show(); // Atualiza a vista
            }
        });
    }


    private void eliminarProjeto(Solicitacaoprojeto sp) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Tem certeza que deseja recusar esta solicitação?");
        confirmacao.setContentText("Esta ação é irreversível.");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
                service.eliminarSolicitacao(sp.getId());
                show();
            }
        });
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