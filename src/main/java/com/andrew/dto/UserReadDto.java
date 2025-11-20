package com.andrew.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserReadDto (Long id, String name, String password, LocalDate birthday){
}
