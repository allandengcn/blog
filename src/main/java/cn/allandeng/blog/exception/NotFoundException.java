package cn.allandeng.blog.exception;
/**
 * @Auther: Allan
 * @Date: 2020/2/4 17:20
 * @Description:
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ClassName NotFoundException
 * @Date:2020/2/4 17:20
 * @Description: 资源找不到异常
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends  RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
