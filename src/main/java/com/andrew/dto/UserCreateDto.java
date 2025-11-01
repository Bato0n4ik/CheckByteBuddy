package com.andrew.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public record UserCreateDto (String name, String password, LocalDate birthday){}
