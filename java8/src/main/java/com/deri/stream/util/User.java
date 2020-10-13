package com.deri.stream.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: User
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/4/13 15:37
 * @Version: v1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String password;
    private String gender;
    private String age;
    private String phone;
    private String email;
}
