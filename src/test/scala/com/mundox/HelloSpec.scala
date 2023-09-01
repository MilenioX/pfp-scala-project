package example

import com.mundox.Hello

class HelloSpec extends munit.FunSuite {
  test("say hello") {
    assertEquals(Hello.greeting, "hello")
  }
}
