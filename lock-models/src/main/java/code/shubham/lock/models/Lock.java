package code.shubham.lock.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lock {
    private String name;
    private String owner;
    private Long expiryAt;

    @Builder.Default
    private Integer version = 0;
}
