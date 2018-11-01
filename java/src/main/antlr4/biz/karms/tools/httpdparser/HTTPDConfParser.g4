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

config      : (SEA_WS* element | SEA_WS* directive | COMMENT)+;
content     : (        element |         directive | COMMENT | SEA_WS)*;

// Directives
directive   : SERVER_ROOT                         serverRootPath
            | LISTEN                              listenAddresses
            | DIRECTORY_INDEX                     directoryIndex
            | USER                                user
            | GROUP                               group
            | CHROOT_DIR                          chrootDir
            | SUEXEC                              suexec
            | SERVER_ADMIN                        serverAdmin
            | ALLOW_OVERRIDE                      allowOverride
            | REQUIRE                             require
            | OPTIONS                             options_
            | ERROR_LOG                           errorLog
            | LOG_LEVEL                           logLevel
            | LOG_FORMAT                          logFormat
            | CUSTOM_LOG                          customLog
            | SCRIPT_ALIAS                        scriptAlias
            | TYPES_CONFIG                        typesConfig
            | ADD_TYPE                            addType
            | ADD_OUTPUT_FILTER                   addOutputFilter
            | ADD_DEFAULT_CHARSET                 addDefaultCharset
            | MIME_MAGIC_FILE                     mimeMagicFile
            | ENABLE_SEND_FILE                    enableSendfile
            | INDEX_OPTIONS                       indexOptions
            | ALIAS                               alias
            | ACCESS_FILE_NAME                    accessFileName
            | ADD_CHARSET                         addCharset
            | ADD_DESCRIPTION                     addDescription
            | ADD_HANDLER                         addHandler
            | ADD_ICON                            addIcon
            | ADD_ICONBYENCODING                  addIconByEncoding
            | ADD_ICONBYTYPE                      addIconByType
            | ADD_LANGUAGE                        addLanguage
            | ALIAS_MATCH                         aliasMatch
            | AUTH_DIGEST_PROVIDER                authDigestProvider
            | AUTH_NAME                           authName
            | AUTH_TYPE                           authType
            | AUTH_USER_FILE                      authUserFile
            | BROWSER_MATCH                       browserMatch
            | DAV                                 dAV
            | DAV_LOCK_DB                         dAVLockDB
            | DEFAULT_ICON                        defaultIcon
            | ERROR_DOCUMENT                      errorDocument
            | FORCE_LANGUAGE_PRIORITY             forceLanguagePriority
            | HEADER_NAME                         headerName
            | HOSTNAME_LOOKUPS                    hostnameLookups
            | INDEX_IGNORE                        indexIgnore
            | KEEP_ALIVE                          keepAlive
            | KEEP_ALIVE_TIMEOUT                  keepAliveTimeout
            | LANGUAGE_PRIORITY                   languagePriority
            | MAX_CONNECTIONS_PER_CHILD           maxConnectionsPerChild
            | MAX_KEEP_ALIVE_REQUESTS             maxKeepAliveRequests
            | MAX_MEM_FREE                        maxMemFree
            | PID_FILE                            pidFile
            | PROXY_HTML_EVENTS                   proxyHTMLEvents
            | PROXY_HTML_LINKS                    proxyHTMLLinks
            | README_NAME                         readmeName
            | REDIRECT_MATCH                      redirectMatch
            | REMOVE_TYPE                         removeType
            | REQUEST_HEADER                      requestHeader
            | REQUEST_READ_TIMEOUT                requestReadTimeout
            | SERVER_SIGNATURE                    serverSignature
            | SERVER_TOKENS                       serverTokens
            | SET_ENV_IF                          setEnvIf
            | SET_HANDLER                         setHandler
            | SSL_CIPHER_SUITE                    sSLCipherSuite
            | SSL_HONOR_CIPHER_ORDER              sSLHonorCipherOrder
            | SSL_OPTIONS                         sSLOptions
            | SSL_PASSPHRASE_DIALOG               sSLPassPhraseDialog
            | SSL_PROTOCOL                        sSLProtocol
            | SSL_PROXY_CIPHER_SUITE              sSLProxyCipherSuite
            | SSL_PROXY_PROTOCOL                  sSLProxyProtocol
            | SSL_RANDOM_SEED                     sSLRandomSeed
            | SSL_REQUIRE                         sSLRequire
            | SSL_SESSION_CACHE                   sSLSessionCache
            | SSL_SESSION_CACHE_TIMEOUT           sSLSessionCacheTimeout
            | SSL_STAPLING_CACHE                  sSLStaplingCache
            | SSL_STAPLING_ERROR_CACHE_TIMEOUT    sSLStaplingErrorCacheTimeout
            | SSL_STAPLING_STANDARD_CACHE_TIMEOUT sSLStaplingStandardCacheTimeout
            | SSL_USE_STAPLING                    sSLUseStapling
            | THREADS_PER_CHILD                   threadsPerChild
            | TIME_OUT                            timeOut
            | TRANSFER_LOG                        transferLog
            | USE_CANONICAL_NAME                  useCanonicalName
            | USER_DIR                            userDir
            ;
