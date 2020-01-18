//    FontIcon is a JavaFX library to use FontIcons
//    Copyright (C) 2014-2016 Adrián Romero Corchado.
//
//    This file is part of FontIcon
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

package com.adr.fontIconLib.support;

import javafx.scene.text.Font;

/**
 * @author adrian
 */
public interface FIcon {

    static String load(String fileName) {
        return Font.loadFont(FontAwesome.class.getResourceAsStream("/com/adr/fontIconLib/icons/" + fileName), 10.0).getName();
    }

    String getFontName();

    char getChar();

    default String getString() {
        return Character.toString(getChar());
    }
}
