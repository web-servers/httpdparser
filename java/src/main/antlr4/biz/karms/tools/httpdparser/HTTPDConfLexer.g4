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
COMMENT : '#'.*?NL -> channel(COMMENTS);

// Add more Directives here:
SERVER_ROOT     : 'ServerRoot' -> pushMode(RIGHT_SIDE);
LISTEN          : 'Listen' -> pushMode(RIGHT_SIDE);
DIRECTORY_INDEX : 'DirectoryIndex' -> pushMode(RIGHT_SIDE);
USER            : 'User' -> pushMode(RIGHT_SIDE);
GROUP           : 'Group' -> pushMode(RIGHT_SIDE);
CHROOT_DIR      : 'ChrootDir' -> pushMode(RIGHT_SIDE);
SUEXEC          : 'Suexec' -> pushMode(RIGHT_SIDE);
SERVER_ADMIN    : 'ServerAdmin' -> pushMode(RIGHT_SIDE);

ALLOW_OVERRIDE      : 'AllowOverride' -> pushMode(RIGHT_SIDE);
REQUIRE             : 'Require' -> pushMode(RIGHT_SIDE);
OPTIONS             : 'Options' -> pushMode(RIGHT_SIDE);
ERROR_LOG           : 'ErrorLog' -> pushMode(RIGHT_SIDE);
LOG_LEVEL           : 'LogLevel' -> pushMode(RIGHT_SIDE);
LOG_FORMAT          : 'LogFormat' -> pushMode(RIGHT_SIDE);
CUSTOM_LOG          : 'CustomLog' -> pushMode(RIGHT_SIDE);
SCRIPT_ALIAS        : 'ScriptAlias' -> pushMode(RIGHT_SIDE);
TYPES_CONFIG        : 'TypesConfig' -> pushMode(RIGHT_SIDE);
ADD_TYPE            : 'AddType' -> pushMode(RIGHT_SIDE);
ADD_OUTPUT_FILTER   : 'AddOutputFilter' -> pushMode(RIGHT_SIDE);
ADD_DEFAULT_CHARSET : 'AddDefaultCharset' -> pushMode(RIGHT_SIDE);
MIME_MAGIC_FILE     : 'MimeMagicFile' -> pushMode(RIGHT_SIDE);
ENABLE_SEND_FILE    : 'EnableSendfile' -> pushMode(RIGHT_SIDE);
INDEX_OPTIONS       : 'IndexOptions' -> pushMode(RIGHT_SIDE);
ALIAS               : 'Alias' -> pushMode(RIGHT_SIDE);


NL     : (('\r''\n'? | '\n')+EOF?|EOF) -> skip;
WS     : [ \t\u000C] -> skip;
SEA_WS : (' '|'\t'|'\r'? '\n')+;
OPEN   : '<' -> pushMode(INSIDE);

mode RIGHT_SIDE;
    S_RIGHT : [ \t]+? -> skip;
    ONOFF   : [oO]([nN]|[fF][fF]) -> popMode;
    VALUE   : ~[ \t\r\n]~[\r\n]+ -> popMode;

mode INSIDE;
// Add more Elements here:
    EL_IFMODULE     : 'IfModule';
    EL_FILES        : 'Files';
    EL_VIRTUAL_HOST : 'VirtualHost';
    EL_DIRECTORY    : 'Directory';

    CLOSE           : '>' -> popMode;
    SLASH           : '/';
    TILDA           : '~';
    QUOTED_STRING   : '"' ~[><"]* '"'
                    | '\'' ~[><']* '\''
                    ;

    EL_IFMODULE_ATTR     : [a-zA-Z0-9]+'_module';
    EL_VIRTUAL_HOST_ATTR : ADDRESS_CHARS+;
    SX              : [ \t\r\n] -> skip;

fragment ADDRESS_CHARS
    : '.'
    | ']'
    | '['
    | '*'
    | ':'
    | '_'
    | [0-9a-zA-Z]
;
