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
import biz.karms.tools.httpdparser.domain.ServerConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Michal Karm Babacek
 */
public class ChrootDir implements Node {

    private final String chrootdir;
    private final Node parent;
    public static final String doc = "https://httpd.apache.org/docs/trunk/mod/mod_unixd.html#chrootdir";

    public ChrootDir(String chrootdir, Node parent) {
        if (StringUtils.isBlank(chrootdir)) {
            throw new IllegalArgumentException("ChrootDir must not be empty. See " + doc);
        }
        if (!(parent instanceof ServerConfig)) {
            throw new IllegalArgumentException("ChrootDir seems to be buried in an illegal context. See " + doc);
        }
        this.chrootdir = chrootdir;
        this.parent = parent;
    }

    @Override
    public String getData() {
        return chrootdir;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "ChrootDir " + chrootdir;
    }
}

