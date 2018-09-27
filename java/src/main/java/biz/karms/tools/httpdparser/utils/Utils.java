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

package biz.karms.tools.httpdparser.utils;

import java.util.Collection;

/**
 * Arbitrary collection of simple stateless helper methods.
 *
 * @author Michal Karm Babacek
 */
public class Utils {

    public static boolean isCollectionEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }
}