serverRootPath                      : VALUE;
listenAddresses                     : VALUE;
directoryIndex                      : VALUE;
user                                : VALUE;
group                               : VALUE;
chrootDir                           : VALUE;
suexec                              : ONOFF;
serverAdmin                         : VALUE;
allowOverride                       : VALUE;
require                             : VALUE;
options_                            : VALUE;
errorLog                            : VALUE;
logLevel                            : VALUE;
logFormat                           : VALUE;
customLog                           : VALUE;
scriptAlias                         : VALUE;
typesConfig                         : VALUE;
addType                             : VALUE;
addOutputFilter                     : VALUE;
addDefaultCharset                   : VALUE;
mimeMagicFile                       : VALUE;
enableSendfile                      : ONOFF;
indexOptions                        : VALUE;
alias                               : VALUE;
accessFileName                      : VALUE;
addCharset                          : VALUE;
addDescription                      : VALUE;
addHandler                          : VALUE;
addIcon                             : VALUE;
addIconByEncoding                   : VALUE;
addIconByType                       : VALUE;
addLanguage                         : VALUE;
aliasMatch                          : VALUE;
authDigestProvider                  : VALUE;
authName                            : VALUE;
authType                            : VALUE;
authUserFile                        : VALUE;
browserMatch                        : VALUE;
dAV                                 : ONOFF;
dAVLockDB                           : VALUE;
defaultIcon                         : VALUE;
errorDocument                       : VALUE;
forceLanguagePriority               : VALUE;
headerName                          : VALUE;
hostnameLookups                     : ONOFF;
indexIgnore                         : VALUE;
keepAlive                           : ONOFF;
keepAliveTimeout                    : NUMBER;
languagePriority                    : VALUE;
maxConnectionsPerChild              : NUMBER;
maxKeepAliveRequests                : NUMBER;
maxMemFree                          : NUMBER;
pidFile                             : VALUE;
proxyHTMLEvents                     : VALUE;
proxyHTMLLinks                      : VALUE;
readmeName                          : VALUE;
redirectMatch                       : VALUE;
removeType                          : VALUE;
requestHeader                       : VALUE;
requestReadTimeout                  : VALUE;
serverSignature                     : ONOFF;
serverTokens                        : VALUE;
setEnvIf                            : VALUE;
setHandler                          : VALUE;
sSLCipherSuite                      : VALUE;
sSLHonorCipherOrder                 : ONOFF;
sSLOptions                          : VALUE;
sSLPassPhraseDialog                 : VALUE;
sSLProtocol                         : VALUE;
sSLProxyCipherSuite                 : VALUE;
sSLProxyProtocol                    : VALUE;
sSLRandomSeed                       : VALUE;
sSLRequire                          : VALUE;
sSLSessionCache                     : VALUE;
sSLSessionCacheTimeout              : NUMBER;
sSLStaplingCache                    : VALUE;
sSLStaplingErrorCacheTimeout        : NUMBER;
sSLStaplingStandardCacheTimeout     : NUMBER;
sSLUseStapling                      : ONOFF;
threadsPerChild                     : NUMBER;
timeOut                             : NUMBER;
transferLog                         : VALUE;
useCanonicalName                    : ONOFF;
userDir                             : VALUE;

// Elements
ifModule      : EL_VAL*;
files         : EL_VAL*;
virtualHost   : EL_VAL*;
directory     : EL_VAL*;
locationMatch : EL_VAL*;
location      : EL_VAL*;
filesMatch    : EL_VAL*;
requireAny    : EL_VAL*;
requireAll    : EL_VAL*;
requireNone   : EL_VAL*;
proxy         : EL_VAL*;
proxyMatch    : EL_VAL*;
nameClose     : EL_IFMODULE
              | EL_FILES
              | EL_VIRTUAL_HOST
              | EL_DIRECTORY
              | EL_LOCATION_MATCH
              | EL_LOCATION
              | EL_FILES_MATCH
              | EL_REQUIRE_ANY
              | EL_REQUIRE_ALL
              | EL_REQUIRE_NONE
              | EL_PROXY
              | EL_PROXY_MATCH
              ;
element       : EL_OPEN (
                    EL_IFMODULE       ifModule       |
                    EL_FILES          files          |
                    EL_VIRTUAL_HOST   virtualHost    |
                    EL_DIRECTORY      directory      |
                    EL_LOCATION_MATCH locationMatch  |
                    EL_LOCATION       location       |
                    EL_FILES_MATCH    filesMatch     |
                    EL_REQUIRE_ANY    requireAny     |
                    EL_REQUIRE_ALL    requireAll     |
                    EL_REQUIRE_NONE   requireNone    |
                    EL_PROXY          proxy          |
                    EL_PROXY_MATCH    proxyMatch
                ) EL_CLOSE content EL_OPEN nameClose EL_CLOSE;