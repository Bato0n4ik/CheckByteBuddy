package com.andrew.dto;

import java.time.LocalDate;

public record UserReadDto (Long id, String name, String password, LocalDate birthday){
}
