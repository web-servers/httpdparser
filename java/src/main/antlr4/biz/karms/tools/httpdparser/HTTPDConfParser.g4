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

config      : (SEA_WS* element | SEA_WS* directive | SEA_WS* COMMENT)+;
content     : (SEA_WS* element | SEA_WS* directive | SEA_WS* COMMENT)*;

// Directives
directive   : SERVER_ROOT         serverRootPath
            | LISTEN              listenAddresses
            | DIRECTORY_INDEX     directoryIndex
            | USER                user
            | GROUP               group
            | CHROOT_DIR          chrootDir
            | SUEXEC              suexec
            | SERVER_ADMIN        serverAdmin
            | ALLOW_OVERRIDE      allowOverride
            | REQUIRE             require
            | OPTIONS             options_
            | ERROR_LOG           errorLog
            | LOG_LEVEL           logLevel
            | LOG_FORMAT          logFormat
            | CUSTOM_LOG          customLog
            | SCRIPT_ALIAS        scriptAlias
            | TYPES_CONFIG        typesConfig
            | ADD_TYPE            addType
            | ADD_OUTPUT_FILTER   addOutputFilter
            | ADD_DEFAULT_CHARSET addDefaultCharset
            | MIME_MAGIC_FILE     mimeMagicFile
            | ENABLE_SEND_FILE    enableSendfile
            | INDEX_OPTIONS       indexOptions
            | ALIAS               alias
            ;
serverRootPath  : VALUE;
listenAddresses : VALUE;
directoryIndex  : VALUE;
user            : VALUE;
group           : VALUE;
chrootDir       : VALUE;
suexec          : ONOFF;
serverAdmin     : VALUE;
allowOverride     : VALUE;
require           : VALUE;
options_          : VALUE;
errorLog          : VALUE;
logLevel          : VALUE;
logFormat         : VALUE;
customLog         : VALUE;
scriptAlias       : VALUE;
typesConfig       : VALUE;
addType           : VALUE;
addOutputFilter   : VALUE;
addDefaultCharset : VALUE;
mimeMagicFile     : VALUE;
enableSendfile    : ONOFF;
indexOptions      : VALUE;
alias             : VALUE;

// Elements
ifModule    : EL_IFMODULE_ATTR;
files       : QUOTED_STRING;
virtualHost : EL_VIRTUAL_HOST_ATTR;
directory   : (TILDA WS*)? QUOTED_STRING
| QUOTED_STRING
| SLASH
;
nameClose   : EL_IFMODULE | EL_FILES | EL_VIRTUAL_HOST | EL_DIRECTORY;
element     : '<'
               (
                 EL_IFMODULE ifModule        |
                 EL_FILES files              |
                 EL_VIRTUAL_HOST virtualHost |
                 EL_DIRECTORY directory
               )
              '>' content SEA_WS* '<' '/' nameClose '>'
            ;
