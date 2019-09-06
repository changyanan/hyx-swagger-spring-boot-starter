##模板
    spring:
      swagger:
        enabled: false                                  # 是否启用swagger
        group:
          user-api:                                     # 用户组api，可以配置多个组
            group-name: 01.user-api                     # api组的名字，会在swagger-ui的api下拉列表中显示；组名前的序号，多个组可以排序；最好不要写中文
            title: 用户相关的操作                        # api组的标题，会在swagger-ui的标题处显示
            description: 用户相关的操作，包括用户登录登出  # api组的描述，会在swagger-ui的描述中显示
            path-regex: /api/user/.*                    # 匹配到本组的api接口，匹配uri，可以用用正则表达式
            path-mapping: /                             # 匹配到的url在swagger中测试请求时加的url前缀
            version: 0.0.0                              # api版本
            license: 该文档仅限公司内部传阅               # 授权协议
            license-url: '#'                            # 授权协议地址
            terms-of-service-url:                       # 服务条款地址
            contact:                                    # 文档联系人
              name: 张三                                # 联系人名字
              email: zhangsan@team.com                  # 联系人邮箱
              url: http://www.team.com                  # 联系地址 