/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package webdsl

import org.junit.Test
import webdsl.support.HiddenInputDsl

@Mixin(NonServerMixin)
class HiddenInputDslTest {

  @Test
  void test_dsl_instance() {
    html {
      form {
        input(name: "name", id: "id", type: 'hidden', value: ' MYVALUE ')
      }
    }

    webdsl {
      assert $("#id") instanceof HiddenInputDsl
    }
  }

  @Test
  void test_getValue() {
    html {
      form {
        input(name: "name", id: "id", type: 'hidden', value: ' MYVALUE ')
      }
    }

    webdsl {
      assert $("#id").value == "MYVALUE"
      assert $("#id").untrimmedValue == " MYVALUE "
    }
  }

  @Test
  void test_setValue() {
    html {
      form {
        input(name: "name", id: "id", type: 'hidden', value: 'ABC')
      }
    }

    webdsl {
      $("#id").value = " MYVALUE "

      assert $("#id").value == "MYVALUE"
    }
  }

  @Test
  void test_getText() {
    html {
      form {
        input(name: "name", id: "id", type: 'hidden', value: ' MYVALUE ')
      }
    }

    webdsl {
      assert $("#id").text == "MYVALUE"
      assert $("#id").untrimmedText == " MYVALUE "
    }
  }

  @Test
  void test_setText() {
    html {
      form {
        input(name: "name", id: "id", type: 'hidden', value: 'ABC')
      }
    }

    webdsl {
      $("#id").value = " MYVALUE "

      assert $("#id").text == "MYVALUE"
    }
  }

  @Test
  void test_fillInWith_by_name_input() {
    html {
      form {
        input(name: "name", id: "id", type: 'hidden')
      }
    }

    webdsl {
      form {
        fillInWith([name: "MYVALUE"])
      }
      assert $("#id").value == "MYVALUE"
      assert $("#id").text == "MYVALUE"
    }
  }

  @Test
  void test_fillInWith_by_id() {
    html {
      form {
        input(id: "id", name: "name", type: 'hidden')
      }
    }

    webdsl {
      form {
        fillInWith([id: "MYVALUE"])
      }
      assert $("#id").value == "MYVALUE"
      assert $("#id").text == "MYVALUE"
    }
  }

  @Test
  void test_values() {
    html {
      form {
        input(name: "name", type: 'hidden', value: "MYVALUE")
      }
    }

    webdsl {
      form {
        assert [name: "MYVALUE"] == values()
      }
    }
  }

  @Test
  void test_valuesById_input() {
    html {
      form {
        input(id: "id", type: 'hidden', name: "name", value: "MYVALUE")
      }
    }

    webdsl {
      form {
        assert [id: "MYVALUE"] == valuesById()
      }
    }
  }

  @Test
  void test_page_updated_when_javascript_executes() {
    html """
      <input id="textToEcho" onchange="echo();" type="text" value=""/>
      <input id="echoedText" type="text" value=""/>

      <script>
        function echo() {
          var textToEcho = document.getElementById('textToEcho');
          if (textToEcho.value != "")
            document.getElementById('echoedText').value = textToEcho.value;
        }
      </script>
    """

    webdsl {
      textToEcho.value = 'abcd'
      assert echoedText.value == 'abcd'
    }
  }


}