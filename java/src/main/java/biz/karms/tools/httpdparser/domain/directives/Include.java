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

package biz.karms.tools.httpdparser.domain.directives;

import biz.karms.tools.httpdparser.domain.Node;
import biz.karms.tools.httpdparser.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Included config
 *
 * @author Michal Karm Babacek
 */
public class Include implements Node {

    private final List<Node> children;
    private final Node parent;
    private final String path;
    public static String doc = "https://httpd.apache.org/docs/trunk/mod/core.html#include";

    public Include(List<Node> children, Node parent, String path) {
        if (Utils.isCollectionEmpty(children)) {
            throw new IllegalArgumentException("Empty include is most likely an error.");
        }
        if (StringUtils.isBlank(path)) {
            throw new IllegalArgumentException("Path to a config file(s) must not be empty. See " + doc);
        }
        this.children = children;
        this.parent = parent;
        this.path = path;
    }

    @Override
    public String getData() {
        return path;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Include " + path;
    }
}
