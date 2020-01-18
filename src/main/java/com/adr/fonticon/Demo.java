//    FontIcon is a JavaFX library to use FontIcons
//    Copyright (C) 2014 Adrián Romero Corchado.
//
//    This file is part of FontAwesome
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//     
//         http://www.apache.org/licenses/LICENSE-2.0
//     
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.

package com.adr.fonticon;

import com.adr.fonticon.lip.IconBuilder;
import com.adr.fonticon.lip.decorator.*;
import com.adr.fonticon.lip.support.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author adrian
 */
public class Demo extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        TabPane tabpane = new TabPane();

        FlowPane flow = new FlowPane();
        flow.setVgap(6);
        flow.setHgap(6);
        flow.setPadding(new Insets(6));
        flow.setPrefWrapLength(100); // preferred width = 300
        flow.setPrefSize(650.0, 500.0);

        flow.getChildren().addAll(
                createButton(IconBuilder.create(IonIcons.ION_LOADING_A, 48.0).apply(new Rotate()).build()),
                createButton(IconBuilder.create(FontAwesome.FA_ANDROID, 48.0).apply(new FillPaint(Color.RED.darker())).build()),
                createButton(IconBuilder.create(FontAwesome.FA_BANK, 48.0).apply(new ShadowHole(Color.MEDIUMBLUE)).build()),
                createButton(IconBuilder.create(FontAwesome.FA_APPLE, 48.0).apply(new ShadowHole(Color.WHITE)).build()),
                createButton(IconBuilder.create(FontAwesome.FA_BELL, 48.0).apply(new ShadowHigh()).color(Color.LIGHTGREY).build()),
                createButton(IconBuilder.create(FontAwesome.FA_FILTER, 48.0).apply(new ShadowHigh()).color(Color.GREEN).build()),
                createButton(IconBuilder.create(FontAwesome.FA_BOMB, 48.0).apply(new ShadowHigh()).color(Color.RED).build()),
                createButton(IconBuilder.create(IonIcons.ION_ALERT, 48.0).apply(new ShadowHigh()).color(Color.BLUE).build()),
                createButton(IconBuilder.create(OpenIconic.ACCOUNT_LOGIN, 48.0).apply(new ShadowHole(Color.GRAY)).build()),
                createButton(IconBuilder.create(Octicons.OCTICON_ALERT, 48.0).apply(new ShadowHole(Color.YELLOW)).build()),
                createButton(IconBuilder.create(WeatherIcons.WI_CLOUDY, 48.0).apply(new ShadowHole(Color.WHITE)).build()),
//                createButton("Stacked test 1", new StackPane(
//                        IconBuilder.create(FontAwesome.FA_SQUARE, 48.0).classes("fi-icondarkblue", "fi-stack-base").build(),
//                        IconBuilder.create(FontAwesome.FA_REFRESH, 32.0).classes("fi-iconwhite", "fi-plain").build())),
                createButton("Stacked test 2", new StackPane(
                        IconBuilder.create(FontAwesome.FA_CIRCLE_THIN, 48.0).apply(new Shine(Color.LIME)).build(),
                        new Label("31")))
        );

        ScrollPane p = new ScrollPane();
        p.setContent(flow);
        Tab t = new Tab("Styles");
        t.setClosable(false);
        t.setContent(p);
        tabpane.getTabs().add(t);

        List<? extends Class<? extends Enum<? extends FIcon>>> supportedIcons = Arrays.asList(
                FontAwesome.class,
                IonIcons.class,
                Octicons.class,
                OpenIconic.class,
                WeatherIcons.class,
                Material.class,
                Holo.class
        );

        ArrayList<FIcon> all = new ArrayList<>();
        for (Class<? extends Enum<? extends FIcon>> iconFont : supportedIcons) {
            FIcon[] enumConstants = (FIcon[]) iconFont.getEnumConstants();
            all.addAll(Arrays.asList(enumConstants));
            tabpane.getTabs().add(addFontIcon(iconFont.getSimpleName(), enumConstants));
        }

        tabpane.getTabs().add(1, addFontIcon("All Icons", all.toArray(new FIcon[0])));

        Scene scene = new Scene(tabpane);
        stage.setScene(scene);
        stage.show();
    }

    private Tab addFontIcon(String name, FIcon[] icons) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        Tab t = new Tab(name);
        TextField filter = new TextField();
        filter.setPromptText("filter");
        FlowPane flow = new FlowPane();


        filter.setOnKeyReleased(event -> {
            flow.getChildren().clear();
            String f = filter.getText().toUpperCase();
            String ch = filter.getText();

            for (FIcon icon : icons) {
                if (icon.toString().contains(f) || icon.getString().contains(ch)) {
                    addButton(flow, icon);
                }
            }
        });


        flow.setVgap(6);
        flow.setHgap(6);
        flow.setPadding(new Insets(6));
        flow.setColumnHalignment(HPos.CENTER);
        flow.setRowValignment(VPos.CENTER);
        ObservableList<Screen> screens = Screen.getScreens();

        double width = screens.get(0).getBounds().getWidth() * 3 / 4;
        flow.setPrefWrapLength(width);

        flow.setPrefSize(width, screens.get(0).getBounds().getHeight() * 2 / 3);

        for (FIcon icon : icons) {
            addButton(flow, icon);
        }

        ScrollPane p = new ScrollPane();
        p.setContent(flow);

        vBox.getChildren().add(filter);
        vBox.getChildren().add(p);
        t.setClosable(false);
        t.setContent(vBox);
        return t;
    }

    private void addButton(FlowPane flow, FIcon icon) {
        Tooltip tp = new Tooltip(icon.getFontName() + "." + ((Enum) icon).name());
        Button button = createButton(IconBuilder.create(icon, 48.0).build(), evnt -> {
            if (icon instanceof Enum) {
                System.out.println(icon.getFontName() + "." + ((Enum) icon).name());
            }
        });
        Tooltip.install(button, tp);
        flow.getChildren().add(button);
    }

    private Button createButton(Node graph) {
        return createButton((String) graph.getProperties().get("ICONLABEL"), graph);
    }

    private Button createButton(Node graph, EventHandler<ActionEvent> sds) {
        Button but = createButton((String) graph.getProperties().get("ICONLABEL"), graph);
        but.setOnAction(sds);
        return but;
    }

    private Button createButton(String text, Node graph) {
        Button b = new Button(text, graph);

        double v = 80;
        double v1 = 80;

        b.setMinSize(v, v1);
        b.setMaxSize(v, v1);
        b.setPrefSize(v, v1);

        b.setContentDisplay(ContentDisplay.TOP);
        return b;
    }
}
