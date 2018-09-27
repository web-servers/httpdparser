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

import java.io.File;
import java.io.IOException;

/**
 * @author Michal Karm Babacek
 */
class Main {
    public static void main(final String[] args) throws IOException {
        final ServerConfig serverConfig = HttpdParser.deserialize(new File(args[0]));
        System.out.println("File successfully deserialized. Set level FINE in resources/logging.properties to see more.");
    }
}
