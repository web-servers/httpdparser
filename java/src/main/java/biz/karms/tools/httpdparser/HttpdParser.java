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
import biz.karms.tools.httpdparser.domain.directives.AddDefaultCharset;
import biz.karms.tools.httpdparser.domain.directives.AddOutputFilter;
import biz.karms.tools.httpdparser.domain.directives.AddType;
import biz.karms.tools.httpdparser.domain.directives.Alias;
import biz.karms.tools.httpdparser.domain.directives.AllowOverride;
import biz.karms.tools.httpdparser.domain.directives.ChrootDir;
import biz.karms.tools.httpdparser.domain.directives.CustomLog;
import biz.karms.tools.httpdparser.domain.directives.DirectoryIndex;
import biz.karms.tools.httpdparser.domain.directives.EnableSendfile;
import biz.karms.tools.httpdparser.domain.directives.ErrorLog;
import biz.karms.tools.httpdparser.domain.directives.Group;
import biz.karms.tools.httpdparser.domain.directives.IndexOptions;
import biz.karms.tools.httpdparser.domain.directives.Listen;
import biz.karms.tools.httpdparser.domain.directives.LogFormat;
import biz.karms.tools.httpdparser.domain.directives.LogLevel;
import biz.karms.tools.httpdparser.domain.directives.MimeMagicFile;
import biz.karms.tools.httpdparser.domain.directives.Options;
import biz.karms.tools.httpdparser.domain.directives.Require;
import biz.karms.tools.httpdparser.domain.directives.ScriptAlias;
import biz.karms.tools.httpdparser.domain.directives.ServerAdmin;
import biz.karms.tools.httpdparser.domain.directives.ServerRoot;
import biz.karms.tools.httpdparser.domain.directives.Suexec;
import biz.karms.tools.httpdparser.domain.directives.TypesConfig;
import biz.karms.tools.httpdparser.domain.directives.User;
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
            node.getChildren().add(new Listen(ctx.getText(), node));
        }

        @Override
        public void enterServerRootPath(HTTPDConfParser.ServerRootPathContext ctx) {
            LOG.log(Level.FINE, ". enterServerRootPath:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new ServerRoot(ctx.getText(), node));
        }

        @Override
        public void enterUser(HTTPDConfParser.UserContext ctx) {
            LOG.log(Level.FINE, ". enterUser:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new User(ctx.getText(), node));
        }

        @Override
        public void enterGroup(HTTPDConfParser.GroupContext ctx) {
            LOG.log(Level.FINE, ". enterGroup:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new Group(ctx.getText(), node));
        }

        @Override
        public void enterChrootDir(HTTPDConfParser.ChrootDirContext ctx) {
            LOG.log(Level.FINE, ". enterChrootDir:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new ChrootDir(ctx.getText(), node));
        }

        @Override
        public void enterSuexec(HTTPDConfParser.SuexecContext ctx) {
            LOG.log(Level.FINE, ". enterSuexec:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new Suexec(ctx.getText(), node));
        }

        @Override
        public void enterServerAdmin(HTTPDConfParser.ServerAdminContext ctx) {
            LOG.log(Level.FINE, ". enterServerAdmin:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new ServerAdmin(ctx.getText(), node));
        }

        @Override
        public void enterDirectoryIndex(HTTPDConfParser.DirectoryIndexContext ctx) {
            LOG.log(Level.FINE, ". enterDirectoryIndex:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new DirectoryIndex(ctx.getText(), node));
        }

        @Override
        public void enterAllowOverride(HTTPDConfParser.AllowOverrideContext ctx) {
            LOG.log(Level.FINE, ". enterAllowOverride:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new AllowOverride(ctx.getText(), node));
        }

        @Override
        public void enterRequire(HTTPDConfParser.RequireContext ctx) {
            LOG.log(Level.FINE, ". enterRequire:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new Require(ctx.getText(), node));
        }

        @Override
        public void enterOptions_(HTTPDConfParser.Options_Context ctx) {
            LOG.log(Level.FINE, ". enterOptions_:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new Options(ctx.getText(), node));
        }

        @Override
        public void enterErrorLog(HTTPDConfParser.ErrorLogContext ctx) {
            LOG.log(Level.FINE, ". enterErrorLog:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new ErrorLog(ctx.getText(), node));
        }

        @Override
        public void enterLogLevel(HTTPDConfParser.LogLevelContext ctx) {
            LOG.log(Level.FINE, ". enterLogLevel:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new LogLevel(ctx.getText(), node));
        }

        @Override
        public void enterLogFormat(HTTPDConfParser.LogFormatContext ctx) {
            LOG.log(Level.FINE, ". enterLogFormat:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new LogFormat(ctx.getText(), node));
        }

        @Override
        public void enterCustomLog(HTTPDConfParser.CustomLogContext ctx) {
            LOG.log(Level.FINE, ". enterCustomLog:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new CustomLog(ctx.getText(), node));
        }

        @Override
        public void enterScriptAlias(HTTPDConfParser.ScriptAliasContext ctx) {
            LOG.log(Level.FINE, ". enterScriptAlias:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new ScriptAlias(ctx.getText(), node));
        }

        @Override
        public void enterTypesConfig(HTTPDConfParser.TypesConfigContext ctx) {
            LOG.log(Level.FINE, ". enterTypesConfig:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new TypesConfig(ctx.getText(), node));
        }

        @Override
        public void enterAddType(HTTPDConfParser.AddTypeContext ctx) {
            LOG.log(Level.FINE, ". enterAddType:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new AddType(ctx.getText(), node));
        }

        @Override
        public void enterAddOutputFilter(HTTPDConfParser.AddOutputFilterContext ctx) {
            LOG.log(Level.FINE, ". enterAddOutputFilter:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new AddOutputFilter(ctx.getText(), node));
        }

        @Override
        public void enterAddDefaultCharset(HTTPDConfParser.AddDefaultCharsetContext ctx) {
            LOG.log(Level.FINE, ". enterAddDefaultCharset:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new AddDefaultCharset(ctx.getText(), node));
        }

        @Override
        public void enterMimeMagicFile(HTTPDConfParser.MimeMagicFileContext ctx) {
            LOG.log(Level.FINE, ". enterMimeMagicFile:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new MimeMagicFile(ctx.getText(), node));
        }

        @Override
        public void enterEnableSendfile(HTTPDConfParser.EnableSendfileContext ctx) {
            LOG.log(Level.FINE, ". enterEnableSendfile:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new EnableSendfile(ctx.getText(), node));
        }

        @Override
        public void enterIndexOptions(HTTPDConfParser.IndexOptionsContext ctx) {
            LOG.log(Level.FINE, ". enterIndexOptions:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new IndexOptions(ctx.getText(), node));
        }

        @Override
        public void enterAlias(HTTPDConfParser.AliasContext ctx) {
            LOG.log(Level.FINE, ". enterAlias:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new Alias(ctx.getText(), node));
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
            stack.peek().getChildren().add(node);
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
