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

class BaseElementDslTest extends AbstractNonServerTest {

  void test_hasClass() {
    html {
      div(id: "my-div", class: 'look-a-class') {}
    }

    webdsl {
      assert $('#my-div').hasClass('look-a-class')
    }
  }

  void test_hasClass_not_the_same_class() {
    html {
      div(id: "my-div", class: 'look-a-class') {}
    }

    webdsl {
      assertFalse $('#my-div').hasClass('look-another-class')
    }
  }

  void test_hasClass_does_not_have_a_class() {
    html {
      div(id: "my-div") {}
    }

    webdsl {
      assertFalse $('#my-div').hasClass('look-a-class')
    }
  }

  void test_hasClass_multiple_classes() {
    html {
      div(id: "my-div", class: 'one-class two-class three-class blue-class') {}
    }

    webdsl {
      assert $('#my-div').hasClass('one-class')
      assert $('#my-div').hasClass('two-class')
      assert $('#my-div').hasClass('three-class')
      assert $('#my-div').hasClass('blue-class')
    }
  }


  void test_hasAttribute() {
    html {
      div(id: "my-div", class: 'look-a-class') {}
    }

    webdsl {
      assert $('#my-div').hasAttribute('class')
      assert $('#my-div').hasAttribute('id')
    }
  }

  void test_hasAttribute_empty_attribute() {
    html {
      div(id: "my-div", class: 'look-a-class', disabled: '', checked: '') {}
    }

    webdsl {
      assert $("#my-div").hasAttribute('disabled')
      assert $("#my-div").hasAttribute('checked')
    }
  }

  void test_hasAttribute_attribute_with_no_value() {
    html("<div id='my-div' class='look-a-class' disabled checked></div>")

    webdsl {
      assert $("#my-div").hasAttribute('disabled')
      assert $("#my-div").hasAttribute('checked')
    }
  }

  void test_hasAttribute_no_attribute() {
    html {
      div(id: "my-div") {}
    }

    webdsl {
      assertFalse $("#my-div").hasAttribute('disabled')
      assertFalse $("#my-div").hasAttribute('style')
    }
  }

  void test_insertBefore_first_element() {
    html {
      div('b', id: 'target')
      div('c')
    }

    webdsl {
      assert $$('div').text == ['b', 'c']

      $('#target').insertBefore { div('a') }

      assert $$('div').text == ['a', 'b', 'c']
    }
  }

  void test_insertBefore_last_element() {
    html {
      div('a')
      div('c', id: 'target')
    }

    webdsl {
      assert $$('div').text == ['a', 'c']

      $('#target').insertBefore { div('b') }

      assert $$('div').text == ['a', 'b', 'c']
    }
  }

  void test_insertAfter_first_element() {
    html {
      div('a', id: 'target')
      div('c')
    }

    webdsl {
      assert $$('div').text == ['a', 'c']

      $('#target').insertAfter { div('b') }

      assert $$('div').text == ['a', 'b', 'c']
    }
  }

  void test_insertAfter_last_element() {
    html {
      div('a')
      div('b', id: 'target')
    }

    webdsl {
      assert $$('div').text == ['a', 'b']

      $('#target').insertAfter { div('c') }

      assert $$('div').text == ['a', 'b', 'c']
    }
  }

  void test_prependChild_children_exist() {
    html {
      div(id: 'target') {
        div('a2')
        div('a3')
      }
      div('b')
    }

    webdsl {
      assert $$('div').text == ['a2a3', 'a2', 'a3', 'b']

      $('#target').prependChild { div('a1') }

      assert $$('div').text == ['a1a2a3', 'a1', 'a2', 'a3', 'b']
    }
  }

  void test_prependChild_no_children_exist() {
    html {
      div(id: 'target') {}

      div('b')
    }

    webdsl {
      assert $$('div').text == ['', 'b']

      $('#target').prependChild { div('a') }

      assert $$('div').text == ['a', 'a', 'b']
    }
  }


  void test_appendChild_children_exist() {
    html {
      div(id: 'target') {
        div('a1')
        div('a2')
      }
      div('b')
    }

    webdsl {
      assert $$('div').text == ['a1a2', 'a1', 'a2', 'b']

      $('#target').appendChild { div('a3') }

      assert $$('div').text == ['a1a2a3', 'a1', 'a2', 'a3', 'b']
    }
  }

  void test_appendChild_no_children_exist() {
    html {
      div(id: 'target') {}

      div('b')
    }

    webdsl {
      assert $$('div').text == ['', 'b']

      $('#target').appendChild { div('a') }

      assert $$('div').text == ['a', 'a', 'b']
    }
  }

}