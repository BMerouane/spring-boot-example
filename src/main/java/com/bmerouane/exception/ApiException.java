package com.bmerouane.exception;

import java.time.ZonedDateTime;

public record ApiException(String message, Throwable throwable, ZonedDateTime zonedDateTime) {
}
