package com.detectiveos.ui;

import com.detectiveos.engine.*;
import com.detectiveos.exception.*;
import com.detectiveos.model.*;
import com.detectiveos.service.*;
import com.detectiveos.util.ReportGenerator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.time.*;
import java.util.*;

/** Main JavaFX investigation console. */
public class DashboardView {
    private final BorderPane root = new BorderPane();
    private final User user;
    private final CaseService caseService = new CaseService();
    private final InvestigationService investigationService = new InvestigationService();
    private final ConcurrentAnalysisService concurrentAnalysisService = new ConcurrentAnalysisService();
    private final JDBCAnalyticsService jdbcAnalyticsService = new JDBCAnalyticsService();
    private final ReasoningEngine reasoningEngine = new ReasoningEngine();
    private final CaseGenerator generator = new CaseGenerator();

    private final ComboBox<CaseFile> casePicker = new ComboBox<>();
    private final Label status = new Label("SYSTEM READY");
    private final Label caseTitle = new Label("No case selected");
    private final Label caseMeta = new Label();
    private final Label progressLabel = new Label("0% investigation readiness");
    private final ProgressBar progress = new ProgressBar(0);
    private final ListView<String> suspectList = new ListView<>();
    private final ListView<String> evidenceList = new ListView<>();
    private final ListView<String> timelineList = new ListView<>();
    private final ListView<String> statementList = new ListView<>();
    private final TextArea analysisArea = new TextArea();
    private final TextArea reportArea = new TextArea();
    private final ComboBox<Suspect> evidenceSuspect = new ComboBox<>();
    private final ComboBox<Suspect> timelineSuspect = new ComboBox<>();
    private final ComboBox<Suspect> statementSuspect = new ComboBox<>();
    private final TextField suspectSearch = new TextField();
    private CaseFile current;

    public DashboardView(Stage stage, User user) {
        this.user = user;
        root.getStyleClass().add("app-root"); root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        root.setTop(buildHeader(stage));
        root.setCenter(buildContent());
        root.setBottom(buildStatusBar());
        refreshCases();
    }

    private HBox buildHeader(Stage stage) {
        HBox h = new HBox(14); h.setAlignment(Pos.CENTER_LEFT); h.setPadding(new Insets(15,22,15,22)); h.getStyleClass().add("header");
        Label brand = new Label("DETECTIVE OS"); brand.getStyleClass().add("header-title");
        Label badge = new Label("INVESTIGATION CONSOLE"); badge.getStyleClass().add("badge");
        Region spacer = new Region(); HBox.setHgrow(spacer,Priority.ALWAYS);
        casePicker.setPrefWidth(330); casePicker.setPromptText("Select active case");
        casePicker.setOnAction(e->{current=casePicker.getValue();refreshCurrent();});
        Button newCase = new Button("+ NEW CASE"); newCase.getStyleClass().add("primary-button"); newCase.setOnAction(e->createCase());
        Button logout = new Button("Logout"); logout.setOnAction(e->stage.setScene(new javafx.scene.Scene(new LoginView(stage).root(),1360,860)));
        h.getChildren().addAll(brand,badge,spacer,casePicker,newCase,logout); return h;
    }

    private VBox buildContent() {
        VBox wrap = new VBox(12); wrap.setPadding(new Insets(16));
        wrap.getChildren().addAll(buildCaseSummary(), buildTabs()); VBox.setVgrow(wrap.getChildren().get(1),Priority.ALWAYS); return wrap;
    }

    private GridPane buildCaseSummary() {
        GridPane grid = new GridPane(); grid.setHgap(12); grid.setVgap(10);
        HBox titleBox = new HBox(10); titleBox.setAlignment(Pos.CENTER_LEFT); titleBox.getChildren().addAll(caseTitle,caseMeta); caseTitle.getStyleClass().add("case-title"); caseMeta.getStyleClass().add("muted");
        VBox dossier = card(titleBox,caseMeta);
        VBox progressCard = card(new Label("INVESTIGATION READINESS"),progress,progressLabel);
        Button start = new Button("Start / Resume"); start.setOnAction(e->{if(current!=null){investigationService.start(current,user);setStatus("Investigation session started.");}});
        Button close = new Button("Close Case"); close.setOnAction(e->closeCase());
        HBox actions = new HBox(8,start,close); VBox actionCard=card(new Label("CASE CONTROL"),actions);
        grid.add(dossier,0,0); grid.add(progressCard,1,0); grid.add(actionCard,2,0);
        ColumnConstraints a=new ColumnConstraints();a.setPercentWidth(48);ColumnConstraints b=new ColumnConstraints();b.setPercentWidth(28);ColumnConstraints c=new ColumnConstraints();c.setPercentWidth(24);grid.getColumnConstraints().addAll(a,b,c);
        return grid;
    }

