package com.stitches.constants;

import org.springframework.beans.factory.annotation.Value;

public class Constant {

    @Value("${application.file_transfer_schedule}")
    public static Long file_transfer_schedule;
}
