package com.zwj.springboot.demo;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 1.JUnit4中包含了几个比较重要的注解：@BeforeClass、@AfterClass、@Before、@After和@Test。
 * 其中， @BeforeClass和@AfterClass在每个类加载的开始和结束时运行，必须为静态方法；而@Before和@After则在每个测试方法开始之前和结束之后运行。见如下例子：
 *
 *
 * 2.Assert
 * 下面代码中，我们使用了Assert类提供的assert口方法，下面列出了一些常用的assert方法：
 *
 * assertEquals("message",A,B)，判断A对象和B对象是否相等，这个判断在比较两个对象时调用了equals()方法。
 *
 * assertSame("message",A,B)，判断A对象与B对象是否相同，使用的是==操作符。
 *
 * assertTrue("message",A)，判断A条件是否为真。
 *
 * assertFalse("message",A)，判断A条件是否不为真。
 *
 * assertNotNull("message",A)，判断A对象是否不为null。
 *
 * assertArrayEquals("message",A,B)，判断A数组与B数组是否相等。
 *
 *
 * 3.MockMvc
 * 下文中，对Controller的测试需要用到MockMvc技术。MockMvc，从字面上来看指的是模拟的MVC，即其可以模拟一个MVC环境，向Controller发送请求然后得到响应。
 *
 * 在单元测试中，使用MockMvc前需要进行初始化，如下所示：
 * private MockMvc mockMvc;
 * @Autowired
 * private WebApplicationContext wac;
 * @Before
 * public void setupMockMvc(){
 *     mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
 * }
 *
 *
 * MockMvc模拟MVC请求
 *
 * 模拟一个get请求：
 * mockMvc.perform(MockMvcRequestBuilders.get("/hello?name={name}","mrbird"));
 *
 * 模拟一个post请求：
 * mockMvc.perform(MockMvcRequestBuilders.post("/user/{id}", 1));
 *
 * 模拟文件上传：
 * mockMvc.perform(MockMvcRequestBuilders.fileUpload("/fileupload").file("file", "文件内容".getBytes("utf-8")));
 *
 * 模拟请求参数：
 * // 模拟发送一个message参数，值为hello
 * mockMvc.perform(MockMvcRequestBuilders.get("/hello").param("message", "hello"));
 * // 模拟提交一个checkbox值，name为hobby，值为sleep和eat
 * mockMvc.perform(MockMvcRequestBuilders.get("/saveHobby").param("hobby", "sleep", "eat"));
 *
 * 也可以直接使用MultiValueMap构建参数：
 * MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
 * params.add("name", "mrbird");
 * params.add("hobby", "sleep");
 * params.add("hobby", "eat");
 * mockMvc.perform(MockMvcRequestBuilders.get("/hobby/save").params(params));
 *
 * 模拟发送JSON参数：
 * String jsonStr = "{\"username\":\"Dopa\",\"passwd\":\"ac3af72d9f95161a502fd326865c2f15\",\"status\":\"1\"}";
 * mockMvc.perform(MockMvcRequestBuilders.post("/user/save").content(jsonStr.getBytes()));
 *
 * 实际测试中，要手动编写这么长的JSON格式字符串很繁琐也很容易出错，可以借助Spring Boot自带的Jackson技术来序列化一个Java对象（可参考Spring Boot中的JSON技术），如下所示：
 * User user = new User();
 * user.setUsername("Dopa");
 * user.setPasswd("ac3af72d9f95161a502fd326865c2f15");
 * user.setStatus("1");
 * String userJson = mapper.writeValueAsString(user);
 * mockMvc.perform(MockMvcRequestBuilders.post("/user/save").content(userJson.getBytes()));
 * 其中，mapper为com.fasterxml.jackson.databind.ObjectMapper对象。
 *
 * 模拟Session和Cookie：
 * mockMvc.perform(MockMvcRequestBuilders.get("/index").sessionAttr(name, value));
 * mockMvc.perform(MockMvcRequestBuilders.get("/index").cookie(new Cookie(name, value)));
 *
 * 设置请求的Content-Type：
 * mockMvc.perform(MockMvcRequestBuilders.get("/index").contentType(MediaType.APPLICATION_JSON_UTF8));
 *
 * 设置返回格式为JSON：
 * mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1).accept(MediaType.APPLICATION_JSON));
 *
 * 模拟HTTP请求头：
 * mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1).header(name, values));
 *
 *
 * MockMvc处理返回结果
 *
 * 期望成功调用，即HTTP Status为200：
 * mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1))
 *     .andExpect(MockMvcResultMatchers.status().isOk());
 *
 * 期望返回内容是application/json：
 * mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1))
 *     .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
 *
 * 检查返回JSON数据中某个值的内容：
 * mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1))
 *     .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("mrbird"));
 * 这里使用到了jsonPath，$代表了JSON的根节点。更多关于jsonPath的介绍可参考 https://github.com/json-path/JsonPath。
 *
 * 判断Controller方法是否返回某视图：
 * mockMvc.perform(MockMvcRequestBuilders.post("/index"))
 *     .andExpect(MockMvcResultMatchers.view().name("index.html"));
 *
 * 比较Model：
 * mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1))
 *     .andExpect(MockMvcResultMatchers.model().size(1))
 *     .andExpect(MockMvcResultMatchers.model().attributeExists("password"))
 *     .andExpect(MockMvcResultMatchers.model().attribute("username", "mrbird"));
 *
 * 比较forward或者redirect：
 * mockMvc.perform(MockMvcRequestBuilders.get("/index"))
 *     .andExpect(MockMvcResultMatchers.forwardedUrl("index.html"));
 * // 或者
 * mockMvc.perform(MockMvcRequestBuilders.get("/index"))
 *     .andExpect(MockMvcResultMatchers.redirectedUrl("index.html"));
 *
 * 比较返回内容，使用content()：
 * // 返回内容为hello
 * mockMvc.perform(MockMvcRequestBuilders.get("/index"))
 *     .andExpect(MockMvcResultMatchers.content().string("hello"));
 * // 返回内容是XML，并且与xmlCotent一样
 * mockMvc.perform(MockMvcRequestBuilders.get("/index"))
 *     .andExpect(MockMvcResultMatchers.content().xml(xmlContent));
 * // 返回内容是JSON ，并且与jsonContent一样
 * mockMvc.perform(MockMvcRequestBuilders.get("/index"))
 *     .andExpect(MockMvcResultMatchers.content().json(jsonContent));
 *
 * 输出响应结果：
 * mockMvc.perform(MockMvcRequestBuilders.get("/index"))
 *     .andDo(MockMvcResultHandlers.print());
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTest {

    @BeforeClass
    public static void beforeClassTest() {
        System.out.println("before class test");
    }

    @Before
    public void beforeTest() {
        System.out.println("before test");
    }

    @Test
    public void Test1() {
        System.out.println("test 1+1=2");
        Assert.assertEquals(2, 1 + 1);
    }

    @Test
    public void Test2() {
        System.out.println("test 2+2=4");
        Assert.assertEquals(4, 2 + 2);
    }

    @After
    public void afterTest() {
        System.out.println("after test");
    }

    @AfterClass
    public static void afterClassTest() {
        System.out.println("after class test");
    }

}
