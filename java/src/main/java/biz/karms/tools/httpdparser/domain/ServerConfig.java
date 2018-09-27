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

package biz.karms.tools.httpdparser.domain;

import java.util.List;

/**
 * Root of the config tree
 *
 * @author Michal Karm Babacek
 */
public class ServerConfig implements Node {

    private final List<Node> children;

    public ServerConfig(List<Node> children) {
        if (children == null) {
            throw new IllegalArgumentException("children must not be null");
        }
        this.children = children;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public Node getParent() {
        return null;
    }
}
