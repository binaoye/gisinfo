## 基于spring boot的soap中间件开发说明

### 服务端编写
1. 导入xsd, 新建文件夹用于存放生成的代码. xsd 用于描述所有接口, 相当于接口文档.
类似于去年以前在机器学习项目里, 通过把每个特征的处理方法写到xml中, 再读取xml去进行预处理工作.
2. 编写endpoint, 绑定TargetNameSpace, 编写业务流程.

### 客户端编写
1. 从wsdl文件中截取<wsdl: types>选项卡内容, 在resources目录中新建一个xsd文件, 把截取的内容粘贴进去
2. 用上一步生成的xsd文件生成代码.
3. 对应每一个接口, 编写对应的rest风格接口.

### 其他
其实有了各部分服务的wsdl文件, 比看接口文档简单多了.

---
## 部署说明
### 环境说明
1. 服务器环境  适用于windows server 2008及以上版本.
2. 开发环境  jdk 1.8.0_61, 经纬度解析可能需要mysql 5.6, IDE jetbrain idea 2017.3.8
3. 使用软件包 详情见pom.xml

### rest
https://www.cnblogs.com/paddix/p/8215245.html
