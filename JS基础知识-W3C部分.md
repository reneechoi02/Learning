# 前言
这是JS基本知识的第二篇，这篇小结主要是针对W3C标准展开。以下是前一篇小结和这篇的范围：
- JS 基础知识：ECMA 262标准：变量类型、原型、作用域、异步
- JS-Web-API: W3C标准： 只管定义用于浏览器中JS操作页面的API和全局变量， 如JS内置的全局函数和对象window, document, navigator。

这篇文章的主要内容是小结于慕课网的视频课程[《前端JavaScript面试技巧》](https://coding.imooc.com/class/115.html),  如有错误，请多指正，谢谢。

# 1. DOM操作
### 1.1 DOM概念
#### 1.1.1 DOM概念
Document Object Model, 文档对象模型。是一套操作HTML和XML文档的API。
#### 1.1.2 DOM本质
- 浏览器拿到html代码后，DOM把html代码结构话浏览器可识别以及JS可识别的东西。
- HTML代码就是一个字符串，但是浏览器已经把字符串结构话树形结构了。
### 1.2 DOM节点操作
- 获取DOM节点： `document,getElementById()`, `document.getElementByTagName`, `docuement.getElementsByClassName()`, `document.querySelectorAll()`
- DOM结构操作
   1. 新增节点 `document.createElement(node)`
   2. 获取父元素 `childNode.parentElement`
   3. 获取子元素 `parentNode.childNodes`
   4. 删除节点 `parentNode.removeChild(childNode)`
### 1.3 property与Attribute的区别
- property: nodeName, 对象的属性，修改的是html标签
- Attribute: `getAttribute()`, `setAttribute()`, 文档的属性，修改的是标签里面的属性

# 2. BOM操作
### 1.1 BOM理解
Browser Object Model， 浏览器对象模型。浏览器对象模型提供了独立于内容的、可以与浏览器窗口进行互动的对象结构。BOM由多个对象组成，其中代表浏览器窗口的Window对象是BOM的顶层对象，其他对象都是该对象的子对象。
### 1.2 四个比较重要的对象
navigator、screen、location、history， 以下是常用的API：
```javascript
  //navigator
  var ua = navigator.userAgent
  var isChrome = ua.indexOf("Chrome")
  console.log(isChrome)

  //screen
  console.log(screen.width)
  console.log(screen.height)

  //localtion
  console.log(location.herf)
  console.log(localtion.protocol)
  console.log(location.pathname)
  console.log(location.search)
  console.log(location.hash)

  //history
  history.back()
  history.forward()
```

# 3. 事件绑定
### 3.1 事件绑定之事件监听
事件绑定指的是事件处理程序。事件绑定一共有三种方法：行内绑定（嵌入DOM的onclick），动态监听（绑定DOM元素`document.getElementById('btn').onclick`），事件监听， 以下是事件监听的事例（通用的事件监听函数）
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <button id="btn1">click</button>
    <a id="link1" herf="baidu.com">link1</button>
</body>
<script type="text/javascript">
//方法一
    var btn = document.getElementById('btn1');
btn.addEventListener('click',function(event){
    console.log('clicked');
}) 

//方法二（封装addEventListener）
function bindEvent(elem, type, fn){
    elem.addEventListener(type,fn)
}
var a = document.getElementById('link1')
bindEvent(a,'click',function(e){
    e.preventDefault()//阻止默认行为
    alert('clicked')
})
</script>
</html>
```
### 3.2 事件冒泡流程
#### 3.2.1 流程的理解
DOM元素自身事件被触发后，会根据**DOM树形结构**一层一层地往父元素来冒泡，如果有相同的事情，就会被触发。另外，在冒泡过程中，还可以**阻止冒泡**。
#### 3.2.2 事件冒泡的例子
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
   <div id="div1">
       <p id="p1">激活</p>
       <p id="p2">取消</p>
       <p id="p3">取消</p>
       <p id="p4">取消</p>
   </div>
   <div id="div2">
       <p id="p5">取消</p>
       <p id="p6">取消</p>
   </div>
</body>
<script type="text/javascript">
function bindEvent(elem, type, fn){
    elem.addEventListener(type, fn);
}

var p1 = document.getElementById('p1')
var body = document.body
bindEvent(p1, 'click', function(e){ 
    e.stopPropagation();//阻止冒泡
    alert('激活')  
})
bindEvent(body, 'click', function(e){
    alert('取消')
})
</script>
</html>
```

- 解析：点击p1时，触发了p1的事件，理论上会冒泡去触发body绑定的事件，但使用了e.stopPropagation()阻止了事件冒泡，所以不会触发body绑定的事件

#### 3.3 代理
- 理解：事件代理， 也叫事件委托，就是利用事件冒泡，只制定一个时间处理程序，就可以管理某一类型的所有事件。
- 应用：实现无限下拉加载图片的页面
- 好处：代码简洁，浏览器压力小
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
   <div id="div1">
       <a href="">a1</a>
       <a href="">a2</a>
       <a href="">a3</a>
       <a href="">a4</a>
   </div>
</body>
<script type="text/javascript">
var div1 = document.getElementById('div1')
div1.addEventListener('click',function(e){
    var target = e.target;
    if(target.nodeName === 'A'){
        alert(target.innerHTML)
    }
})
</script>
</html>
```
另外，IE低版本的兼容性（了解即可，无需深究）：IE低版本使用attachEvent绑定事件，和W3C标准不一样

### 3.4 最后编写的一个通用绑定事件的函数
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
   <div id="div1">
       <a href="">a1</a>
       <a href="">a2</a>
       <a href="">a3</a>
       <a href="">a4</a>
   </div>
</body>
<script type="text/javascript">
function bindEvent(elem, type, selector, fn){
    if(fn == null){
        fn = selector
        selector = null
    }
    elem.addEventListenr(type, function(e){
        var target
        if(selector){
            target = e.target
            if(target.matches(selector)){
                fn.call(target, e)
            }
        }else{
            fn(e)
        }
    })
}

//使用代理
var div1 = document.getElementById('div1')
bindEvent(div1, 'click', 'a', function(e){
    console.log(this.innerHTML)
})
//不使用代理
var a = document.getElementById('a1')
bindEvent(div1, 'click', function(e){
    console.log(a.innerHTML)
})
</script>
</html>
```

# 4. ajax请求（http协议）

### 4.1 Ajax原生写法: XMLHttpRequest
```javascript
var xhr = new XMLHttpRequest()
xhr.open("Get", "/api", false)
xhr.onreadystatechange = function(){
    //这里的函数是异步执行的
    if(xhr.readyState == 4){
        if(xhr.status == 200){
            alert(xhr.responseText)
        }
    }
}
xhr.send(null)
```
另外，IE低版本使用activeXObject

### 4.2 状态码说明
**readState**
1. 0 - (未初始化) 还没有调用send()方法
2. 1 - (载入) 已调用send()方法，正在发送请求
3. 2 - (载入完成) send()方法执行完成，已经接收到全部响应内容
4. 3 - (交互) 正在解析相应内容
5. 4 - (完成) 相应内容解析完成，可以在客户端调用了
**status**
1. 2xx - 表示成功处理请求，如200
2. 3xx - 需要重定向，浏览器直接跳转
3. 4xx - 客户端请求错误，如404
4. 5xx - 客户端错误

### 4.3  跨域
**定义**
浏览器有同源策略，不充许ajax访问其他域接口。 协议， 域名，端口，有一个不同就算跨域
可以跨域的三个标签
1. ```<img src=xxx>```  用于打点统计，统计网站可能是其他域
2. ```<ink href=xxx> ```  可以使用CDN， CDN的也是其他域
3. ```<script src=xxx>```  可以用域JSONP
服务器端设置http header : 通过response.setHeader()

### 4.4 JSONP
**实现原理**: 浏览器有同源策略，不允许ajax访问其他域的接口，有三个标签 img, link, script允许跨域，所以可以在自己的浏览器端定义一个函数A（如下面的callback函数），用script src请求一个外域的API， API返回的是一个执行函数A的片段（callback函数的执行），通过这种形式可以请求其他域的数据。

```javascript
<script type="text/javascript">
window.callback = function(data){
    //这是跨域得到的信息
    console.log(data)
}
</script>
<script src="http://coding.m.imooc.com/api.js"></script>
<!-- 以上将返回callback({x:100, y:200}) -->
```

# 5. 存储
### 5.1 cookie
- 定义
本身用域客户端和服务器端通信， 有本地存储的功能，于是被“借用”， 使用document.cookie=获取和修改
- 缺点
存储量太小，只有4KB
所有http请求都带着，会影响获取资源的效率
API简单，需要封装才能用document.cookie=...

### 5.2 sessionStorage和localStorage
HTML5专门为存储而设计，最大容量5M
API简单易用：localStorage.setItem(key, value), localStorage.getItem(key, value)
IOS safari隐藏模式下，localStorage.getItem会报错，建议统一用try-catch封装

### cookie, sessionStorage, localStorage的区别
容量，是否携带ajax中，API易容性。