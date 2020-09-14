package com.ztman.admin.controller;

import com.ztman.admin.api.dto.UserDTO;
import com.ztman.admin.service.SysUserService;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
public class AccountController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送验证码
     *
     * @param encrypted
     * @return
     */
    @GetMapping("/sendAuthCode")
    public R<Boolean> sendAuthCode(@RequestParam(required = true) String encrypted) {
        R<Boolean> booleanR = userService.sendAuthCode(encrypted);
        return booleanR;
    }

    /**
     * 对比验证码
     *
     * @param authCode
     * @param encrypted
     * @return
     */
    @GetMapping("/contrastAuthCode")
    public R<Boolean> contrastAuthCode(@RequestParam(required = true) String authCode,
                                       @RequestParam(required = true) String encrypted) {
        R<Boolean> booleanR = userService.contrastAuthCode(authCode, encrypted);
        return booleanR;
    }

    /**
     * 修改密码
     *
     * @param userDto
     * @return
     */
    @PutMapping("/editPassword")
    public R<Boolean> editPassword(@RequestBody UserDTO userDto) {
        return userService.updatePassword(userDto);
    }
}
