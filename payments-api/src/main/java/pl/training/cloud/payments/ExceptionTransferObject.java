package pl.training.cloud.payments;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ExceptionTransferObject {

    @NonNull
    private String description;
    @NonNull
    private LocalDateTime timestamp;

}
