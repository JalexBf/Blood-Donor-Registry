package gr.hua.dit.ds.BloodRegistry.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponse {

    private Long roleId;
    private String username;

    private Long userId;

    private String token;
}
