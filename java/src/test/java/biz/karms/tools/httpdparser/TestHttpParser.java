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

        int expectedChildren = 37;
        assertEquals("There were suppsoed to be "+expectedChildren+" children in the root node. " +
                        "Seeing a different number usually means there were unexpected tokens. " +
                        "Look at the very beginning of the parsing log.",
                expectedChildren, sc.getChildren().size());

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

        assertTrue("VirtualHost element expected here.",
                sc.getChildren().get(6) instanceof VirtualHost);
        assertEquals("VirtualHost address is wrong.", "*:6666",
                sc.getChildren().get(6).getData());
        assertEquals("VirtualHost element has wrong amount of children.",
                1, sc.getChildren().get(6).getChildren().size());
        assertTrue("VirtualHost element expected to have directive DirectoryIndex as its child.",
                sc.getChildren().get(6).getChildren().get(0) instanceof DirectoryIndex);
        assertEquals("DirectoryIndex string is wrong.", "index.htm",
                sc.getChildren().get(6).getChildren().get(0).getData());

        assertTrue("VirtualHost element expected here.",
                sc.getChildren().get(7) instanceof VirtualHost);
        assertEquals("VirtualHost address is wrong.", "10.1.2.3:80",
                sc.getChildren().get(7).getData());
        assertEquals("VirtualHost element has wrong amount of children.",
                1, sc.getChildren().get(7).getChildren().size());
        assertTrue("VirtualHost element expected to have directive ServerAdmin as its child.",
                sc.getChildren().get(7).getChildren().get(0) instanceof ServerAdmin);
        assertEquals("ServerAdmin string is wrong.", "webmaster@host.example.com",
                sc.getChildren().get(7).getChildren().get(0).getData());

        assertTrue("VirtualHost element expected here.",
                sc.getChildren().get(8) instanceof VirtualHost);
        assertEquals("VirtualHost address is wrong.", "[2001:db8::a00:20ff:fea7:ccea]:80",
                sc.getChildren().get(8).getData());
        assertEquals("VirtualHost element has wrong amount of children.",
                1, sc.getChildren().get(8).getChildren().size());
        assertTrue("VirtualHost element expected to have directive ServerAdmin as its child.",
                sc.getChildren().get(8).getChildren().get(0) instanceof ServerAdmin);
        assertEquals("ServerAdmin string is wrong.", "IPv6Fan@host.example.com",
                sc.getChildren().get(8).getChildren().get(0).getData());

        assertTrue("VirtualHost element expected here.",
                sc.getChildren().get(9) instanceof VirtualHost);
        assertEquals("VirtualHost address is wrong.", "[2001:db8::a00:20ff:fea7:ccea]:80",
                sc.getChildren().get(9).getData());
        assertEquals("VirtualHost element has wrong amount of children.",
                1, sc.getChildren().get(9).getChildren().size());
        assertTrue("VirtualHost element expected to have directive ServerAdmin as its child.",
                sc.getChildren().get(9).getChildren().get(0) instanceof ServerAdmin);
        assertEquals("ServerAdmin string is wrong.", "IPv6Fan-who-also-adds-space@host.example.com",
                sc.getChildren().get(9).getChildren().get(0).getData());

        assertTrue("VirtualHost element expected here.",
                sc.getChildren().get(10) instanceof VirtualHost);
        assertEquals("VirtualHost address is wrong.", "_default_",
                sc.getChildren().get(10).getData());
        assertEquals("VirtualHost element has wrong amount of children.",
                1, sc.getChildren().get(10).getChildren().size());
        assertTrue("VirtualHost element expected to have directive ServerAdmin as its child.",
                sc.getChildren().get(10).getChildren().get(0) instanceof ServerAdmin);
        assertEquals("ServerAdmin string is wrong.", "https://Defaultmaster.example.com",
                sc.getChildren().get(10).getChildren().get(0).getData());

        assertTrue("VirtualHost element expected here.",
                sc.getChildren().get(11) instanceof VirtualHost);
        assertEquals("VirtualHost address is wrong.", "www.example.com:443",
                sc.getChildren().get(11).getData());
        assertEquals("VirtualHost element has wrong amount of children.",
                1, sc.getChildren().get(11).getChildren().size());
        assertTrue("VirtualHost element expected to have directive ServerAdmin as its child.",
                sc.getChildren().get(11).getChildren().get(0) instanceof ServerAdmin);
        assertEquals("ServerAdmin string is wrong.", "https://master.example.com",
                sc.getChildren().get(11).getChildren().get(0).getData());

        assertTrue("User directive expected here.",
                sc.getChildren().get(12) instanceof User);
        assertEquals("User string is wrong.", "apache",
                sc.getChildren().get(12).getData());

        assertTrue("Group directive expected here.",
                sc.getChildren().get(13) instanceof Group);
        assertEquals("Group string is wrong.", "www-data",
                sc.getChildren().get(13).getData());

        assertTrue("ChrootDir directive expected here.",
                sc.getChildren().get(14) instanceof ChrootDir);
        assertEquals("ChrootDir string is wrong.", "/path/to/directory",
                sc.getChildren().get(14).getData());

        assertTrue("Suexec directive expected here.",
                sc.getChildren().get(15) instanceof Suexec);
        assertEquals("Suexec string is wrong.", "Off",
                sc.getChildren().get(15).getData());

        assertTrue("ServerAdmin directive expected here.",
                sc.getChildren().get(16) instanceof ServerAdmin);
        assertEquals("ServerAdmin string is wrong.", "root@localhost",
                sc.getChildren().get(16).getData());

        assertTrue("Directory element expected here.",
                sc.getChildren().get(17) instanceof Directory);
        assertEquals("Directory dir is wrong.", "/",
                sc.getChildren().get(17).getData());
        assertEquals("Directory element has wrong amount of children.",
                2, sc.getChildren().get(17).getChildren().size());
        assertTrue("Directory element expected to have directive AllowOverride as its child.",
                sc.getChildren().get(17).getChildren().get(0) instanceof AllowOverride);
        assertEquals("AllowOverride string is wrong.", "none",
                sc.getChildren().get(17).getChildren().get(0).getData());
        assertTrue("Directory element expected to have directive Require as its child.",
                sc.getChildren().get(17).getChildren().get(1) instanceof Require);
        assertEquals("Require string is wrong.", "all denied",
                sc.getChildren().get(17).getChildren().get(1).getData());

        assertTrue("Directory element expected here.",
                sc.getChildren().get(18) instanceof Directory);
        assertEquals("Directory dir is wrong.", "~\"abc$\"",
                sc.getChildren().get(18).getData());
        assertEquals("Directory element has wrong amount of children.",
                3, sc.getChildren().get(18).getChildren().size());
        assertTrue("Directory element expected to have directive Options as its child.",
                sc.getChildren().get(18).getChildren().get(0) instanceof Options);
        assertEquals("Options string is wrong.", "Indexes FollowSymLinks",
                sc.getChildren().get(18).getChildren().get(0).getData());
        assertTrue("Directory element expected to have directive AllowOverride as its child.",
                sc.getChildren().get(18).getChildren().get(1) instanceof AllowOverride);
        assertEquals("AllowOverride string is wrong.", "None",
                sc.getChildren().get(18).getChildren().get(1).getData());
        assertTrue("Directory element expected to have directive Require as its child.",
                sc.getChildren().get(18).getChildren().get(2) instanceof Require);
        assertEquals("Require string is wrong.", "all granted",
                sc.getChildren().get(18).getChildren().get(2).getData());

        assertTrue("Directory element expected here.",
                sc.getChildren().get(19) instanceof Directory);
        assertEquals("Directory dir is wrong.", "\"/oh/no\"",
                sc.getChildren().get(19).getData());
        assertEquals("Directory element has wrong amount of children.",
                3, sc.getChildren().get(19).getChildren().size());
        assertTrue("Directory element expected to have directive Options as its child.",
                sc.getChildren().get(19).getChildren().get(0) instanceof Options);
        assertEquals("Options string is wrong.", "Indexes MultiViews FollowSymlinks",
                sc.getChildren().get(19).getChildren().get(0).getData());
        assertTrue("Directory element expected to have directive AllowOverride as its child.",
                sc.getChildren().get(19).getChildren().get(1) instanceof AllowOverride);
        assertEquals("AllowOverride string is wrong.", "None",
                sc.getChildren().get(19).getChildren().get(1).getData());
        assertTrue("Directory element expected to have directive Require as its child.",
                sc.getChildren().get(19).getChildren().get(2) instanceof Require);
        assertEquals("Require string is wrong.", "all granted",
                sc.getChildren().get(19).getChildren().get(2).getData());

        assertTrue("ErrorLog directive expected here.",
                sc.getChildren().get(20) instanceof ErrorLog);
        assertEquals("ErrorLog string is wrong.", "\"logs/error_log\"",
                sc.getChildren().get(20).getData());

        assertTrue("LogLevel directive expected here.",
                sc.getChildren().get(21) instanceof LogLevel);
        assertEquals("LogLevel string is wrong.", "warn",
                sc.getChildren().get(21).getData());

        assertTrue("LogFormat directive expected here.",
                sc.getChildren().get(22) instanceof LogFormat);
        assertEquals("LogFormat string is wrong.", "\"%h %l %u %t \\\"%r\\\" %>s %b \\\"%{Referer}i\\\" \\\"%{User-Agent}i\\\"\" combined",
                sc.getChildren().get(22).getData());

        assertTrue("LogFormat directive expected here.",
                sc.getChildren().get(23) instanceof LogFormat);
        assertEquals("LogFormat string is wrong.", "\"%h %l %u %t \\\"%r\\\" %>s %b\" common",
                sc.getChildren().get(23).getData());

        assertTrue("LogFormat directive expected here.",
                sc.getChildren().get(24) instanceof LogFormat);
        assertEquals("LogFormat string is wrong.", "\"%h %l %u %t \\\"%r\\\" %>s %b \\\"%{Referer}i\\\" \\\"%{User-Agent}i\\\" %I %O\" combinedio",
                sc.getChildren().get(24).getData());

        assertTrue("CustomLog directive expected here.",
                sc.getChildren().get(25) instanceof CustomLog);
        assertEquals("CustomLog string is wrong.", "\"logs/access_log\" combined",
                sc.getChildren().get(25).getData());

        assertTrue("ScriptAlias directive expected here.",
                sc.getChildren().get(26) instanceof ScriptAlias);
        assertEquals("ScriptAlias string is wrong.", "/cgi-bin/ \"/var/www/cgi-bin/\"",
                sc.getChildren().get(26).getData());

        assertTrue("TypesConfig directive expected here.",
                sc.getChildren().get(27) instanceof TypesConfig);
        assertEquals("TypesConfig string is wrong.", "/etc/mime.types",
                sc.getChildren().get(27).getData());

        assertTrue("AddType directive expected here.",
                sc.getChildren().get(28) instanceof AddType);
        assertEquals("AddType string is wrong.", "application/x-compress .Z",
                sc.getChildren().get(28).getData());

        assertTrue("AddType directive expected here.",
                sc.getChildren().get(29) instanceof AddType);
        assertEquals("AddType string is wrong.", "application/x-gzip .gz .tgz",
                sc.getChildren().get(29).getData());

        assertTrue("AddType directive expected here.",
                sc.getChildren().get(30) instanceof AddType);
        assertEquals("AddType string is wrong.", "text/html .shtml",
                sc.getChildren().get(30).getData());

        assertTrue("AddOutputFilter directive expected here.",
                sc.getChildren().get(31) instanceof AddOutputFilter);
        assertEquals("AddOutputFilter string is wrong.", "INCLUDES .shtml",
                sc.getChildren().get(31).getData());

        assertTrue("AddDefaultCharset directive expected here.",
                sc.getChildren().get(32) instanceof AddDefaultCharset);
        assertEquals("AddDefaultCharset string is wrong.", "UTF-8",
                sc.getChildren().get(32).getData());

        assertTrue("MimeMagicFile directive expected here.",
                sc.getChildren().get(33) instanceof MimeMagicFile);
        assertEquals("MimeMagicFile string is wrong.", "conf/magic",
                sc.getChildren().get(33).getData());

        assertTrue("EnableSendfile directive expected here.",
                sc.getChildren().get(34) instanceof EnableSendfile);
        assertEquals("EnableSendfile string is wrong.", "on",
                sc.getChildren().get(34).getData());

        assertTrue("IndexOptions directive expected here.",
                sc.getChildren().get(35) instanceof IndexOptions);
        assertEquals("IndexOptions string is wrong.", "FancyIndexing HTMLTable VersionSort",
                sc.getChildren().get(35).getData());

        assertTrue("Alias directive expected here.",
                sc.getChildren().get(36) instanceof Alias);
        assertEquals("Alias string is wrong.", "/icons/ \"/usr/share/httpd/icons/\"",
                sc.getChildren().get(36).getData());
    }
}
