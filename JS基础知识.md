#### 变量类型
typeof运算符结果：undefined，string，number，Boolean，object，function。（typeof只能区分值类型的详细类型， 区分引用类型时只能区分出函数）

###### 按储存方式区分变量类型
- 值类型：字符串（String）、数值（Number）、布尔值（Boolean）、Undefined、Null 
占用空间固定，保存在栈中/保存与复制的是值本身/使用typeof检测数据的类型
- 引用类型：对象（Object）、数组（Array）、函数（Function）
占用空间不固定，保存在堆中/保存与复制的是指向对象的一个指针/使用instanceof检测数据类型/使用new()方法构造出的对象是引用型

#### JS中的内置函数
Object, Array, Boolean, Number, String, Function, Date, RegExp
- 作用： 作为构造函数使用

#### JS内置对象
Math, JSON

#### === 与==
“==” 会强制类型转换
“===” 不会强制类型转换

？何时用===，何时用==
除了以下这种写法用==，其余全部用===
```
if(obj.a == null){
    //这里相当于obj.a === null || obj.a === undefined 简写形式
    //这是jquery源码中推荐的写法
}
```
#### 如何理解Json
- JSON是一个JS对象,也是 一种数据格式
- 有两个API
JSON.stringify({a:10,b:11})//把对象变成字符串
JSON.parse('{a:10,b:11}')//把字符串变成对象

#### 强制类型转换
if() {}   if里会转换成false
0: false
NaN: false
'': false
null: false
false: false
undefined: false

#### 构造函数
- 构造函数的特点：
  1. 构造函数的首字母必须大写，用来区分于普通函数
  2. 内部使用的this对象，来指向即将要生成的实例对象
  3. 使用New来生成实例对象
- 构造函数的扩展
  1. var a = {} 其实是var a = new Object()的语法糖
  2. var a = [] 其实是var a = new Array()的语法糖
  3. function Foo(){...} 其实是var Foo = new Function(...)
  4. 使用instanceof判断一个函数是否是一个变量的构造函数， 如判断一个变量是否为“数组”：
  变量 instanceof Array
  
#### 原型和原理链
###### 5条原型规则（是学习原型链的基础）
- 所有的引用类型（数组，对象，函数），都具有对象特性，即可自由扩展属性（除“null”以外）。
- 所有的引用类型，都有一个_proto_属性（隐式原型属性），属性值是一个普通的对象。
- 所有的函数，都有一个prototype属性（显式原型），属性值是一个普通的对象。
- 所有的引用类型，_proto_属性值指向它的构造函数的“prototype”属性值。（两者完全相等）
- 当试图得到一个对象的某个属性时，如果这个对象本身没有这个属性，那么会去它的_proto_（即它的构造函数的prototype）中寻找。
##### 补充
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
##### 原型链
- 写一个原型链继承的例子（写更贴近于实战的例子）
##### instanceof
f instanceof Foo的判断逻辑是：
 	1. f的\_\_proto\_\_一层一层往上，能否对应到Foo.prototype
 	2. 再试着判断f instanceof Object

##### New一个对象的过程
- 创建一个新对象
- this指向这个新对象
- 执行代码，即对this赋值
- 返回this

#### 作用域
变量提升：
this不同的使用场景
理解作用域：


#### 闭包