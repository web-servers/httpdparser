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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * @author Michal Karm Babacek
 */
class Main {
    private static final Deque<Node> stack = new ArrayDeque<>();

    static class ConfigListener extends HTTPDConfParserBaseListener {

        @Override
        public void enterConfig(HTTPDConfParser.ConfigContext ctx) {
            System.out.println("enterConfig: " + ctx.getChildCount());
            stack.push(new ServerConfig(new ArrayList<>(ctx.getChildCount())));
        }

        @Override
        public void exitConfig(HTTPDConfParser.ConfigContext ctx) {
            System.out.println("exitConfig");
            if (!(stack.peek() instanceof ServerConfig)) {
                throw new IllegalStateException("There should be ServerConfig node on the stack now." +
                        "Take a look what was the last token parsed.");
            }
            if (Utils.isCollectionEmpty(stack.pop().getChildren())) {
                throw new IllegalArgumentException("The main server config must have some directives." +
                        "The parsing probably failed horribly.");
            }
        }

        @Override
        public void enterListenAddresses(HTTPDConfParser.ListenAddressesContext ctx) {
            System.out.println(". enterListenAddresses:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new Listen(ctx.getText(), node));
        }

        @Override
        public void enterServerRootPath(HTTPDConfParser.ServerRootPathContext ctx) {
            System.out.println(". enterServerRootPath:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new ServerRoot(ctx.getText(), node));
        }

        @Override
        public void enterDirectoryIndex(HTTPDConfParser.DirectoryIndexContext ctx) {
            System.out.println(". enterDirectoryIndex:\"" + ctx.getText() + "\"");
            final Node node = stack.peek();
            node.getChildren().add(new DirectoryIndex(ctx.getText(), node));
        }

        @Override
        public void enterIfModuleAttribute(HTTPDConfParser.IfModuleAttributeContext ctx) {
            System.out.println(". enterIfModuleAttribute:\"" + ctx.getText() + "\"");
            stack.push(new IfModule(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void enterFilesAttribute(HTTPDConfParser.FilesAttributeContext ctx) {
            System.out.println(". enterFilesAttribute:\"" + ctx.getText() + "\"");
            stack.push(new Files(new ArrayList<>(ctx.getChildCount()), ctx.getText(), stack.peek()));
        }

        @Override
        public void exitNameClose(HTTPDConfParser.NameCloseContext ctx) {
            System.out.println(". exitNameClose");
            stack.pop();
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("/httpd.conf");
        Lexer lexer = new HTTPDConfLexer(CharStreams.fromStream(inputStream));
        TokenStream tokenStream = new CommonTokenStream(lexer);
        HTTPDConfParser.ConfigContext tree = new HTTPDConfParser(tokenStream).config();

        ConfigListener configListener = new ConfigListener();
        ParseTreeWalker.DEFAULT.walk(configListener, tree);
    }
}