    private VBox buildTabs() {
        TabPane tabs = new TabPane();
        tabs.getTabs().addAll(
                new Tab("Overview", overviewPane()), new Tab("Suspects", suspectPane()), new Tab("Evidence", evidencePane()),
                new Tab("Timeline", timelinePane()), new Tab("Interviews", statementPane()), new Tab("Reasoning", analysisPane()), new Tab("Reports", reportPane()));
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); return new VBox(tabs);
    }

    private VBox overviewPane() {
        VBox box=page();
        Label title=section("CASE DOSSIER");
        TextArea dossier=new TextArea(); dossier.setEditable(false); dossier.setWrapText(true); dossier.setPrefRowCount(10);
        Button refresh=new Button("Refresh dossier"); refresh.setOnAction(e->refreshCurrent());
        Label workflow=section("INVESTIGATION WORKFLOW");
        Label flow=new Label("OBSERVE  →  COLLECT  →  QUESTION  →  RECONSTRUCT  →  ANALYZE  →  CONNECT  →  ACCUSE"); flow.getStyleClass().add("workflow");
        box.getChildren().addAll(title,dossier,workflow,flow); VBox.setVgrow(dossier,Priority.ALWAYS);
        currentDossier=dossier; return box;
    }
    private TextArea currentDossier;

    private VBox suspectPane() {
        VBox box=page();
        HBox top=new HBox(10); suspectSearch.setPromptText("Search suspects by name or motive..."); suspectSearch.setPrefWidth(340); Button search=new Button("Filter"); search.setOnAction(e->refreshSuspects()); Button all=new Button("Show All"); all.setOnAction(e->{suspectSearch.clear();refreshSuspects();}); top.getChildren().addAll(suspectSearch,search,all);
        GridPane form=new GridPane(); form.setHgap(8);form.setVgap(8);
        TextField name=new TextField(), age=new TextField(), occupation=new TextField(), motive=new TextField(), alibi=new TextField();
        name.setPromptText("Name");age.setPromptText("Age");occupation.setPromptText("Occupation");motive.setPromptText("Motive");alibi.setPromptText("Alibi");
        form.addRow(0,new Label("Name"),name,new Label("Age"),age,new Label("Occupation"),occupation); form.addRow(1,new Label("Motive"),motive,new Label("Alibi"),alibi);
        Button add=new Button("Add Suspect"); add.setOnAction(e->{try{requireCase();int a=Integer.parseInt(age.getText());current.addSuspect(new Suspect(name.getText(),a,occupation.getText(),motive.getText(),alibi.getText()));caseService.update(current);refreshCurrent();name.clear();age.clear();occupation.clear();motive.clear();alibi.clear();setStatus("Suspect added to active case.");}catch(Exception ex){showError(ex.getMessage());}});
        Button analyze=new Button("Recalculate Scores"); analyze.setOnAction(e->runAnalysis(true)); Button explain=new Button("Explain Selected"); explain.setOnAction(e->explainSelectedSuspect()); Button accuse=new Button("Accuse Selected"); accuse.getStyleClass().add("danger-button"); accuse.setOnAction(e->accuseSelected());
        box.getChildren().addAll(section("SUSPECT INTELLIGENCE"),top,form,new HBox(8,add,analyze,explain,accuse),suspectList); VBox.setVgrow(suspectList,Priority.ALWAYS); return box;
    }

    private VBox evidencePane() {
        VBox box=page(); GridPane form=new GridPane();form.setHgap(8);form.setVgap(8);
        TextField code=new TextField(), desc=new TextField(), loc=new TextField(); code.setPromptText("E-006");desc.setPromptText("Evidence description");loc.setPromptText("Location");
        ComboBox<EvidenceType> type=new ComboBox<>(FXCollections.observableArrayList(EvidenceType.values()));type.setValue(EvidenceType.PHYSICAL);Spinner<Integer> reliability=new Spinner<>(0,100,80);evidenceSuspect.setPromptText("Optional linked suspect");
        form.addRow(0,new Label("Code"),code,new Label("Type"),type,new Label("Reliability"),reliability);form.addRow(1,new Label("Description"),desc,new Label("Location"),loc,new Label("Link"),evidenceSuspect);
        Button add=new Button("Secure Evidence");add.setOnAction(e->{try{requireCase();Evidence ev=new Evidence(code.getText(),type.getValue(),desc.getText(),loc.getText(),LocalDateTime.now(),reliability.getValue());if(evidenceSuspect.getValue()!=null)ev.setLinkedSuspectId(evidenceSuspect.getValue().getId());investigationService.addEvidence(current,ev);refreshCurrent();code.clear();desc.clear();loc.clear();setStatus("Evidence secured and logged.");}catch(Exception ex){showError(ex.getMessage());}});
        box.getChildren().addAll(section("EVIDENCE LOCKER"),form,add,evidenceList);VBox.setVgrow(evidenceList,Priority.ALWAYS);return box;
    }

    private VBox timelinePane() {
        VBox box=page(); GridPane form=new GridPane();form.setHgap(8);form.setVgap(8);
        DatePicker date=new DatePicker(LocalDate.now());TextField time=new TextField("22:30"),desc=new TextField(),loc=new TextField();desc.setPromptText("Event description");loc.setPromptText("Location");timelineSuspect.setPromptText("Related person (optional)");
        form.addRow(0,new Label("Date"),date,new Label("Time"),time,new Label("Location"),loc);form.addRow(1,new Label("Description"),desc,new Label("Related"),timelineSuspect);
        Button add=new Button("Add Timeline Event");add.setOnAction(e->{try{requireCase();LocalDateTime dt=LocalDateTime.of(date.getValue(),LocalTime.parse(time.getText()));Long id=timelineSuspect.getValue()==null?null:timelineSuspect.getValue().getId();investigationService.addEvent(current,new TimelineEvent(dt,desc.getText(),loc.getText(),id));refreshCurrent();desc.clear();loc.clear();setStatus("Timeline event added.");}catch(Exception ex){showError("Invalid event: "+ex.getMessage());}});
        box.getChildren().addAll(section("TIMELINE RECONSTRUCTION"),form,add,timelineList);VBox.setVgrow(timelineList,Priority.ALWAYS);return box;
    }

    private VBox statementPane() {
        VBox box=page(); statementSuspect.setPromptText("Select speaker"); TextArea content=new TextArea();content.setPromptText("Record the suspect/witness statement...");content.setPrefRowCount(5);
        Button add=new Button("Record Statement");add.setOnAction(e->{try{requireCase();if(statementSuspect.getValue()==null)throw new IllegalArgumentException("Select a speaker.");investigationService.addStatement(current,new Statement(statementSuspect.getValue().getId(),statementSuspect.getValue().getName(),content.getText(),LocalDateTime.now()));refreshCurrent();content.clear();setStatus("Statement recorded.");}catch(Exception ex){showError(ex.getMessage());}});
        Button analyze=new Button("Check Contradictions");analyze.setOnAction(e->runAnalysis(true));
        box.getChildren().addAll(section("INTERVIEW & STATEMENT LOG"),statementSuspect,content,new HBox(8,add,analyze),statementList);VBox.setVgrow(statementList,Priority.ALWAYS);return box;
    }

    private VBox analysisPane() {
        VBox box=page();
        Label intro=new Label("The explainable reasoning engine checks statements, reconstructs the timeline and calculates weighted suspicion scores. Concurrent analysis uses three worker tasks with synchronized access to the active case.");intro.setWrapText(true);intro.getStyleClass().add("muted");
        Button run=new Button("RUN CONCURRENT ANALYSIS");run.getStyleClass().add("primary-button");run.setOnAction(e->runAnalysis(true));
        Button reflect=new Button("Inspect Domain Model (Reflection)");reflect.setOnAction(e->{if(current!=null)analysisArea.appendText("\n"+com.detectiveos.util.ReflectionInspector.describe(current)+"\n");});
        analysisArea.setEditable(false);analysisArea.setWrapText(true); VBox.setVgrow(analysisArea,Priority.ALWAYS);
        box.getChildren().addAll(section("EXPLAINABLE REASONING ENGINE"),intro,new HBox(8,run,reflect),analysisArea);return box;
    }

    private VBox reportPane() {
        VBox box=page();
        Button report=new Button("Generate Case Report");report.getStyleClass().add("primary-button");report.setOnAction(e->{try{requireCase();Path p=ReportGenerator.generate(current);reportArea.setText("REPORT GENERATED SUCCESSFULLY\n\n"+p.toAbsolutePath());setStatus("Case report generated.");}catch(Exception ex){showError(ex.getMessage());}});
        Button analytics=new Button("Run JDBC Analytics");analytics.setOnAction(e->reportArea.setText(jdbcAnalyticsService.caseSummary()+"\nTOP SUSPECTS\n------------\n"+String.join("\n",jdbcAnalyticsService.topSuspects())));
        Button history=new Button("Show Investigation History");history.setOnAction(e->reportArea.setText("ACTION HISTORY\n--------------\n"+investigationService.history().dump()));
        reportArea.setEditable(false);reportArea.setWrapText(true);VBox.setVgrow(reportArea,Priority.ALWAYS);
        box.getChildren().addAll(section("REPORTS, LOGS & JDBC ANALYTICS"),new HBox(8,report,analytics,history),reportArea);return box;
    }

    private VBox page(){VBox v=new VBox(12);v.setPadding(new Insets(18));return v;}
    private VBox card(Node... nodes){VBox v=new VBox(7,nodes);v.getStyleClass().add("card");v.setPadding(new Insets(14));return v;}
    private Label section(String text){Label l=new Label(text);l.getStyleClass().add("section-title");return l;}
    private HBox buildStatusBar(){HBox h=new HBox(status);h.setPadding(new Insets(8,18,8,18));h.getStyleClass().add("status-bar");return h;}

    private void refreshCases(){List<CaseFile> cases=caseService.all();casePicker.setItems(FXCollections.observableArrayList(cases));if(!cases.isEmpty()){current=cases.get(0);casePicker.setValue(current);refreshCurrent();}}
    private void refreshCurrent(){
        if(current==null)return;
        caseTitle.setText("CASE #"+current.getId()+"  •  "+current.getTitle());
        caseMeta.setText(current.getStatus()+"  •  "+current.getDifficulty()+"  •  "+current.getCrimeType());
        if(currentDossier!=null)currentDossier.setText("TITLE: "+current.getTitle()+"\n\nCRIME: "+current.getCrimeType()+"\nLOCATION: "+current.getLocation()+"\nDATE: "+current.getCrimeDate()+"\nSTATUS: "+current.getStatus()+"\nDIFFICULTY: "+current.getDifficulty()+"\nVICTIM: "+(current.getVictim()==null?"—":current.getVictim().getName())+"\n\n"+current.getDescription());
        refreshSuspects();
        evidenceList.setItems(FXCollections.observableArrayList(current.getEvidence().stream().map(e->e.getCode()+"  |  "+e.getType()+"  |  "+e.getDescription()+"  |  reliability "+e.getReliability()+"%").toList()));
        timelineList.setItems(FXCollections.observableArrayList(current.getTimelineEvents().stream().sorted(Comparator.comparing(TimelineEvent::getEventTime)).map(e->e.getEventTime()+"  |  "+e.getDescription()+"  |  "+e.getLocation()).toList()));
        statementList.setItems(FXCollections.observableArrayList(current.getStatements().stream().map(s->(s.isContradictionDetected()?"⚠ ":"✓ ")+s.getSpeakerName()+"  |  "+s.getContent()).toList()));
        evidenceSuspect.setItems(FXCollections.observableArrayList(current.getSuspects()));timelineSuspect.setItems(FXCollections.observableArrayList(current.getSuspects()));statementSuspect.setItems(FXCollections.observableArrayList(current.getSuspects()));
        int total=Math.max(1,current.getSuspects().size()+current.getEvidence().size()+current.getTimelineEvents().size()+current.getStatements().size());
        int complete=current.getEvidence().size()+current.getTimelineEvents().size()+current.getStatements().size();double p=Math.min(1.0,complete/(double)Math.max(1,total-1));progress.setProgress(p);progressLabel.setText(String.format(Locale.ROOT,"%.0f%% investigation readiness",p*100));
    }
    private void refreshSuspects(){if(current==null)return;String q=suspectSearch.getText()==null?"":suspectSearch.getText().trim().toLowerCase(Locale.ROOT);List<Suspect> ranked=new ArrayList<>(current.getSuspects());ranked.sort(Comparator.comparingDouble(Suspect::getSuspicionScore).reversed());if(!q.isBlank())ranked=ranked.stream().filter(s->s.getName().toLowerCase(Locale.ROOT).contains(q)||Optional.ofNullable(s.getMotive()).orElse("").toLowerCase(Locale.ROOT).contains(q)).toList();suspectList.setItems(FXCollections.observableArrayList(ranked.stream().map(s->String.format(Locale.ROOT,"%-20s  %6.1f/100   | motive: %s | alibi: %s",s.getName(),s.getSuspicionScore(),s.getMotive(),s.getAlibi())).toList()));}
    private void createCase(){try{String title="Generated Case • "+LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd MMM HH:mm"));CaseFile c=caseService.save(generator.generate(title,new Random().nextInt()));generator.linkGeneratedSolution(c);caseService.update(c);refreshCases();casePicker.setValue(c);current=c;refreshCurrent();setStatus("New fictional case generated.");}catch(Exception ex){showError(ex.getMessage());}}
    private void runAnalysis(boolean concurrent){if(current==null){showError("Select a case first.");return;}try{analysisArea.clear();if(concurrent)for(String line:concurrentAnalysisService.analyze(current))analysisArea.appendText("✓ "+line+"\n");for(AnalysisResult r:reasoningEngine.analyze(current)){analysisArea.appendText("\n"+r.title()+"\n");for(String f:r.findings())analysisArea.appendText("• "+f+"\n");}caseService.update(current);refreshCurrent();setStatus("Analysis complete — evidence, timeline and statements processed.");}catch(Exception ex){showError("Analysis failed: "+ex.getMessage());}}
    private Suspect selectedSuspect(){int i=suspectList.getSelectionModel().getSelectedIndex();if(i<0||current==null)return null;String q=suspectSearch.getText()==null?"":suspectSearch.getText().trim().toLowerCase(Locale.ROOT);List<Suspect> ranked=new ArrayList<>(current.getSuspects());ranked.sort(Comparator.comparingDouble(Suspect::getSuspicionScore).reversed());if(!q.isBlank())ranked=ranked.stream().filter(s->s.getName().toLowerCase(Locale.ROOT).contains(q)||Optional.ofNullable(s.getMotive()).orElse("").toLowerCase(Locale.ROOT).contains(q)).toList();return i<ranked.size()?ranked.get(i):null;}
    private void explainSelectedSuspect(){Suspect s=selectedSuspect();if(s==null){showError("Select a suspect first.");return;}runAnalysis(false);Alert a=new Alert(Alert.AlertType.INFORMATION);a.setTitle("Reasoning Explanation");a.setHeaderText(s.getName()+"  •  "+String.format(Locale.ROOT,"%.1f/100",s.getSuspicionScore()));a.setContentText(reasoningEngine.explain(s,current));a.showAndWait();}
    private void accuseSelected(){Suspect s=selectedSuspect();if(s==null){showError("Select a suspect first.");return;}Alert confirm=new Alert(Alert.AlertType.CONFIRMATION,"Accuse "+s.getName()+"? This decision will be logged.",ButtonType.YES,ButtonType.NO);confirm.setTitle("Confirm Accusation");Optional<ButtonType> result=confirm.showAndWait();if(result.isEmpty()||result.get()!=ButtonType.YES)return;try{boolean correct=investigationService.accuse(current,s);Alert a=new Alert(correct?Alert.AlertType.INFORMATION:Alert.AlertType.WARNING);a.setTitle("Accusation Result");a.setHeaderText(correct?"CASE SOLVED":"ACCUSATION NOT SUPPORTED");a.setContentText(correct?"Correct. The reasoning trail supports the selected suspect.":"The current evidence does not support this accusation. Re-check the case.");a.showAndWait();refreshCurrent();setStatus(correct?"Case solved.":"Case remains unsolved.");}catch(Exception ex){showError(ex.getMessage());}}
    private void closeCase(){if(current==null){showError("Select a case first.");return;}if(new Alert(Alert.AlertType.CONFIRMATION,"Close this investigation? Closed cases become read-only.",ButtonType.YES,ButtonType.NO).showAndWait().orElse(ButtonType.NO)==ButtonType.YES){investigationService.close(current);refreshCurrent();setStatus("Case closed and locked.");}}
    private void requireCase(){if(current==null)throw new IllegalStateException("Select a case first.");}
    private void setStatus(String s){status.setText(s.toUpperCase(Locale.ROOT));}
    private void showError(String s){new Alert(Alert.AlertType.ERROR,"Operation failed: "+s,ButtonType.OK).showAndWait();}
    public Parent root(){return root;}
}
