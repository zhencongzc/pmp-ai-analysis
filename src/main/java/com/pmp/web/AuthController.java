package com.pmp.web;

import com.pmp.domain.auth.LoginResultDTO;
import com.pmp.infrastructure.pojo.ResponseCode;
import com.pmp.infrastructure.pojo.ResponseResult;
import com.pmp.infrastructure.util.TokenUtils;
import com.pmp.service.auth.AuthService;
import com.pmp.web.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * 授权管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 登录
     *
     * @param loginVO 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseResult<LoginResultDTO> login(@RequestBody LoginVO loginVO) {
        // 参数校验
        if (loginVO.getUsername() == null || loginVO.getPassword() == null) {
            return ResponseResult.error(ResponseCode.PARAMETER_ERROR.getCode(), "用户名或密码不能为空");
        }
        try {
            // 调用应用层服务
            LoginResultDTO result = authService.login(
                    loginVO.getUsername(),
                    loginVO.getPassword()
            );
            // 返回成功结果
            return ResponseResult.success(result);
        } catch (AuthenticationException e) {
            // 认证失败处理
            return ResponseResult.error(ResponseCode.OPERATION_FAILED.getCode(), "用户名或密码错误");
        } catch (Exception e) {
            // 其他异常处理
            return ResponseResult.error(ResponseCode.OPERATION_FAILED.getCode(), "登录失败");
        }
    }

    /**
     * 登出接口
     *
     * @param request HTTP请求对象，用于获取token
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ResponseResult<String> logout(HttpServletRequest request) {
        try {
            // 从请求头中获取token
            String token = TokenUtils.extractAuthorizationHeader(request);

            if (token != null && authService.logout(token)) {
                return ResponseResult.success("登出成功");
            } else {
                return ResponseResult.error(ResponseCode.OPERATION_FAILED.getCode(), "登出失败");
            }
        } catch (Exception e) {
            return ResponseResult.error(ResponseCode.OPERATION_FAILED.getCode(), "登出异常");
        }
    }

}
