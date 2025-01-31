package com.raimi_dikamona.visorpost.Controllers.authUtils;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
