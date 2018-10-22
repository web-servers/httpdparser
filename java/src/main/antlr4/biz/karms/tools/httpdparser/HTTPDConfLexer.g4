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
ADD_DEFAULT_CHARSET                 : A D D D E F A U L T C H A R S E T -> pushMode(RIGHT_SIDE);
ADD_OUTPUT_FILTER                   : A D D O U T P U T F I L T E R -> pushMode(RIGHT_SIDE);
ADD_TYPE                            : A D D T Y P E -> pushMode(RIGHT_SIDE);
ALIAS                               : A L I A S -> pushMode(RIGHT_SIDE);
ALLOW_OVERRIDE                      : A L L O W O V E R R I D E -> pushMode(RIGHT_SIDE);
CHROOT_DIR                          : C H R O O T D I R -> pushMode(RIGHT_SIDE);
CUSTOM_LOG                          : C U S T O M L O G -> pushMode(RIGHT_SIDE);
DIRECTORY_INDEX                     : D I R E C T O R Y I N D E X -> pushMode(RIGHT_SIDE);
ENABLE_SEND_FILE                    : E N A B L E S E N D F I L E -> pushMode(RIGHT_SIDE);
ERROR_LOG                           : E R R O R L O G -> pushMode(RIGHT_SIDE);
GROUP                               : G R O U P -> pushMode(RIGHT_SIDE);
INDEX_OPTIONS                       : I N D E X O P T I O N S -> pushMode(RIGHT_SIDE);
LISTEN                              : L I S T E N -> pushMode(RIGHT_SIDE);
LOG_FORMAT                          : L O G F O R M A T -> pushMode(RIGHT_SIDE);
LOG_LEVEL                           : L O G L E V E L -> pushMode(RIGHT_SIDE);
MIME_MAGIC_FILE                     : M I M E M A G I C F I L E -> pushMode(RIGHT_SIDE);
OPTIONS                             : O P T I O N S -> pushMode(RIGHT_SIDE);
REQUIRE                             : R E Q U I R E -> pushMode(RIGHT_SIDE);
SCRIPT_ALIAS                        : S C R I P T A L I A S -> pushMode(RIGHT_SIDE);
SERVER_ADMIN                        : S E R V E R A D M I N -> pushMode(RIGHT_SIDE);
SERVER_ROOT                         : S E R V E R R O O T -> pushMode(RIGHT_SIDE);
SUEXEC                              : S U E X E C -> pushMode(RIGHT_SIDE);
TYPES_CONFIG                        : T Y P E S C O N F I G -> pushMode(RIGHT_SIDE);
USER                                : U S E R -> pushMode(RIGHT_SIDE);
ACCESS_FILE_NAME                    : A C C E S S F I L E N A M E -> pushMode(RIGHT_SIDE);
ADD_CHARSET                         : A D D C H A R S E T -> pushMode(RIGHT_SIDE);
ADD_DESCRIPTION                     : A D D D E S C R I P T I O N -> pushMode(RIGHT_SIDE);
ADD_HANDLER                         : A D D H A N D L E R -> pushMode(RIGHT_SIDE);
ADD_ICON                            : A D D I C O N -> pushMode(RIGHT_SIDE);
ADD_ICONBYENCODING                  : A D D I C O N B Y E N C O D I N G -> pushMode(RIGHT_SIDE);
ADD_ICONBYTYPE                      : A D D I C O N B Y T Y P E -> pushMode(RIGHT_SIDE);
ADD_LANGUAGE                        : A D D L A N G U A G E -> pushMode(RIGHT_SIDE);
ALIAS_MATCH                         : A L I A S M A T C H -> pushMode(RIGHT_SIDE);
AUTH_DIGEST_PROVIDER                : A U T H D I G E S T P R O V I D E R -> pushMode(RIGHT_SIDE);
AUTH_NAME                           : A U T H N A M E -> pushMode(RIGHT_SIDE);
AUTH_TYPE                           : A U T H T Y P E -> pushMode(RIGHT_SIDE);
AUTH_USER_FILE                      : A U T H U S E R F I L E -> pushMode(RIGHT_SIDE);
BROWSER_MATCH                       : B R O W S E R M A T C H -> pushMode(RIGHT_SIDE);
DAV                                 : D A V -> pushMode(RIGHT_SIDE);
DAV_LOCK_DB                         : D A V L O C K D B -> pushMode(RIGHT_SIDE);
DEFAULT_ICON                        : D E F A U L T I C O N -> pushMode(RIGHT_SIDE);
ERROR_DOCUMENT                      : E R R O R D O C U M E N T -> pushMode(RIGHT_SIDE);
FORCE_LANGUAGE_PRIORITY             : F O R C E L A N G U A G E P R I O R I T Y -> pushMode(RIGHT_SIDE);
HEADER_NAME                         : H E A D E R N A M E -> pushMode(RIGHT_SIDE);
HOSTNAME_LOOKUPS                    : H O S T N A M E L O O K U P S -> pushMode(RIGHT_SIDE);
INDEX_IGNORE                        : I N D E X I G N O R E -> pushMode(RIGHT_SIDE);
KEEP_ALIVE                          : K E E P A L I V E -> pushMode(RIGHT_SIDE);
KEEP_ALIVE_TIMEOUT                  : K E E P A L I V E T I M E O U T -> pushMode(RIGHT_SIDE);
LANGUAGE_PRIORITY                   : L A N G U A G E P R I O R I T Y -> pushMode(RIGHT_SIDE);
MAX_CONNECTIONS_PER_CHILD           : M A X C O N N E C T I O N S P E R C H I L D -> pushMode(RIGHT_SIDE);
MAX_KEEP_ALIVE_REQUESTS             : M A X K E E P A L I V E R E Q U E S T S -> pushMode(RIGHT_SIDE);
MAX_MEM_FREE                        : M A X M E M F R E E -> pushMode(RIGHT_SIDE);
PID_FILE                            : P I D F I L E -> pushMode(RIGHT_SIDE);
PROXY_HTML_EVENTS                   : P R O X Y H T M L E V E N T S -> pushMode(RIGHT_SIDE);
PROXY_HTML_LINKS                    : P R O X Y H T M L L I N K S -> pushMode(RIGHT_SIDE);
README_NAME                         : R E A D M E N A M E -> pushMode(RIGHT_SIDE);
REDIRECT_MATCH                      : R E D I R E C T M A T C H -> pushMode(RIGHT_SIDE);
REMOVE_TYPE                         : R E M O V E T Y P E -> pushMode(RIGHT_SIDE);
REQUEST_HEADER                      : R E Q U E S T H E A D E R -> pushMode(RIGHT_SIDE);
REQUEST_READ_TIMEOUT                : R E Q U E S T R E A D T I M E O U T -> pushMode(RIGHT_SIDE);
SERVER_SIGNATURE                    : S E R V E R S I G N A T U R E -> pushMode(RIGHT_SIDE);
SERVER_TOKENS                       : S E R V E R T O K E N S -> pushMode(RIGHT_SIDE);
SET_ENV_IF                          : S E T E N V I F -> pushMode(RIGHT_SIDE);
SET_HANDLER                         : S E T H A N D L E R -> pushMode(RIGHT_SIDE);
SSL_CIPHER_SUITE                    : S S L C I P H E R S U I T E -> pushMode(RIGHT_SIDE);
SSL_HONOR_CIPHER_ORDER              : S S L H O N O R C I P H E R O R D E R -> pushMode(RIGHT_SIDE);
SSL_OPTIONS                         : S S L O P T I O N S -> pushMode(RIGHT_SIDE);
SSL_PASSPHRASE_DIALOG               : S S L P A S S P H R A S E D I A L O G -> pushMode(RIGHT_SIDE);
SSL_PROTOCOL                        : S S L P R O T O C O L -> pushMode(RIGHT_SIDE);
SSL_PROXY_CIPHER_SUITE              : S S L P R O X Y C I P H E R S U I T E -> pushMode(RIGHT_SIDE);
SSL_PROXY_PROTOCOL                  : S S L P R O X Y P R O T O C O L -> pushMode(RIGHT_SIDE);
SSL_RANDOM_SEED                     : S S L R A N D O M S E E D -> pushMode(RIGHT_SIDE);
SSL_REQUIRE                         : S S L R E Q U I R E -> pushMode(RIGHT_SIDE);
SSL_SESSION_CACHE                   : S S L S E S S I O N C A C H E -> pushMode(RIGHT_SIDE);
SSL_SESSION_CACHE_TIMEOUT           : S S L S E S S I O N C A C H E T I M E O U T -> pushMode(RIGHT_SIDE);
SSL_STAPLING_CACHE                  : S S L S T A P L I N G C A C H E -> pushMode(RIGHT_SIDE);
SSL_STAPLING_ERROR_CACHE_TIMEOUT    : S S L S T A P L I N G E R R O R C A C H E T I M E O U T -> pushMode(RIGHT_SIDE);
SSL_STAPLING_STANDARD_CACHE_TIMEOUT : S S L S T A P L I N G S T A N D A R D C A C H E T I M E O U T -> pushMode(RIGHT_SIDE);
SSL_USE_STAPLING                    : S S L U S E S T A P L I N G -> pushMode(RIGHT_SIDE);
THREADS_PER_CHILD                   : T H R E A D S P E R C H I L D -> pushMode(RIGHT_SIDE);
TIME_OUT                            : T I M E O U T -> pushMode(RIGHT_SIDE);
TRANSFER_LOG                        : T R A N S F E R L O G -> pushMode(RIGHT_SIDE);
USE_CANONICAL_NAME                  : U S E C A N O N I C A L N A M E -> pushMode(RIGHT_SIDE);
USER_DIR                            : U S E R D I R -> pushMode(RIGHT_SIDE);

