
#### 目前的方式是这样:

  ● 网关部门定时扫描各个服务,进行监控状态检查,并通知给指定负责人.
  
  
#### 实现逻辑:
  ● 1. 要求每个线上服务暴露一个/status 地址,并返回指定json,将项目中依赖的数据库连接信息返回
  
#### 实现要求:
  ● 1. 组件式开发
  ● 2. 引入即可用,自动监控项目中数据库连接
  ● 3. 暴露一个指定的可供网关扫描的地址
  
#### 技术实现方式:
  ● 1. 引入spring-boot-autoconfigure 自动配置
  ● 2. 引入spring-boot-actuator  使用EndPoint 定制暴露给网关的扫描地址


#### 扩展演示

```java
  public class OtoSaasApplication{
    public static void main(String[] args) {
         ConfigurableApplicationContext applicationContext = SpringApplication.run(OtoSaasApplication.class, args);
                SaasGateWayTools.addCheckChain(new AbstractCheckChain
                        (){
                    @Override
                    public void Check(Map<String, Object> map) {
                        map.put("customerInfo","customerInfo");
                        map.put("customerInfo2","customerInfo2");
                    }
                }); 
      }
  }

```

```json
//liuxin@MacBook-Pro  ~  http get http://127.0.0.1:32028/blm_status
//HTTP/1.1 200
//Content-Type: application/vnd.spring-boot.actuator.v1+json;charset=UTF-8
//Date: Tue, 30 Jan 2018 09:04:50 GMT
//Transfer-Encoding: chunked
//X-Application-Context: oto_saas_bank_pay:dev:32028

{
    "active": "dev",
    "customerInfo": "customerInfo",
    "customerInfo2": "customerInfo2",
    "mongo": "online",
    "name": "oto_saas_bank_pay",
    "port": "192.168.18.135:32028",
    "rabbit": "fail",
    "redis": "fail",
    "time": "2018-01-30 17:04:50"
}
```