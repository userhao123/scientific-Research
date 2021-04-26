package com.hao.scientificresearch.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationParam {
    private String email,title,phone,college,username,major;
}