NL        : (('\r''\n'? | '\n')+EOF?|EOF) -> skip;
WS        : [ \t\u000C] -> skip;
SEA_WS    : (' '|'\t'|'\r'? '\n')+;
EL_OPEN   : ('<' | '<' SEA_WS? '/') -> pushMode(EL);

mode RIGHT_SIDE;
    S_RIGHT : [ \t]+? -> skip;
    ONOFF   : [oO]([nN]|[fF][fF]) -> popMode;
    NUMBER  : [0-9]+ -> popMode;
    VALUE   : ~[ \t\r\n]~[\r\n]+ -> popMode;

mode EL;
// Add more Elements here:
    EL_IFMODULE       : 'IfModule';
    EL_FILES          : 'Files';
    EL_VIRTUAL_HOST   : 'VirtualHost';
    EL_DIRECTORY      : 'Directory';
    EL_LOCATION_MATCH : 'LocationMatch';
    EL_LOCATION       : 'Location';
    EL_FILES_MATCH    : 'FilesMatch';
    EL_REQUIRE_ANY    : 'RequireAny';
    EL_REQUIRE_ALL    : 'RequireAll';
    EL_REQUIRE_NONE   : 'RequireNone';
    EL_PROXY          : 'Proxy';
    EL_PROXY_MATCH    : 'ProxyMatch';

    EL_S              : [ \t\r\n] -> skip;
    EL_VAL            : ~[>];
    EL_CLOSE          : '>' -> popMode;

fragment A : [aA]; // match either an 'a' or 'A'
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];