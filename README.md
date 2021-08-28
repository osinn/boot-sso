# 简介
- boot-sso 基于 smart-sso 将local、redis整合到一个模块中，改成starter自动化配置

## 简述
    smart-sso使用当下最流行的SpringBoot技术，基于OAuth2认证授权协议，为您构建一个易理解、高可用、高扩展性的分布式单点登录应用基层。

## 相关文档
- [smart-sso单点登录（一）：简介](./doc/(一)简介.md)
- [smart-sso单点登录（二）：部署文档](./doc/(二)部署文档.md)
- [smart-sso单点登录（三）：App登录支持](./doc/(三)App登录支持.md)
- [smart-sso单点登录（四）：引入redis支持分布式](./doc/(四)引入redis支持分布式.md)
- [smart-sso单点登录（五）：boot-sso修改演示说明](./doc/(五)boot-sso修改演示说明.md)

## 组织结构

```lua
smart-sso
├── smart-sso-client -- 客户端依赖包
├── smart-sso-client-redis -- 客户端依赖包，分布式redis支持
├── smart-sso-demo -- 客户端
├── smart-sso-server -- 服务端
```

## 单点登录原理
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201118170252707.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)


## 单点退出原理
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201118165835197.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)


## 效果展示
### 单点登录页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030163204421.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)

### 服务端登录成功页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030163112313.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)

### 客户端登录成功页
![](https://img-blog.csdnimg.cn/20201020163349855.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)