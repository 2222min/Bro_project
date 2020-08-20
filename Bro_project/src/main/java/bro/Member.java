package bro;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class Member {
    @NotNull
    private String id;
    @NotNull
    @Size(min=8,message = "8자리 채우세요.")
    private String pw;
}
