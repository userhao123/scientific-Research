package com.hao.scientificresearch.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPwdParam {

    private String oldPassword,newPassword,againPassword;
}
