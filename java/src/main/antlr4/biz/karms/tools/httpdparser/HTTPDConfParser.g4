/*
Copyright 2018 Michal Karm Babacek

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
parser grammar HTTPDConfParser;

options { tokenVocab=HTTPDConfLexer; }

config      :   (SEA_WS* element | SEA_WS* directive | SEA_WS* COMMENT)+;
content     :   (SEA_WS* element | SEA_WS* directive | SEA_WS* COMMENT)*;

// Directives
directive   :   SERVER_ROOT serverRootPath
            |   LISTEN listenAddresses
            |   DIRECTORY_INDEX directoryIndex
            ;
serverRootPath  :   VALUE;
listenAddresses :   VALUE;
directoryIndex  :   VALUE;

// Elements
ifModuleAttribute   :   MODULE_NAME;
filesAttribute      :   STRING;
nameClose           :   EL_IFMODULE | EL_FILES;
element             :   '<'
                    (
                        EL_IFMODULE ifModuleAttribute |
                        EL_FILES filesAttribute
                    )
                    '>' content SEA_WS* '<' '/' nameClose '>';

