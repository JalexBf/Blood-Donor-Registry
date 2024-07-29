package gr.hua.dit.ds.BloodRegistry.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequest {

    private String username;

    private String password;
}
