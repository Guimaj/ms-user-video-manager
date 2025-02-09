package com.fiap.msuservideomanager.domain.model;

public record Error (
   String timestamp,
   int status,
   String error,
   String path
){
}