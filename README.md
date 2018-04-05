# qrcode-login

## 操作流程

- 启动项目，电脑使用 IP 地址访问 `/user/login` ，例如 `http://192.168.31.254:8082/user/login`

- 手机同样访问 `/user/login` ，然后登录账号，账号可在项目的 `classpath:sql/test-data.sql` 文件中查看

- 手机端保持登录状态，电脑端在登录页点击二维码图标，出现二维码

- 手机扫码获取二维码内容，是一个网址，访问此网址进入授权登录页

- 手机端在授权登录页点击 `允许登录` 按钮，可看见电脑端跳转进入用户主页，扫码登录成功

- 访问 `/user/logout` 可退出登录

## 实现原理

- 后端将用户 id 放入 `HttpSession` 中作为登录状态凭证，有 id 即为登录状态

- 电脑端选择二维码登录，建立 `WebSocket` 连接，并通过握手请求拦截器将 `HttpSession` 绑定到 `WebSocketSession` 中，然后向前端发送 `websocketSessionId` ，放入二维码中作为参数

- 手机扫描二维码，访问带 `websocketSessionId` 参数的连接，后台得到 `websocketSessionId` 后从容器中获取对应的 `WebSocketSession` ，并从 `WebSocketSession` 中获取绑定的电脑端 `HttpSession`

- 取出手机端 `HttpSession` 中的用户 id 放入电脑端 `HttpSession` ，然后通过 `WebSocketSession` 通知电脑端跳转至用户主页

- 电脑端 `HttpSession` 中有用户 id ，所以能访问用户主页，至此扫码登录流程结束
