/**
 * Copyright 2013 Slawomir Wojcicki
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Sorting list.
 *
 * @author Slawomir Wojcicki
 */
public class SortExample {

  public static void main(String[] args) {
    String[] col = {"a", "c", "b"};
    List list = Arrays.asList(col);
    System.out.println(list);
    Collections.sort(list);
    System.out.println(list);
    Collections.reverse(list);
    System.out.println(list);
  }
}
