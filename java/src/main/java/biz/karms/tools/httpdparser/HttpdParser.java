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
import biz.karms.tools.httpdparser.domain.directives.DirectoryIndex;
import biz.karms.tools.httpdparser.domain.directives.Listen;
import biz.karms.tools.httpdparser.domain.directives.ServerRoot;
import biz.karms.tools.httpdparser.domain.elements.Files;
import biz.karms.tools.httpdparser.domain.elements.IfModule;
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
        public void enterDirectoryIndex(HTTPDConfParser.DirectoryIndexContext ctx) {
            LOG.log(Level.FINE, ". enterDirectoryIndex:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new DirectoryIndex(ctx.getText(), node));
        }

        @Override
        public void enterIfModuleAttribute(HTTPDConfParser.IfModuleAttributeContext ctx) {
            LOG.log(Level.FINE, ". enterIfModuleAttribute:\"" + ctx.getText() + "\"");
            stack.push(new IfModule(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void enterFilesAttribute(HTTPDConfParser.FilesAttributeContext ctx) {
            LOG.log(Level.FINE, ". enterFilesAttribute:\"" + ctx.getText() + "\"");
            stack.push(new Files(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
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
