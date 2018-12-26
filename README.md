# auto-table2bean
视频地址: http://www.fei-kuai.com <br>
自动把数据库表转换为java bean <br>

自动把数据库表生成 java bean 代码 <br>

代码实现步骤： <br>
  1、查询当前数据库所有的表信息
  2、查询每张表的列信息
  3、把表明和类别转换为对于的java类名和属性名以及对应的类型
  4、设置模板文件Bean.java.tpl
  5、使用Velocity模板引擎渲染模板
  6、使用ZipEntry生成java文件并且添加到ZipOutputStream 输出流中
  7、下载生成的源码文件
