# 前言
最近总觉得自己所学到的Javascript基础知识不够系统，所以重新学习了Javascript的基本知识并有了这篇文章的产出，这篇文章主要是总结ECMA 262的的语法标准。

这篇文章的主要内容是小结于慕课网的视频课程[《前端JavaScript面试技巧》](https://coding.imooc.com/class/115.html),  如有错误，请多指正，谢谢。

# 1. 变量类型和计算
#### 1.1 按储存方式区分变量类型
- 值类型：字符串（String）、数值（Number）、布尔值（Boolean）、Undefined、Null 
占用空间固定，保存在栈中/保存与复制的是值本身/使用typeof检测数据的类型
- 引用类型：对象（Object）、数组（Array）、函数（Function）
占用空间不固定，保存在堆中/保存与复制的是指向对象的一个指针/使用instanceof检测数据类型/使用new()方法构造出的对象是引用型

另外，typeof运算符结果：undefined，string，number，Boolean，object，function。（typeof只能区分值类型的详细类型， 区分引用类型时只能区分出函数）

#### 1.2 JS中的内置函数
- Object, Array, Boolean, Number, String, Function, Date, RegExp

- 作用： 作为构造函数使用

#### 1.3 JS内置对象
Math, JSON

#### 1.4 如何理解Json
- JSON是一个JS对象,也是 一种数据格式
- 有两个API
JSON.stringify({a:10,b:11})//把对象变成字符串
JSON.parse('{a:10,b:11}')//把字符串变成对象

#### 1.5 强制类型转换
##### 1.5.1 if() {}   if里会转换成false
0: false
NaN: false
'': false
null: false
false: false
undefined: false

##### 1.5.2  === 与==
- “==” 会强制类型转换
- “===” 不会强制类型转换

另外，何时用===，何时用==？ 除了以下这种写法用==，其余全部用===

```
if(obj.a == null){
    //这里相当于obj.a === null || obj.a === undefined 简写形式
    //这是jquery源码中推荐的写法
}
```

#### 1.6 构造函数
##### 1.6.1 构造函数的特点
- 构造函数的首字母必须大写，用来区分于普通函数
- 内部使用的this对象，来指向即将要生成的实例对象
- 使用New来生成实例对象
##### 1.6.2 构造函数的扩展
- var a = {} 其实是var a = new Object()的语法糖
- var a = [] 其实是var a = new Array()的语法糖
- function Foo(){...} 其实是var Foo = new Function(...)
- 使用instanceof判断一个函数是否是一个变量的构造函数， 如判断一个变量是否为“数组”：
  变量 instanceof Array
  
## 2. 原型和原理链
#### 2.1原型
##### 2.1.1 5条原型规则（是学习原型链的基础）
- 所有的引用类型（数组，对象，函数），都具有对象特性，即可自由扩展属性（除“null”以外）。
- 所有的引用类型，都有一个_proto_属性（隐式原型属性），属性值是一个普通的对象。
- 所有的函数，都有一个prototype属性（显式原型），属性值是一个普通的对象。
- 所有的引用类型，_proto_属性值指向它的构造函数的“prototype”属性值。（两者完全相等）
- 当试图得到一个对象的某个属性时，如果这个对象本身没有这个属性，那么会去它的_proto_（即它的构造函数的prototype）中寻找。
##### 2.1.2 补充
- this：实例调用构造函数的方法时，this永远指向实例自身
- 循环对象自身的属性： for in
```
var item
for(item in f){
    //高级浏览器已经在for in中屏蔽了来自原型的属性
    //但是这里建议大家还是加上这个判断，保证程序的健壮性
    if(f,hasOwnProperty(item)){
        console.log(item);
    }
}
```
#### 2.2原型链
- 原型链继承的例子（之后补充）
##### instanceof
f instanceof Foo的判断逻辑是：
 	1. f的\_\_proto\_\_一层一层往上，能否对应到Foo.prototype
 	2. 再试着判断f instanceof Object

#### 2.3 New一个对象的过程
- 创建一个新对象
- this指向这个新对象
- 执行代码，即对this赋值
- 返回this

# 3. 闭包和作用域

#### 3.1 作用域
##### 3.1.1 执行上下文

- 执行上下文的分类

1. 全局：变量定义，函数声明（一段<script>）
2. 函数：变量定义、函数声明、this、arguments (函数中)
   另外，  ”函数声明“与“函数表达式”的区别：比如函数声明是functon fn(){ }， 函数表达式是var fn = function(){}; 
```
fn()//不会报错
function fn(){
    //函数声明, 全局上下文提取到fn()函数
}

fn1()//会报错
var fn1 = function(){
    //函数表达式，全局上下文会提取到fn1为undefined
}


fn('zhangsan')
function fn(name){
    //函数
    console.log(this);
    console.log(arguments);
}
```

- 变量提升
执行代码块的时候，会将申明变量、函数、argument等提前提取出来，然后再执行代码， 即变量提前，函数声明提前

- this不同的使用场景
  this要在执行时才能确认值，定义时无法确认

  - 作为构造函数执行 
  - 作为对象属性执行
  - 作为普通函数执行
  - call apply bind函数

  ```javascript
  var a = {
      name:'A',
      fn: function(){
          console.log(this.name);
          console.log(this )
      }
  }
  a.fn(); // this === a 
  a.fn.call({name:'B'}) // this === {name:'B'}
  var fn1 = a.fn; 
  fn1(); // this === window
  
  ```
#### 3.2 作用域概念
- 作用域
全局作用域，函数作用域，块级作用域（ES6: let, const）

- 作用域链：
自由变量：当前作用域没有定义的变量

#### 3.2 闭包
- 定义： 闭包是一个函数和函数所声明的词法环境的结合。
- 应用场景：模块化、封装。
- 优点：封装性强，使得变量始终保持在内存中。
- 缺点：内存的消耗导致的性能问题。
- 闭包创建：函数嵌套函数，使得内部函数返回出去，让外部来访问内部的变量。
- 闭包的使用场景
  1.函数作为一个返回值
  2.函数作为参数传递。
- 闭包的例子：需注意， 一个函数的父级作用域是在它定义的时候的作用域，而非它执行时候的作用域。
```javascript
function F1(){
    var a = 100;
    return function(){
        console.log(a) //100
    }
}

var f1 = F1();
var a = 200
f1()
```

# 4. 异步和单线程
#### 4.1 异步
- 异步定义
同步会阻塞后续程序代码的执行，而异步不会阻塞程序的运行。
- 何时需要异步：
1. 在可能发生等待的情况：在等待的过程中程序仍然要执行其他操作。
2. 等待过程中不能像 alert 一样阻塞程序运行。
- 前端使用异步的场景：
1. 定时任务：setTimeout，setInterval
2. 网络请求：ajax 请求，动态 <img> 加载、脚本等文件下载和加载。
3. 事件绑定

#### 4.2 单线程
由于js是单线程，在代码执行的时候又不能因为执行需要等待的代码而造成阻塞，因此js会首先将无需等待的（同步）的代码执行完成后，来处理异步的代码，如果达到异步代码的执行条件的话，就会执行。

- setTimeout的例子
```javascript
console.log(1)
setTimeout(function(){//因为setTimeout是异步，所以执行时会被暂存起来
    console.log(2)
}, 0)
console.log(3)
setTimeout(function(){
    console.log((4))
},1000)
console.log(5)

//打印顺序 1 3 5 2 4
```

# 5. 其他知识点-日期和Math，数组API，对象API
#### 5.1 日期常用的API
```javascript
Date.now() //获取当前时间毫秒数
var dt = new Date();
dt.getTime()  //获取毫秒数
dt.getFullYear() //年
dt.getMonth()  //月(0-11)
dt.getDate()  //日(0-31)
dt.getHours() //小时
dt.getMinutes() //分钟(0-59)
dt.getSeconds() //秒(0-59)
```
#### 5.2 Math常用的API
```javascript
Math.random()  //(0-1)
//常见的用法：清除缓存，比如说网站访问链接，同一个链接会使同一个缓存，链接后面加上一个随机数，就可以清楚缓存
```
#### 5.3数组API

1. forEach 遍历所有元素
2. every 判断所有元素是否都符合条件
3. some 判断是否有至少一个元素符合条件
4. sort 排序
5. map 对元素重新组装，生成新数组
6. filter 过滤符合条件的元素

#### 5.4 对象API（for in）

```javascript
var arr = [1, 2, 3]
arr.forEach(function(item, index){
    console.log(index, item)
})

// 0 1
// 1 2
// 2 3

var arr = [1, 2, 3]
var result = arr.every(function(item, index){
    if(item<4){
        return true;
    }
})
console.log(result) // true

var arr = [1, 2, 3]
var result2 = arr.some(function(item, index){
    if(item<2){
        return true
    }
})
console.log(result2) //true

var arr = [5,4,3,2,1]
var arr2 = arr.sort(function(a,b){
    //从小到大排序
    console.log(a+'..'+b)
    return a-b
    //从大到小排序
    // return b-a
})
console.log(arr2)

var arr = [1,2,3,4]
var arr2 = arr.map(function(item,index){
    //将元素重新组装，并返回
    return '<b>'+item+'</b>'
})
console.log(arr2)

var arr = [1,2,3]
var arr2 = arr.filter(function(item,index){
    //通过某一个条件过滤
    if(item >= 2){
        return true
    }
})
console.log(arr2)

//对象代码的例子
var obj={
    x:100,
    y:200,
    z:300
}
var key
for(key in obj){
    //hasOwnProperty判断是不是obj原生的属性
    if(obj.hasOwnProperty(key)){
        console.log(key,obj[key])
    }
}
```

