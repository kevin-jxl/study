# 本地启动
127.0.0.1 server0
127.0.0.1 server1
127.0.0.1 server2
127.0.0.1 spark-gateway
192.168.10.121 spark-mysql
127.0.0.1 spark-redis
需启动 eureka config auth gateway admin 

# 服务器启动
192.168.10.121 server0
192.168.10.121 server1
192.168.10.121 server2
192.168.10.121 spark-gateway
192.168.10.121 spark-mysql
192.168.10.121 spark-redis
多人需更换bootstap.yml中的端口号
只启动自己需要启动的项目

#maven
setting.xml  servers增加 
  ```
    <server>
        <id>releases</id>
        <username>admin</username>
        <password>admin123</password>
    </server>
    
    <server>
        <id>snapshots</id>
        <username>admin</username>
        <password>admin123</password>
    </server>
```
setting.xml profiles增加 
```$xslt
    <repositories>
        <!-- 配置nexus远程仓库 -->
        <repository>
            <id>nexus</id>
            <name>Nexus Snapshot Repository</name>
            <url>http://192.168.10.121:8081/repository/maven-public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```