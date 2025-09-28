package com.josh.store.dtos;

import lombok.Data;

@Data

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
