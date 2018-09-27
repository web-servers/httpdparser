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
lexer grammar HTTPDConfLexer;

// TODO: How do I work with this from Java?
channels {COMMENTS}
COMMENT     :   '#'.*?NL -> channel(COMMENTS);

// Add more Directives here:
SERVER_ROOT     :   'ServerRoot' -> pushMode(RIGHT_SIDE);
LISTEN          :   'Listen' -> pushMode(RIGHT_SIDE);
DIRECTORY_INDEX :   'DirectoryIndex' -> pushMode(RIGHT_SIDE);

NL          :   (('\r''\n'? | '\n')+EOF?|EOF) -> skip;
WS          :   [ \t\u000C] -> skip;
SEA_WS      :   (' '|'\t'|'\r'? '\n')+;
OPEN        :   '<' -> pushMode(INSIDE);

mode RIGHT_SIDE;
    S_RIGHT       :   [ \t]+? -> skip;
    VALUE   :   ~[ \t\r\n]~[\r\n]+ -> popMode;

mode INSIDE;
    CLOSE       :   '>' -> popMode;
    SLASH       :   '/';
    //TODO: Do we allow >< here?
    STRING      :   '"' ~[><"]* '"'
                |   '\'' ~[><']* '\''
                ;
    MODULE_NAME : [a-zA-Z0-9]+'_module';
    SX          :   [ \t\r\n] -> skip;

// Add more Elements here:
    EL_IFMODULE :   'IfModule';
    EL_FILES    :   'Files';
