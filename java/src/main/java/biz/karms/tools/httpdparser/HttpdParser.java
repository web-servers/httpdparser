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

package biz.karms.tools.httpdparser;

import biz.karms.tools.httpdparser.domain.Node;
import biz.karms.tools.httpdparser.domain.ServerConfig;
import biz.karms.tools.httpdparser.domain.directives.AccessFileName;
import biz.karms.tools.httpdparser.domain.directives.AddCharset;
import biz.karms.tools.httpdparser.domain.directives.AddDefaultCharset;
import biz.karms.tools.httpdparser.domain.directives.AddDescription;
import biz.karms.tools.httpdparser.domain.directives.AddHandler;
import biz.karms.tools.httpdparser.domain.directives.AddIcon;
import biz.karms.tools.httpdparser.domain.directives.AddIconByEncoding;
import biz.karms.tools.httpdparser.domain.directives.AddIconByType;
import biz.karms.tools.httpdparser.domain.directives.AddLanguage;
import biz.karms.tools.httpdparser.domain.directives.AddOutputFilter;
import biz.karms.tools.httpdparser.domain.directives.AddType;
import biz.karms.tools.httpdparser.domain.directives.Alias;
import biz.karms.tools.httpdparser.domain.directives.AliasMatch;
import biz.karms.tools.httpdparser.domain.directives.AllowOverride;
import biz.karms.tools.httpdparser.domain.directives.BrowserMatch;
import biz.karms.tools.httpdparser.domain.directives.ChrootDir;
import biz.karms.tools.httpdparser.domain.directives.CustomLog;
import biz.karms.tools.httpdparser.domain.directives.DAVLockDB;
import biz.karms.tools.httpdparser.domain.directives.DefaultIcon;
import biz.karms.tools.httpdparser.domain.directives.DirectoryIndex;
import biz.karms.tools.httpdparser.domain.directives.EnableSendfile;
import biz.karms.tools.httpdparser.domain.directives.ErrorDocument;
import biz.karms.tools.httpdparser.domain.directives.ErrorLog;
import biz.karms.tools.httpdparser.domain.directives.ForceLanguagePriority;
import biz.karms.tools.httpdparser.domain.directives.Group;
import biz.karms.tools.httpdparser.domain.directives.HeaderName;
import biz.karms.tools.httpdparser.domain.directives.HostnameLookups;
import biz.karms.tools.httpdparser.domain.directives.IndexIgnore;
import biz.karms.tools.httpdparser.domain.directives.IndexOptions;
import biz.karms.tools.httpdparser.domain.directives.KeepAlive;
import biz.karms.tools.httpdparser.domain.directives.KeepAliveTimeout;
import biz.karms.tools.httpdparser.domain.directives.LanguagePriority;
import biz.karms.tools.httpdparser.domain.directives.Listen;
import biz.karms.tools.httpdparser.domain.directives.LogFormat;
import biz.karms.tools.httpdparser.domain.directives.LogLevel;
import biz.karms.tools.httpdparser.domain.directives.MaxConnectionsPerChild;
import biz.karms.tools.httpdparser.domain.directives.MaxKeepAliveRequests;
import biz.karms.tools.httpdparser.domain.directives.MaxMemFree;
import biz.karms.tools.httpdparser.domain.directives.MimeMagicFile;
import biz.karms.tools.httpdparser.domain.directives.Options;
import biz.karms.tools.httpdparser.domain.directives.PidFile;
import biz.karms.tools.httpdparser.domain.directives.ProxyHTMLEvents;
import biz.karms.tools.httpdparser.domain.directives.ProxyHTMLLinks;
import biz.karms.tools.httpdparser.domain.directives.ReadmeName;
import biz.karms.tools.httpdparser.domain.directives.RequestHeader;
import biz.karms.tools.httpdparser.domain.directives.RequestReadTimeout;
import biz.karms.tools.httpdparser.domain.directives.Require;
import biz.karms.tools.httpdparser.domain.directives.SSLCipherSuite;
import biz.karms.tools.httpdparser.domain.directives.SSLHonorCipherOrder;
import biz.karms.tools.httpdparser.domain.directives.SSLPassPhraseDialog;
import biz.karms.tools.httpdparser.domain.directives.SSLProtocol;
import biz.karms.tools.httpdparser.domain.directives.SSLProxyCipherSuite;
import biz.karms.tools.httpdparser.domain.directives.SSLProxyProtocol;
import biz.karms.tools.httpdparser.domain.directives.SSLRandomSeed;
import biz.karms.tools.httpdparser.domain.directives.SSLSessionCache;
import biz.karms.tools.httpdparser.domain.directives.SSLSessionCacheTimeout;
import biz.karms.tools.httpdparser.domain.directives.SSLStaplingCache;
import biz.karms.tools.httpdparser.domain.directives.SSLStaplingErrorCacheTimeout;
import biz.karms.tools.httpdparser.domain.directives.SSLStaplingStandardCacheTimeout;
import biz.karms.tools.httpdparser.domain.directives.SSLUseStapling;
import biz.karms.tools.httpdparser.domain.directives.ScriptAlias;
import biz.karms.tools.httpdparser.domain.directives.ServerAdmin;
import biz.karms.tools.httpdparser.domain.directives.ServerRoot;
import biz.karms.tools.httpdparser.domain.directives.ServerSignature;
import biz.karms.tools.httpdparser.domain.directives.ServerTokens;
import biz.karms.tools.httpdparser.domain.directives.SetHandler;
import biz.karms.tools.httpdparser.domain.directives.Suexec;
import biz.karms.tools.httpdparser.domain.directives.ThreadsPerChild;
import biz.karms.tools.httpdparser.domain.directives.TimeOut;
import biz.karms.tools.httpdparser.domain.directives.TransferLog;
import biz.karms.tools.httpdparser.domain.directives.TypesConfig;
import biz.karms.tools.httpdparser.domain.directives.UseCanonicalName;
import biz.karms.tools.httpdparser.domain.directives.User;
import biz.karms.tools.httpdparser.domain.directives.UserDir;
import biz.karms.tools.httpdparser.domain.elements.Directory;
import biz.karms.tools.httpdparser.domain.elements.Files;
import biz.karms.tools.httpdparser.domain.elements.IfModule;
import biz.karms.tools.httpdparser.domain.elements.VirtualHost;
import biz.karms.tools.httpdparser.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Michal Karm Babacek
 */
