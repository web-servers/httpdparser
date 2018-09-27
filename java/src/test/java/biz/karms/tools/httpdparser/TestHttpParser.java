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

import biz.karms.tools.httpdparser.domain.ServerConfig;
import biz.karms.tools.httpdparser.domain.directives.DirectoryIndex;
import biz.karms.tools.httpdparser.domain.directives.Listen;
import biz.karms.tools.httpdparser.domain.directives.ServerRoot;
import biz.karms.tools.httpdparser.domain.elements.Files;
import biz.karms.tools.httpdparser.domain.elements.IfModule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Michal Karm Babacek
 */
public class TestHttpParser {

    @Test
    public void testHttpConf_1() throws URISyntaxException, IOException {
        final File f = new File(TestHttpParser.class.getResource("/httpd-1.conf").toURI());
        final ServerConfig sc = HttpdParser.deserialize(f);
        assertFalse("ServerConfig was empty.",
                sc.getChildren().isEmpty());
        assertTrue("There were suppsoed to be 6 children in the root node.",
                sc.getChildren().size() == 6);

        assertTrue("Files element expected here.",
                sc.getChildren().get(0) instanceof Files);
        assertEquals("Files expression is wrong.", "\".htx*\"",
                sc.getChildren().get(0).getData());
        assertTrue("Files element expected to be empty as its contents are commented out.",
                sc.getChildren().get(0).getChildren().isEmpty());

        assertTrue("Files element expected here.",
                sc.getChildren().get(1) instanceof Files);
        assertEquals("Files expression is wrong.", "\".password*\"",
                sc.getChildren().get(1).getData());
        assertEquals("Files element has wrong amount of children.",
                1, sc.getChildren().get(1).getChildren().size());
        assertTrue("Files element expected to have directive DirectoryIndex as its child.",
                sc.getChildren().get(1).getChildren().get(0) instanceof DirectoryIndex);
        assertEquals("DirectoryIndex string is wrong.", "index.php",
                sc.getChildren().get(1).getChildren().get(0).getData());

        assertTrue("ServerRoot directive expected here.",
                sc.getChildren().get(2) instanceof ServerRoot);
        assertEquals("ServerRoot path is wrong.", "\"/home/karm/Projects/MOD_CLUSTER/httpd-trunk-build\"",
                sc.getChildren().get(2).getData());

        assertTrue("IfModule element expected here.",
                sc.getChildren().get(3) instanceof IfModule);
        assertEquals("IfModule module is wrong.", "dir_module",
                sc.getChildren().get(3).getData());
        assertEquals("IfModule element has wrong amount of children.",
                1, sc.getChildren().get(3).getChildren().size());
        assertTrue("IfModule element expected to have directive DirectoryIndex as its child.",
                sc.getChildren().get(3).getChildren().get(0) instanceof DirectoryIndex);
        assertEquals("DirectoryIndex string is wrong.", "index.html",
                sc.getChildren().get(3).getChildren().get(0).getData());


        assertTrue("Listen directive expected here.",
                sc.getChildren().get(4) instanceof Listen);
        assertEquals("Listen address is wrong.", "0.0.0.0:80",
                sc.getChildren().get(4).getData());

        assertTrue("Listen directive expected here.",
                sc.getChildren().get(5) instanceof Listen);
        assertEquals("Listen address is wrong.", "0.0.0.0:80 192.168.0.1:8080",
                sc.getChildren().get(5).getData());
    }

}