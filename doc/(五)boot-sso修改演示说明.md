# 支持类型
- 服务端session管理方式，支持local和redis两种(二选一)
- 注解@EnableSSOLocal - 支持local
- 注解@EnableSSORedis - 支持redis
- 在启动类上使用@EnableSSOLocal或@EnableSSORedis(二选一)

# 依赖
- 服务端使用redis方式来实现管理rsession，需要引入以下依赖
```
<!-- redis支持 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```
# 项目结构
```
smart-sso
├── smart-sso-client -- local支持/分布式redis支持-客户端依赖包
├── smart-sso-demo -- 客户端
├── smart-sso-server -- 服务端
```
# nginx配置
```
upstream demoServers {
    server localhost:9092;
    server localhost:9093;
}

server {
    listen       80;
    server_name  demo.smart-sso.com;

    location / {
        proxy_pass http://demoServers;
        proxy_set_header Host $host;
        proxy_set_header X-real-ip $remote_addr;
    }
}

upstream serverServers {
    server localhost:9090;
    server localhost:9091;
}

server {
    listen       80;
    server_name  server.smart-sso.com;

    location / {
        proxy_pass http://serverServers;
        proxy_set_header Host $host;
        proxy_set_header X-real-ip $remote_addr;
    }
}
```
- local方式，smart-sso-server和smart-sso-demo每个项目只能启一个服务
- redis方式，smart-sso-server和smart-sso-demo可以启动多个服务，一个域名转发多个端口