public class HttpdParser {

    private static final Logger LOG;

    static {
        try (final InputStream stream = HttpdParser.class.getClassLoader()
                .getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG = Logger.getLogger(HttpdParser.class.getName());
    }

    static class ConfigListener extends HTTPDConfParserBaseListener {

        final Deque<Node> stack = new ArrayDeque<>();

        @Override
        public void enterConfig(HTTPDConfParser.ConfigContext ctx) {
            LOG.log(Level.FINE, "enterConfig: " + ctx.getChildCount());
            stack.push(new ServerConfig(new ArrayList<>(ctx.getChildCount())));
        }

        @Override
        public void enterListenAddresses(HTTPDConfParser.ListenAddressesContext ctx) {
            LOG.log(Level.FINE, ". enterListenAddresses:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new Listen(ctx.getText(), node));
        }

        @Override
        public void enterServerRootPath(HTTPDConfParser.ServerRootPathContext ctx) {
            LOG.log(Level.FINE, ". enterServerRootPath:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ServerRoot(ctx.getText(), node));
        }

        @Override
        public void enterUser(HTTPDConfParser.UserContext ctx) {
            LOG.log(Level.FINE, ". enterUser:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new User(ctx.getText(), node));
        }

        @Override
        public void enterGroup(HTTPDConfParser.GroupContext ctx) {
            LOG.log(Level.FINE, ". enterGroup:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new Group(ctx.getText(), node));
        }

        @Override
        public void enterChrootDir(HTTPDConfParser.ChrootDirContext ctx) {
            LOG.log(Level.FINE, ". enterChrootDir:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ChrootDir(ctx.getText(), node));
        }

        @Override
        public void enterSuexec(HTTPDConfParser.SuexecContext ctx) {
            LOG.log(Level.FINE, ". enterSuexec:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new Suexec(ctx.getText(), node));
        }

        @Override
        public void enterServerAdmin(HTTPDConfParser.ServerAdminContext ctx) {
            LOG.log(Level.FINE, ". enterServerAdmin:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ServerAdmin(ctx.getText(), node));
        }

        @Override
        public void enterDirectoryIndex(HTTPDConfParser.DirectoryIndexContext ctx) {
            LOG.log(Level.FINE, ". enterDirectoryIndex:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new DirectoryIndex(ctx.getText(), node));
        }

        @Override
        public void enterAllowOverride(HTTPDConfParser.AllowOverrideContext ctx) {
            LOG.log(Level.FINE, ". enterAllowOverride:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AllowOverride(ctx.getText(), node));
        }

        @Override
        public void enterRequire(HTTPDConfParser.RequireContext ctx) {
            LOG.log(Level.FINE, ". enterRequire:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new Require(ctx.getText(), node));
        }

        @Override
        public void enterOptions_(HTTPDConfParser.Options_Context ctx) {
            LOG.log(Level.FINE, ". enterOptions_:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new Options(ctx.getText(), node));
        }

        @Override
        public void enterErrorLog(HTTPDConfParser.ErrorLogContext ctx) {
            LOG.log(Level.FINE, ". enterErrorLog:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ErrorLog(ctx.getText(), node));
        }

        @Override
        public void enterLogLevel(HTTPDConfParser.LogLevelContext ctx) {
            LOG.log(Level.FINE, ". enterLogLevel:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new LogLevel(ctx.getText(), node));
        }

        @Override
        public void enterLogFormat(HTTPDConfParser.LogFormatContext ctx) {
            LOG.log(Level.FINE, ". enterLogFormat:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new LogFormat(ctx.getText(), node));
        }

        @Override
        public void enterCustomLog(HTTPDConfParser.CustomLogContext ctx) {
            LOG.log(Level.FINE, ". enterCustomLog:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new CustomLog(ctx.getText(), node));
        }

        @Override
        public void enterScriptAlias(HTTPDConfParser.ScriptAliasContext ctx) {
            LOG.log(Level.FINE, ". enterScriptAlias:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ScriptAlias(ctx.getText(), node));
        }

        @Override
        public void enterTypesConfig(HTTPDConfParser.TypesConfigContext ctx) {
            LOG.log(Level.FINE, ". enterTypesConfig:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new TypesConfig(ctx.getText(), node));
        }

        @Override
        public void enterAddType(HTTPDConfParser.AddTypeContext ctx) {
            LOG.log(Level.FINE, ". enterAddType:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddType(ctx.getText(), node));
        }

        @Override
        public void enterAddOutputFilter(HTTPDConfParser.AddOutputFilterContext ctx) {
            LOG.log(Level.FINE, ". enterAddOutputFilter:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddOutputFilter(ctx.getText(), node));
        }

        @Override
        public void enterAddDefaultCharset(HTTPDConfParser.AddDefaultCharsetContext ctx) {
            LOG.log(Level.FINE, ". enterAddDefaultCharset:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddDefaultCharset(ctx.getText(), node));
        }

        @Override
        public void enterMimeMagicFile(HTTPDConfParser.MimeMagicFileContext ctx) {
            LOG.log(Level.FINE, ". enterMimeMagicFile:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new MimeMagicFile(ctx.getText(), node));
        }

        @Override
        public void enterEnableSendfile(HTTPDConfParser.EnableSendfileContext ctx) {
            LOG.log(Level.FINE, ". enterEnableSendfile:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new EnableSendfile(ctx.getText(), node));
        }

        @Override
        public void enterIndexOptions(HTTPDConfParser.IndexOptionsContext ctx) {
            LOG.log(Level.FINE, ". enterIndexOptions:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new IndexOptions(ctx.getText(), node));
        }

        @Override
        public void enterAlias(HTTPDConfParser.AliasContext ctx) {
            LOG.log(Level.FINE, ". enterAlias:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new Alias(ctx.getText(), node));
        }


        @Override
        public void enterAccessFileName(HTTPDConfParser.AccessFileNameContext ctx) {
            LOG.log(Level.FINE, ". enterAccessFileName:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AccessFileName(ctx.getText(), node));
        }

        @Override
        public void enterAddCharset(HTTPDConfParser.AddCharsetContext ctx) {
            LOG.log(Level.FINE, ". enterAddCharset:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddCharset(ctx.getText(), node));
        }

        @Override
        public void enterAddDescription(HTTPDConfParser.AddDescriptionContext ctx) {
            LOG.log(Level.FINE, ". enterAddDescription:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddDescription(ctx.getText(), node));
        }

        @Override
        public void enterAddHandler(HTTPDConfParser.AddHandlerContext ctx) {
            LOG.log(Level.FINE, ". enterAddHandler:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddHandler(ctx.getText(), node));
        }

        @Override
        public void enterAddIcon(HTTPDConfParser.AddIconContext ctx) {
            LOG.log(Level.FINE, ". enterAddIcon:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddIcon(ctx.getText(), node));
        }

        @Override
        public void enterAddIconByEncoding(HTTPDConfParser.AddIconByEncodingContext ctx) {
            LOG.log(Level.FINE, ". enterAddIconByEncoding:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddIconByEncoding(ctx.getText(), node));
        }

        @Override
        public void enterAddIconByType(HTTPDConfParser.AddIconByTypeContext ctx) {
            LOG.log(Level.FINE, ". enterAddIconByType:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddIconByType(ctx.getText(), node));
        }

        @Override
        public void enterAddLanguage(HTTPDConfParser.AddLanguageContext ctx) {
            LOG.log(Level.FINE, ". enterAddLanguage:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AddLanguage(ctx.getText(), node));
        }

        @Override
        public void enterAliasMatch(HTTPDConfParser.AliasMatchContext ctx) {
            LOG.log(Level.FINE, ". enterAliasMatch:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new AliasMatch(ctx.getText(), node));
        }

        @Override
        public void enterBrowserMatch(HTTPDConfParser.BrowserMatchContext ctx) {
            LOG.log(Level.FINE, ". enterBrowserMatch:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new BrowserMatch(ctx.getText(), node));
        }

        @Override
        public void enterDAVLockDB(HTTPDConfParser.DAVLockDBContext ctx) {
            LOG.log(Level.FINE, ". enterDAVLockDB:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new DAVLockDB(ctx.getText(), node));
        }

        @Override
        public void enterDefaultIcon(HTTPDConfParser.DefaultIconContext ctx) {
            LOG.log(Level.FINE, ". enterDefaultIcon:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new DefaultIcon(ctx.getText(), node));
        }

        @Override
        public void enterErrorDocument(HTTPDConfParser.ErrorDocumentContext ctx) {
            LOG.log(Level.FINE, ". enterErrorDocument:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ErrorDocument(ctx.getText(), node));
        }

        @Override
        public void enterForceLanguagePriority(HTTPDConfParser.ForceLanguagePriorityContext ctx) {
            LOG.log(Level.FINE, ". enterForceLanguagePriority:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ForceLanguagePriority(ctx.getText(), node));
        }

        @Override
        public void enterHeaderName(HTTPDConfParser.HeaderNameContext ctx) {
            LOG.log(Level.FINE, ". enterHeaderName:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new HeaderName(ctx.getText(), node));
        }

        @Override
        public void enterHostnameLookups(HTTPDConfParser.HostnameLookupsContext ctx) {
            LOG.log(Level.FINE, ". enterHostnameLookups:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new HostnameLookups(ctx.getText(), node));
        }

        @Override
        public void enterIndexIgnore(HTTPDConfParser.IndexIgnoreContext ctx) {
            LOG.log(Level.FINE, ". enterIndexIgnore:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new IndexIgnore(ctx.getText(), node));
        }

        @Override
        public void enterKeepAlive(HTTPDConfParser.KeepAliveContext ctx) {
            LOG.log(Level.FINE, ". enterKeepAlive:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new KeepAlive(ctx.getText(), node));
        }

        @Override
        public void enterKeepAliveTimeout(HTTPDConfParser.KeepAliveTimeoutContext ctx) {
            LOG.log(Level.FINE, ". enterKeepAliveTimeout:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new KeepAliveTimeout(ctx.getText(), node));
        }

        @Override
        public void enterLanguagePriority(HTTPDConfParser.LanguagePriorityContext ctx) {
            LOG.log(Level.FINE, ". enterLanguagePriority:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new LanguagePriority(ctx.getText(), node));
        }

        @Override
        public void enterMaxConnectionsPerChild(HTTPDConfParser.MaxConnectionsPerChildContext ctx) {
            LOG.log(Level.FINE, ". enterMaxConnectionsPerChild:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new MaxConnectionsPerChild(ctx.getText(), node));
        }

        @Override
        public void enterMaxKeepAliveRequests(HTTPDConfParser.MaxKeepAliveRequestsContext ctx) {
            LOG.log(Level.FINE, ". enterMaxKeepAliveRequests:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new MaxKeepAliveRequests(ctx.getText(), node));
        }

        @Override
        public void enterMaxMemFree(HTTPDConfParser.MaxMemFreeContext ctx) {
            LOG.log(Level.FINE, ". enterMaxMemFree:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new MaxMemFree(ctx.getText(), node));
        }

        @Override
        public void enterPidFile(HTTPDConfParser.PidFileContext ctx) {
            LOG.log(Level.FINE, ". enterPidFile:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new PidFile(ctx.getText(), node));
        }

        @Override
        public void enterProxyHTMLEvents(HTTPDConfParser.ProxyHTMLEventsContext ctx) {
            LOG.log(Level.FINE, ". enterProxyHTMLEvents:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ProxyHTMLEvents(ctx.getText(), node));
        }

        @Override
        public void enterProxyHTMLLinks(HTTPDConfParser.ProxyHTMLLinksContext ctx) {
            LOG.log(Level.FINE, ". enterProxyHTMLLinks:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ProxyHTMLLinks(ctx.getText(), node));
        }

        @Override
        public void enterReadmeName(HTTPDConfParser.ReadmeNameContext ctx) {
            LOG.log(Level.FINE, ". enterReadmeName:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ReadmeName(ctx.getText(), node));
        }

        @Override
        public void enterRequestHeader(HTTPDConfParser.RequestHeaderContext ctx) {
            LOG.log(Level.FINE, ". enterRequestHeader:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new RequestHeader(ctx.getText(), node));
        }

        @Override
        public void enterRequestReadTimeout(HTTPDConfParser.RequestReadTimeoutContext ctx) {
            LOG.log(Level.FINE, ". enterRequestReadTimeout:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new RequestReadTimeout(ctx.getText(), node));
        }

        @Override
        public void enterServerSignature(HTTPDConfParser.ServerSignatureContext ctx) {
            LOG.log(Level.FINE, ". enterServerSignature:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ServerSignature(ctx.getText(), node));
        }

        @Override
        public void enterServerTokens(HTTPDConfParser.ServerTokensContext ctx) {
            LOG.log(Level.FINE, ". enterServerTokens:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ServerTokens(ctx.getText(), node));
        }

        @Override
        public void enterSetHandler(HTTPDConfParser.SetHandlerContext ctx) {
            LOG.log(Level.FINE, ". enterSetHandler:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SetHandler(ctx.getText(), node));
        }

        @Override
        public void enterSSLCipherSuite(HTTPDConfParser.SSLCipherSuiteContext ctx) {
            LOG.log(Level.FINE, ". enterSSLCipherSuite:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLCipherSuite(ctx.getText(), node));
        }

        @Override
        public void enterSSLHonorCipherOrder(HTTPDConfParser.SSLHonorCipherOrderContext ctx) {
            LOG.log(Level.FINE, ". enterSSLHonorCipherOrder:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLHonorCipherOrder(ctx.getText(), node));
        }

        @Override
        public void enterSSLPassPhraseDialog(HTTPDConfParser.SSLPassPhraseDialogContext ctx) {
            LOG.log(Level.FINE, ". enterSSLPassPhraseDialog:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLPassPhraseDialog(ctx.getText(), node));
        }

        @Override
        public void enterSSLProtocol(HTTPDConfParser.SSLProtocolContext ctx) {
            LOG.log(Level.FINE, ". enterSSLProtocol:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLProtocol(ctx.getText(), node));
        }

        @Override
        public void enterSSLProxyCipherSuite(HTTPDConfParser.SSLProxyCipherSuiteContext ctx) {
            LOG.log(Level.FINE, ". enterSSLProxyCipherSuite:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLProxyCipherSuite(ctx.getText(), node));
        }

        @Override
        public void enterSSLProxyProtocol(HTTPDConfParser.SSLProxyProtocolContext ctx) {
            LOG.log(Level.FINE, ". enterSSLProxyProtocol:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLProxyProtocol(ctx.getText(), node));
        }

        @Override
        public void enterSSLRandomSeed(HTTPDConfParser.SSLRandomSeedContext ctx) {
            LOG.log(Level.FINE, ". enterSSLRandomSeed:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLRandomSeed(ctx.getText(), node));
        }

        @Override
        public void enterSSLSessionCache(HTTPDConfParser.SSLSessionCacheContext ctx) {
            LOG.log(Level.FINE, ". enterSSLSessionCache:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLSessionCache(ctx.getText(), node));
        }

        @Override
        public void enterSSLSessionCacheTimeout(HTTPDConfParser.SSLSessionCacheTimeoutContext ctx) {
            LOG.log(Level.FINE, ". enterSSLSessionCacheTimeout:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLSessionCacheTimeout(ctx.getText(), node));
        }

        @Override
        public void enterSSLStaplingCache(HTTPDConfParser.SSLStaplingCacheContext ctx) {
            LOG.log(Level.FINE, ". enterSSLStaplingCache:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLStaplingCache(ctx.getText(), node));
        }

        @Override
        public void enterSSLStaplingErrorCacheTimeout(HTTPDConfParser.SSLStaplingErrorCacheTimeoutContext ctx) {
            LOG.log(Level.FINE, ". enterSSLStaplingErrorCacheTimeout:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLStaplingErrorCacheTimeout(ctx.getText(), node));
        }

        @Override
        public void enterSSLStaplingStandardCacheTimeout(HTTPDConfParser.SSLStaplingStandardCacheTimeoutContext ctx) {
            LOG.log(Level.FINE, ". enterSSLStaplingStandardCacheTimeout:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLStaplingStandardCacheTimeout(ctx.getText(), node));
        }

        @Override
        public void enterSSLUseStapling(HTTPDConfParser.SSLUseStaplingContext ctx) {
            LOG.log(Level.FINE, ". enterSSLUseStapling:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new SSLUseStapling(ctx.getText(), node));
        }

        @Override
        public void enterThreadsPerChild(HTTPDConfParser.ThreadsPerChildContext ctx) {
            LOG.log(Level.FINE, ". enterThreadsPerChild:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new ThreadsPerChild(ctx.getText(), node));
        }

        @Override
        public void enterTimeOut(HTTPDConfParser.TimeOutContext ctx) {
            LOG.log(Level.FINE, ". enterTimeout:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new TimeOut(ctx.getText(), node));
        }

        @Override
        public void enterTransferLog(HTTPDConfParser.TransferLogContext ctx) {
            LOG.log(Level.FINE, ". enterTransferLog:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new TransferLog(ctx.getText(), node));
        }
        
        @Override
        public void enterUseCanonicalName(HTTPDConfParser.UseCanonicalNameContext ctx) {
            LOG.log(Level.FINE, ". enterUseCanonicalName:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new UseCanonicalName(ctx.getText(), node));
        }

        @Override
        public void enterUserDir(HTTPDConfParser.UserDirContext ctx) {
            LOG.log(Level.FINE, ". enterUserDir:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            if (node != null) node.getChildren().add(new UserDir(ctx.getText(), node));
        }


        @Override
        public void enterIfModule(HTTPDConfParser.IfModuleContext ctx) {
            LOG.log(Level.FINE, ". enterIfModule:\"" + ctx.getText() + "\"");
            stack.push(new IfModule(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void enterVirtualHost(HTTPDConfParser.VirtualHostContext ctx) {
            LOG.log(Level.FINE, ". enterVirtualHost:\"" + ctx.getText() + "\"");
            stack.push(new VirtualHost(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void enterFiles(HTTPDConfParser.FilesContext ctx) {
            LOG.log(Level.FINE, ". enterFiles:\"" + ctx.getText() + "\"");
            stack.push(new Files(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void enterDirectory(HTTPDConfParser.DirectoryContext ctx) {
            LOG.log(Level.FINE, ". enterDirectory:\"" + ctx.getText() + "\"");
            stack.push(new Directory(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void exitNameClose(HTTPDConfParser.NameCloseContext ctx) {
            LOG.log(Level.FINE, ". exitNameClose");
            Node node = stack.pop();
            if(stack.peek() != null) stack.peek().getChildren().add(node);
        }

        public ServerConfig getConfig() {
            final Node bottom = stack.pop();
            if (!stack.isEmpty()) {
                throw new IllegalArgumentException("The stack should have been empty at this point. " +
                        "Something went wrong with the parting tree. Step ConfigListener in debugger.");
            }
            if (!(bottom instanceof ServerConfig)) {
                throw new IllegalStateException("There should be ServerConfig node on the stack now. " +
                        "Use debugger and take a look at what was the last token parsed.");
            }
            if (Utils.isCollectionEmpty(bottom.getChildren())) {
                throw new IllegalArgumentException("The main server config must have some config directives. " +
                        "The parsing probably failed horribly.");
            }
            return (ServerConfig) bottom;
        }
    }

    public static ServerConfig deserialize(final File config) throws IOException {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        if (!config.exists()) {
            throw new IllegalArgumentException("Check the file with httpd.conf. It might not exist: " + config.getPath());
        }

        final ServerConfig serverConfig;

        try (final InputStream is = new FileInputStream(config)) {
            final Lexer lexer = new HTTPDConfLexer(CharStreams.fromStream(is));
            final TokenStream tokenStream = new CommonTokenStream(lexer);
            final HTTPDConfParser.ConfigContext tree = new HTTPDConfParser(tokenStream).config();
            ConfigListener configListener = new ConfigListener();
            ParseTreeWalker.DEFAULT.walk(configListener, tree);
            serverConfig = configListener.getConfig();
            configListener = null;
        }

        return serverConfig;
    }
}
